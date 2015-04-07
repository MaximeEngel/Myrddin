package fr.imac.myrddin.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import fr.imac.myrddin.physic.PhysicActor;

public abstract class Character extends PhysicActor {

	private int life;
	private int maxLife;
	
	public Character(Rectangle bounds, Rectangle collisionBox, BodyType bodyType, FixtureDef fixtureDef, boolean preventRotation, World world, int initialLife) {
		super(bounds, collisionBox, bodyType, fixtureDef, preventRotation, world);
		this.life = initialLife;
		this.maxLife = initialLife;
	}

	public void healedBy(int point) {
		this.life = Math.min(maxLife, life + point);
	}
	
	public void hurtedBy(int point) {
		this.life -= point;
	}
}
