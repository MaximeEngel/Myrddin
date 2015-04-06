package fr.imac.myrddin.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

import fr.imac.myrddin.physic.Collidable;
import fr.imac.myrddin.physic.PhysicActor;
import fr.imac.myrddin.physic.PhysicUtil;

public class Myrddin extends Character {
	
	Texture texture = new Texture(Gdx.files.internal("set/tmw_desert_spacing.png"));

	public Myrddin(int initialLife, Vector2 pos, World world) {
		super(PhysicUtil.createRectangle(pos, 48f, 96f, BodyType.DynamicBody, 100f, 0.05f, false, world), 3);
		this.setBounds(pos.x, pos.y, 48f, 96f);
	}
	
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(texture, getX(), getY(), getWidth(), getHeight());
	}



	@Override
	public void collideWith(Collidable collidable) {
		// TODO Auto-generated method stub

	}

	@Override
	public CollidableType getCollidableType() {
		return CollidableType.Myrddin;
	}

}
