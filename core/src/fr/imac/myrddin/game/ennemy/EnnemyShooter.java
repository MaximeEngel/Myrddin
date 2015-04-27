package fr.imac.myrddin.game.ennemy;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.Character;
import fr.imac.myrddin.game.GameScreen;
import fr.imac.myrddin.game.MagicWeapon;
import fr.imac.myrddin.game.MagicWeaponOwner;
import fr.imac.myrddin.game.magic.MagicState;
import fr.imac.myrddin.game.myrddin.Myrddin;
import fr.imac.myrddin.physic.Collidable;
import fr.imac.myrddin.physic.PhysicActor;

public abstract class EnnemyShooter extends Character implements Enemy, MagicWeaponOwner {
	
	private static final float MAX_LAST_TIME_SEEN = 2f;
	private MagicState magicState;
	private GameScreen gameScreen;
	
	private MagicWeapon<EnnemyShooter> magicWeapon;
	
	private float lastTimeSeen;

	/**
	 * In stage pixel unit
	 */
	private float maximumScope = MyrddinGame.WIDTH * 0.5f;

	public EnnemyShooter(Rectangle bounds, Rectangle collisionBox,
			BodyType bodyType, FixtureDef fixtureDef, boolean preventRotation,
			int initialLife, MagicState magicState, GameScreen gameScreen) {
		super(bounds, collisionBox, bodyType, fixtureDef, preventRotation,
				initialLife);
		this.magicState = magicState;
		this.gameScreen = gameScreen;
		
		defaultInit();
	}
	
	public EnnemyShooter() {
	}
	
	private void defaultInit() {
		this.magicWeapon = new MagicWeapon<EnnemyShooter>(0.3f, this);
		this.lastTimeSeen = MAX_LAST_TIME_SEEN;
	}
	
	// ENNEMY SHOOTER
	
	
	
	public void shootOnMyrddin() {
		Myrddin myrddin = gameScreen.getMyrddin();
		Vector2 targetPos = new Vector2(myrddin.getX() + myrddin.getWidth() * 0.5f, myrddin.getY() + myrddin.getHeight() * 0.5f);
		// Add some randomness on the shoot
		targetPos.add(MathUtils.random(-20, 20), MathUtils.random(-20, 20));
		
		magicWeapon.fire(targetPos);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		magicWeapon.act(delta);
		if(canShootMyrddin())
			shootOnMyrddin();
		this.lastTimeSeen += delta;
	}

	/**
	 * 
	 * @return if can shoot and can shoot Myrddin
	 */
	public boolean canShootMyrddin() {
		if (!canShoot())
			return false;
		
		// Test if myrddin is in scope
		Myrddin myrddin = gameScreen.getMyrddin();
		Vector2 myrddinPos = new Vector2(myrddin.getCenterX(), myrddin.getCenterY());
		Vector2 myrddinPos2 = new Vector2(myrddinPos);
		Vector2 firePos = getWeaponPos();
		float len2 = myrddinPos2.sub(firePos).len2();
		if ( len2 > maximumScope * maximumScope) 
			return false;		
		
		
		Vector2 physicFirePos = new Vector2(firePos).scl(MyrddinGame.GAME_TO_PHYSIC);
		myrddinPos.scl(MyrddinGame.GAME_TO_PHYSIC);
		final boolean[] noObstruction = new boolean[] {true};
		GameScreen.physicWorld.rayCast(new RayCastCallback() {
			
			@Override
			public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
				Collidable collidable = (Collidable) fixture.getBody().getUserData();

				if(collidableObstruct(collidable)) {
					noObstruction[0] = false;
					// Stop ray one obstruction is sufficient to obstruct the shoot
					return 0;
				}
				
				// Continue the ray
				return 1;
			}
			
		}, physicFirePos, myrddinPos);
		return noObstruction[0];
	}
	
	public boolean collidableObstruct(Collidable collidable) {
		CollidableType type = collidable.getCollidableType();
		return 	type == CollidableType.Solid
				|| (type == CollidableType.Ennemy && ((Enemy) collidable).obstructBulletOf(this));
	}
	
	/**
	 * 
	 * @return if the tower is visible on the screen or has been visible few time ago
	 */
	public boolean canShoot() {
		if (lastTimeSeen < MAX_LAST_TIME_SEEN)
			return true;
		
		if (isVisible()) {
			lastTimeSeen = 0;
			return true;
		}
		
		return false;		
	}
	
	// WEAPON OWNER
	
	@Override
	public Vector2 getWeaponPos() {
		return new Vector2(25 + getX(), 55 + getY());
	}

	@Override
	public MagicState getMagicState() {
		return magicState;
	}

	@Override
	public int getMeleeDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean obstructBulletOf(PhysicActor owner) {
		// TODO Auto-generated method stub
		return false;
	}
	
	// EXTERNALIZATION

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// TODO Auto-generated method stub
		super.writeExternal(out);
		
		out.writeObject(magicState);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		super.readExternal(in);
		
		magicState = (MagicState) in.readObject();
		
		gameScreen = (GameScreen) MyrddinGame.MYRDDIN_GAME.getScreen();
		
		this.magicWeapon = new MagicWeapon<EnnemyShooter>(0.3f, this);
		this.lastTimeSeen = MAX_LAST_TIME_SEEN;
	}	
	
	

}
