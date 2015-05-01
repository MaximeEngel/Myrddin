package fr.imac.myrddin.game.ennemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Rectangle;

import fr.imac.myrddin.MyrddinGame;

public class EnemyRun extends EnemyState {

	public EnemyRun(EnnemyShooter enemy) {
		super(enemy, new Animation(0.2f, MyrddinGame.assetManager.get("enemy/enemy.atlas", TextureAtlas.class).findRegions("run"), PlayMode.LOOP));
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
