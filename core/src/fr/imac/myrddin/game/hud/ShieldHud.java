package fr.imac.myrddin.game.hud;

import javax.media.j3d.Texture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.myrddin.Shield;


public class ShieldHud extends Actor {
	private Shield shield;
	private NinePatch patch;
	private AtlasRegion atlasRegion;
	
	public ShieldHud(Shield shield) {
		this.shield = shield;
		
		TextureAtlas textureAtlas = MyrddinGame.assetManager.get("ui/hud.atlas", TextureAtlas.class);
		atlasRegion = textureAtlas.findRegion("shield");
		
		patch = new NinePatch(atlasRegion, 12, 12, 12, 12);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(
				atlasRegion,
				getParent().getWidth() - atlasRegion.getRegionWidth() - 10,
				getParent().getHeight() - atlasRegion.getRegionHeight() - 5
		);
	}
}
