package fr.imac.myrddin.game.powerup;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import fr.imac.myrddin.physic.PhysicUtil;

public class PowerFactory {
	
	static final float ORB_SPACING = 32;
	
	public Powerup[] create(RectangleMapObject mapObject, String type){
		switch (type) {
			case "PowerFire":
				return createPowerFire(mapObject);
			case "PowerIce":
				return createPowerIce(mapObject);
			case "PowerSave":
				return createPowerSave(mapObject);
			case "PowerHealth":
				return createPowerHealth(mapObject);
			case "PowerScore":
				return createPowerScore(mapObject);
			default:
				break;
		}
		
		return null;
	}

	private Powerup[] createPowerScore(RectangleMapObject mapObject) {
		Rectangle rectangle = mapObject.getRectangle();
		Vector2 pos = new Vector2();
		rectangle.getPosition(pos);
		float width = rectangle.getWidth();
		
		float orbTotalSize = ORB_SPACING + PowerScore.ORB_SIZE;
		int nbOrbs = MathUtils.floor(width / orbTotalSize);
		Powerup tab[] = new Powerup[nbOrbs];
		
		for(int i = 0; i < nbOrbs; ++i) {
			tab[i] = new PowerScore(new Vector2(pos.x + i * orbTotalSize, pos.y));
		}
		
		return tab;
	}

	private Powerup[] createPowerHealth(RectangleMapObject mapObject) {
		Powerup tab[] = {new PowerHealth(PhysicUtil.positionFromRectMapObject(mapObject))};
		return tab;
	}

	private Powerup[] createPowerSave(RectangleMapObject mapObject) {
		Powerup tab[] = {new PowerSave(PhysicUtil.positionFromRectMapObject(mapObject))};
		return tab;
	}

	private Powerup[] createPowerIce(RectangleMapObject mapObject) {
		Powerup tab[] = {new PowerIce(PhysicUtil.positionFromRectMapObject(mapObject))};
		return tab;
	}

	private Powerup[] createPowerFire(RectangleMapObject mapObject) {
		Powerup tab[] = { new PowerFire(PhysicUtil.positionFromRectMapObject(mapObject)) };
		return tab;
	}
}
