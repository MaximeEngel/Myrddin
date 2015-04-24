package fr.imac.myrddin.game.myrddin;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Rectangle;

import fr.imac.myrddin.MyrddinGame;

public class MyrddinDead extends MyrddinState {
	
	public static final float TIME_TO_BE_DEAD = 3f;

	public MyrddinDead(Myrddin myrddin) {
		super(myrddin, new Animation(0.2f, MyrddinGame.assetManager.get("myrddin/myrddin.atlas", TextureAtlas.class).findRegions("death"), PlayMode.LOOP));
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public StateType getStateType() {
		return StateType.Dead;
	}
	

	@Override
	public void setNewRectBox() {
		// if look right)
		if(myrddin.getScaleX() >= 1)
			myrddin.setNewRectBox(new Rectangle(myrddin.getX(), myrddin.getY(), 96, 96), new Rectangle(5, 5, 96, 20));
		else
			myrddin.setNewRectBox(new Rectangle(myrddin.getX(), myrddin.getY(), 96, 96), new Rectangle(5, 5, 96, 20));
	}
}
