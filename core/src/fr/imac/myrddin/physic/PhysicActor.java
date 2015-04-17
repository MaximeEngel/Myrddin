package fr.imac.myrddin.physic;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.GameScreen;
import fr.imac.myrddin.physic.Collidable.CollidableType;

public abstract class PhysicActor extends Actor implements Collidable, Externalizable  {
	
	protected Body body;
	
	/**
	 * offset and size of the collision box in pixel
	 */
	private Rectangle collisionBounds;
	
	private FixtureDef newFixture;
	protected Fixture fixtureToDestroy;
	private Vector2 newPos;
	private float newAngle;
	private boolean changing;
	
	/**
	 * @param bounds set the bounds of the actor in pixel
	 * @param collisionBox offset and size of the collision box in pixel
	 * @param bodyType
	 * @param fixtureDef
	 * @param preventRotation
	 * @param world
	 */
	public PhysicActor(Rectangle bounds, Rectangle collisionBox, BodyType bodyType, FixtureDef fixtureDef, boolean preventRotation) {
		super();		
		if (collisionBox == null)
			throw new IllegalArgumentException("collisionBox must not be null");
		if (bounds == null)
			throw new IllegalArgumentException("bounds must not be null");
		
		this.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);		
		this.collisionBounds = collisionBox;
		init(bodyType, fixtureDef, preventRotation);
	}
	
	public PhysicActor() {
		
	}
	
	/**
	 * Bounds and collision bounds must be always setted.
	 * @param bodyType
	 * @param fixtureDef
	 * @param preventRotation
	 */
	private void init(BodyType bodyType, FixtureDef fixtureDef, boolean preventRotation) {
		Vector2 collisionBoxPos = new Vector2(collisionBounds.x, collisionBounds.y);
		this.body = PhysicUtil.createRect(collisionBoxPos.add(getX(), getY()).scl(MyrddinGame.GAME_TO_PHYSIC), collisionBounds.getWidth() * MyrddinGame.GAME_TO_PHYSIC, collisionBounds.getHeight() * MyrddinGame.GAME_TO_PHYSIC, bodyType, fixtureDef, preventRotation, GameScreen.physicWorld);
		
		this.body.setUserData(this);
		
		this.fixtureToDestroy = null;
		this.newFixture = null;
		this.changing = false;
		this.newPos = null;
	}
	
	/**
	 * Update the position and angle of the actor to correspond to physic world.
	 */
	@Override
	public void act(float delta) {
		Vector2 position = body.getPosition();
		this.setPosition(position.x * MyrddinGame.PHYSIC_TO_GAME  - collisionBounds.getWidth() / 2f - collisionBounds.x, position.y * MyrddinGame.PHYSIC_TO_GAME - collisionBounds.getHeight() / 2f - collisionBounds.y);
		this.setRotation(body.getAngle());
		
		switchFixture();
		
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
		this.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		Vector2 collisionBoxPos = new Vector2(collisionBox.x, collisionBox.y);
		this.collisionBounds = collisionBox;		

		Fixture fixture = this.body.getFixtureList().get(0);
		FixtureDef fixtureDef = PhysicUtil.createFixtureDef(fixture);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(collisionBox.getWidth(), collisionBox.getHeight());
		fixtureDef.shape = shape ;
		shape.setAsBox(collisionBox.width * MyrddinGame.GAME_TO_PHYSIC / 2f, collisionBox.height  * MyrddinGame.GAME_TO_PHYSIC / 2f);
		
		fixtureToDestroy = fixture;
		newFixture = fixtureDef;
		newPos = new Vector2(collisionBoxPos.add(bounds.getX() + collisionBox.width / 2f, bounds.getY() + collisionBox.height / 2f).scl(MyrddinGame.GAME_TO_PHYSIC));
		newAngle = body.getAngle();
		changing = true;
	}
	
	public void switchFixture() {
		if (fixtureToDestroy != null) {
			this.body.destroyFixture(fixtureToDestroy);
			this.body.createFixture(newFixture);

			this.body.setTransform(newPos, newAngle);
			newPos = null;
			newAngle = 0;
			newFixture = null;
			fixtureToDestroy = null;
			changing = false;
		}
	}
	
	/**
	 * Destroy body and remove the actor of the parent
	 */
	public void dispose() {
		this.body.getWorld().destroyBody(this.body);
		remove();
	}
	
	public boolean isChanging() {
		return changing;
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
	
	// UTILS METHODES ON ACTOR
	
	public boolean isVisible() {
		Camera camera = getStage().getCamera();
		float halfScreenWidth = MyrddinGame.WIDTH * 0.5f;
		float halfWidth = 0.5f * getWidth();
		
		if ( getX() - halfWidth >= camera.position.x - halfScreenWidth && getX() + halfWidth <= camera.position.x + halfScreenWidth )
			return true;
		
		return false;
	}
	
	
	
	// EXTERNALIZABLE

	@Override
	public CollidableType getCollidableType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beginContact(Contact contact, Collidable other) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endContact(Contact contact, Collidable other) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Collidable other) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, Collidable other) {
		// TODO Auto-generated method stub
		
	}

	public float getCenterX() {
		return getX() + getWidth() * 0.5f;
	}

	public float getCenterY() {
		return getY() + getHeight() * 0.5f;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// ACTOR
		out.writeFloat(getX());
		out.writeFloat(getY());
		out.writeFloat(getWidth());
		out.writeFloat(getHeight());
		out.writeFloat(getRotation());
		
		// PHYSIC
		out.writeObject(collisionBounds);
		// fixture
		Fixture fixture = body.getFixtureList().get(0);
		out.writeFloat(fixture.getDensity());
		out.writeFloat(fixture.getRestitution());
		out.writeFloat(fixture.getFriction());
		out.writeBoolean(fixture.isSensor());
		//other physic
		out.writeObject(body.getType().toString());
		out.writeBoolean(body.isFixedRotation());
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		// ACTOR
		this.setBounds(in.readFloat(), in.readFloat(), in.readFloat(), in.readFloat());
		this.setRotation(in.readFloat());
		
		// PHYSIC
		this.collisionBounds = (Rectangle) in.readObject();
		FixtureDef fixtureDef = PhysicUtil.createFixtureDef(in.readFloat(), in.readFloat(), in.readFloat(), in.readBoolean());
		init(BodyType.valueOf(String.valueOf(in.readObject())), fixtureDef, in.readBoolean());
	}

	public boolean isSavable() {
		return false;
	}	
	
	
	
	
	
}
