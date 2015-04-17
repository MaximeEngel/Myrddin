package fr.imac.myrddin.game.ennemy;

import fr.imac.myrddin.physic.PhysicActor;

public interface Enemy {
	
	public int getMeleeDamage();

	public boolean obstructBulletOf(PhysicActor owner);
}
