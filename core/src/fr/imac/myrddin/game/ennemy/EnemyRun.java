package fr.imac.myrddin.game.ennemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import fr.imac.myrddin.MyrddinGame;

public class EnemyRun extends EnemyState {
	
	private int direction;
	private float timeToRun;
	private float percentDistanceToRunBeforeChange;

	public EnemyRun(DynamicEnemy enemy) {
		super(enemy, new Animation(0.2f, MyrddinGame.assetManager.get("enemy/enemy.atlas", TextureAtlas.class).findRegions("run"), PlayMode.LOOP));
		direction = MathUtils.randomSign();
		timeToRun = MathUtils.random(3, 12);
		percentDistanceToRunBeforeChange = MathUtils.random(0.75f, 1.01f);
	}
	
	

	@Override
	public void act(float delta) {
		super.act(delta);
		
		if (stateTime > timeToRun) {
			enemy.setEnemyState(new EnemyIddle(enemy));
			return;
		}
		
		if (changeDirection()) {			
			if(enemy.isMyrddinDetected()) {
				enemy.setEnemyState(new EnemyIddle(enemy));
				return;
			}
			
			direction *= -1;
			percentDistanceToRunBeforeChange = MathUtils.random(0.75f, 1.01f);
		}
				
		enemy.applyImpulse(new Vector2(direction * enemy.getMass() * 1, 0f));
	}

	private boolean changeDirection() {
		Rectangle runZone = enemy.getRunZone();
		float x = enemy.getX();
		
		return 	direction < 0 && x < runZone.getX() + runZone.getWidth() - runZone.getWidth() * percentDistanceToRunBeforeChange
				|| direction > 0 && x + enemy.getWidth() > runZone.getX() + runZone.getWidth() * percentDistanceToRunBeforeChange;
	}

	@Override
	public EnemyStateType getStateType() {
		return EnemyStateType.Run;
	}
	
	@Override
	public void setNewRectBox() {
		enemy.setNewRectBox(new Rectangle(enemy.getX(), enemy.getY(), 47, 96), new Rectangle(5, 5, 37, 91));		
	}

}
