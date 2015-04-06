package fr.imac.myrddin.physic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicUtil {
	
	/**
	 * 
	 * @param width in meter
	 * @param height in meter
	 * @param bodyType
	 * @param density in Kg/m^2
	 * @param restitution ranges from 0.0 to 1.0. 1 being elastic and more than 1 will give really odd results
	 * @param pos in meter
	 * @param isSensor
	 * @param world
	 * @return
	 */
	public static Body createRectangle(Vector2 pos, float width,float height,BodyDef.BodyType bodyType,
	float density,float restitution,boolean isSensor, World world){
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(pos);
		bodyDef.type = bodyType;
		Body body = world.createBody(bodyDef);
		
		PolygonShape bodyShape = new PolygonShape();
		float w= width * 0.5f;
		float h= height * 0.5f;
		bodyShape.setAsBox(w,h);
		
		FixtureDef fixtureDef=new FixtureDef();
		fixtureDef.density=density;
		fixtureDef.restitution=restitution;
		fixtureDef.shape=bodyShape;
		fixtureDef.isSensor = isSensor;
		 
		body.createFixture(fixtureDef);
		bodyShape.dispose();
		
		return body;
	}
}
