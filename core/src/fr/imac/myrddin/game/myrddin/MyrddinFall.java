package fr.imac.myrddin.game.myrddin;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import fr.imac.myrddin.MyrddinGame;

public class MyrddinFall extends MyrddinState {

	public MyrddinFall(Myrddin myrddin) {
		super(myrddin, new Animation(0.15f, MyrddinGame.assetManager.get("myrddin/myrddin.atlas", TextureAtlas.class).findRegions("fall"), PlayMode.NORMAL));
	}

	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		moveInTheAir();
		
		Vector2 velocity = myrddin.getLinearVelocity();		
		if (velocity.y == 0) {
			myrddin.setMyrddinState(new MyrddinIddle(myrddin));
			if (myrddin.getScaleX() < 1)
				myrddin.setNewRectBox(new Rectangle(myrddin.getX() + 25, myrddin.getY(), 32, 96), new Rectangle(5, 5, 22, 90));
		}
	}

	@Override
	public void setNewRectBox() {
		// if look right)
		if(myrddin.getScaleX() >= 1)
			myrddin.setNewRectBox(new Rectangle(myrddin.getX(), myrddin.getY(), 70, 105), new Rectangle(5, 5, 32, 80));
		else
			myrddin.setNewRectBox(new Rectangle(myrddin.getX(), myrddin.getY(), 70, 105), new Rectangle(30, 5, 32, 80));
	}

	@Override
	public StateType getStateType() {
		return StateType.Fall;
	}
	
	@Override
	public Vector2 getFirePos() {
		// Look right
		if(myrddin.getScaleX() >= 0)
			return new Vector2(myrddin.getCenterX(), myrddin.getCenterY()).add(0, -26);
		else
			return new Vector2(myrddin.getCenterX(), myrddin.getCenterY()).add(-30, -26);
	}
}
