package fr.imac.myrddin.game.powerup;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import fr.imac.myrddin.game.magic.MagicState;
import fr.imac.myrddin.game.myrddin.Myrddin;

public class PowerFire extends Powerup {

	/**
	 * Only for externalization
	 */
	public PowerFire() {
		
	}
	
	public PowerFire(Vector2 pos) {
		super(new Rectangle(pos.x, pos.y, 45, 65), new Rectangle(0, 0, 45, 65), "powerupfire");
	}

	@Override
	public void pickedUp(Myrddin myrddin) {
		myrddin.setMagicState(MagicState.FIRE);
	}

}
