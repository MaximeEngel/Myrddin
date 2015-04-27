package fr.imac.myrddin.game.hud;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.myrddin.Myrddin;


public class ScoreHud extends Actor {
	private Myrddin myrddin;
	private BitmapFont bitmapfont;


	public ScoreHud (Myrddin myrddin) {
		this.myrddin = myrddin;
		
		bitmapfont = (BitmapFont) MyrddinGame.assetManager.get("ui/test.fnt", BitmapFont.class);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		bitmapfont.setColor(1f, 1f, 1f, 1f);
		bitmapfont.setScale(0.5f);
		bitmapfont.draw(
				batch,
				String.valueOf(myrddin.getScore()),
				getParent().getWidth() - 70,
				getParent().getHeight() - 30
		);
	}

	public void setMyrddin(Myrddin myrddin) {
		this.myrddin = myrddin;	
		
	}
}