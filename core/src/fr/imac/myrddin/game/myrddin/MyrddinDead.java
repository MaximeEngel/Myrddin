package fr.imac.myrddin.game.myrddin;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import fr.imac.myrddin.MyrddinGame;

public class MyrddinDead extends MyrddinState {
	
	public static final float TIME_TO_BE_DEAD = 2f;

	private BitmapFontCache bitmapFontCache;

	public MyrddinDead(Myrddin myrddin) {
		super(myrddin, new Animation(0.2f, MyrddinGame.assetManager.get("myrddin/myrddin.atlas", TextureAtlas.class).findRegions("death"), PlayMode.NORMAL));
		
		myrddin.setZIndex(myrddin.getStage().getActors().size + 1);
		BitmapFont bitmap = (BitmapFont) MyrddinGame.assetManager.get("ui/theonlyexception_25.fnt", BitmapFont.class);
		bitmapFontCache = new BitmapFontCache(bitmap);
		bitmapFontCache.addMultiLineText("Perdu \nScore : "+myrddin.getScore(), myrddin.getStage().getCamera().position.x - MyrddinGame.WIDTH / 2, MyrddinGame.HEIGHT * 0.5f,(float) MyrddinGame.WIDTH, HAlignment.CENTER);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		if (this.animation.isAnimationFinished(stateTime)) {
			// No slide to many time
			myrddin.getBody().setLinearVelocity(0, myrddin.getLinearVelocity().y);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		bitmapFontCache.draw(batch);
	}

	@Override
	public StateType getStateType() {
		return StateType.Dead;
	}	

	@Override
	public void orientTexture() {
		int sens = Integer.signum((int)myrddin.getLinearVelocity().x);
		
		if (sens == 0)
			return;
		
		float oldSens = myrddin.getScaleX();		
		myrddin.setScale(-sens, 1f);
		if(-sens != oldSens)
			setNewRectBox();
	}

	@Override
	public void setNewRectBox() {
		// if look right)
		if(myrddin.getScaleX() >= 1)
			myrddin.setNewRectBox(new Rectangle(myrddin.getX(), myrddin.getY(), 96, 96), new Rectangle(5, 5, 86, 20));
		else
			myrddin.setNewRectBox(new Rectangle(myrddin.getX(), myrddin.getY(), 96, 96), new Rectangle(5, 5, 86, 20));
	}

	@Override
	public Vector2 getFirePos() {
		return null;
	}
	
	
}
