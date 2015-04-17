package fr.imac.myrddin.game.ennemy;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.Character;
import fr.imac.myrddin.game.GameScreen;
import fr.imac.myrddin.game.MagicWeapon;
import fr.imac.myrddin.game.MagicWeaponOwner;
import fr.imac.myrddin.game.magic.MagicState;
import fr.imac.myrddin.game.myrddin.Myrddin;
import fr.imac.myrddin.physic.Collidable;
import fr.imac.myrddin.physic.Collidable.CollidableType;
import fr.imac.myrddin.physic.PhysicActor;
import fr.imac.myrddin.physic.PhysicUtil;

public class TowerEnemy extends EnnemyShooter {
	
	/**
	 * Only use it for externalization.
	 */
	public TowerEnemy() {
		super();
	}
	
	public TowerEnemy(Vector2 pos, MagicState magicState, GameScreen gameScreen) {
		super(new Rectangle(pos.x, pos.y, 50, 50), new Rectangle(5, 0, 40, 50), BodyType.StaticBody, PhysicUtil.createFixtureDef(10f, 0f, false), true, 3, magicState, gameScreen);

	}
	
	
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
	}

	@Override
	public CollidableType getCollidableType() {
		return CollidableType.Ennemy;
	}
	
	// ENNEMY

	@Override
	public int getMeleeDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean obstructBulletOf(PhysicActor owner) {
		return true;
	}	
	
}
