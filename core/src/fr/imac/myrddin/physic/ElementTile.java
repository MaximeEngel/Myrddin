package fr.imac.myrddin.physic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.magic.MagicState;
import fr.imac.myrddin.game.myrddin.Myrddin;
import fr.imac.myrddin.game.myrddin.MyrddinBump;
import fr.imac.myrddin.game.magic.MagicBullet;

public class ElementTile extends PhysicTile {
	
	MagicState magicState;

	public ElementTile(Body body, MagicState magicState) {
		super(CollidableType.Element, body);
		
		this.magicState = magicState;
	}

	@Override
	public void preSolve(Contact contact, Collidable other) {
		super.preSolve(contact, other);
		
		Myrddin myrddin = null;
		MagicState magicStateOther = null;	
		CollidableType collidableType = other.getCollidableType();
		
		switch (collidableType) {
		case Myrddin:
			myrddin = (Myrddin) other;
			magicStateOther = myrddin.getMagicState();
			break;
		case MagicBullet:
			magicStateOther = ((MagicBullet) other).getMagicState();
			break;
		default:
			break;
		}
		
		if (magicStateOther == null || magicStateOther == magicState)
			contact.setEnabled(false);
		else if (myrddin != null) {
			Vector2 bumpImpulse = new Vector2(Integer.signum((int) (myrddin.getX() + myrddin.getWidth() / 2f - ((this.body.getPosition().x + this.body.getFixtureList().get(0).getShape().getRadius() * 0.5f) * MyrddinGame.PHYSIC_TO_GAME))) * 5f, 3f).scl(myrddin.getMass());			
			myrddin.hurtedBy(1);
			myrddin.bump(bumpImpulse);
		}
	}
	
	

}
