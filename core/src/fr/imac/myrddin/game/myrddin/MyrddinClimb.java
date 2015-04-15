package fr.imac.myrddin.game.myrddin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

import fr.imac.myrddin.game.myrddin.MyrddinState.StateType;

public class MyrddinClimb extends MyrddinState {
	
	public MyrddinClimb(Myrddin myrddin) {
		super(myrddin, null);
	}

	@Override
	public void act(float delta) {
		super.act(delta);		
		
		myrddin.setLinearVelocity(new Vector2(myrddin.getLinearVelocity().x, -1f));			
		moveOnTheClimb();
		moveInTheAir();	
	}

	private void moveOnTheClimb() {
		if(Gdx.input.isKeyPressed(Input.Keys.Z))
			myrddin.applyImpulse(new Vector2(0, myrddin.getMass() * 5));
		else if(Gdx.input.isKeyPressed(Input.Keys.S))
			myrddin.applyImpulse(new Vector2(0, myrddin.getMass() * -7));
	}	

	@Override
	public StateType getStateType() {
		return StateType.Climb;
	}

}
