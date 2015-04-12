package fr.imac.myrddin.game.myrddin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class MyrddinState {
	
	public static final float MAX_VEL = 3;
	
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
		
		setNewRectBox();
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
			
	}
	
	public void draw(Batch batch, float parentAlpha) {
		if (animation != null) {
			batch.draw(	animation.getKeyFrame(stateTime), myrddin.getX(), myrddin.getY(), myrddin.getX() + myrddin.getWidth() / 2f,
						myrddin.getY() + myrddin.getHeight() / 2f, myrddin.getWidth(), myrddin.getHeight(), myrddin.getScaleX(), myrddin.getScaleY(), myrddin.getRotation());
		}
	}
	
	/**
	 * 
	 * @return the origin position in pixel of the fire. Null is return if myrddin can't fire this time.
	 */
	public Vector2 getFirePos() {
		return new Vector2(myrddin.getX(), myrddin.getY()).add(30, 70);
	}
	
	/**
	 * Default new rect box
	 */
	public void setNewRectBox() {
		myrddin.setNewRectBox(new Rectangle(myrddin.getX(), myrddin.getY(), 48, 96), new Rectangle(5, 5, 38, 86));
	}
	
	protected void moveInTheAir() {
		if(Gdx.input.isKeyPressed(Input.Keys.D))
			myrddin.applyImpulse(new Vector2(myrddin.getMass() * 2, 0));
		else if(Gdx.input.isKeyPressed(Input.Keys.Q))
			myrddin.applyImpulse(new Vector2(-myrddin.getMass() * 2, 0));
		else 
			myrddin.setLinearVelocity(new Vector2(0, myrddin.getLinearVelocity().y));
	}
	
}
