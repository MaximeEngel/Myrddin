package fr.imac.myrddin.game.ennemy;
import com.badlogic.gdx.maps.objects.RectangleMapObject;

import fr.imac.myrddin.game.GameScreen;
import fr.imac.myrddin.game.magic.MagicState;
import fr.imac.myrddin.physic.PhysicActor;
import fr.imac.myrddin.physic.PhysicUtil;

public class EnemyFactory {	
	
	GameScreen gameScreen;	
	
	public EnemyFactory(GameScreen gameScreen) {
		super();
		this.gameScreen = gameScreen;
	}

	public PhysicActor create(RectangleMapObject mapObject, String type){
		
		switch (type) {
			case "DynamicEnemy":
				return createDynamicEnnemy(mapObject);
			case "TowerEnemy":
				return createTowerEnnemy(mapObject);
			case "StaticEnemy":
			default:
				return createStaticEnnemy(mapObject);
		}
	}

	private PhysicActor createTowerEnnemy(RectangleMapObject mapObject) {
		MagicState magicState = MagicState.valueOf(mapObject.getProperties().get("magicState", String.valueOf(MagicState.FIRE), String.class));
		return new TowerEnemy(PhysicUtil.positionFromRectMapObject(mapObject), magicState, gameScreen);
	}
	
	private PhysicActor createDynamicEnnemy(RectangleMapObject mapObject) {
		MagicState magicState = MagicState.valueOf(mapObject.getProperties().get("magicState", String.valueOf(MagicState.FIRE), String.class));
		return new DynamicEnemy(mapObject.getRectangle(), magicState, gameScreen);
	}

	public PhysicActor createStaticEnnemy(RectangleMapObject mapObject) {
		return new StaticEnemy(PhysicUtil.positionFromRectMapObject(mapObject));
	}
	
}
