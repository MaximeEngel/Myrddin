package fr.imac.myrddin.game.powerup;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.myrddin.Myrddin;
import fr.imac.myrddin.physic.Collidable;
import fr.imac.myrddin.physic.PhysicActor;
import fr.imac.myrddin.physic.PhysicUtil;

public abstract class Powerup extends PhysicActor {
	
	protected boolean enable = true ;
	protected AtlasRegion atlasRegion;
	
	/**
	 * Exist only for externalization
	 */
	public Powerup() {
		super();
	}

	public Powerup(Rectangle bounds, Rectangle collisionBox, String atlasRegionName) {
		this(bounds, collisionBox, BodyType.StaticBody, PhysicUtil.createFixtureDef(0f, 0f, 0f, true), true, atlasRegionName);
	}

	public Powerup(Rectangle bounds, Rectangle collisionBox, BodyType bodyType,	FixtureDef fixtureDef, boolean preventRotation, String atlasRegionName) {
		super(bounds, collisionBox, bodyType, fixtureDef, preventRotation);
		loadAtlasRegion(atlasRegionName);
	}
	
	private void loadAtlasRegion(String name) {
		TextureAtlas atlas = MyrddinGame.assetManager.get("power/power.atlas", TextureAtlas.class);
		atlasRegion = atlas.findRegion(name);
	}
	
	/**
	 * Call each time myrddin pick up the power up.
	 * @param other
	 */
	public abstract void pickedUp(Myrddin other);

	@Override
	public void beginContact(Contact contact, Collidable other) {
		super.preSolve(contact, other);
		
		if(other.getCollidableType() == CollidableType.Myrddin)
			pickedUp((Myrddin) other); 
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(!enable)
			dispose();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(atlasRegion, getX(), getY());
	}
	
	// EXTERNALIZATION

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
		
		out.writeBoolean(enable);
		out.writeObject(atlasRegion.name);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		super.readExternal(in);
		
		enable = in.readBoolean();
		loadAtlasRegion((String) in.readObject());
	}

	@Override
	public boolean isSavable() {
		return true;
	}
	
	
	
	
	
}
