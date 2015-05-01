package fr.imac.myrddin.game.ennemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class EnemyState {
	
	public static final float MAX_VEL = 3;
	protected Animation animation;
	protected float stateTime;
	protected boolean goRight;
	protected DynamicEnemy enemy;
	
	public enum EnemyStateType {
		Iddle, Run, Dead;
	}
	
	public EnemyState(DynamicEnemy enemy, Animation animation) {
		super();
		this.enemy = enemy;
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
		
		Vector2 v = enemy.getLinearVelocity();
		if(Math.abs(v.x) > MAX_VEL)
			enemy.setLinearVelocity(new Vector2(Math.signum(v.x) * MAX_VEL, v.y));		
		
		orientTexture();
	}
	
	public void draw(Batch batch, float parentAlpha) {
		if (animation != null) {
			batch.draw(	animation.getKeyFrame(stateTime), 
						enemy.getX(),
						enemy.getY(), 
						enemy.getWidth() * 0.5f, 
						enemy.getHeight() * 0.5f, 
						enemy.getWidth(), 
						enemy.getHeight(), 
						enemy.getScaleX(), 
						enemy.getScaleY(), 
						enemy.getRotation()
					);
		}
	}

	public abstract EnemyStateType getStateType();

	public void setNewRectBox() {
		enemy.setNewRectBox(new Rectangle(enemy.getX(), enemy.getY(), 32, 96), new Rectangle(5, 5, 22, 91));		
	}
	
	// Called each frame to flip the texture to orient the look of the enemy in the good direction
	public void orientTexture() {
		int sens = -Integer.signum((int)enemy.getLinearVelocity().x);
		
		if (sens == 0)
			return;
		
		float oldSens = enemy.getScaleX();		
		enemy.setScale(sens, 1f);
		if(sens != oldSens)
			setNewRectBox();
	}
	
	public Vector2 getWeaponPos() {
		return new Vector2(enemy.getCenterX() - 30, enemy.getCenterY());
	}
	
}
