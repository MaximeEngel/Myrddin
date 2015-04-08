package fr.imac.myrddin.game.myrddin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

public class MyrddinRun extends MyrddinState {
	
	private float direction;

	public MyrddinRun(Myrddin myrddin, float direction) {
		super(myrddin, null);
		this.direction = direction;
		
		myrddin.applyImpulse(new Vector2(direction * myrddin.getMass() * 3, 0f));
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		int goodKey = direction > 0 ? Input.Keys.D : Input.Keys.Q ;
		
		if(Gdx.input.isKeyPressed(goodKey)) {
			myrddin.applyForce(new Vector2(direction * myrddin.getMass() * 4, 0));
		} else {
			myrddin.setLinearVelocity(new Vector2(0, myrddin.getLinearVelocity().y));
			myrddin.setMyrddinState(new MyrddinIddle(myrddin));
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.Z))
			myrddin.setMyrddinState(new MyrddinJump(myrddin));
	}
	
	@Override
	public Vector2 getFirePos() {
		return null;
	}

}
