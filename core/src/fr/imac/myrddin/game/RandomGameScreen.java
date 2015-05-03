package fr.imac.myrddin.game;

import java.util.HashMap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTile.BlendMode;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.ennemy.EnemyFactory;
import fr.imac.myrddin.game.ennemy.StaticEnemy;
import fr.imac.myrddin.game.ennemy.TowerEnemy;
import fr.imac.myrddin.game.magic.MagicState;
import fr.imac.myrddin.game.powerup.PowerFactory;
import fr.imac.myrddin.physic.PhysicActor;
import fr.imac.myrddin.physic.PhysicTileFactory;

public class RandomGameScreen extends GameScreen {
	
	public static final int TILE_SIZE = 32;
	/**
	 * big block are allocated by this size. So you can dysalloc by this size without break the logic of the game.
	 */
	public static final int CHUNK_WIDTH = 100;
	public static final int CHUNK_HEIGHT = 23;
	
	/**
	 * potential size
	 */
	protected int mapTileWidth = 100000;
	/**
	 * size computed and generated
	 */
	protected int actualMapTileWidth = 0;
	protected TiledMapTileLayer tiles;
	private TiledMapTileSet tileSet;
	protected HashMap<String, Integer> tilesetIds;
	
	protected PhysicTileFactory physicTileFactory;
	private Vector2 lastChunkGoodPos;
	private int nbChunks;
	
	private int oldDistanceScore = 0;
	private PowerFactory powerFactory;
	private EnemyFactory ennemyFactory;

	public RandomGameScreen() {
		super();
		
		tilesetIds = new HashMap<String, Integer>();
		
		// Init map and set
		tiledMap = new TiledMap();
		tileSet = initTileSet();
		tiledMap.getTileSets().addTileSet(tileSet);
		mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, getBatch());
		mapRenderer.setView((OrthographicCamera) getCamera());
		mapWidth = mapTileWidth * TILE_SIZE;
		
		// init tiles
		tiles = new TiledMapTileLayer(mapTileWidth, 23, TILE_SIZE, TILE_SIZE);		
		tiles.setName("Tiles");
		tiledMap.getLayers().add(tiles);
		
		// factories
		physicTileFactory = new PhysicTileFactory(this.physicWorld);
		powerFactory = new PowerFactory();
		ennemyFactory = new EnemyFactory(this);
		
		lastChunkGoodPos = new Vector2();
		nbChunks = 0;
		for(int i = 0; i < 1; i++)
			createChunk();
	}
	
	public void createChunk() {
		// Init for new chunk
		int minChunkX = nbChunks * CHUNK_WIDTH;
		int maxChunkX = minChunkX + CHUNK_WIDTH - 1;
		int actualChunkX = minChunkX;
		
		while(actualChunkX < maxChunkX) {
			// Minimum init
			int originX = actualChunkX;
			if(actualChunkX != 0 && MathUtils.randomBoolean(0.35f))
				originX += MathUtils.random(4, 10);
			
			int originY = 0;
			int nbTileWidth = Math.min(maxChunkX - actualChunkX, MathUtils.random(4, 20));
			int nbTileHeight = 1;
			
			
			if(actualChunkX != 0 && MathUtils.randomBoolean())
				originY = MathUtils.random(0, Math.min(CHUNK_HEIGHT - 4, (int)lastChunkGoodPos.y + 6));
			
			nbTileHeight += MathUtils.random(0, Math.min(CHUNK_HEIGHT - 4 - originY, (int)lastChunkGoodPos.y + 6 - originY));
			createBlock(originX, originY, nbTileWidth, nbTileHeight);
			addFunToBlock(originX, originY, nbTileWidth, nbTileHeight);
			
			// Update for next block
			actualChunkX = originX + nbTileWidth;
			lastChunkGoodPos.x = actualChunkX;
			lastChunkGoodPos.y = originY + nbTileHeight;
		}
		
		// Update for next chunk
		nbChunks++;
	}
	
	/**
	 * 
	 * @param originX in tile unit
	 * @param originY in tile unit
	 * @param nbTileWidth
	 * @param nbTileHeight
	 */
	public void createBlock(int originX, int originY, int nbTileWidth, int nbTileHeight) {
		int xMax = originX + nbTileWidth;
		int yMax = originY + nbTileHeight;
		
		TiledMapTile cube = tileSet.getTile(tilesetIds.get("cube"));
		Cell cellCube = new Cell();
		cellCube.setTile(cube);
		
		String herbs[] = { "herbe1", "herbe2", "herbe4"};
		String rocks[] = { "rocher1", "rocher2"};
		
		
		// Add the cube tiles
		for (int x = originX ; x < xMax ; x++) {
			for (int y = originY ; y < yMax ; y++) {				
				tiles.setCell(x, y, cellCube);
			}
		}
		
		// Add the decorations
		for (int x = originX ; x < xMax ; x++) {
			int rand = MathUtils.random(99);
			
			String decoName;
			if (rand <= 75)
				decoName = "herbe3";
			else if(rand <= 95)
				decoName = herbs[rand % herbs.length];
			else
				decoName = rocks[rand % rocks.length];
			
			Cell cell = new Cell();
			cell.setTile(tileSet.getTile(tilesetIds.get(decoName)));
			cell.setFlipHorizontally(MathUtils.randomBoolean());
			tiles.setCell(x, yMax, cell);
		}
		
		// Create the physic
		RectangleMapObject rectangleMapObject = new RectangleMapObject(originX * TILE_SIZE, originY * TILE_SIZE, nbTileWidth * TILE_SIZE, nbTileHeight * TILE_SIZE);
		physicTileFactory.create(rectangleMapObject, "Solid");
	}
	
	/**
	 * Add some enemies, score and fun to the given coordinate's prebuild block
	 * @param originX in tile unit
	 * @param originY in tile unit
	 * @param nbTileWidth
	 * @param nbTileHeight
	 */
	public void addFunToBlock(int originX, int originY, int nbTileWidth, int nbTileHeight) {
		int nbElementsToTry = nbTileWidth - 2;
		if (nbElementsToTry <= 0)
			return;
		
		nbElementsToTry = MathUtils.random(nbElementsToTry);
		for(int i = 0; i < nbElementsToTry ; i++) {
			int random = MathUtils.random(100);
			if (random == 100)
				createLife(originX, originY, nbTileWidth, nbTileHeight);
			else if (random > 95)
				createPower(originX, originY, nbTileWidth, nbTileHeight);
			else if (random > 75)
				createEnemy(originX, originY, nbTileWidth, nbTileHeight);
			else if (random > 40)
				createScore(originX, originY, nbTileWidth, nbTileHeight);
		}
	}
	
	private void createScore(int originX, int originY, int nbTileWidth,
			int nbTileHeight) {
		// TODO Auto-generated method stub
		
	}

	private void createEnemy(int originX, int originY, int nbTileWidth,
			int nbTileHeight) {
		if (originX <= 40)
			return ;
		PhysicActor actor = null;
		RectangleMapObject rectangleMapObject;
		switch(MathUtils.random(2)) {
		case 0:
			rectangleMapObject = new RectangleMapObject(MathUtils.random(originX * TILE_SIZE, (originX + nbTileWidth) * TILE_SIZE - StaticEnemy.WIDTH), (originY + nbTileHeight) * TILE_SIZE, 10, 10);
			actor = ennemyFactory.create(rectangleMapObject, "StaticEnemy");
			break;
		case 1:
			rectangleMapObject = new RectangleMapObject(MathUtils.random(originX * TILE_SIZE, (originX + nbTileWidth) * TILE_SIZE - TowerEnemy.WIDTH), (originY + nbTileHeight) * TILE_SIZE, 10, 10);
			rectangleMapObject.getProperties().put("magicState", String.valueOf(MathUtils.randomBoolean() ? MagicState.FIRE : MagicState.ICE));
			actor = ennemyFactory.create(rectangleMapObject, "TowerEnemy");
			break;
		default:
			float factor = 0.1f;
			if (nbTileWidth > 7)
				factor = 0.4f;
			float runZoneX = MathUtils.random(originX * TILE_SIZE,(int) ((originX + nbTileWidth * factor) * TILE_SIZE) );			
			
			float runZoneWidth = (originX + nbTileWidth) * TILE_SIZE - runZoneX; 
			if (nbTileWidth > 7)
				runZoneWidth *= MathUtils.random(0.5f, 1);
			
			rectangleMapObject = new RectangleMapObject(runZoneX, (originY + nbTileHeight) * TILE_SIZE, runZoneWidth, 10);
			rectangleMapObject.getProperties().put("magicState", String.valueOf(MathUtils.randomBoolean() ? MagicState.FIRE : MagicState.ICE));
			actor = ennemyFactory.create(rectangleMapObject, "DynamicEnemy");
			break;
		}
		
		if(actor != null)
			addActor(actor);
		
	}

	private void createPower(int originX, int originY, int nbTileWidth,
			int nbTileHeight) {
		// TODO Auto-generated method stub
		
	}

	private void createLife(int originX, int originY, int nbTileWidth,
			int nbTileHeight) {
		RectangleMapObject rectangleMapObject = new RectangleMapObject(MathUtils.random(originX * TILE_SIZE, (originX + nbTileWidth) * TILE_SIZE), (originY + nbTileHeight + 2) * TILE_SIZE, 10, 10);
		PhysicActor powerups[] = powerFactory.create(rectangleMapObject, "PowerHealth");
		for (PhysicActor powerup : powerups) {
			addActor(powerup);					
		}
		
	}

	public TiledMapTileSet initTileSet() {
		TextureAtlas tilesetAtlas = MyrddinGame.assetManager.get("set/tilesetRandom.atlas", TextureAtlas.class);
		Array<AtlasRegion> allRegions = tilesetAtlas.getRegions();
				
		TiledMapTileSet tileSet = new TiledMapTileSet();
		int i = 0;
		for (AtlasRegion atlasRegion : allRegions) {
			tilesetIds.put(atlasRegion.name, i);
			tileSet.putTile(i, new StaticTiledMapTile(atlasRegion));
			i++;
		}
		return tileSet;
	}
		

	@Override
	public void act(float delta) {
		super.act(delta);
		
		
		int newDistanceScore = (int) myrddin.getX() / 10;
		myrddin.addScore(newDistanceScore - oldDistanceScore);
		oldDistanceScore = newDistanceScore;
		
		// Create chunk if we are near to the limit
		if((myrddin.getX() + MyrddinGame.WIDTH) % CHUNK_WIDTH > nbChunks)
			createChunk();
	}

	/**
	 * cancel instantSave
	 */
	@Override
	public void instantSave() {
	}
	
	/**
	 * cancel instantLoad
	 */
	@Override
	public void instantLoad() {
	}
	
	
}
