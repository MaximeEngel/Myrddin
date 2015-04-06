package fr.imac.myrddin.physic;

public class PhysicTile implements Collidable {

	private CollidableType type;	
	
	public PhysicTile(CollidableType type) {
		this.type = type;
	}

	@Override
	public void collideWith(Collidable collidable) {
		
	}

	@Override
	public CollidableType getCollidableType() {
		return type;
	}

}
