package fr.imac.myrddin.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

/*
 * Handle the mechanism of a level.
 */
public class GameScreen extends Stage implements Screen {
	
	private World physicWorld;
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer mapRenderer;
	
	/**
	 * 
	 * @param level numero of the level to instanciate and play
	 */
	public GameScreen(int level) {
		super(new FitViewport(1280, 720));
		
		TmxMapLoader mapLoader = new TmxMapLoader();
		tiledMap = mapLoader.load("lvl/"+level+".tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, getBatch());
		mapRenderer.setView((OrthographicCamera) getCamera());
	}
	
	
	
	// ___ SCREEN ___
	
	@Override
	public void draw() {
		mapRenderer.render();
		super.draw();
	}



	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
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

}
