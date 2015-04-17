package fr.imac.myrddin.menu;


import java.io.File;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Array;

import fr.imac.myrddin.MyrddinGame;

public class MenuScreen extends Stage implements Screen {
	private Table table;
	private ShapeRenderer shapeRenderer;
	private MyrddinGame myrddinGame;
	private TextButton.TextButtonStyle mainTextButtonStyle;
	
	//CONSTRUCTOR
	public MenuScreen(MyrddinGame myrddinGame) {
		super();
		this.myrddinGame = myrddinGame;
		mainTextButtonStyle = initMainTextButton();
		
		table = new Table();
		table.setFillParent(true);
		this.addActor(table);
		
		//do debug
		shapeRenderer = new ShapeRenderer();
		table.setDebug(true);
		shapeRenderer.setAutoShapeType(true);
		shapeRenderer.begin();
		
		//accept to catch events signals
		Gdx.input.setInputProcessor(this);
		initMainMenu();
	}
	
	//MENU SCREEN METHODS
	private void initMainMenu() {
		//clear screen
		table.reset();
				
		//do debug
		table.setDebug(true);
				
		//add resume button
		TextButton textButtonResume = new TextButton("Reprendre la partie", this.mainTextButtonStyle);
		textButtonResume.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				myrddinGame.startLastSave();
				
			}
		});
		table.add(textButtonResume);
		
		table.row();
		
		//add new game button
		TextButton textButtonNew = new TextButton("Nouvelle partie", this.mainTextButtonStyle);
		textButtonNew.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				initNewGameMenu();
				
			}
		});
		table.add(textButtonNew);
		
		table.row();
		
		//add quit button
		TextButton textButtonQuit = new TextButton("Quitter", this.mainTextButtonStyle);
		textButtonQuit.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();	
			}
		});
		table.add(textButtonQuit);
	}
	
	//screen to choose a level
	private void initNewGameMenu() {
		//clear screen
		table.reset();
		
		//do debug
		table.setDebug(true);
		
		//count number of files
		int count = getHandles();
		
		//create text buttons depending on the number of levels
		for(int nbLevel = 0; nbLevel < count; nbLevel++) {
			String stringNbLevel = Integer.toString(nbLevel);
			TextButton textButton = new TextButton(stringNbLevel, this.mainTextButtonStyle);
			
			final int nbLevelTmp = nbLevel;
			textButton.addListener(new ChangeListener() {
				
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					myrddinGame.startGame(nbLevelTmp);
						
				}
			});
			table.add(textButton);
		}
		
		//add return button
		TextButton textButtonReturn = new TextButton("Retour au menu", this.mainTextButtonStyle);
		textButtonReturn.addListener(new ChangeListener() {
							
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				initMainMenu();
							
			}
		});
		table.add(textButtonReturn);
	}
	
	//count number of files in a repertory
	public int getHandles() {
		FileHandle dirHandle;
		if (Gdx.app.getType() == ApplicationType.Android)
			dirHandle = Gdx.files.internal("lvl");
		else
			dirHandle = Gdx.files.internal("./bin/lvl");
		
		int count = 0;
	    FileHandle[] newHandles = dirHandle.list();
	    for (FileHandle f : newHandles)
	        if(f.name().endsWith(".tmx")) count++;

	    return count;
	}
	
	//
	private TextButton.TextButtonStyle initMainTextButton() {
		//get TextureAtlas
		TextureAtlas textureAtlas = (TextureAtlas) MyrddinGame.assetManager.get("ui/ui.atlas", TextureAtlas.class);
		AtlasRegion upRegion = textureAtlas.findRegion("buttonUp");
		AtlasRegion downRegion = textureAtlas.findRegion("buttonDown");
		AtlasRegion checkedRegion = textureAtlas.findRegion("buttonChecked");
		
		//get Font
		BitmapFont bitmapFont = (BitmapFont) MyrddinGame.assetManager.get("ui/dosis_39.fnt", BitmapFont.class);
		
		return new TextButton.TextButtonStyle(new TextureRegionDrawable(upRegion), new TextureRegionDrawable(downRegion),new TextureRegionDrawable(checkedRegion), bitmapFont);
	}
	
	
	//METHODS OF STAGE
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		super.draw();
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
	}

	
	
	//METHODS OF SCREEN
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		this.act(delta);
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clear old drawing
		this.draw();
		
		table.drawDebug(shapeRenderer);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

}
