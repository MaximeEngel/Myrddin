package fr.imac.myrddin.physic;

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
		x *= MyrddinGame.GAME_TO_PHYSIC;
		y *= MyrddinGame.GAME_TO_PHYSIC;
		switch (type) {
		case "Solid":
			createSolid(x, y);
			break;
		case "Climb":
			createClimb(x, y);
		default:
			break;
		}
	}

	/**
	 * 
	 * @param x in meters
	 * @param y in meters
	 */
	private void createClimb(int x, int y) {
		Body body = createRectangleTile(x, y, BodyType.StaticBody, true);
	    body.setUserData(new PhysicTile(CollidableType.Climb));
	}

	/**
	 * 
	 * @param x in meters
	 * @param y in meters
	 */
	private void createSolid(int x, int y) {
	    Body body = createRectangleTile(x, y, BodyType.StaticBody, false);
	    body.setUserData(new PhysicTile(CollidableType.Solid));
	}
	
	/**
	 * 
	 * @param x in meters
	 * @param y in meters
	 */
	private Body createRectangleTile(int x, int y, BodyType bodyType, boolean isSensor) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);		
		bodyDef.type = bodyType;
		Body body = world.createBody(bodyDef);
		
	    PolygonShape shape = new PolygonShape();
	    shape.setAsBox(this.tileWidth, this.tileHeight);
	    
	    FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = shape;
	    fixtureDef.isSensor = isSensor;
	    body.createFixture(fixtureDef);
	    
	    shape.dispose();
	    return body;	    
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
