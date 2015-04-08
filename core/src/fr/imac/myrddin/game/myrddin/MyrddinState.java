package fr.imac.myrddin.game.myrddin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public abstract class MyrddinState {
	
	protected Myrddin myrddin;
	protected Animation animation;
	protected float stateTime;
	protected boolean goRight;
	
	public MyrddinState(Myrddin myrddin, Animation animation) {
		super();
		this.myrddin = myrddin;
		this.animation = animation;
		this.stateTime = 0;
		this.goRight = true;
	}
	
	public void act(float delta) {
		stateTime += delta;
	}
	
	public void draw(Batch batch, float parentAlpha) {
		if (animation != null) {
			batch.draw(	animation.getKeyFrame(stateTime), myrddin.getX(), myrddin.getY(), myrddin.getX() + myrddin.getWidth() / 2f,
						myrddin.getY() + myrddin.getHeight() / 2f, myrddin.getWidth(), myrddin.getHeight(), myrddin.getScaleX(), myrddin.getScaleY(), myrddin.getRotation());
		}
	}
	
	/**
	 * 
	 * @return the position in pixel of where the magical power can start
	 */
	public abstract Vector2 getFirePos();
	
	protected void moveInTheAir() {
		if(Gdx.input.isKeyPressed(Input.Keys.D))
			myrddin.applyForce(new Vector2(myrddin.getMass() * 4, 0));
		else if(Gdx.input.isKeyPressed(Input.Keys.Q))
			myrddin.applyForce(new Vector2(-myrddin.getMass() * 4, 0));
		else 
			myrddin.setLinearVelocity(new Vector2(0, myrddin.getLinearVelocity().y));
	}
	
}
