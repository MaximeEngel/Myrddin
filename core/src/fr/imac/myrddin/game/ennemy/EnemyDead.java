package fr.imac.myrddin.game.ennemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import fr.imac.myrddin.MyrddinGame;

public class EnemyDead extends EnemyState {

	public EnemyDead(DynamicEnemy enemy) {
		super(enemy, new Animation(0.05f, MyrddinGame.assetManager.get("enemy/enemy.atlas", TextureAtlas.class).findRegions("death"), PlayMode.NORMAL));
	}
	
	

	@Override
	public void act(float delta) {
		super.act(delta);
		
		if (this.animation.isAnimationFinished(stateTime))
			enemy.dispose();
	}



	@Override
	public EnemyStateType getStateType() {
		return EnemyStateType.Dead;
	}
	
	@Override
	public void setNewRectBox() {
		enemy.setNewRectBox(new Rectangle(enemy.getX(), enemy.getY(), 56, 96), new Rectangle(5, 5, 46, 91));		
	}
	
	@Override
	public Vector2 getWeaponPos() {
		return null;
	}

}
