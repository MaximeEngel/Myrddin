package fr.imac.myrddin;

import java.io.File;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import fr.imac.myrddin.game.GameScreen;
import fr.imac.myrddin.menu.MenuScreen;

public class MyrddinGame extends Game {
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final float GAME_TO_PHYSIC = 0.02f;
	public static final float PHYSIC_TO_GAME = 50f;
	public static MyrddinGame MYRDDIN_GAME;
	
	
	public static AssetManager assetManager;
	private boolean eraseLastInstantSave;
	
	@Override
	public void create () {
		assetManager = new AssetManager();
		initialLoadAsset();
		
		
		this.setScreen(new MenuScreen(this));
		MYRDDIN_GAME = this;
		eraseLastInstantSave = true;
	}

	private void initialLoadAsset() {
		//UI ressources
		assetManager.load("ui/ui.atlas", TextureAtlas.class);
		assetManager.load("ui/theonlyexception_25.fnt", BitmapFont.class);
		
		// Myrddin
		assetManager.load("myrddin/myrddin.atlas", TextureAtlas.class);
		
		//HUD
		assetManager.load("ui/hud.atlas", TextureAtlas.class);
		assetManager.load("ui/test.fnt", BitmapFont.class);

		// Myrddin
		assetManager.load("myrddin/myrddin.atlas", TextureAtlas.class);
		
		assetManager.load("enemy/enemy.atlas", TextureAtlas.class);
		
		// Power
		assetManager.load("power/power.atlas", TextureAtlas.class);

		// Background
		assetManager.load("background/background.atlas", TextureAtlas.class);
		assetManager.load("set/various.atlas", TextureAtlas.class);
		
		assetManager.finishLoading(); //block until all assets loaded
	}
	
	public void startGame(int lvl) {
		if(eraseLastInstantSave)
			new File("save/instantSave.ms").delete();
		
		this.setScreen(new GameScreen(lvl));
		
		Preferences prefs = Gdx.app.getPreferences("My Preferences");
		prefs.putInteger("lastLevelPlayed", lvl);
		prefs.flush();	
		
	}
	
	public void startLastSave() {
		Preferences prefs = Gdx.app.getPreferences("My Preferences");
		int lvl = prefs.getInteger("lastLevelPlayed", 1);
		eraseLastInstantSave = false;
		startGame(lvl);
		GameScreen gameScreen = (GameScreen) this.getScreen();
		gameScreen.instantLoad();
		eraseLastInstantSave = true;
	}
}
