package fr.imac.myrddin.game.ennemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;

import fr.imac.myrddin.MyrddinGame;

public class EnemyIddle extends EnemyState {

	public EnemyIddle(EnnemyShooter enemy) {
		super(enemy, new Animation(0.2f, MyrddinGame.assetManager.get("enemy/enemy.atlas", TextureAtlas.class).findRegions("iddle"), PlayMode.LOOP));
	}

	@Override
	public EnemyStateType getStateType() {
		return EnemyStateType.Iddle;
	}	

}
