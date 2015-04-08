package fr.imac.myrddin.game.myrddin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

public class MyrddinFall extends MyrddinState {

	public MyrddinFall(Myrddin myrddin) {
		super(myrddin, null);
	}

	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		Vector2 velocity = myrddin.getLinearVelocity();		
		if (velocity.y == 0)
			myrddin.setMyrddinState(new MyrddinIddle(myrddin));
		
		moveInTheAir();
	}



	@Override
	public Vector2 getFirePos() {
		// TODO Auto-generated method stub
		return null;
	}

}
