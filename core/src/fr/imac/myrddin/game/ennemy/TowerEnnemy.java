package fr.imac.myrddin.game.ennemy;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.Character;
import fr.imac.myrddin.game.GameScreen;
import fr.imac.myrddin.game.MagicWeapon;
import fr.imac.myrddin.game.MagicWeaponOwner;
import fr.imac.myrddin.game.magic.MagicState;
import fr.imac.myrddin.game.myrddin.Myrddin;
import fr.imac.myrddin.physic.Collidable;
import fr.imac.myrddin.physic.PhysicUtil;

public class TowerEnnemy extends Character implements MagicWeaponOwner{
	
	private static final float MAXIMUM_SCOPE = MyrddinGame.WIDTH * 0.5f;
	private MagicState magicState;
	private GameScreen gameScreen;
	
	// offset in function of the bottom left of the fire's origin.
	private MagicWeapon<TowerEnnemy> magicWeapon;
	private Vector2 firePos;
	
	private float lastTimeSeen;
	
	/**
	 * Only use it for externalization.
	 */
	public TowerEnnemy() {
		super();
		
		defaultInit();
	}
	
	public TowerEnnemy(Vector2 pos, MagicState magicState, GameScreen gameScreen) {
		super(new Rectangle(pos.x, pos.y, 50, 50), new Rectangle(5, 0, 40, 50), BodyType.StaticBody, PhysicUtil.createFixtureDef(10f, 0f, false), true, 3);
		
		defaultInit();
		this.magicState = magicState;
		this.gameScreen = gameScreen;
	}
	
	
	
	private void defaultInit() {
		this.firePos = new Vector2(10 + getX(), 10 + getY());		
		this.magicWeapon = new MagicWeapon<TowerEnnemy>(0.3f, this);
	}

	
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(canShootMyrddin())
			shootOnMyrddin();
		this.lastTimeSeen += delta;
	}
	
	public void shootOnMyrddin() {
		Myrddin myrddin = gameScreen.getMyrddin();
		Vector2 targetPos = new Vector2(myrddin.getX() + myrddin.getWidth() * 0.5f, myrddin.getY() + myrddin.getHeight() * 0.5f);
		// Add some randomness on the shoot
		targetPos.add(MathUtils.random(-20, 20), MathUtils.random(-20, 20));
		
		magicWeapon.fire(targetPos);
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
		Vector2 myrddinPos = new Vector2(myrddin.getX(), myrddin.getY());
		Vector2 myrddinPos2 = new Vector2(myrddinPos);
		if (myrddinPos2.sub(getFirePos()).len2() > MAXIMUM_SCOPE * MAXIMUM_SCOPE) 
			return false;		
		
		final boolean[] noObstruction = new boolean[] {true};
		gameScreen.physicWorld.rayCast(new RayCastCallback() {
			
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
			
		}, getFirePos(), myrddinPos);
		
		return noObstruction[0];
	}
	
	public boolean collidableObstruct(Collidable collidable) {
		CollidableType type = collidable.getCollidableType();
		return 	type == CollidableType.Solid
				|| type == CollidableType.Ennemy;
	}
	
	/**
	 * 
	 * @return if the tower is visible on the screen or has been visible few time ago
	 */
	public boolean canShoot() {
		if (lastTimeSeen < 2f)
			return true;
		
		if (isVisible()) {
			lastTimeSeen = 0;
			return true;
		}
		
		return false;		
	}
	
	// MAGIC WEAPON OWNER

	@Override
	public Vector2 getFirePos() {
		return firePos;
	}

	@Override
	public MagicState getMagicState() {
		return magicState;
	}	
	
}
