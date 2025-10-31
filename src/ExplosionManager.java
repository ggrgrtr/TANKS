import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class ExplosionManager {

	static ArrayList<Explosion> explosions = new ArrayList<Explosion>();
	
	public static void addExplosion (int x, int y) {
		Explosion e = new Explosion(x, y);
		explosions.add(e);
		
	}
	
	public static void addBigExplosion (int x, int y) {
		
		Random r = new Random();
		
		int number = r.nextInt(5)+2;
		
		for (int i = 0; i < number; i++) {
			int dx = r.nextInt(10)-5;
			int dy = r.nextInt(10)-5;
			Explosion e = new Explosion(x+dx, y+dy);
			e.setAlpha(r.nextDouble()*2 * Math.PI);
			explosions.add(e);
		}
		
	}
	
	
	public static void update (int ms) {
		for (Explosion explosion : explosions) {
			explosion.update(ms);
		}
	}
	
	
	public static void paint (Graphics g) {
		for (Explosion explosion : explosions) {
			explosion.paint(g);
		}
	}
	
	
}
