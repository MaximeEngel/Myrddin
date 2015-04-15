package fr.imac.myrddin.game;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import fr.imac.myrddin.physic.Collidable;
import fr.imac.myrddin.physic.PhysicActor;

public abstract class Character extends PhysicActor {

	private int life;
	private int maxLife;
	
	public Character(Rectangle bounds, Rectangle collisionBox, BodyType bodyType, FixtureDef fixtureDef, boolean preventRotation, int initialLife) {
		super(bounds, collisionBox, bodyType, fixtureDef, preventRotation);
		this.life = initialLife;
		this.maxLife = initialLife;
	}
	
	public Character() {
		this.maxLife = 3;
		this.life = this.maxLife;
	}
	
	public boolean isSavable() {
		return true;
	}

	public void healedBy(int point) {
		this.life = Math.min(maxLife, life + point);
	}
	
	public void hurtedBy(int point) {
		this.life -= point;
	}

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
	
	// EXTERNALIZABLE

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
		
		out.writeInt(life);
		out.writeInt(maxLife);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		super.readExternal(in);
		
		life = in.readInt();
		maxLife = in.readInt();
	}
	
	
}
