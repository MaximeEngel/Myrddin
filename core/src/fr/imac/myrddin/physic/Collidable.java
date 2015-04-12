package fr.imac.myrddin.physic;

import com.badlogic.gdx.physics.box2d.Contact;

public interface Collidable {
	
	public enum CollidableType {
		Solid, Climb, Myrddin, MagicBullet;
	}
	
	public CollidableType getCollidableType();
	
	/** Called when two fixtures begin to touch. */
	public void beginContact (Contact contact, Collidable other);

	/** Called when two fixtures cease to touch. */
	public void endContact (Contact contact, Collidable other);

	/**
	 * This is called after a contact is updated. This allows you to inspect a contact before it goes to the solver. 
	 */
	public void preSolve (Contact contact, Collidable other);

	/**
	 * This lets you inspect a contact after the solver is finished.
	 */
	public void postSolve (Contact contact, Collidable other);
}
