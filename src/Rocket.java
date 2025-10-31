import java.awt.Point;

public class Rocket extends Sprite {

	private Tank owner;
	private int damage;
	
	
	
	public int getDamage() {
		return damage;
	}


	public Rocket(Tank owner) {
		super(  (owner.isRocket())?56:19, (owner.isRocket())?56:19);
		this.owner = owner;
		setPadding(10);
		setX(owner.getX());
		setY(owner.getY());
		if (owner.isRocket()) {
			damage = 100;
			setSpeed(100);
		}
		else {
			damage = 50;
			setSpeed(300);
		}
		
		setAlpha( owner.getGunAlpha());
		setFlyable(true);
		
	}
	

	public Tank getOwner() {
		return owner;
	}



	@Override
	public void update(int ms) {
		super.update(ms);
		
		checkTankCollision();
		checkWallCollision();

	}
	
	private void checkTankCollision () {
		
		NPCManager.checkTankCollision(this);

	}
	private void checkWallCollision () {
		
		if (isCollided()) {
			// Столкновение со стеной
			Point p = getCollidedPoint();
			int object = Map.getObjectByXY(p.getX(), p.getY());
			if (object == Map.WALL) {
				setActive(false);
			}
			else if (object == Map.BRICK) {
				setActive(false);
				Map.setObjectByXY(p.getX(), p.getY(), Map.GROUND);
				
			} 
			
		}
		
		
	}
	

	
	
	
	
}
