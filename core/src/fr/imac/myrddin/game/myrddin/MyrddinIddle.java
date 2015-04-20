package fr.imac.myrddin.game.myrddin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.myrddin.MyrddinState.StateType;

public class MyrddinIddle extends MyrddinState {

	public MyrddinIddle(Myrddin myrddin) {
		super(myrddin, new Animation(0.2f, MyrddinGame.assetManager.get("myrddin/myrddin.atlas", TextureAtlas.class).findRegions("iddle"), PlayMode.LOOP));
	}
	
	

	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(Gdx.input.isKeyPressed(Input.Keys.Z))
			myrddin.setMyrddinState(new MyrddinJump(myrddin));
		else if(Gdx.input.isKeyPressed(Input.Keys.Q))
			myrddin.setMyrddinState(new MyrddinRun(myrddin, -1f));
		else if(Gdx.input.isKeyPressed(Input.Keys.D))
			myrddin.setMyrddinState(new MyrddinRun(myrddin, 1f));		
		else if(Gdx.input.isKeyPressed(Input.Keys.S))
			myrddin.setMyrddinState(new MyrddinDuck(myrddin));
	}


	@Override
	public StateType getStateType() {
		return StateType.Iddle;
	}
}
