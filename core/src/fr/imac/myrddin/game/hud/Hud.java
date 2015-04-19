package fr.imac.myrddin.game.hud;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import fr.imac.myrddin.MyrddinGame;
import fr.imac.myrddin.game.myrddin.Myrddin;

public class Hud extends Group {
	
	private Myrddin myrddin;
	
	public Hud(Myrddin myrddin) {
		if(myrddin == null)
			throw new IllegalArgumentException("myrddin can't be null");
		
		this.myrddin = myrddin;
		
		this.setBounds(0, 0, MyrddinGame.WIDTH, MyrddinGame.HEIGHT);
		
		// add life HUD
		addActor(new LifeHud(myrddin));
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}
	
	
}
