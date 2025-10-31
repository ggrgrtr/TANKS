import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class NPCManager {

	private static boolean gameOver = false;
	private static int     gameOverType = 0;
	
	private static int count1 = 0;
	private static int count2 = 0;
	
	private static Font f = new Font(Font.SANS_SERIF, Font.BOLD, 36);
	
	private static ArrayList<Tank> tanks = new ArrayList<Tank>();
	
	static {
		addNew (1);
		getPlayer().setNpc(false);
	}
	
	
	public static void paintMiniMap (Graphics g) {
		
		int mapSize = 200;
		int offsetX = 300, offsetY = 300;
		
		double k = (double)mapSize / Math.max( Map.getWidth() ,
										Map.getHeight()
										);
		
		g.fillRect(offsetX, 
				offsetY, 
				(int)(k * Map.getWidth()) , 
				(int)(k * Map.getHeight()));
		
		double x, y;
		for ( Tank t   : tanks     ) {
			x = t.getX() * k;
			y = t.getY() * k;
			if (t.getTeam() == 1) {
				g.setColor(Color.RED);
			}
			else {
				g.setColor(Color.BLUE);
			}
			if (t  == getPlayer() ) {
				g.setColor(Color.RED);
			}
			g.fillOval((int)x+offsetX, (int)y+offsetY, 4, 4);
		}
		
		
		
	}
	
	public static void checkTankCollision (Rocket r) {
		
		Rectangle rocketCollider = r.getCollider();
		for ( Tank t  : tanks   ) {
			
			if (r.getOwner() == t) {
				continue;
			}
			
			
			if (t.getCollider().intersects(rocketCollider) && t.isActive() && r.isActive()) {
				r.setActive(false);
				
				int damage = r.getDamage();
				
				t.setHp(  t.getHp() - damage);
				
				if (t.getHp() <= 0) {
					t.setActive(false);
					ExplosionManager.addBigExplosion((int)r.getX(), (int)r.getY());
				}
				else {
					ExplosionManager.addExplosion((int)r.getX(), (int)r.getY());
				}
				
			}
			
			
		}
		
	}
	
	public static void setTarget (Tank t) {
		
		double r;
		Tank target = null;
		double min = Double.MAX_VALUE;
		for ( Tank tank : tanks) {
			if (t.getTeam() != tank.getTeam() ) {
				r = t.getDistance(tank);
				if (r < min) {
					min = r;
					target = tank;
				}
			}
		}
		
		t.setTarget(target);
		
	}
	
	
	public static void create(int q) {
		for (int i = 0; i < q-1; i ++) {
			addNew(1);
		}
		for (int i = 0; i < q; i ++) {
			addNew(2);
		}
	}
	
	public static Tank getPlayer () {
		return tanks.get(0);
	}
	
	public static void update (int ms) {
		
		count1 = 0;
		count2 = 0;
		
		for (int i = tanks.size() - 1; i>=0; i--) {
			if (!tanks.get(i).isActive()) {
				if (i > 0) {
					tanks.remove(i);
				}
			}
			else {
				if (tanks.get(i).getTeam() == 1) {
					count1++;
				}
				else {
					count2++;
				}
				
				tanks.get(i).update(ms);
			}
		}
		
		// Завершилась ли игра?
		if (!getPlayer().isActive() ) {
			gameOver = true;
			gameOverType = 1;
		}
		else if (count2 == 0) {
			gameOver = true;
			gameOverType = 2;
		}
		
		
		
		//for (Tank t : tanks  ) {
			
		//}
			
	}
	
	
	public static void paint (Graphics g) {
		for (Tank t : tanks  ) {
			t.paint(g);
			if (t == getPlayer()) {
				g.setColor(Color.RED);
				g.drawRect( Camera.getScreenX(t.getX()),
						Camera.getScreenY(t.getY()), 
						Map.BLOCK_SIZE, 
						Map.BLOCK_SIZE);
			}
		}
		
		g.setFont(f);
		g.setColor(Color.RED);
		g.drawString(count1+"", 50, 50);
		g.setColor(Color.BLACK);
		g.drawString(getPlayer().getAmmo()+"", 100, 50);
		
		g.setColor(Color.BLUE);
		g.drawString(count2+"", 50, 100);
		
		// Отрисовываем оружие 19 или 56
		if (getPlayer().isRocket()) {
			ImageHelper.paint(g, 100, 50, 56);
		}
		else {
			ImageHelper.paint(g, 100, 50, 19);
		}
		
		if (gameOver)  {
			String text ="";
			if (gameOverType == 1) {
				text = "Вы проиграли";
			}
			else {
				text = "Вы победили";
			}
			g.drawString(text, Camera.getWidth() / 2 - 100, Camera.getHeight() / 2 - 50 );
			
		}
		
	}
	
	
	public static void addNew (int team) {
		
		Tank t = new Tank(team);
			
		Point p = Map.respawn();
		t.setLocation(p.x * Map.BLOCK_SIZE, p.y * Map.BLOCK_SIZE);
		tanks.add(t);
		
	}
	
	
	
	
	
}
