package fr.imac.myrddin.game.myrddin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.myrddin.MyrddinState.StateType;

public class MyrddinFall extends MyrddinState {

	public MyrddinFall(Myrddin myrddin) {
		super(myrddin, new Animation(0.1f, MyrddinGame.assetManager.get("myrddin/myrddin.atlas", TextureAtlas.class).findRegions("fall"), PlayMode.NORMAL));
	}

	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		moveInTheAir();
		
		Vector2 velocity = myrddin.getLinearVelocity();		
		if (velocity.y == 0)
			myrddin.setMyrddinState(new MyrddinIddle(myrddin));
	}

	@Override
	public void setNewRectBox() {
		myrddin.setNewRectBox(new Rectangle(myrddin.getX(), myrddin.getY(), 54, 96), new Rectangle(5, 5, 40, 90));
	}

	@Override
	public StateType getStateType() {
		return StateType.Fall;
	}
}
