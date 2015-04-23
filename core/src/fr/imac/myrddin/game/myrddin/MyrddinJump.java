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

public class MyrddinJump extends MyrddinState {

	public MyrddinJump(Myrddin myrddin) {
		//super(myrddin, null);
		super(myrddin, new Animation(0.1f, MyrddinGame.assetManager.get("myrddin/myrddin.atlas", TextureAtlas.class).findRegions("jump"), PlayMode.NORMAL));
		myrddin.applyImpulse(new Vector2(0f, 10 * myrddin.getMass()));
		Vector2 velocity = myrddin.getLinearVelocity();
	}

	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		Vector2 velocity = myrddin.getLinearVelocity();
		if(!Gdx.input.isKeyPressed(Input.Keys.Z) && velocity.y > 0) {
			velocity.y = 0;
			myrddin.setLinearVelocity(velocity);
		}			
		
		if (velocity.y <= 0)
			myrddin.setMyrddinState(new MyrddinFall(myrddin));
		
		moveInTheAir();
		
	}
	
	@Override
	public void setNewRectBox() {
		// if look right)
		if(myrddin.getScaleX() >= 1)
			myrddin.setNewRectBox(new Rectangle(myrddin.getX(), myrddin.getY(), 47, 105), new Rectangle(8, 5, 25, 99));
		else
			myrddin.setNewRectBox(new Rectangle(myrddin.getX(), myrddin.getY(), 47, 105), new Rectangle(16, 5, 25, 99));
	}

	@Override
	public StateType getStateType() {
		return StateType.Jump;
	}
	
	@Override
	public Vector2 getFirePos() {
		// Look right
		if(myrddin.getScaleX() >= 0)
			return new Vector2(myrddin.getCenterX(), myrddin.getCenterY()).add(20, 43);
		else
			return new Vector2(myrddin.getCenterX(), myrddin.getCenterY()).add(-40, 43);
	}

}
