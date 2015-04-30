package fr.imac.myrddin.game.powerup;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.GameScreen;
import fr.imac.myrddin.game.myrddin.Myrddin;

public class PowerSave extends Powerup {
	
	/**
	 * Only for externalization
	 */
	public PowerSave() {
		
	}

	public PowerSave(Vector2 pos) {
		super(new Rectangle(pos.x, pos.y, 45, 65), new Rectangle(0, 0, 45, 65), "save");
	}

	@Override
	public void pickedUp(Myrddin myrddin) {
		GameScreen gameScreen = (GameScreen) MyrddinGame.MYRDDIN_GAME.getScreen();
		gameScreen.instantSave();
	}

}