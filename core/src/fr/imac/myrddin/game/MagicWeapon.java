package fr.imac.myrddin.game;

import com.badlogic.gdx.math.Vector2;
import fr.imac.myrddin.game.magic.MagicBullet;
import fr.imac.myrddin.game.magic.MagicState;
import fr.imac.myrddin.physic.PhysicActor;

public class MagicWeapon<T extends PhysicActor & MagicWeaponOwner> {
	
	private float timeWithoutFire ;
	private float lastFire ;
	private T  owner;		
	
	public MagicWeapon(float timeWithoutFire, T owner) {
		super();
		
		this.timeWithoutFire = timeWithoutFire;
		this.lastFire = 0;
		this.owner = owner;
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
	
	
}
