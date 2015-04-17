package fr.imac.myrddin.game;

import com.badlogic.gdx.math.Vector2;

import fr.imac.myrddin.game.magic.MagicState;

public interface MagicWeaponOwner {
	
	public Vector2 getWeaponPos();
	
	public MagicState getMagicState();
} 
