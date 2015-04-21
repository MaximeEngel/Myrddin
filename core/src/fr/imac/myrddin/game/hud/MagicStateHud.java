package fr.imac.myrddin.game.hud;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.magic.MagicState;
import fr.imac.myrddin.game.myrddin.Myrddin;

public class MagicStateHud extends Actor {
	private Myrddin myrddin;
	private BitmapFont bitmapfont;
	private AtlasRegion atlasRegionPower1;
	private AtlasRegion atlasRegionPower2;
	
	public MagicStateHud (Myrddin myrddin) {
		this.myrddin = myrddin;
		
		bitmapfont = (BitmapFont) MyrddinGame.assetManager.get("ui/test.fnt", BitmapFont.class);
		
		TextureAtlas textureAtlas = MyrddinGame.assetManager.get("ui/hud.atlas", TextureAtlas.class);
		atlasRegionPower1 = textureAtlas.findRegion("magicFire");
		atlasRegionPower2 = textureAtlas.findRegion("magicIce");
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (myrddin.getMagicState() == MagicState.POWER_1) {
			batch.draw(
					atlasRegionPower1,
					getParent().getWidth() - atlasRegionPower1.getRegionWidth() - 20,
					getParent().getHeight() - atlasRegionPower1.getRegionHeight() - 55
			);
		}
		else {
			batch.draw(
					atlasRegionPower2,
					getParent().getWidth() - atlasRegionPower2.getRegionWidth() - 20,
					getParent().getHeight() - atlasRegionPower2.getRegionHeight() - 55
			);
		}
		
		
		bitmapfont.setColor(1f, 1f, 1f, 1f);
		bitmapfont.setScale(0.5f);
		bitmapfont.draw(
				batch,
				"pouvoir : ",
				getParent().getWidth() - 150,
				getParent().getHeight() - 55
		);
	}
}
