package fr.imac.myrddin.physic;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fr.imac.myrddin.MyrddinGame;

public abstract class PhysicActor extends Actor implements Collidable  {
	
	protected Body body;
	/**
	 * offset and size of the collision box in pixel
	 */
	private Rectangle collisionBounds;

	/**
	 * @param bounds set the bounds of the actor in pixel
	 * @param collisionBox offset and size of the collision box in pixel
	 * @param bodyType
	 * @param fixtureDef
	 * @param preventRotation
	 * @param world
	 */
	public PhysicActor(Rectangle bounds, Rectangle collisionBox, BodyType bodyType, FixtureDef fixtureDef, boolean preventRotation, World world) {
		super();		
		if (collisionBox == null)
			throw new IllegalArgumentException("collisionBox must not be null");
		if (bounds == null)
			throw new IllegalArgumentException("bounds must not be null");
		
		this.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		
		Vector2 collisionBoxPos = new Vector2(collisionBox.x, collisionBox.y);
		this.collisionBounds = collisionBox;
		this.body = PhysicUtil.createRect(collisionBoxPos.add(bounds.x, bounds.y).scl(MyrddinGame.GAME_TO_PHYSIC), collisionBox.getWidth() * MyrddinGame.GAME_TO_PHYSIC, collisionBox.getHeight() * MyrddinGame.GAME_TO_PHYSIC, bodyType, fixtureDef, preventRotation, world);
		
		this.body.setUserData(this);
	}
	
	/**
	 * Update the position and angle of the actor to correspond to physic world.
	 */
	@Override
	public void act(float delta) {
		Vector2 position = body.getPosition();
		this.setPosition(position.x * MyrddinGame.PHYSIC_TO_GAME  - collisionBounds.getWidth() / 2f - collisionBounds.x, position.y * MyrddinGame.PHYSIC_TO_GAME - collisionBounds.getHeight() / 2f - collisionBounds.y);
		this.setRotation(body.getAngle());
	}
	
	public void setUserData(Collidable uData) {
		this.body.setUserData(uData);
	}
	
	/**
	 * 
	 * @param bounds set the bounds of the actor in pixel
	 * @param collisionBox offset and size of the collision box in pixel
	 */
	public void setNewRectBox(Rectangle bounds, Rectangle collisionBox) {		

		System.out.println(this.body.getPosition());
		this.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		Vector2 collisionBoxPos = new Vector2(collisionBox.x, collisionBox.y);
		this.collisionBounds = collisionBox;		

		Fixture fixture = this.body.getFixtureList().get(0);
		FixtureDef fixtureDef = PhysicUtil.createFixtureDef(fixture);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(collisionBox.getWidth(), collisionBox.getHeight());
		fixtureDef.shape = shape ;
		shape.setAsBox(collisionBox.width * MyrddinGame.GAME_TO_PHYSIC / 2f, collisionBox.height  * MyrddinGame.GAME_TO_PHYSIC / 2f);
		
		this.body.destroyFixture(fixture);
		this.body.createFixture(fixtureDef);
		System.out.println(this.body.getPosition());
		this.body.setTransform(collisionBoxPos.add(bounds.getX() + collisionBox.width / 2f, bounds.getY() + collisionBox.height / 2f).scl(MyrddinGame.GAME_TO_PHYSIC), body.getAngle());
	}
	
	public void applyImpulse(Vector2 force) {
		this.body.applyLinearImpulse(force, body.getWorldCenter(), true);
	}
	
	public void applyForce(Vector2 force) {
		this.body.applyForceToCenter(force, true);
	}
	
	public float getMass() {
		return body.getMass();
	}
	
	public Vector2 getLinearVelocity() {
		return body.getLinearVelocity();
	}
	
	public void setLinearVelocity(Vector2 v) {
		body.setLinearVelocity(v);
	}	
	
}
