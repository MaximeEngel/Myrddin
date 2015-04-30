package fr.imac.myrddin.game.magic;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fr.imac.myrddin.MyrddinGame;

public class MagicHit extends Actor {
	private Animation animation;
	private float stateTime;
	private Vector2 posCenter;
	
	public MagicHit(Vector2 posCenter, MagicState magicState) {
		TextureAtlas atlas = MyrddinGame.assetManager.get("power/power.atlas", TextureAtlas.class);
		
		switch(magicState) {
		case FIRE:
			animation = new Animation(0.1f, atlas.findRegions("impactFire"), PlayMode.NORMAL);
			break;
		// Default is ice
		default:
			animation = new Animation(0.1f, atlas.findRegions("impactIce"), PlayMode.NORMAL);
			break;
		
		}
		this.stateTime = 0;
		this.posCenter = posCenter;
		setRotation(MathUtils.random(0, 360f));
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		TextureRegion textureRegion = animation.getKeyFrame(stateTime);
		batch.draw(textureRegion, posCenter.x - textureRegion.getRegionWidth() * 0.5f, posCenter.y - textureRegion.getRegionHeight() * 0.5f, textureRegion.getRegionWidth() * 0.5f, textureRegion.getRegionHeight() * 0.5f,textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), 1f, 1f, getRotation());
	}

	@Override
	public void act(float delta) {
		stateTime += delta;
		if(animation.isAnimationFinished(stateTime))
			remove();
	}	
}
