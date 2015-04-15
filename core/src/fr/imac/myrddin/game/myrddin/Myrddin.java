package fr.imac.myrddin.game.myrddin;


import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

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
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import fr.imac.myrddin.game.Character;
import fr.imac.myrddin.game.GameScreen;
import fr.imac.myrddin.game.magic.MagicBullet;
import fr.imac.myrddin.game.magic.MagicState;
import fr.imac.myrddin.physic.Collidable;
import fr.imac.myrddin.physic.PhysicUtil;

public class Myrddin extends Character {
	
	/**
	 * time in seconds
	 */
	public static final float INITIAL_TIME_WITHOUT_FIRE = 0.3f;
	
	private float timeWithoutFire = INITIAL_TIME_WITHOUT_FIRE;
	private float lastFire = INITIAL_TIME_WITHOUT_FIRE;
	
	private Texture texture = new Texture(Gdx.files.internal("set/tmw_desert_spacing.png"));
	
	private MyrddinState myrddinState ;
	private MagicState magicState;
	
	// CONSTRUCTOR

	public Myrddin(Vector2 pos) {
		super(new Rectangle(pos.x, pos.y, 48, 96),	new Rectangle(5, 5, 38, 86), BodyType.DynamicBody, 
				PhysicUtil.createFixtureDef(100f, 0f, false), true, 3);
		
		myrddinState = new MyrddinIddle(this);
		magicState = MagicState.POWER_1;
	}
	
	public Myrddin() {
		
	}
	
	public void bump(Vector2 impulse) {
		this.setMyrddinState(new MyrddinBump(this, impulse));
	}
	
	// CHARACTER

	
	public void kill() {
		
		GameScreen gameScreen = (GameScreen) getStage();
		gameScreen.instantLoad();
	}
	
	
	
	// ACTOR
	
	@Override
	public void act(float delta) {
		super.act(delta);
		myrddinState.act(delta);
		
		// Fire
		this.lastFire += delta;
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
			fire(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
		
		// KILL
		if(isKilled())
			kill();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(texture, getX(), getY(), getWidth(), getHeight());
	}
	
	public boolean isOutOfTheBox() {
		return getY() + getHeight() + 10 < 0;
	}

	
	// FIRE
	
	public void fire(Vector2 targetPos) {
		Vector2 originFire = myrddinState.getFirePos();
		
		if(!canFire() || targetPos == null)
			return;
		
		Stage stage = this.getStage();
		OrthographicCamera camera = (OrthographicCamera) stage.getCamera();
		Vector3 tmp = camera.unproject(new Vector3(targetPos, 0));
		targetPos.x = tmp.x;
		targetPos.y = tmp.y;
		
		Vector2 directionFire = targetPos.sub(originFire);
		MagicBullet magicBullet = new MagicBullet(originFire, directionFire, magicState, this);
		stage.addActor(magicBullet);
		this.lastFire = 0f;
	}
	
	public boolean canFire() {
		return 	myrddinState.getFirePos() != null
				&& lastFire >= timeWithoutFire;
	}	

	// GETTERS - SETTERS

	public MyrddinState getMyrddinState() {
		return myrddinState;
	}

	public void climb() {
		if (!(myrddinState instanceof MyrddinClimb))
			this.setMyrddinState(new MyrddinClimb(this));
	}

	public void setMyrddinState(MyrddinState myrddinState) {
		if (myrddinState == null)
			throw new IllegalArgumentException("myrddinState must not be null");
		
		if(this.myrddinState.getStateType() != myrddinState.getStateType())
			this.myrddinState = myrddinState;
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
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		super.readExternal(in);
		
		magicState = MagicState.valueOf(String.valueOf(in.readObject()));
		myrddinState = new MyrddinIddle(this);
	}

}
