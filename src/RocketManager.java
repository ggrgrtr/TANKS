import java.awt.Graphics;
import java.util.ArrayList;

public class RocketManager {

	private static ArrayList<Rocket> rockets = new ArrayList<Rocket>();
	
	public  static void addRocket (Tank owner) {
		Rocket r = new Rocket(owner);
		rockets.add(r);
	}
	
	
	public static void update (int ms) {
		for (int i = rockets.size()-1; i >= 0; i--) {
			if (!rockets.get(i).isActive()) {
				rockets.remove(i);
			}
			else {
				rockets.get(i).update(ms);
			}
		}

	}
	
	public static void paint (Graphics g) {
		for (Rocket rocket : rockets) {
			rocket.paint(g);
		}
	}
	
	static {
		
	}
	
	
}
