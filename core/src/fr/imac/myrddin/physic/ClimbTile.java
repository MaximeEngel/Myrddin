package fr.imac.myrddin.physic;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;

import fr.imac.myrddin.game.myrddin.Myrddin;
import fr.imac.myrddin.game.myrddin.MyrddinClimb;
import fr.imac.myrddin.game.myrddin.MyrddinFall;

public class ClimbTile extends PhysicTile {
	
	public ClimbTile(Body body) {
		super(CollidableType.Climb, body);		
		
	}

	@Override
	public void beginContact(Contact contact, Collidable other) {
		super.beginContact(contact, other);
		
		if (other.getCollidableType() == CollidableType.Myrddin) {
			Myrddin myrddin = (Myrddin) other;
			myrddin.climb();
		}
	}

	@Override
	public void endContact(Contact contact, Collidable other) {
		super.endContact(contact, other);
		
		if (other.getCollidableType() == CollidableType.Myrddin) {
			Myrddin myrddin = (Myrddin) other;
			System.out.println("STOP TOUCH");
			if(!myrddin.isChanging())
				myrddin.setMyrddinState(new MyrddinFall(myrddin));
		}
	}
	
	
	
	

}
