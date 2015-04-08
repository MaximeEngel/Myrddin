package fr.imac.myrddin.physic;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.physic.Collidable.CollidableType;

public class PhysicTileFactory {
	
	
	private World world;

	/**
	 * 
	 * @param tileWidth width of the next created tile in pixel
	 * @param tileHeight height of the next created tile in pixel
	 * @param physicWorld 
	 */
	public PhysicTileFactory(World physicWorld) {
		this.world = physicWorld;
	}
	
	// METHODES
	
	/**
	 * 
	 * @param x x position in pixel of the tile
	 * @param y y position in pixel of the tile
	 * @param type type of the tile
	 */
	public void create(RectangleMapObject mapObject, String type) {
		PolygonShape shape = PhysicUtil.getRectangle((RectangleMapObject)mapObject);
		switch (type) {
		case "Solid":
			createSolid(shape);
			break;
		case "Climb":
			createClimb(shape);
		default:
			break;
		}
	}

	/**
	 * 
	 * @param x in meters
	 * @param y in meters
	 */
	private void createClimb(Shape shape) {
		Body body = PhysicUtil.createBody(shape, BodyType.StaticBody, false, PhysicUtil.createFixtureDef(0, 0, true), this.world);
	    body.setUserData(new PhysicTile(CollidableType.Climb));
	}

	/**
	 * 
	 * @param x in meters
	 * @param y in meters
	 */
	private void createSolid(Shape shape) {
		Body body = PhysicUtil.createBody(shape, BodyType.StaticBody, false, PhysicUtil.createFixtureDef(0, 0, false), this.world);
	    body.setUserData(new PhysicTile(CollidableType.Solid));
	}		
	
	// GETTERS - SETTERS

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}	
}
