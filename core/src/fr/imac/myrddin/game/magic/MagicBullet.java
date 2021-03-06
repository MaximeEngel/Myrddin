package fr.imac.myrddin.game.magic;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.Character;
import fr.imac.myrddin.game.myrddin.Myrddin;
import fr.imac.myrddin.physic.Collidable;
import fr.imac.myrddin.physic.PhysicActor;
import fr.imac.myrddin.physic.PhysicUtil;

public class MagicBullet extends PhysicActor {
	
	/**
	 * Maximum time to live in second
	 */
	public static final float LIFE = 12;
	
	private MagicState magicState;
	private boolean born;
	private float timeSinceBirth;
	private boolean hasContacted;
	private PhysicActor owner;
	private Vector2 forceToApply;

	private Animation loadAnimation;
	private Animation landAnimation;

	private Character targetHitted;
	
	
	/**
	 * 
	 * @param initialPos
	 * @param directionShoot will be normalized in the constructor
	 * @param world
	 */
	public MagicBullet(Vector2 initialPos, Vector2 directionShoot, MagicState magicState, PhysicActor owner) {
		super(new Rectangle(initialPos.x, initialPos.y, 16f, 16f), new Rectangle(0, 0, 16, 16), BodyType.DynamicBody, PhysicUtil.createFixtureDef(10f, 0f, false), false);
		
		this.magicState = magicState;
		this.timeSinceBirth = 0;
		this.born = false;
		this.body.setGravityScale(0f);
		this.body.setBullet(true);
		this.owner = owner;
		this.hasContacted = false;
		
		forceToApply = directionShoot.nor().scl(this.getMass() * 20);
		this.body.setTransform(this.body.getPosition(), directionShoot.angleRad());	
		
		TextureAtlas atlas = MyrddinGame.assetManager.get("power/power.atlas", TextureAtlas.class);
		switch(magicState)
		{
		case FIRE:
			loadAnimation = new Animation(0.04f, atlas.findRegions("loadfire"), PlayMode.NORMAL);
			landAnimation = new Animation(0.04f, atlas.findRegions("landfire"), PlayMode.LOOP);
			break;
		// default is ICE
		default:
			loadAnimation = new Animation(0.04f, atlas.findRegions("loadice"), PlayMode.NORMAL);
			landAnimation = new Animation(0.04f, atlas.findRegions("landice"), PlayMode.LOOP);
			break;
		
		}
	}
	
	

	@Override
	public void act(float delta) {
		super.act(delta);
		this.timeSinceBirth += delta;
		
		if(!born) {
			if(owner.getCollidableType() == CollidableType.Myrddin) {
				Myrddin myrddin = (Myrddin) owner;
				Vector2 weaponPos = myrddin.getWeaponPos();
				if (weaponPos != null)
					this.body.setTransform(weaponPos .scl(MyrddinGame.GAME_TO_PHYSIC), this.body.getAngle());
			}
			
			if(loadAnimation.isAnimationFinished(timeSinceBirth)) {
				born = true;
				this.applyImpulse(forceToApply);
				
				Sound sound;
				if(magicState == MagicState.ICE) {
					sound = MyrddinGame.assetManager.get("sounds/Bulle"+MathUtils.random(1, 4)+".wav", Sound.class);
					sound.play(0.9f);
				} else {
					sound = MyrddinGame.assetManager.get("sounds/FIRE"+MathUtils.random(1, 4)+".mp3", Sound.class);
					sound.play(0.5f);
				}
			}
		}
		
		if(timeSinceBirth > LIFE || hasContacted) {
			if (hasContacted) {
				this.getStage().addActor(new MagicHit(new Vector2(this.getCenterX(), this.getCenterY()), this.magicState));
				if (targetHitted != null)
					targetHitted.hurtedBy(1);
			}
			dispose();
		}
	}
	
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Animation animation = born ? landAnimation : loadAnimation;
		
		batch.draw(	animation.getKeyFrame(timeSinceBirth), 
				this.getX(),
				this.getY(), 
				this.getWidth() * 0.5f, 
				this.getHeight() * 0.5f, 
				this.getWidth(), 
				this.getHeight(), 
				this.getScaleX(), 
				this.getScaleY(), 
				this.getRotation()
			);
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
		
		if(other instanceof Character) {
			Character character = (Character) other;
			targetHitted = character;
		}
	}


	@Override
	public void preSolve(Contact contact, Collidable other) {
		if (this.owner.getCollidableType() == other.getCollidableType() 
			|| !born
			|| (other.getCollidableType() == CollidableType.MagicBullet && obstructBullet((MagicBullet) other))) {
			
			contact.setEnabled(false);
			return;
		}
	}

	private boolean obstructBullet(MagicBullet other) {
		return other.magicState == this.magicState;
	}



	public MagicState getMagicState() {
		return magicState;
	}
}
