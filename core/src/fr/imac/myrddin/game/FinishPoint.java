package fr.imac.myrddin.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.myrddin.Myrddin;
import fr.imac.myrddin.game.myrddin.MyrddinIddle;
import fr.imac.myrddin.physic.Collidable;
import fr.imac.myrddin.physic.PhysicActor;
import fr.imac.myrddin.physic.PhysicUtil;

public class FinishPoint extends PhysicActor {

	private boolean finish = false;
	private float timeFinish = 0;
	private float time = 0;
	private Animation animation;
	
	/**
	 * 
	 * @param pos in pixels
	 */
	public FinishPoint(Vector2 pos) {
		super(	new Rectangle(pos.x, pos.y, 60, 124), 
				new Rectangle(5, 0, 50, 120), 
				BodyType.StaticBody, 
				PhysicUtil.createFixtureDef(0f, 0f, 0f, true), 
				false
			);
		
		animation = new Animation(0.2f, MyrddinGame.assetManager.get("set/various.atlas", TextureAtlas.class).findRegions("finishPoint"), PlayMode.LOOP_REVERSED);
	}
	
	
	/**
	 * Update timeFinish
	 */
	@Override
	public void act(float delta) {
		super.act(delta);
		
		time += delta;
		if(finish)
			timeFinish += delta;
	}


	/**
	 * Try to detect if myrddin has reached the point
	 */
	@Override
	public void beginContact(Contact contact, Collidable other) {
		super.beginContact(contact, other);
		
		if(other.getCollidableType() == CollidableType.Myrddin) {
			finish = true;
			Myrddin myrddin = (Myrddin) other;
			myrddin.setMyrddinState(new MyrddinIddle(myrddin));
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		TextureRegion region = animation.getKeyFrame(time);
		batch.draw(region, getX(), getY());
	}	
	
	// GETTERS - SETTERS
	
	/**
	 * 
	 * @return if myrddin has win (touch the finish point)
	 */
	public boolean isFinish() {
		return this.finish;
	}
	
	/**
	 * 
	 * @param secs in seconds
	 * @return
	 */
	public boolean isTimeFinishPast(float secs) {
		return timeFinish >= secs;
	}
	
}
