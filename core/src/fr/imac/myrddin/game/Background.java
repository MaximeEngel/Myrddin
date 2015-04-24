package fr.imac.myrddin.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import fr.imac.myrddin.MyrddinGame;

public class Background extends Actor {
	private Camera camera;
	private Array<AtlasRegion> atlasRegions;
  
  
	public Background(Camera camera) {
	  this.camera = camera;
	  TextureAtlas textureAtlas = (TextureAtlas) MyrddinGame.assetManager.get("background/background.atlas", TextureAtlas.class);
	  atlasRegions  = textureAtlas.findRegions("bg");
	}
  
  @Override
 	public void draw(Batch batch, float parentAlpha) {
 		// TODO Auto-generated method stub
	  	for(int i = 0; i < atlasRegions.size; ++i) {
	  		AtlasRegion atlasRegion = atlasRegions.get(i);
	  		
	  		// we get the width and the height to place the background on the screen
	  		int regionWidth = atlasRegion.getRegionWidth();
	  		int regionHeight = atlasRegion.getRegionHeight();
	  		
	  		// variable to make the parallax effect
	  		int facteur = (int) (this.camera.position.x / (atlasRegions.size - ((i + 1)/1.2)));
	  		
	  		// to repeat a background when needed
	  		int temp = 0;
	  		do {
	  			if(this.camera.position.x - 2 * regionWidth <= (temp * regionWidth))
	  				batch.draw(atlasRegion, (float) (temp * regionWidth - facteur), MyrddinGame.HEIGHT - regionHeight);
		  		temp++;
		  	}
	  		while(this.camera.position.x + (MyrddinGame.WIDTH / 2) >= (temp * regionWidth) - facteur);
	  		
	  		//atlasRegion.getRegionWidth()
	  		// recupérer position caméra this.camera.position.x;
	  		// taille écran MyrddinGame.WIDTH
	  		// que tout s'enchaîne et place facteur au bon endroit qui décale les positions
	  		// facteur à def
	  	}
 	}
  
  
}
