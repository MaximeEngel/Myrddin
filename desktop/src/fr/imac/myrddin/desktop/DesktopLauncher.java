package fr.imac.myrddin.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import fr.imac.myrddin.MyrddinGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = MyrddinGame.WIDTH;
		config.height = MyrddinGame.HEIGHT;
		config.resizable = true ;
		new LwjglApplication(new MyrddinGame(), config);
	}
}
