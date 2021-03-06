package fr.imac.myrddin.game.myrddin;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.GameScreen;
import fr.imac.myrddin.game.MyrddinUtil;
import fr.imac.myrddin.game.myrddin.MyrddinState.StateType;
import fr.imac.myrddin.physic.Collidable;
import fr.imac.myrddin.physic.PhysicActor;
import fr.imac.myrddin.physic.PhysicUtil;

public class Shield extends PhysicActor implements Externalizable {

	public static final float MOTOR_SPEED = 300f;
	public static final float MAX_ENERGY = 100f;
	
	private Myrddin myrddin;
	private RevoluteJoint revoluteJoint;
	/**
	 * [0 - 100]
	 */
	private float energy;
	private Array<AtlasRegion> regions;
	
	public Shield() {
		super();
		
		init();
	}

	public Shield(Myrddin myrddin) {
		super(	new Rectangle(myrddin.getX(), myrddin.getY(), 25f, 96f), 
				new Rectangle(2, 5, 16f, 86f), 
				BodyType.DynamicBody, 
				PhysicUtil.createFixtureDef(2f, 0f, 0f, false), 
				false
			);		
		this.body.setGravityScale(0);
		this.myrddin = myrddin;
		this.energy = MAX_ENERGY;
		
		this.linkShieldToMyrddin();
		this.enableShield(false);
		
		init();
	}
	
	public void init() {
		TextureAtlas atlas = MyrddinGame.assetManager.get("myrddin/myrddin.atlas", TextureAtlas.class);
		regions = atlas.findRegions("shield");
	}
	
	// ACTOR
	
	@Override
	public void act(float delta) {
			super.act(delta);
			enableShield(canEnableShield());
			if(isEnable()) {
				modifyEnergy(-delta * 5);
				moveShield();
			}
			else
				modifyEnergy(delta * 10);		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(isEnable())
		{
			TextureRegion region = regions.get(MathUtils.random(0, regions.size - 1));
			batch.draw(region, getX(), getY(), 0.5f * getWidth(), 0.5f * getHeight(), getWidth(), getHeight(), 1, 1, getRotation());
			
		}
	}

	/** create a revolute joint between the two bodies.
	 * 
	 */
	public void linkShieldToMyrddin() {
		RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
		revoluteJointDef.bodyA = myrddin.getBody();
		revoluteJointDef.bodyB = this.body;
		revoluteJointDef.collideConnected = true;
		
		revoluteJointDef.localAnchorA.set(0f, 0f);
		revoluteJointDef.localAnchorB.set(-1.2f, 0f);
		
		revoluteJointDef.maxMotorTorque = 300f;
		revoluteJointDef.referenceAngle = 0;
		
		this.revoluteJoint = (RevoluteJoint) GameScreen.physicWorld.createJoint(revoluteJointDef);	
	}
	
	@Override
	public boolean isSavable() {
		return true;
	}
	
	
	
	// SHIELD

	public boolean isEnable() {
		return this.body.isActive();
	}
	
	public boolean canEnableShield() {
		return 	Gdx.input.isButtonPressed(Input.Buttons.RIGHT) 
				&& energy > 0 
				&& myrddin.getMyrddinState().getStateType() != StateType.Dead;
	}
	
	public void enableShield(boolean enable) {
		if(enable && !isEnable() && energy < 5)
			return;
		
		if (isEnable() != enable)
		{
			// Put next to myrddin because myrddin can have move far away the last desactivation position.
			if (enable)
				this.body.setTransform(myrddin.getBody().getPosition().x - 1.2f, myrddin.getBody().getPosition().y, 0);			

			this.body.setActive(enable);			
		}
	}
	
	/**
	 * 
	 * @param value add or substract some energy
	 */
	public void modifyEnergy(float value) {
		this.energy += value;
		if (energy > MAX_ENERGY)
			energy = MAX_ENERGY;
		else if (energy < 0)
			energy = 0;
	}
	
	public float getEnergy() {
		return energy;
	}
	
	// CONTROL THE SHIELD
	
	public void moveShield() {
		// Dont let the shield move with other force because myrddin control it totally
		this.revoluteJoint.setMotorSpeed(0);
		this.body.setAngularDamping(0);
		this.body.setAngularVelocity(0);
		

		float ecartAngle = this.computeDesiredAngle() - this.computeActualAngle();
		if(MathUtils.isZero(ecartAngle, 3f)) {
			this.body.setLinearVelocity(0, 0);
			revoluteJoint.enableMotor(false);
		}
		else {
			revoluteJoint.setMotorSpeed(computeShortestDirection() * MOTOR_SPEED);
			revoluteJoint.enableMotor(true);
		}
	}
	
	public float computeShortestDirection() {
		
		float actualAngle = computeActualAngle();
		float actualAnglePlus180 = (actualAngle + 180) % 360;
		float desiredAngle = computeDesiredAngle();
		
		if (actualAnglePlus180 > actualAngle)
			return desiredAngle >= actualAngle && desiredAngle <= actualAnglePlus180 ? 1 : -1 ;
		else
			return desiredAngle >= actualAngle || desiredAngle <= actualAnglePlus180 ? 1 : -1 ;
	}
	
	public float computeActualAngle() {
		float actualAngle = MyrddinUtil.project(MathUtils.radiansToDegrees * revoluteJoint.getJointAngle() % 180, -180, 180, 0, 360);
		if (actualAngle < 0)
			actualAngle = actualAngle % 360 + 360;
		else if (actualAngle > 360)
			actualAngle = actualAngle % 360 - 360;
		
		return actualAngle;
	}
	
	public float computeDesiredAngle() {
		Vector3 mouseStagePos3D = getStage().getCamera().unproject(new Vector3( Gdx.input.getX(), Gdx.input.getY(), 0));
		Vector2 mouseStagePos = new Vector2(mouseStagePos3D.x, mouseStagePos3D.y);
		Vector2 myrddinPos = new Vector2(myrddin.getCenterX(), myrddin.getCenterY());
		
		return myrddinPos.sub(mouseStagePos).angle();
	}
	
	// COLLISION
	
	

	@Override
	public void preSolve(Contact contact, Collidable other) {
		super.preSolve(contact, other);
		
		
	}
	
	@Override
	public CollidableType getCollidableType() {
		return CollidableType.Shield;
	}

	public boolean ignore(Collidable other) {
		CollidableType type = other.getCollidableType();
		return 	type == CollidableType.Myrddin
				|| type == CollidableType.MagicBullet
				|| type == CollidableType.Climb;
	}
	
	// EXTERNALIZATION

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
		
		out.writeObject(myrddin);
		out.writeFloat(energy);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		super.readExternal(in);
		
		myrddin = (Myrddin) in.readObject();
		energy = in.readFloat();		

		this.body.setGravityScale(0);
		this.linkShieldToMyrddin();
		this.enableShield(false);
	}

}
