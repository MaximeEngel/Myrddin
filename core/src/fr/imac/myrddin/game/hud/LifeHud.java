package fr.imac.myrddin.game.hud;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.myrddin.Myrddin;

public class LifeHud extends Actor {
	private Myrddin myrddin;
	private AtlasRegion atlasRegion;
	
	public LifeHud(Myrddin myrddin) {
		this.myrddin = myrddin;
		
		TextureAtlas textureAtlas = MyrddinGame.assetManager.get("ui/hud.atlas", TextureAtlas.class);
		atlasRegion = textureAtlas.findRegion("life");
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		for (int i = 0; i < myrddin.getLife(); i++) {
			batch.draw(
					atlasRegion,
					getParent().getWidth() - atlasRegion.getRegionWidth() - 10 - 30*i,
					getParent().getHeight() - atlasRegion.getRegionHeight() - 5
			);
		}
	}
	
}
