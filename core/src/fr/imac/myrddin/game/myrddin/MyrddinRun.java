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

public class MyrddinRun extends MyrddinState {
	
	private float direction;
	private float lastY;

	public MyrddinRun(Myrddin myrddin, float direction) {
		super(myrddin, new Animation(0.1f, MyrddinGame.assetManager.get("myrddin/myrddin.atlas", TextureAtlas.class).findRegions("run"), PlayMode.LOOP));
		this.direction = direction;
		
		this.lastY = myrddin.getY();
		myrddin.applyImpulse(new Vector2(direction * myrddin.getMass() * 4, 0f));
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		int goodKey = direction > 0 ? Input.Keys.D : Input.Keys.Q ;
		
		if(Gdx.input.isKeyPressed(goodKey)) {
			myrddin.applyImpulse(new Vector2(direction * myrddin.getMass() * 4, 0));
		} else {
			myrddin.setLinearVelocity(new Vector2(0, myrddin.getLinearVelocity().y));
			myrddin.setMyrddinState(new MyrddinIddle(myrddin));
		}
		
		if(lastY > myrddin.getY() + 2)
			myrddin.setMyrddinState(new MyrddinFall(myrddin));
		else
			lastY = myrddin.getY();
		
		if(Gdx.input.isKeyPressed(Input.Keys.Z))
			myrddin.setMyrddinState(new MyrddinJump(myrddin));
		
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			myrddin.setMyrddinState(new MyrddinDuck(myrddin));
		}
	}
	
	@Override
	public StateType getStateType() {
		return StateType.Run;
	}
	
	public void setNewRectBox() {
		myrddin.setNewRectBox(new Rectangle(myrddin.getX(), myrddin.getY(), 48, 96), new Rectangle(12, 5, 24, 90));
	}

}
