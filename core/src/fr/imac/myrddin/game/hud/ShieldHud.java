package fr.imac.myrddin.game.hud;

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
	private NinePatch patch1;
	private NinePatch patch2;
	private AtlasRegion atlasRegion1;
	private AtlasRegion atlasRegion2;
	
	public ShieldHud(Shield shield) {
		this.shield = shield;
		
		TextureAtlas textureAtlas = MyrddinGame.assetManager.get("ui/hud.atlas", TextureAtlas.class);
		atlasRegion1 = textureAtlas.findRegion("shield");
		atlasRegion2 = textureAtlas.findRegion("shieldMax");
		
		patch1 = new NinePatch(atlasRegion1, 8, 8, 8, 8);
		patch2 = new NinePatch(atlasRegion2, 8, 8, 8, 8);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (shield.getEnergy() > 15) {
			patch1.draw(
					batch,
					getParent().getWidth() - atlasRegion1.getRegionWidth() - 90,
					getParent().getHeight() - atlasRegion1.getRegionHeight() - 55,
					shield.getEnergy(),
					20f
			);
		}
		
		patch2.draw(
				batch,
				getParent().getWidth() - atlasRegion2.getRegionWidth() - 90,
				getParent().getHeight() - atlasRegion2.getRegionHeight() - 55,
				Shield.MAX_ENERGY,
				20f
		);
	}
}
