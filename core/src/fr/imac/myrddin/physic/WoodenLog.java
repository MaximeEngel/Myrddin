package fr.imac.myrddin.physic;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import fr.imac.myrddin.MyrddinGame;

public class WoodenLog extends PhysicActor {
	
	AtlasRegion texture;
	
	public WoodenLog(Vector2 pos) {
		super(	new Rectangle(pos.x, pos.y, 99f, 99f), 
				new Rectangle(2, 8, 96f, 87f), 
				BodyType.DynamicBody,
				PhysicUtil.createFixtureDef(1500f, 0f, 1f, false), true
			);
		
		texture = MyrddinGame.assetManager.get("set/various.atlas", TextureAtlas.class).findRegion("wooden-log");
	}
	
	public WoodenLog() {
		texture = MyrddinGame.assetManager.get("set/various.atlas", TextureAtlas.class).findRegion("wooden-log");
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(texture, getX(), getY());
	}

	@Override
	public boolean isSavable() {
		return true;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// TODO Auto-generated method stub
		super.writeExternal(out);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		super.readExternal(in);
	}

	@Override
	public CollidableType getCollidableType() {
		return CollidableType.Solid;
	}	
	
	
}
