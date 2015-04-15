package fr.imac.myrddin.game.ennemy;
import com.badlogic.gdx.maps.objects.RectangleMapObject;

import fr.imac.myrddin.physic.PhysicActor;
import fr.imac.myrddin.physic.PhysicUtil;

public class EnnemyFactory {	
	
	public PhysicActor create(RectangleMapObject mapObject, String type){
		
		switch (type) {
		case "StaticEnnemy":
			return createStaticEnnemy(mapObject);
		default:
			break;
		}
		
		return null;
	}

	public PhysicActor createStaticEnnemy(RectangleMapObject mapObject) {
		return new StaticEnnemy(PhysicUtil.positionFromRectMapObject(mapObject));
	}
	
}
