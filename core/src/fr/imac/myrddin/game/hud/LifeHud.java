package fr.imac.myrddin.game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.myrddin.Myrddin;

public class LifeHud extends Actor {
	private Myrddin myrddin;
	private AtlasRegion atlasRegion;
	private BitmapFont bitmapfont;
	
	public LifeHud(Myrddin myrddin) {
		this.myrddin = myrddin;
		
		TextureAtlas textureAtlas = MyrddinGame.assetManager.get("ui/hud.atlas", TextureAtlas.class);
		atlasRegion = textureAtlas.findRegion("life");
		bitmapfont = (BitmapFont) MyrddinGame.assetManager.get("ui/test.fnt", BitmapFont.class);
		
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
		
		//bitmapfont.setColor(1f, 0f, 0f, 1f);
		//bitmapfont.draw(
		//			batch,
		//			String.valueOf(MathUtils.random()),
		//			getParent().getWidth() - 100,
		//			getParent().getHeight() - 20
		//	);
	}
	
	
	
}
