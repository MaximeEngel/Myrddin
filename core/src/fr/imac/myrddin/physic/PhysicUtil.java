package fr.imac.myrddin.physic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicUtil {
	
	/**
	 * 
	 * @param width in meter
	 * @param height in meter
	 * @param bodyType
	 * @param pos the bottom left position in meter
	 * @param world
	 * @return
	 */	
	public static Body createRect(Vector2 pos, float width,float height,BodyDef.BodyType bodyType,
	FixtureDef fixtureDef, boolean preventRotation, World world){
		float w= width * 0.5f;
		float h= height * 0.5f;
		pos.add(w, h);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(pos);
		bodyDef.type = bodyType;
		Body body = world.createBody(bodyDef);
		body.setFixedRotation(preventRotation);
		
		PolygonShape bodyShape = new PolygonShape();
		bodyShape.setAsBox(w,h);
		
		fixtureDef.shape=bodyShape;
		 
		body.createFixture(fixtureDef);
		bodyShape.dispose();
		
		return body;
	}
	
	public static Body createRect(Vector2 pos, float width,float height, World world){
		return createRect(pos, width, height, BodyType.StaticBody, createFixtureDef(0, 0, false), false, world);
	}
	
	public static Body createRectSensor(Vector2 pos, float width,float height, World world){
		return createRect(pos, width, height, BodyType.StaticBody, createFixtureDef(0, 0, true), false, world);
	}
	
	public static FixtureDef createFixtureDef(float density,float restitution,boolean isSensor) {
		FixtureDef fixtureDef=new FixtureDef();
		fixtureDef.density=density;
		fixtureDef.restitution=restitution;
		fixtureDef.isSensor = isSensor;
		return fixtureDef;
	}
}
