package fr.imac.myrddin.game.myrddin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import fr.imac.myrddin.MyrddinGame;

public class MyrddinDuck extends MyrddinState {

	public MyrddinDuck(Myrddin myrddin) {
		super(myrddin, new Animation(0.15f, MyrddinGame.assetManager.get("myrddin/myrddin.atlas", TextureAtlas.class).findRegions("duck"), PlayMode.NORMAL));
		myrddin.setLinearVelocity(new Vector2(0f, 0f));
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if (!Gdx.input.isKeyPressed(Input.Keys.S))
		{
			myrddin.setMyrddinState(new MyrddinIddle(myrddin));
		}
	}	

	@Override
	public void setNewRectBox() {
		// if look right)
		if(myrddin.getScaleX() >= 1)
			myrddin.setNewRectBox(new Rectangle(myrddin.getX(), myrddin.getY(), 60, 96), new Rectangle(18, 5, 30, 70));
		else
			myrddin.setNewRectBox(new Rectangle(myrddin.getX(), myrddin.getY(), 60, 96), new Rectangle(12, 5, 30, 70));
	}

	@Override
	public StateType getStateType() {
		return StateType.Duck;
	}

	@Override
	public Vector2 getFirePos() {
		// Look right
		if(myrddin.getScaleX() >= 0)
			return new Vector2(myrddin.getCenterX(), myrddin.getCenterY()).add(22, -20);
		else
			return new Vector2(myrddin.getCenterX(), myrddin.getCenterY()).add(-30, -26);
	}
	
	

}
