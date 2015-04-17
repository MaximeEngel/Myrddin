package fr.imac.myrddin.game.magic;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import fr.imac.myrddin.game.ennemy.Enemy;
import fr.imac.myrddin.game.myrddin.Myrddin;
import fr.imac.myrddin.physic.Collidable;
import fr.imac.myrddin.physic.PhysicActor;
import fr.imac.myrddin.physic.PhysicUtil;

public class MagicBullet extends PhysicActor {
	
	/**
	 * Maximum time to live in second
	 */
	public static final float LIFE = 2;
	
	private MagicState magicState;
	private float timeSinceBirth;
	private boolean hasContacted;
	private PhysicActor owner;
	

	
	/**
	 * 
	 * @param initialPos
	 * @param directionShoot will be normalized in the constructor
	 * @param world
	 */
	public MagicBullet(Vector2 initialPos, Vector2 directionShoot, MagicState magicState, PhysicActor owner) {
		super(new Rectangle(initialPos.x, initialPos.y, 40f, 10f), new Rectangle(4, 2, 32, 6), BodyType.DynamicBody, PhysicUtil.createFixtureDef(10f, 0f, false), false);
		
		this.magicState = magicState;
		this.timeSinceBirth = 0;
		this.owner = owner;
		this.hasContacted = false;
		
		this.applyImpulse(directionShoot.nor().scl(this.getMass() * 30));
		this.body.setTransform(this.body.getPosition(), directionShoot.angleRad());		
	}
	
	

	@Override
	public void act(float delta) {
		this.timeSinceBirth += delta;
		
		if(timeSinceBirth > LIFE || hasContacted)
			dispose();
	}
	
	public void dispose() {
		this.remove();
		this.body.getWorld().destroyBody(this.body);
	}

	// COLLISION

	@Override
	public CollidableType getCollidableType() {
		return CollidableType.MagicBullet;
	}


	@Override
	public void beginContact(Contact contact, Collidable other) {
		
	}


	@Override
	public void endContact(Contact contact, Collidable other) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void postSolve(Contact contact, Collidable other) {		
		hasContacted = true;		
	}


	@Override
	public void preSolve(Contact contact, Collidable other) {
		if (this.owner.equals(other)) {
			contact.setEnabled(false);
		}	
		else if (other.getCollidableType() == CollidableType.Ennemy) {
			Enemy enemy = (Enemy) other;
			if (!enemy.obstructBulletOf(this.owner)) {
				contact.setEnabled(false);
			}
		}
	}
}
