package fr.imac.myrddin.game.hud;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.myrddin.Myrddin;

public class Hud extends Group {
	
	private Myrddin myrddin;
	private LifeHud lifeHud;
	private ScoreHud scoreHud;
	private MagicStateHud magicStateHud;
	private ShieldHud shieldHud;
	
	public Hud(Myrddin myrddin) {
		if(myrddin == null)
			throw new IllegalArgumentException("myrddin can't be null");
		
		this.myrddin = myrddin;
		
		this.setBounds(0, 0, MyrddinGame.WIDTH, MyrddinGame.HEIGHT);
		
		lifeHud = new LifeHud(myrddin);
		addActor(lifeHud);
		
		scoreHud = new ScoreHud(myrddin);
		addActor(scoreHud);
		
		magicStateHud = new MagicStateHud(myrddin);
		addActor(magicStateHud);
		
		shieldHud = new ShieldHud(myrddin.getShield());
		addActor(shieldHud);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	public void update(Myrddin myrddin) {
		lifeHud.setMyrddin(myrddin);
		scoreHud.setMyrddin(myrddin);
		magicStateHud.setMyrddin(myrddin);
		shieldHud.setShield(myrddin.getShield());
		
	}
	
	
}
