package fr.imac.myrddin.game.myrddin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import fr.imac.myrddin.MyrddinGame;

@SuppressWarnings("unused")
public class MyrddinDuck extends MyrddinState {

	public MyrddinDuck(Myrddin myrddin) {
		super(myrddin, null);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if (!Gdx.input.isKeyPressed(Input.Keys.S))
		{
			myrddin.setMyrddinState(new MyrddinIddle(myrddin));
		}
	}	

	@Override
	public void setNewRectBox() {
		myrddin.setNewRectBox(new Rectangle(myrddin.getX(), myrddin.getY(), 48, 48), new Rectangle(5, 5, 38, 43));
	}

	@Override
	public Vector2 getFirePos() {
		return null;
	}

}
