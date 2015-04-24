package fr.imac.myrddin.game.myrddin;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Rectangle;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.GameScreen;

public class MyrddinDead extends MyrddinState {
	
	public static final float TIME_TO_BE_DEAD = 3f;

	public MyrddinDead(Myrddin myrddin) {
		super(myrddin, new Animation(0.2f, MyrddinGame.assetManager.get("myrddin/myrddin.atlas", TextureAtlas.class).findRegions("death"), PlayMode.NORMAL));
		System.out.println("BECOME DEAD");
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		if (this.animation.isAnimationFinished(stateTime)) {
			// No slide to many time
			myrddin.getBody().setLinearVelocity(0, myrddin.getLinearVelocity().y);
		
			if (stateTime > TIME_TO_BE_DEAD) {
				GameScreen gameScreen = (GameScreen) myrddin.getStage();
				gameScreen.instantLoad();
			}
		}
	}

	@Override
	public StateType getStateType() {
		return StateType.Dead;
	}
	
	
	

	@Override
	public void orientTexture() {
		int sens = Integer.signum((int)myrddin.getLinearVelocity().x);
		
		if (sens == 0)
			return;
		
		float oldSens = myrddin.getScaleX();		
		myrddin.setScale(-sens, 1f);
		if(-sens != oldSens)
			setNewRectBox();
	}

	@Override
	public void setNewRectBox() {
		// if look right)
		if(myrddin.getScaleX() >= 1)
			myrddin.setNewRectBox(new Rectangle(myrddin.getX(), myrddin.getY(), 96, 96), new Rectangle(5, 5, 86, 20));
		else
			myrddin.setNewRectBox(new Rectangle(myrddin.getX(), myrddin.getY(), 96, 96), new Rectangle(5, 5, 86, 20));
	}
}
