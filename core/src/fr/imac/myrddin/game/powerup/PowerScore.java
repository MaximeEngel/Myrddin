package fr.imac.myrddin.game.powerup;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import fr.imac.myrddin.game.myrddin.Myrddin;

public class PowerScore extends Powerup {
	
	public static final int ORB_SIZE = 16;
	
	/**
	 * Only for externalization
	 */
	public PowerScore() {
		
	}

	public PowerScore(Vector2 pos) {
		super(new Rectangle(pos.x, pos.y, ORB_SIZE, ORB_SIZE), new Rectangle(0, 0, ORB_SIZE, ORB_SIZE), "orb");
	}

	@Override
	public void pickedUp(Myrddin myrddin) {
		myrddin.addScore(10);
		this.enable = false;
	}

}
