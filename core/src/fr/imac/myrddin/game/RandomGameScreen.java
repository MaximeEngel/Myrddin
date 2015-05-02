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
		int nbElementsToTry = MathUtils.random(nbTileWidth - 2);
		for(int i = 0; i < nbElementsToTry ; i++) {
			int random = MathUtils.random(100);
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
