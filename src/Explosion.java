import java.awt.Graphics;

public class Explosion extends Sprite {

	int delayTime = 0;
	int delayTimeMax = 20;
	
	@Override
	public void update(int ms) {
		delayTime += ms;
		if (delayTime >= delayTimeMax) {
			super.update(ms);
		}
	}
	
	@Override
	public void paint(Graphics g) {
		if (delayTime >= delayTimeMax) {
			super.paint(g);
		}
	}
	
	
	public Explosion (int x, int y) {
		super(20, 20+25-1);
		
		delayTimeMax = (int)(Math.random() * 50);
		
		setAnimateWhenStopped(true);
		setLoopAnimation(false);
		setX(x);
		setY(y);
		setSpeed(0);
		setCollidable(false);
		setFrameTimeMax(20);
	
	}
	
	
	
}
