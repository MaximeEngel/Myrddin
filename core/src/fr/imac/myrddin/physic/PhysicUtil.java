package fr.imac.myrddin.physic;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import fr.imac.myrddin.MyrddinGame;

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
	
	public static Body createBody(Shape shape,BodyDef.BodyType bodyType, boolean preventRotation, FixtureDef fixtureDef, World world) {
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = bodyType;
		Body body = world.createBody(bodyDef);
		body.setFixedRotation(preventRotation);
		
				
		fixtureDef.shape=shape;
		 
		body.createFixture(fixtureDef);
		shape.dispose();
		
		return body;
	}
	
	public static Body createRect(Vector2 pos, float width,float height, World world){
		return createRect(pos, width, height, BodyType.StaticBody, createFixtureDef(0.1f, 0, false), false, world);
	}
	
	public static Body createRectSensor(Vector2 pos, float width,float height, World world){
		return createRect(pos, width, height, BodyType.StaticBody, createFixtureDef(0.1f, 0, true), false, world);
	}
	
	public static FixtureDef createFixtureDef(float density,float restitution,boolean isSensor) {
		return createFixtureDef(density, restitution, 0, isSensor);
	}
	
	public static FixtureDef createFixtureDef(float density,float restitution, float friction, boolean isSensor) {
		FixtureDef fixtureDef=new FixtureDef();
		fixtureDef.density=density;
		fixtureDef.restitution=restitution;
		fixtureDef.isSensor = isSensor;
		fixtureDef.friction = friction;
		return fixtureDef;
	}

	public static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
		 	Rectangle rectangle = rectangleObject.getRectangle();
	        PolygonShape polygon = new PolygonShape();
	        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) * MyrddinGame.GAME_TO_PHYSIC,
	                                   (rectangle.y + rectangle.height * 0.5f ) * MyrddinGame.GAME_TO_PHYSIC);
	        polygon.setAsBox(rectangle.width * 0.5f * MyrddinGame.GAME_TO_PHYSIC,
	                         rectangle.height * 0.5f * MyrddinGame.GAME_TO_PHYSIC,
	                         size,
	                         0.0f);
	        return polygon;
	}

	public static FixtureDef createFixtureDef(Fixture fixture) {
		FixtureDef fixtureDef=new FixtureDef();
		fixtureDef.density=fixture.getDensity();
		fixtureDef.restitution=fixture.getRestitution();
		fixtureDef.isSensor = fixture.isSensor();
		fixtureDef.friction = fixture.getFriction();
		return fixtureDef;
	}
}
