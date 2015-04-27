package fr.imac.myrddin.game.ennemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
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
import fr.imac.myrddin.physic.Collidable.CollidableType;
import fr.imac.myrddin.physic.PhysicActor;
import fr.imac.myrddin.physic.PhysicUtil;

public class TowerEnemy extends EnnemyShooter {
	
	private Animation animation;
	private float time;
	
	/**
	 * Only use it for externalization.
	 */
	public TowerEnemy() {
		super();
		init();
	}
	
	public TowerEnemy(Vector2 pos, MagicState magicState, GameScreen gameScreen) {
		super(new Rectangle(pos.x, pos.y, 64, 38), new Rectangle(5, 5, 54, 28), BodyType.StaticBody, PhysicUtil.createFixtureDef(10f, 0f, false), true, 3, magicState, gameScreen);
		init();
	}
	
	public void init() {
		TextureAtlas atlas = (TextureAtlas) MyrddinGame.assetManager.get("enemy/enemy.atlas", TextureAtlas.class);
		animation = new Animation(0.1f, atlas.findRegions("turret"), PlayMode.LOOP);
		
		this.time = 0;
	}
	
	
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		
		time += delta;
	}
	
	

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(animation.getKeyFrame(time), getX(), getY());
	}

	@Override
	public CollidableType getCollidableType() {
		return CollidableType.Ennemy;
	}
	
	// ENNEMY

	@Override
	public int getMeleeDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean obstructBulletOf(PhysicActor owner) {
		return true;
	}	
	
}
