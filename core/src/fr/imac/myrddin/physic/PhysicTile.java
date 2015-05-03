package fr.imac.myrddin.physic;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;

public class PhysicTile implements Collidable {

	private CollidableType type;
	protected Body body;
	
	public PhysicTile(CollidableType type, Body body) {
		this.type = type;
		this.body = body;
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
