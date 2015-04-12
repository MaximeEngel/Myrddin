package fr.imac.myrddin.physic;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;

public class PhysicTile implements Collidable {

	private CollidableType type;	
	
	public PhysicTile(CollidableType type) {
		this.type = type;
	}


	@Override
	public CollidableType getCollidableType() {
		return type;
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
	
}
