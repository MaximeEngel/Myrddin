package fr.imac.myrddin.game;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.badlogic.gdx.math.Vector2;

import fr.imac.myrddin.game.magic.MagicBullet;
import fr.imac.myrddin.game.magic.MagicState;
import fr.imac.myrddin.physic.PhysicActor;

public class MagicWeapon<T extends PhysicActor & MagicWeaponOwner> implements Externalizable {
	
	private float timeWithoutFire ;
	private float lastFire ;
	private T  owner;		
	
	public MagicWeapon(float timeWithoutFire, T owner) {
		super();
		
		this.timeWithoutFire = timeWithoutFire;
		this.lastFire = 0;
		this.owner = owner;
	}
	
	public MagicWeapon() {
		
	}

	public void act(float delta) {
		this.lastFire += delta;
	}
	
	public void fire(Vector2 targetPos) {
		if(!canFire() || targetPos == null)
			return;		
		
		Vector2 originFire = owner.getWeaponPos();
		if (originFire == null)
			return;
		
		Vector2 directionFire = targetPos.sub(originFire);
		MagicState magicState = owner.getMagicState();
		
		MagicBullet magicBullet = new MagicBullet(originFire, directionFire, magicState, owner);
		owner.getStage().addActor(magicBullet);
		
		this.lastFire = 0f;
	}
	
	public boolean canFire() {
		return  lastFire >= timeWithoutFire;
	}	
	
	// GETTERS - SETTERS 

	public float getTimeWithoutFire() {
		return timeWithoutFire;
	}

	public void setTimeWithoutFire(float timeWithoutFire) {
		this.timeWithoutFire = timeWithoutFire;
	}
	
	// EXTERNALIZATION

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeFloat(timeWithoutFire);
		out.writeFloat(lastFire);
		out.writeObject(owner);
		
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		this.timeWithoutFire = in.readFloat();
		this.lastFire = in.readFloat();
		this.owner = (T) in.readObject();
		
	}
	
	
}
