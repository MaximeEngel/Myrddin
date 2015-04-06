package fr.imac.myrddin.game;

import com.badlogic.gdx.physics.box2d.Body;

import fr.imac.myrddin.physic.Collidable;
import fr.imac.myrddin.physic.PhysicActor;

public abstract class Character extends PhysicActor {

	private int life;
	private int maxLife;
	
	public Character(Body body, int initialLife) {
		super(body);
		this.maxLife = initialLife;
		this.life = initialLife;
	}
	
	public void healedBy(int point) {
		this.life = Math.min(maxLife, life + point);
	}
	
	public void hurtedBy(int point) {
		this.life -= point;
	}
}
