package fr.imac.myrddin.game.myrddin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class MyrddinState {
	
	public static final float MAX_VEL = 3;
	public enum StateType {
		Iddle, Bump, Climb, Run, Duck, Fall, Jump, Dead;
	}
	
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
	
	/**
	 * Update time for animation and limit velocity
	 * @param delta
	 */
	public void act(float delta) {
		stateTime += delta;
		
		Vector2 v = myrddin.getLinearVelocity();
		if(Math.abs(v.x) > MAX_VEL)
			myrddin.setLinearVelocity(new Vector2(Math.signum(v.x) * MAX_VEL, v.y));		
		
		orientTexture();
	}
	
	public void draw(Batch batch, float parentAlpha) {
		if (animation != null) {
			batch.draw(	animation.getKeyFrame(stateTime), 
						myrddin.getX(),
						myrddin.getY(), 
						myrddin.getWidth() * 0.5f, 
						myrddin.getHeight() * 0.5f, 
						myrddin.getWidth(), 
						myrddin.getHeight(), 
						myrddin.getScaleX(), 
						myrddin.getScaleY(), 
						myrddin.getRotation()
					);
		}
	}
	
	/**
	 * 
	 * @return the origin position in pixel of the fire. Null is return if myrddin can't fire this time.
	 */
	public Vector2 getFirePos() {
		// Look right
		if(myrddin.getScaleX() >= 0)
			return new Vector2(myrddin.getCenterX(), myrddin.getCenterY()).add(15, -2);
		else
			return new Vector2(myrddin.getCenterX(), myrddin.getCenterY()).add(-20, -2);
	}
	
	/**
	 * Default new rect box
	 */
	public void setNewRectBox() {
		myrddin.setNewRectBox(new Rectangle(myrddin.getX(), myrddin.getY(), 32, 96), new Rectangle(5, 5, 22, 90));
	}
	
	/**
	 * Must be call to move the character in the air, cancel the gravity if needed
	 */
	protected void moveInTheAir() {
		if(Gdx.input.isKeyPressed(Input.Keys.D))
			myrddin.applyImpulse(new Vector2(myrddin.getMass() * 2, 0));
		else if(Gdx.input.isKeyPressed(Input.Keys.Q))
			myrddin.applyImpulse(new Vector2(-myrddin.getMass() * 2, 0));
		else 
			myrddin.setLinearVelocity(new Vector2(0, myrddin.getLinearVelocity().y));
	}
	
	public abstract StateType getStateType();
	
	// Called each frame to flip the texture to orient the look of myrddin in the good direction
	public void orientTexture() {
		int sens = Integer.signum((int)myrddin.getLinearVelocity().x);
		
		if (sens == 0)
			return;
		
		float oldSens = myrddin.getScaleX();		
		myrddin.setScale(sens, 1f);
		if(sens != oldSens)
			setNewRectBox();
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}
	
	
}
