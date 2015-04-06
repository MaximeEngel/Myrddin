package fr.imac.myrddin.physic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fr.imac.myrddin.MyrddinGame;

public abstract class PhysicActor extends Actor implements Collidable  {
	
	private Body body;

	public PhysicActor(Body body) {
		super();
		this.body = body;
	}
	
	/**
	 * Update the position and angle of the actor to correspond to physic world.
	 */
	@Override
	public void act(float delta) {
		Vector2 position = body.getPosition();
		this.setPosition(position.x * MyrddinGame.PHYSIC_TO_GAME, position.y * MyrddinGame.PHYSIC_TO_GAME);
		this.setRotation(body.getAngle());
	}	
	
}
