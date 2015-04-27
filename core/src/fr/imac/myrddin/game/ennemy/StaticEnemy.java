package fr.imac.myrddin.game.ennemy;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.myrddin.Myrddin;
import fr.imac.myrddin.physic.Collidable;
import fr.imac.myrddin.physic.PhysicActor;
import fr.imac.myrddin.physic.PhysicUtil;

public class StaticEnemy extends PhysicActor implements Enemy {	

	AtlasRegion atlasRegion;

	public StaticEnemy(Vector2 pos) {
		super(new Rectangle(pos.x, pos.y, 64, 47), new Rectangle(6, 0, 50, 32), BodyType.StaticBody, PhysicUtil.createFixtureDef(10f, 0f, false), true);
		init();
	}
	
	public void init() {
		TextureAtlas atlas = (TextureAtlas) MyrddinGame.assetManager.get("enemy/enemy.atlas", TextureAtlas.class);
		atlasRegion = atlas.findRegion("pics");
	}

	@Override
	public CollidableType getCollidableType() {
		return CollidableType.Ennemy;
	}

	@Override
	public int getMeleeDamage() {
		return 1;
	}

	@Override
	public void beginContact(Contact contact, Collidable other) {
		if(other.getCollidableType() == CollidableType.Myrddin) {
			Myrddin myrddin = (Myrddin) other;
			
			Vector2 bumpImpulse = new Vector2(Integer.signum((int) (myrddin.getX() + myrddin.getWidth() / 2f - (getX() + getWidth() /2f))) * 5f, 3f).scl(myrddin.getMass());
			myrddin.bump(bumpImpulse);
			
			myrddin.hurtedBy(getMeleeDamage());
		}

	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(atlasRegion, getX(), getY());
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

	@Override
	public boolean obstructBulletOf(PhysicActor owner) {
		return owner.getCollidableType() != CollidableType.Ennemy;
	}
	
//	// EXTERNALISATION
//
//	@Override
//	public void writeExternal(ObjectOutput out) throws IOException {
//		super.writeExternal(out);
//	}
//
//	@Override
//	public void readExternal(ObjectInput in) throws IOException,
//			ClassNotFoundException {
//		// TODO Auto-generated method stub
//		super.readExternal(in);
//	}
	
	

}
