package fr.imac.myrddin.game.powerup;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.magic.MagicState;
import fr.imac.myrddin.game.myrddin.Myrddin;

public class PowerIce extends Powerup {
	
	/**
	 * Only for externalization
	 */
	public PowerIce() {
		
	}

	public PowerIce(Vector2 pos) {
		super(new Rectangle(pos.x, pos.y, 45, 65), new Rectangle(0, 5, 45, 40), "powerupice");
	}

	@Override
	public void pickedUp(Myrddin myrddin) {
		myrddin.setMagicState(MagicState.ICE);
		Sound sound = MyrddinGame.assetManager.get("sounds/collected.mp3", Sound.class);
		sound.play(0.3f);
	}

}
