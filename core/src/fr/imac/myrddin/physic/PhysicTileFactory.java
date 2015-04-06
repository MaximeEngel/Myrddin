package fr.imac.myrddin.physic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.physic.Collidable.CollidableType;

public class PhysicTileFactory {
	
	/**
	 * width in meters
	 */
	private float tileWidth;
	/**
	 * height in meters
	 */
	private float tileHeight;
	private World world;

	/**
	 * 
	 * @param tileWidth width of the next created tile in pixel
	 * @param tileHeight height of the next created tile in pixel
	 * @param physicWorld 
	 */
	public PhysicTileFactory(float tileWidth, float tileHeight, World physicWorld) {
		this.tileWidth = tileWidth * MyrddinGame.GAME_TO_PHYSIC;
		this.tileHeight = tileHeight * MyrddinGame.GAME_TO_PHYSIC;
		this.world = physicWorld;
	}
	
	// METHODES
	
	/**
	 * 
	 * @param x x position in pixel of the tile
	 * @param y y position in pixel of the tile
	 * @param type type of the tile
	 */
	public void create(int x, int y, String type) {
		Vector2 pos = new Vector2(x * MyrddinGame.GAME_TO_PHYSIC, y * MyrddinGame.GAME_TO_PHYSIC);
		switch (type) {
		case "Solid":
			createSolid(pos);
			break;
		case "Climb":
			createClimb(pos);
		default:
			break;
		}
	}

	/**
	 * 
	 * @param x in meters
	 * @param y in meters
	 */
	private void createClimb(Vector2 pos) {
		Body body = PhysicUtil.createRectangle(pos, this.tileWidth, this.tileHeight, BodyType.StaticBody, 10, 0, true, this.world);
	    body.setUserData(new PhysicTile(CollidableType.Climb));
	}

	/**
	 * 
	 * @param x in meters
	 * @param y in meters
	 */
	private void createSolid(Vector2 pos) {
		Body body = PhysicUtil.createRectangle(pos, this.tileWidth, this.tileHeight, BodyType.StaticBody, 10, 0, false, this.world);
	    body.setUserData(new PhysicTile(CollidableType.Solid));
	}		
	
	// GETTERS - SETTERS

	public float getTileWidth() {
		return tileWidth;
	}

	public void setTileWidth(float tileWidth) {
		if (tileWidth > 0)
			this.tileWidth = tileWidth;
	}

	public float getTileHeight() {
		return tileHeight;
	}

	public void setTileHeight(float tileHeight) {
		if (tileHeight > 0)
			this.tileHeight = tileHeight;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}	
}
