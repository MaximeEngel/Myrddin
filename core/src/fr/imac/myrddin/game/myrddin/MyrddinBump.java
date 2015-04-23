package fr.imac.myrddin.game.myrddin;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;

import fr.imac.myrddin.MyrddinGame;

public class MyrddinBump extends MyrddinState {
	
	public static final float BUMPED_TIME = 0.8f;
	
	float elapsedTime = 0;

	public MyrddinBump(Myrddin myrddin, Vector2 impulse) {
		super(myrddin, new Animation(0.2f, MyrddinGame.assetManager.get("myrddin/myrddin.atlas", TextureAtlas.class).findRegions("iddle"), PlayMode.LOOP));
		myrddin.setLinearVelocity(new Vector2());
		myrddin.applyImpulse(impulse);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		elapsedTime += delta;
		if (elapsedTime > BUMPED_TIME) {
			myrddin.setMyrddinState(new MyrddinIddle(myrddin));
		}
	}

	@Override
	public StateType getStateType() {
		return StateType.Bump;
	}

}
