package fr.imac.myrddin.physic;

public interface Collidable {
	
	public enum CollidableType {
		Solid, Climb, Myrddin, Attackable, Defendable;
	}
	
	/**
	 * Called when started to collide with a collidable
	 * @param collidable
	 */
	public void collideWith(Collidable collidable);
	
	public CollidableType getCollidableType();
}
