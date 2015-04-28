package fr.imac.myrddin.game.myrddin;


import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import fr.imac.myrddin.game.Character;
import fr.imac.myrddin.game.GameScreen;
import fr.imac.myrddin.game.MagicWeapon;
import fr.imac.myrddin.game.MagicWeaponOwner;
import fr.imac.myrddin.game.magic.MagicBullet;
import fr.imac.myrddin.game.magic.MagicState;
import fr.imac.myrddin.game.myrddin.MyrddinState.StateType;
import fr.imac.myrddin.physic.Collidable;
import fr.imac.myrddin.physic.PhysicUtil;

public class Myrddin extends Character implements MagicWeaponOwner {
	
	/**
	 * time in seconds
	 */
	public static final float INITIAL_TIME_WITHOUT_FIRE = 0.3f;
	
	private MyrddinState myrddinState ;
	private MagicState magicState;
	private MagicWeapon<Myrddin> magicWeapon;
	
	private Shield shield;
	
	private Set<Body> climbs;
	
	
	// CONSTRUCTOR

	public Myrddin(Vector2 pos, MagicState magicState) {
		super(new Rectangle(pos.x, pos.y, 48, 96),	new Rectangle(5, 5, 38, 86), BodyType.DynamicBody, 
				PhysicUtil.createFixtureDef(100f, 0f, 0.03f, false), true, 3);
		
		myrddinState = new MyrddinIddle(this);
		myrddinState.setNewRectBox();
		
		this.magicState = magicState;
		magicWeapon = new MagicWeapon<Myrddin>(INITIAL_TIME_WITHOUT_FIRE, this);
		
		shield = new Shield(this);
		
		climbs = new HashSet<Body>();
		
	}
	
	public Myrddin() {
		climbs = new HashSet<Body>();
	}
	
	public void bump(Vector2 impulse) {
		this.setMyrddinState(new MyrddinBump(this, impulse));
	}
	
	// SCORE
	public int getScore() {
		return MathUtils.random(5000);
	}
	
	// WEAPON OWNER

	@Override
	public Vector2 getWeaponPos() {
		return myrddinState.getFirePos();
	}

	@Override
	public MagicState getMagicState() {
		return magicState;
	}
	
	// CHARACTER

	@Override
	public void kill() {
		setMyrddinState(new MyrddinDead(this));
	}
	
	
	
	// ACTOR
	
	@Override
	public void act(float delta) {
		super.act(delta);
		myrddinState.act(delta);
		
		// Fire
		magicWeapon.act(delta);
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
			fire(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
		
		shield.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		myrddinState.draw(batch, parentAlpha);
	}

	@Override
	public void setScaleX(float scaleX) {
		// TODO Auto-generated method stub
		super.setScaleX(scaleX);
	}

	public boolean isOutOfTheBox() {
		return getY() + getHeight() + 10 < 0;
	}

	
	
	// FIRE
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}

	/**
	 * 
	 * @param targetPos mouse position in pixel
	 */
	public void fire(Vector2 targetPos) {
		
		if(targetPos == null || shield.isEnable())
			return;
		
		Stage stage = this.getStage();
		OrthographicCamera camera = (OrthographicCamera) stage.getCamera();
		Vector3 tmp = camera.unproject(new Vector3(targetPos, 0));
		targetPos.x = tmp.x;
		targetPos.y = tmp.y;
		
		magicWeapon.fire(targetPos);
	}
		

	// GETTERS - SETTERS

	public MyrddinState getMyrddinState() {
		return myrddinState;
	}

	public void climb(Body climbOnThisBody) {		
		if (myrddinState.getStateTime() > 0.15f) {
			if(climbs.add(climbOnThisBody) && climbs.size() == 1)
				this.setMyrddinState(new MyrddinClimb(this));
			
		}
	}

	public void unclimb(Body unclimbOfThisBody) {		
		if(climbs.remove(unclimbOfThisBody) && climbs.size() == 0)
			this.setMyrddinState(new MyrddinRun(this, Integer.signum((int)this.getLinearVelocity().x)));
		
	}

	public void setMyrddinState(MyrddinState myrddinState) {
		if (myrddinState == null)
			throw new IllegalArgumentException("myrddinState must not be null");
		
		StateType stateType = this.myrddinState.getStateType();
		if(stateType == StateType.Dead)
			return;
		
		if(stateType != myrddinState.getStateType()) {
			this.myrddinState = myrddinState;
			this.myrddinState.setNewRectBox();
		}
	}
	
	public Shield getShield() {
		return this.shield;
	}
	
	// COLLISION

	@Override
	public CollidableType getCollidableType() {
		return CollidableType.Myrddin;
	}
	
	@Override
	public void beginContact(Contact contact, Collidable other) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endContact(Contact contact, Collidable other) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Collidable other) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, Collidable other) {
		// TODO Auto-generated method stub
		
	}
	
	// EXTERNALIZABLE

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
		
		out.writeObject(magicState.toString());
		out.writeObject(magicWeapon);
		out.writeObject(shield);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		super.readExternal(in);
		
		magicState = MagicState.valueOf(String.valueOf(in.readObject()));
		myrddinState = new MyrddinIddle(this);
		
		magicWeapon = (MagicWeapon<Myrddin>) in.readObject();
		shield = (Shield) in.readObject();
	}

	public boolean respawn() {
		return isOutOfTheBox() || myrddinState.getStateType() == StateType.Dead && myrddinState.getStateTime() > MyrddinDead.TIME_TO_BE_DEAD;
	}

}
