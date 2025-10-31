import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

public class Tank {

	private boolean rocket = false;
	private int hp   = 100;
	private int ammo = 30;
	private int timeToFire = 0;
	private int timeToFireMax = 1000;
	
	private Sprite platform, gun;
	private boolean active = true;
	private int speed = Map.BLOCK_SIZE; 
	private int team = 1;
	
	private int timeToChangeOrientation = 0;
	private int timeToChangeOrientationMax = 1000;
	
	private int timeToChooseTarget = 0;
	private int timeToChooseTargetMax = 0;
	
	private boolean npc = true;
	
	private Tank target = null;
	
	
	public void changeWeapon () {
		rocket = !rocket;
	}
	
	public Point getCenterPoint () {
		
		int x = (int) (getX() + platform.getWidth() / 2);
		int y = (int) (getY() + platform.getHeight() / 2);
		return new Point(x, y);
		
	}
	
	
	// Координаты экранные
	public void rotateGunToXY(int x, int y) {
		Point c = getCenterPoint();
		double k1 = y -  Camera.getScreenY( c.getY() );
		double k2 = x -  Camera.getScreenX( c.getX() );
		
		gun.setAlpha( Math.atan2(k1, k2)  );
	}
	
	public int getAmmo() {
		return ammo;
	}

	public boolean isRocket() {
		return rocket;
	}

	public void setRocket(boolean rocket) {
		this.rocket = rocket;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
		if (this.hp < 0) {
			this.hp = 0;
		}
	}

	public Rectangle getCollider () {
		return platform.getCollider();
	}
	
	public Tank getTarget() {
		return target;
	}

	public void setTarget(Tank target) {
		this.target = target;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isNpc() {
		return npc;
	}

	public void setNpc(boolean npc) {
		this.npc = npc;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam (int team) {
		this.team = team; 
	}
	
	public double getDistance(Tank t) {
		
		return platform.getDistance(t.platform);
	}

	public void setLocation (int x, int y) {
		platform.setX(x);
		platform.setY(y);
	}
	
	
	
	public double getX() {
		return platform.getX();
	}

	public double getY() {
		return platform.getY();
	}

	public void setAlpha (double alpha) {
		platform.setAlpha(alpha);
	}
	
	public void stop() {
		platform.setSpeed(0);
	}
	public void go() {
		platform.setSpeed(speed);
	}
	
	public void right() {
		setAlpha(0);
	}
	public void left() {
		setAlpha(Math.PI);
	}
	public void up() {
		setAlpha(Math.PI / 2 * 3);
	}
	public void down() {
		setAlpha(Math.PI / 2);
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Tank (int team) {
		if (team == 1) {
			platform = new Sprite(8, 17);
			gun = new Sprite(18, 18);
		}
		else {
			platform = new Sprite(45, 54);
			gun = new Sprite(55, 55);
		}
		
		this.team = team;
		
	}
	
	public double getGunAlpha() {
		return gun.getAlpha();
	}
	
	public void fire () {
		if (!isActive()) {
			return;
		}
		
		if (timeToFire >= timeToFireMax) {
			if (ammo > 0) {
				RocketManager.addRocket(this);
				ammo --;
				timeToFire = 0;
			}
		}
	}
	
	public void paint (Graphics g) {
		
		if (!active) {
			return;
		}
		
		platform.paint(g);
		gun.paint(g);
		
		g.setColor(Color.GREEN);
		g.fillRect(Camera.getScreenX(getX()), 
				    Camera.getScreenY(getY() + platform.getHeight()) + 3 , 
					(int)(hp/100.0*Map.BLOCK_SIZE), 
					3);
		
	}
	
	public void AI () {
		AImove();
		AIchooseTarget();
		AIgunRotation ();
		
	}
	
	private void followToTarget () {
		if (target == null) {
			return;
		}
		
		// направление сближения
		if (Math.random() < 0.5) {
			// По горизонтали
			if (target.getX() > getX() ) {
				// Вправо
				right();
			}
			else {
				// Влево
				left();
			}
			
		}
		else {
			// По вертикали
			if (target.getY() > getY() ) {
				down();
			}
			else {
				up();
			}
		}
		
		
		
	}
	
	
	
	
	private void AIgunRotation () {
		
		if (Math.random() > 0.01) {
			return;
		}
		
		if (target == null) {
			return;
		}
		
		double r = getDistance(target);
		if (r > Map.BLOCK_SIZE * 7) {
			return;
		}
		
		double k1 = target.getX() - getX();
		double k2 = target.getY() - getY();
		
		gun.setAlpha( Math.atan2(k2, k1));
		
		if (Math.random() > 0.7) {
			fire();
		}
		
		
		
	}
	
	private void AIchooseTarget() {
		if (timeToChooseTarget >= timeToChooseTargetMax) {
			NPCManager.setTarget(this);
			timeToChooseTarget = 0;
			timeToChooseTargetMax = (int)(Math.random() * 10000) + 500;
		}
		
	}

	public void AImove () {
		
		Random r = new Random();
		
		if (platform.isCollided()) {
			randomOrientation();
		}
		
		if (timeToChangeOrientation >= timeToChangeOrientationMax) {
			if (Math.random() < 0.2) {
				randomOrientation();
			}
			else {
				followToTarget();
			}
			timeToChangeOrientation = 0;
			timeToChangeOrientationMax = r.nextInt(2000) + 500;
		}
		
		
		
	}
	
	public void randomOrientation () {
		Random r = new Random ();
		int orientation = r.nextInt(4);
		
		platform.setAlpha( Math.PI / 2 * orientation );
	}
	
	
	public void update (int ms) {
		
		if (!active) {
			return;
		}
		
		timeToFire += ms;
		
		timeToChangeOrientation += ms;
		timeToChooseTarget +=ms;
		
		platform.update(ms);
		gun.update(ms);
		
		gun.setX( platform.getX()  );
		gun.setY( platform.getY()  );
		
		//gun.setAlpha(  gun.getAlpha() + 0.01     );
		
		if (npc) {
			AI();
		}
		
	}
	
	
	
}
