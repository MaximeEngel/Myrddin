package fr.imac.myrddin.game.ennemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import fr.imac.myrddin.game.GameScreen;
import fr.imac.myrddin.game.ennemy.EnemyState.EnemyStateType;
import fr.imac.myrddin.game.magic.MagicState;
import fr.imac.myrddin.game.myrddin.Myrddin;
import fr.imac.myrddin.game.myrddin.MyrddinDead;
import fr.imac.myrddin.physic.PhysicUtil;

public class DynamicEnemy extends EnnemyShooter {
	
	private EnemyState enemyState;
	private Rectangle runZone;
	
	/**
	 * Only use it for externalization.
	 */
	public DynamicEnemy() {
		super();
		enemyState = new EnemyIddle(this);
	}
	
	/**
	 * 
	 * @param rectangle defines the run zone, spawn in the middle
	 * @param magicState
	 * @param gameScreen
	 */
	public DynamicEnemy(Rectangle rectangle, MagicState magicState, GameScreen gameScreen) {
		super(new Rectangle(rectangle.getX() + rectangle.getWidth() * 0.5f,rectangle.getY(), 32, 96), new Rectangle(5, 5, 22, 91), BodyType.DynamicBody, PhysicUtil.createFixtureDef(100f, 0f, 0.03f, false), true, 2, magicState, gameScreen);
		enemyState = new EnemyIddle(this);
		runZone = rectangle;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		enemyState.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		enemyState.draw(batch, parentAlpha);
	}
	
	
	
	@Override
	public boolean canShootMyrddin() {
		Myrddin myrddin = gameScreen.getMyrddin();
		float myrddinX = myrddin.getCenterX();
		
		// Minus before getScaleX() because the draw of the enemy is inverted
		boolean lookMyrddin = Integer.signum((int) -getScaleX()) == Integer.signum((int) (myrddinX - getCenterX()));		
		return lookMyrddin && super.canShootMyrddin();
	}

	@Override
	public void kill() {
		setEnemyState(new EnemyDead(this));
	}	

	@Override
	public Vector2 getWeaponPos() {
		return enemyState.getWeaponPos();
	}

	public void setEnemyState(EnemyState enemyState) {
		if (enemyState == null)
			throw new IllegalArgumentException("enemyState must not be null");
		
		EnemyStateType stateType = this.enemyState.getStateType();
		if(stateType == EnemyStateType.Dead)
			return;
		
		if(stateType != enemyState.getStateType()) {
			this.enemyState = enemyState;
			this.enemyState.setNewRectBox();
		}
	}
	
	public Rectangle getRunZone() {
		return runZone;
	}	
}
