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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Array;

import fr.imac.myrddin.MyrddinGame;

public class MenuScreen extends Stage implements Screen {
	private Table table;
	private ShapeRenderer shapeRenderer;
	private MyrddinGame myrddinGame;
	private TextButton.TextButtonStyle mainTextButtonStyle;
	private TextureAtlas atlasMenu;
	
	//CONSTRUCTOR
	public MenuScreen(MyrddinGame myrddinGame) {
		super();
		this.myrddinGame = myrddinGame;
		atlasMenu = (TextureAtlas) MyrddinGame.assetManager.get("ui/ui.atlas", TextureAtlas.class);
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
		//table.setDebug(true);
		
		//background Menu
		AtlasRegion backgroundMenu = atlasMenu.findRegion("menu");
		table.background(new TextureRegionDrawable(backgroundMenu));
		
		// padding top
		table.padTop(230);
				
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
		table.add(textButtonNew).padTop(-15);
		
		table.row();
		
		//add how to play button
		TextButton textButtonHow = new TextButton("Comment jouer", this.mainTextButtonStyle);
		textButtonHow.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				initHowToPlayMenu();
				
			}
		});
		table.add(textButtonHow).padTop(-15);
		
		table.row();
		
		//add quit button
		TextButton textButtonQuit = new TextButton("Quitter", this.mainTextButtonStyle);
		textButtonQuit.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();	
			}
		});
		table.add(textButtonQuit).padTop(-15);
	}
	
	//screen to choose a level
	private void initNewGameMenu() {
		//clear screen
		table.reset();
		
		//do debug
		//table.setDebug(true);
		
		//background image
		AtlasRegion backgroundMenuLevel = atlasMenu.findRegion("menu2");
		table.background(new TextureRegionDrawable(backgroundMenuLevel));
		
		//count number of files
		int count = getHandles();
		int temp = -1;
		
		//create text buttons depending on the number of levels
		for(int nbLevel = 1; nbLevel <= count; nbLevel++) {
			String stringNbLevel = Integer.toString(nbLevel);
			TextButton textButton = new TextButton(stringNbLevel, this.mainTextButtonStyle);
			
			// in case there is a file missing
			do {
				temp++;
			}
			while(!((Gdx.files.internal("lvl/"+temp+".tmx")).exists()));
			
			final int nbLevelTmp = temp;	
			textButton.addListener(new ChangeListener() {
				
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					myrddinGame.startGame(nbLevelTmp);
						
				}
			});
			table.add(textButton).padTop(-15);
			table.row();
		}
		
		//add return button
		TextButton textButtonReturn = new TextButton("Retour au menu", this.mainTextButtonStyle);
		textButtonReturn.addListener(new ChangeListener() {
							
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				initMainMenu();
							
			}
		});
		table.add(textButtonReturn).padTop(-15);
	}
	
	//screen to choose a level
		private void initHowToPlayMenu() {
			//clear screen
			table.reset();
			
			//do debug
			//table.setDebug(true);
			
			//background image
			AtlasRegion backgroundMenuLevel = atlasMenu.findRegion("menu3");
			table.background(new TextureRegionDrawable(backgroundMenuLevel));
			
			//add return button
			TextButton textButtonReturn = new TextButton("Retour au menu", this.mainTextButtonStyle);
			textButtonReturn.addListener(new ChangeListener() {
								
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					initMainMenu();
								
				}
			});
			table.add(textButtonReturn).padTop(-20);
			table.row();
			
			//add how to play image
			AtlasRegion howToPlayImage = atlasMenu.findRegion("t_jouer");
			Image howToPlay = new Image(howToPlayImage);
			table.add(howToPlay).padTop(20);
			table.row();
			
			AtlasRegion powersImage = atlasMenu.findRegion("powers_exp");
			Image powers = new Image(powersImage);
			table.add(powers).padTop(20);
		}
	
	//count number of files in a repertory
	public int getHandles() {
		FileHandle dirHandle =  Gdx.files.internal("lvl");
		if (!dirHandle.isDirectory())
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
		AtlasRegion upRegion = atlasMenu.findRegion("buttonUp");
		AtlasRegion downRegion = atlasMenu.findRegion("buttonDown");
		AtlasRegion checkedRegion = atlasMenu.findRegion("buttonChecked");
		
		//get Font
		BitmapFont bitmapFont = (BitmapFont) MyrddinGame.assetManager.get("ui/theonlyexception_25.fnt", BitmapFont.class);
		
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
