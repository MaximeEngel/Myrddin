package fr.imac.myrddin.game;


import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.myrddin.Myrddin;
import fr.imac.myrddin.physic.PhysicTileFactory;
import fr.imac.myrddin.physic.PhysicUtil;

/*
 * Handle the mechanism of a level.
 */
public class GameScreen extends Stage implements Screen {
	
	private World physicWorld;
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer mapRenderer;
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
		
		// Generate physic of the map
		physicWorld = new World(new Vector2(0, -9.1f),  true);
		createPhysicWorld(tiledMap);
		
		myrddin = new Myrddin(new Vector2(500f, 850f), physicWorld);
		this.addActor(myrddin);
		
		Gdx.input.setInputProcessor(this);
	}
	
	
	private void createPhysicWorld(TiledMap tiledMap) {		
		if (tiledMap != null)
		{
//			TiledMapTileLayer tileLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
//			int width = tileLayer.getWidth();
//			int height = tileLayer.getHeight();
//			float tileWidth = tileLayer.getTileWidth();
//			float tileHeight = tileLayer.getTileWidth();
			PhysicTileFactory physicTileFactory = new PhysicTileFactory(this.physicWorld);
//			
//			
//			for (int x = 0; x < width; x++) {
//				for (int y = 0; y < height; y++) {
//					Cell cell = tileLayer.getCell(x, y);
//					if ( cell != null)
//					{
//						MapProperties properties = cell.getTile().getProperties();
//						physicTileFactory.create(x * tileHeight, y * tileWidth, properties.get("Type", "none", String.class));						
//					}
//				}
//			}
			MapObjects objects = tiledMap.getLayers().get("CollisionTile").getObjects();
			for (MapObject mapObject : objects) {
				physicTileFactory.create((RectangleMapObject) mapObject, mapObject.getProperties().get("type", "none", String.class));
			}
		}
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
		updateCamera();
	}



	private void updateCamera() {
		OrthographicCamera camera = (OrthographicCamera) getCamera();
		Vector3 position = camera.position;
		position.set(myrddin.getX(), position.y, position.z);
//		camera.update();
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

}
