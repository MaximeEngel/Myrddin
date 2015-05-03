package fr.imac.myrddin.game.ennemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.GameScreen;
import fr.imac.myrddin.game.magic.MagicState;
import fr.imac.myrddin.physic.PhysicActor;
import fr.imac.myrddin.physic.PhysicUtil;

public class TowerEnemy extends EnnemyShooter {
	
	public static final float WIDTH = 64f;
	public static final float HEIGHT = 38f;
	
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
		super(new Rectangle(pos.x, pos.y, WIDTH, HEIGHT), new Rectangle(5, 5, WIDTH - 10, HEIGHT - 10), BodyType.StaticBody, PhysicUtil.createFixtureDef(10f, 0f, false), true, 1, magicState, gameScreen);
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
		
		if(isKilled())
			dispose();
	}
	
	

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(animation.getKeyFrame(time), getX(), getY());
	}
	
	
	// ENNEMY

	@Override
	public int getMeleeDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean obstructBulletOf(PhysicActor owner) {
		return owner.getCollidableType() != CollidableType.Enemy;
	}	
	
}
