package fr.imac.myrddin.game.ennemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import fr.imac.myrddin.MyrddinGame;

public class EnemyIddle extends EnemyState {
	
	private float timeToIddle;

	public EnemyIddle(DynamicEnemy enemy) {
		super(enemy, new Animation(0.2f, MyrddinGame.assetManager.get("enemy/enemy.atlas", TextureAtlas.class).findRegions("iddle"), PlayMode.LOOP));
		timeToIddle = MathUtils.random(4, 7);
		enemy.setLinearVelocity(new Vector2());
	}
	
	

	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(stateTime > timeToIddle) {
			if(!enemy.isMyrddinDetected()) {
				enemy.setEnemyState(new EnemyRun(enemy));
			}
		}
	}



	@Override
	public EnemyStateType getStateType() {
		return EnemyStateType.Iddle;
	}	

}
