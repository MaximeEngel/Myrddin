package fr.imac.myrddin.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

import fr.imac.myrddin.physic.Collidable;
import fr.imac.myrddin.physic.PhysicUtil;

public class Myrddin extends Character {
	
	Texture texture = new Texture(Gdx.files.internal("set/tmw_desert_spacing.png"));

	public Myrddin(Vector2 pos, World world) {
		super(new Rectangle(pos.x, pos.y, 48, 96),	new Rectangle(5, 5, 38, 86), BodyType.DynamicBody, 
				PhysicUtil.createFixtureDef(100f, 0.05f, false), true, world, 3);
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
