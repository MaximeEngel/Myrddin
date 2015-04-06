package fr.imac.myrddin;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import fr.imac.myrddin.game.GameScreen;

public class MyrddinGame extends Game {
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	
	private AssetManager assetManager;
	
	@Override
	public void create () {
		this.setScreen(new GameScreen(0));
	}

}
