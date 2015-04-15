package fr.imac.myrddin.game;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.magic.MagicBullet;
import fr.imac.myrddin.game.myrddin.Myrddin;
import fr.imac.myrddin.physic.Collidable;
import fr.imac.myrddin.physic.Collidable.CollidableType;
import fr.imac.myrddin.physic.PhysicActor;
import fr.imac.myrddin.physic.PhysicTileFactory;
import fr.imac.myrddin.physic.PhysicUtil;

/*
 * Handle the mechanism of a level.
 */
public class GameScreen extends Stage implements Screen, ContactListener {
	
	public static World physicWorld;
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer mapRenderer;
	private float mapWidth;
	
	private Myrddin myrddin;
	
	/**
	 * 
	 * @param level numero of the level to instanciate and play
	 */
	public GameScreen(int level) {
		super(new FitViewport(1280, 720));
		
		// Generate graphics of the map
		TmxMapLoader mapLoader = new TmxMapLoader();
		tiledMap = mapLoader.load("lvl/"+level+".tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, getBatch());
		mapRenderer.setView((OrthographicCamera) getCamera());
		mapWidth = mapWidth();
		System.out.println(mapWidth);
		
		// Generate physic of the map
		physicWorld = new World(new Vector2(0, -9.1f),  true);
		physicWorld.setContactListener(this);
		createPhysicWorld(tiledMap);
		
		myrddin = new Myrddin(new Vector2(510f, 850f));
		this.addActor(myrddin);
		
		Gdx.input.setInputProcessor(this);
		instantSave();
	}
	
	
	private void createPhysicWorld(TiledMap tiledMap) {		
		if (tiledMap != null)
		{
			PhysicTileFactory physicTileFactory = new PhysicTileFactory(this.physicWorld);
			MapObjects objects = tiledMap.getLayers().get("CollisionTile").getObjects();
			for (MapObject mapObject : objects) {
				physicTileFactory.create((RectangleMapObject) mapObject, mapObject.getProperties().get("type", "none", String.class));
			}
			
			// Create limit world
			RectangleMapObject leftBorder = new RectangleMapObject(-10, 0, 10, MyrddinGame.HEIGHT);
			physicTileFactory.create(leftBorder, "Solid");
			RectangleMapObject rightBorder = new RectangleMapObject(mapWidth, 0, 10, MyrddinGame.HEIGHT);
			physicTileFactory.create(rightBorder, "Solid");
		}
	}
	
	private float mapWidth() {
		MapProperties mapProperties = tiledMap.getProperties();		
		int nbHorizontalTiles = mapProperties.get("width", 0, Integer.class);
		int tileWidth = mapProperties.get("tilewidth", 0, Integer.class);
		
		return tileWidth * nbHorizontalTiles;
	}
	
	
	
	// ___ SCREEN ___
	
	@Override
	public void draw() {
		mapRenderer.render();
		super.draw();
		
		getBatch().begin();
		Box2DDebugRenderer debug = new Box2DDebugRenderer();
		Matrix4 matrixDebug = new Matrix4(getCamera().combined);
		matrixDebug.scale(MyrddinGame.PHYSIC_TO_GAME, MyrddinGame.PHYSIC_TO_GAME, 1);
		debug.render(physicWorld, matrixDebug);
		getBatch().end();
	}



	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		physicWorld.step(Gdx.graphics.getDeltaTime(), 6, 2);
		super.act(delta);
		if(myrddin.isOutOfTheBox())
			instantLoad();
		
		if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && Gdx.input.isKeyPressed(Input.Keys.S) )
			instantSave();
		
		updateCamera();
	}



	private void updateCamera() {
		OrthographicCamera camera = (OrthographicCamera) getCamera();
		Vector3 position = camera.position;
		
		position.x = myrddin.getX();
		float minX = MyrddinGame.WIDTH / 2;
		float maxX = mapWidth - minX;
		if(position.x < minX )
			position.x = minX;
		else if(position.x > maxX)
			position.x = maxX;
		
		position.set(position.x, position.y, position.z);
		mapRenderer.setView((OrthographicCamera) getCamera());		
	}


	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		this.act(delta);
		this.draw();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}
	
	// Input processor


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}


	// CONTACT LISTENER

	@Override
	public void beginContact(Contact contact) {
		Collidable collidable = (Collidable) contact.getFixtureA().getBody().getUserData();
		Collidable other = (Collidable) contact.getFixtureB().getBody().getUserData();
		collidable.beginContact(contact, other);
		other.beginContact(contact, collidable);
		
	}


	@Override
	public void endContact(Contact contact) {
		Collidable collidable = (Collidable) contact.getFixtureA().getBody().getUserData();
		Collidable other = (Collidable) contact.getFixtureB().getBody().getUserData();
		collidable.endContact(contact, other);
		other.endContact(contact, collidable);
	}


	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		Collidable collidable = (Collidable) contact.getFixtureA().getBody().getUserData();
		Collidable other = (Collidable) contact.getFixtureB().getBody().getUserData();
		collidable.preSolve(contact, other);
		other.preSolve(contact, collidable);		
	}


	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		Collidable collidable = (Collidable) contact.getFixtureA().getBody().getUserData();
		Collidable other = (Collidable) contact.getFixtureB().getBody().getUserData();
		collidable.postSolve(contact, other);
		other.postSolve(contact, collidable);
	}

	
	// SAVE
	
	public void instantSave() {
	    try {
	    	
			FileOutputStream fos = new FileOutputStream(new File("save/instantSave.ms"));
		    ObjectOutputStream oos = new ObjectOutputStream(fos);
		    Array<Actor> actors = getActors();
		    
		    // nb actors to save
		    int nbSavable = 0;
		    for (int i = 0; i < actors.size; i++) {
				PhysicActor actor = (PhysicActor) actors.get(i);
				if (actor.isSavable())
					nbSavable++;		
			}
		    oos.writeInt(nbSavable);
		    
		    for (int i = 0; i < actors.size; i++) {
				PhysicActor actor = (PhysicActor) actors.get(i);
				if (actor.isSavable())
					oos.writeObject(actor);			
			}
			
		    oos.flush();
		    oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void instantLoad() {
		try {
			FileInputStream fis = new FileInputStream(new File("save/instantSave.ms"));
		    ObjectInputStream ois = new ObjectInputStream(fis);
		    
		    int nbActorsToLoad = ois.readInt();
		    
		    clear();
		    for(int i = 0; i < nbActorsToLoad ; ++i)
		    {
		    	 try {
					PhysicActor physicActor = (PhysicActor)ois.readObject();
					if (physicActor.getCollidableType() == CollidableType.Myrddin)
						myrddin = (Myrddin) physicActor;
			    	addActor(myrddin);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		   
		    ois.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	    
	}

}
