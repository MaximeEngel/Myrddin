package fr.imac.myrddin.game.myrddin;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.GameScreen;
import fr.imac.myrddin.physic.PhysicActor;
import fr.imac.myrddin.physic.PhysicUtil;

public class Shield extends PhysicActor {

	private Myrddin myrddin;
	private RevoluteJoint revoluteJoint;
	
	public Shield() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Shield(Myrddin myrddin) {
		super(	new Rectangle(myrddin.getX(), myrddin.getY(), 20f, 96f), 
				new Rectangle(0, 5, 20f, 86f), 
				BodyType.DynamicBody, 
				PhysicUtil.createFixtureDef(2f, 0f, 0f, false), 
				false
			);		
		
		this.myrddin = myrddin;
		
		this.linkShieldToMyrddin();
		this.enableShield(false);
	}
	
	public void linkShieldToMyrddin() {
		RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
		revoluteJointDef.bodyA = myrddin.getBody();
		revoluteJointDef.bodyB = this.body;
		revoluteJointDef.collideConnected = true;
		
		revoluteJointDef.localAnchorA.set(0f, 0f);
		revoluteJointDef.localAnchorB.set(-1.2f, 0f);
		
		revoluteJointDef.enableMotor = true;
		revoluteJointDef.motorSpeed = 0f;
		revoluteJointDef.maxMotorTorque = 200f;
		
		this.revoluteJoint = (RevoluteJoint) GameScreen.physicWorld.createJoint(revoluteJointDef);		
	}
	
	public void enableShield(boolean enable) {
		this.body.setActive(enable);
	}
	

}
