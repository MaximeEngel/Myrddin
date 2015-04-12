package fr.imac.myrddin.game.myrddin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

public class MyrddinIddle extends MyrddinState {

	public MyrddinIddle(Myrddin myrddin) {
		super(myrddin, null);
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

		if(Gdx.input.isKeyPressed(Input.Keys.S))
			myrddin.setMyrddinState(new MyrddinDuck(myrddin));
	}

}
