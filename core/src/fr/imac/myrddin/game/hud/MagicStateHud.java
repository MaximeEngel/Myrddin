package fr.imac.myrddin.game.hud;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.magic.MagicState;
import fr.imac.myrddin.game.myrddin.Myrddin;

public class MagicStateHud extends Actor {
	private Myrddin myrddin;
	//private BitmapFont bitmapfont;
	private AtlasRegion atlasRegionFire;
	private AtlasRegion atlasRegionIce;
	
	public MagicStateHud (Myrddin myrddin) {
		this.myrddin = myrddin;
		
		//bitmapfont = (BitmapFont) MyrddinGame.assetManager.get("ui/test.fnt", BitmapFont.class);
		
		TextureAtlas textureAtlas = MyrddinGame.assetManager.get("ui/hud.atlas", TextureAtlas.class);
		atlasRegionFire = textureAtlas.findRegion("magicFire");
		atlasRegionIce = textureAtlas.findRegion("magicIce");
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (myrddin.getMagicState() == MagicState.FIRE) {
			batch.draw(
					atlasRegionFire,
					getParent().getWidth() - atlasRegionFire.getRegionWidth() - 120,
					getParent().getHeight() - atlasRegionFire.getRegionHeight() - 5
			);
		}
		else {
			batch.draw(
					atlasRegionIce,
					getParent().getWidth() - atlasRegionIce.getRegionWidth() - 120,
					getParent().getHeight() - atlasRegionIce.getRegionHeight() - 5
			);
		}
		
		
		//bitmapfont.setColor(1f, 1f, 1f, 1f);
		//bitmapfont.setScale(0.5f);
		//bitmapfont.draw(
		//		batch,
		//		"pouvoir : ",
		//		getParent().getWidth() - 150,
		//		getParent().getHeight() - 55
		//);
	}

	public void setMyrddin(Myrddin myrddin) {
		this.myrddin = myrddin;	
		
	}
}
