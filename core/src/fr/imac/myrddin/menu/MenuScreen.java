package fr.imac.myrddin.menu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import fr.imac.myrddin.MyrddinGame;

public class MenuScreen extends Stage implements Screen {
	private Table table;
	private ShapeRenderer shapeRenderer;
	private MyrddinGame myrddinGame;
	private TextButton.TextButtonStyle mainTextButtonStyle;
	private TextureAtlas atlasMenu;
	
	//CONSTRUCTOR
	public MenuScreen(MyrddinGame myrddinGame) {
		super(new FitViewport(MyrddinGame.WIDTH, MyrddinGame.HEIGHT));
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
		table.padTop(190);
				
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
		table.add(textButtonNew).padTop(-18);
		
		table.row();
		
		//add how to play button
		TextButton textButtonHow = new TextButton("Comment jouer", this.mainTextButtonStyle);
		textButtonHow.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				initHowToPlayMenu();
				
			}
		});
		table.add(textButtonHow).padTop(-18);
		
		table.row();
		
		//add credits button
		TextButton textButtonCredits = new TextButton("Credits", this.mainTextButtonStyle);
		textButtonCredits.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				initCreditsMenu();
				
			}
		});
		table.add(textButtonCredits).padTop(-18);
		
		table.row();
		
		//add quit button
		TextButton textButtonQuit = new TextButton("Quitter", this.mainTextButtonStyle);
		textButtonQuit.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();	
			}
		});
		table.add(textButtonQuit).padTop(-18);
	}
	
	//screen to choose a level
	public void initNewGameMenu() {
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
		
		//get last level unblocked
		Preferences prefs = Gdx.app.getPreferences("My Preferences");
		int lvl = prefs.getInteger("lastLevelUnlocked", 1);
		
		TextButton.TextButtonStyle levelTextButtonStyle = initLevelButton();
		//create text buttons depending on the number of levels
		for(int nbLevel = 1; nbLevel <= count; nbLevel++) {
			String stringNbLevel = Integer.toString(nbLevel);
			TextButton textButton = new TextButton(stringNbLevel, levelTextButtonStyle);
			
			// in case there is a file missing
			do {
				temp++;
			}
			while(!((Gdx.files.internal("lvl/"+temp+".tmx")).exists()));
			
			final int nbLevelTmp = temp;
			if(nbLevelTmp <= lvl) {	
				textButton.addListener(new ChangeListener() {
					
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						myrddinGame.startGame(nbLevelTmp);
						dispose();
							
					}
				});
				table.add(textButton).padTop(-15).padRight(10);
			}
			else {
				AtlasRegion lvlLockedRegion = atlasMenu.findRegion("buttonLocked");
				Image lvlLocked = new Image(lvlLockedRegion);
				table.add(lvlLocked).padTop(-15).padRight(10);
			}
			if(nbLevel % 6 == 0)
				table.row();
		}
		
		//add aleatory button
		TextButton textButtonAleatory = new TextButton("Niveau procédural", this.mainTextButtonStyle);
		textButtonAleatory.addListener(new ChangeListener() {
									
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				myrddinGame.startAleatoryLevel();
				dispose();
								
			}
		});
		table.add(textButtonAleatory).padTop(-15).colspan(2);
		table.row();
		
		//add return button
		TextButton textButtonReturn = new TextButton("Retour au menu", this.mainTextButtonStyle);
		textButtonReturn.addListener(new ChangeListener() {
							
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				initMainMenu();
							
			}
		});
		table.add(textButtonReturn).padTop(20).colspan(6);
	}
	
	//screen to know how to play
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
		
		//screen for credits
		private void initCreditsMenu() {
			//clear screen
			table.reset();
			
			//do debug
			//table.setDebug(true);
				
			//background image
			AtlasRegion backgroundMenuLevel = atlasMenu.findRegion("menu3");
			table.background(new TextureRegionDrawable(backgroundMenuLevel));
			
			//add developped by button
			AtlasRegion developpedByImage = atlasMenu.findRegion("developpedby");
			Image developpedBy = new Image(developpedByImage);
			table.add(developpedBy).padTop(20);
			
			//add musics button
			AtlasRegion musicsImage = atlasMenu.findRegion("musiques");
			Image musics = new Image(musicsImage);
			table.add(musics).padTop(20);
			table.row();
			
			TextButton textButtonMarie = new TextButton("Marie Benoist", this.mainTextButtonStyle);
			table.add(textButtonMarie).padTop(-15);
			TextButton textButtonNewSkies = new TextButton("New Skies", this.mainTextButtonStyle);
			table.add(textButtonNewSkies).padTop(-15);
			table.row();
			
			TextButton textButtonMaximeE = new TextButton("Maxime Engel", this.mainTextButtonStyle);
			table.add(textButtonMaximeE).padTop(-15);
			TextButton textButtonMoveForth = new TextButton("et Move Forth", this.mainTextButtonStyle);
			table.add(textButtonMoveForth).padTop(-15);
			table.row();
			
			TextButton textButtonLisa = new TextButton("Lisa Françoise", this.mainTextButtonStyle);
			table.add(textButtonLisa).padTop(-15);
			TextButton textButtonTheSecession = new TextButton("de The Secession", this.mainTextButtonStyle);
			table.add(textButtonTheSecession).padTop(-15);
			table.row();
			
			TextButton textButtonMaximeG = new TextButton("Maxime Gilbert", this.mainTextButtonStyle);
			table.add(textButtonMaximeG).padTop(-15);
			table.row();
			
			//add return button
			TextButton textButtonReturn = new TextButton("Retour au menu", this.mainTextButtonStyle);
			textButtonReturn.addListener(new ChangeListener() {
								
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					initMainMenu();
								
				}
			});
			table.add(textButtonReturn).padTop(20).colspan(2);
			table.row();
			
			//IMAC image
			AtlasRegion imacImage = atlasMenu.findRegion("imac");
			Image imac = new Image(imacImage);
			table.add(imac).padTop(-50).padLeft(1000).colspan(2);
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
	
	private TextButton.TextButtonStyle initLevelButton() {
		//get TextureAtlas
		AtlasRegion upLvlRegion = atlasMenu.findRegion("buttonLvlUp");
		AtlasRegion downLvlRegion = atlasMenu.findRegion("buttonLvlDown");
		AtlasRegion checkedLvlRegion = atlasMenu.findRegion("buttonLvlChecked");
		
		//get Font
		BitmapFont bitmapFont = (BitmapFont) MyrddinGame.assetManager.get("ui/theonlyexception_25.fnt", BitmapFont.class);
		
		return new TextButton.TextButtonStyle(new TextureRegionDrawable(upLvlRegion), new TextureRegionDrawable(downLvlRegion),new TextureRegionDrawable(checkedLvlRegion), bitmapFont);
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
		getViewport().update(width, height);

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
