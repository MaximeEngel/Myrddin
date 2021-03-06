package fr.imac.myrddin.game.powerup;
import java.util.Timer;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.GameScreen;
import fr.imac.myrddin.game.myrddin.Myrddin;

public class PowerSave extends Powerup {
	
	private float lastTimeSaved = 0 ;
	
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
		this.enable = false;
		GameScreen gameScreen = (GameScreen) MyrddinGame.MYRDDIN_GAME.getScreen();
		gameScreen.instantSave();
		Sound sound = MyrddinGame.assetManager.get("sounds/collected.mp3", Sound.class);
		sound.play(0.3f);
	}

}