import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Sprite {

	private boolean animateWhenStopped = false;
	private boolean loopAnimation = true;
	
	
	private boolean flyable = false;
	private int padding = 3;
	private boolean collidable = true;
	private boolean collided = false;
	private Point   collidedPoint = null;
	
	private double x = 50, y = 120;
	private double alpha;
	private double speed = Map.BLOCK_SIZE; // Пиксели в секунду
	
	private boolean active = true;
	
	private int firstFrame, lastFrame, currentFrame;
	private int frameTime, frameTimeMax = 180;
	
	
	public boolean isAnimateWhenStopped() {
		return animateWhenStopped;
	}

	public void setAnimateWhenStopped(boolean animateWhenStopped) {
		this.animateWhenStopped = animateWhenStopped;
	}

	public boolean isFlyable() {
		return flyable;
	}

	public void setFlyable(boolean flyable) {
		this.flyable = flyable;
	}

	public Rectangle getCollider () {
		
		Rectangle r = new Rectangle((int)x + padding, 
									(int)y + padding, 
									getWidth() - padding * 2,
									getHeight() - padding * 2
									);
		
		return r;
		
	}
	
	public int getPadding() {
		return padding;
	}

	public void setPadding(int padding) {
		this.padding = padding;
	}

	public boolean isCollided() {
		return collided;
	}
	public int getWidth() {
		BufferedImage image = ImageHelper.getFrames()[currentFrame];
		
		return image.getWidth();
		
	}
	public int getHeight() {
		BufferedImage image = ImageHelper.getFrames()[currentFrame];
		return image.getHeight();
	}
	
	public Point[]  getCornerPoints () {
		
		Point [] p = new Point[4];
		
		p[0] = new Point((int)x + padding, (int)y + padding);
		p[1] = new Point((int)x + getWidth() - padding, 
						(int)y + padding);
		p[2] = new Point((int)x + getWidth() - padding, 
				(int)y + getHeight() - padding);
		p[3] = new Point((int)x + padding, 
				(int)y + getHeight() - padding);
		return p;
		
	}
	
	public double getX() {
		return x;
	}
	
	public double getDistance (Sprite s) {
		
		double r = Math.sqrt( Math.pow( x - s.getX(), 2) + 
				 			  Math.pow( y - s.getY(), 2)
				);
		return r;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getFirstFrame() {
		return firstFrame;
	}

	public void setFirstFrame(int firstFrame) {
		this.firstFrame = firstFrame;
	}

	public int getLastFrame() {
		return lastFrame;
	}

	public void setLastFrame(int lastFrame) {
		this.lastFrame = lastFrame;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	public int getFrameTime() {
		return frameTime;
	}

	public void setFrameTime(int frameTime) {
		this.frameTime = frameTime;
	}

	public int getFrameTimeMax() {
		return frameTimeMax;
	}

	public void setFrameTimeMax(int frameTimeMax) {
		this.frameTimeMax = frameTimeMax;
	}

	public Sprite (int firstFrame, int lastFrame) {
		this.firstFrame = firstFrame;
		this.lastFrame  = lastFrame;
		currentFrame = firstFrame;
	}
	
	public void nextFrame () {
		
		if (speed == 0 && animateWhenStopped == false) {
			return;
		}
		
		currentFrame ++;
		if (currentFrame > lastFrame) {
			currentFrame = firstFrame;
			if (!loopAnimation) {
				active = false;
			}
			
		}
	}
	
	public boolean isCollidable() {
		return collidable;
	}

	public void setCollidable(boolean collidable) {
		this.collidable = collidable;
	}

	public boolean isLoopAnimation() {
		return loopAnimation;
	}

	public void setLoopAnimation(boolean loopAnimation) {
		this.loopAnimation = loopAnimation;
	}

	public void update (int ms) {
		
		if (!active) {
			return;
		}
		
		collided = false;
		collidedPoint = null;
		
		double deltaX, deltaY;
		deltaX =  speed * Math.cos(alpha) / 1000 * ms;
		deltaY = speed * Math.sin(alpha)  / 1000 * ms;
		
		x = x + deltaX;
		y = y + deltaY;
		
		frameTime += ms;
		if (frameTime >= frameTimeMax) {
			nextFrame();
			frameTime = 0;
		}
		
		if (collidable) {
			
			Point[] p = getCornerPoints();
			
			for ( Point point  :  p  ) {
				if (!Map.isPassable (point.x, point.y, flyable) ) {
					collided = true;
					collidedPoint = point;
					x = x - deltaX;
					y = y - deltaY;
					break;
				}
			}
			
			
		}
		
		
		
	}
	
	public Point getCollidedPoint() {
		return collidedPoint;
	}

	public void setCollidedPoint(Point collidedPoint) {
		this.collidedPoint = collidedPoint;
	}

	public void paint (Graphics g) {
		
		if (!active) {
			return;
		}
		
		ImageHelper.paint(g, 
				Camera.getScreenX(x), 
				Camera.getScreenY(y), 
				currentFrame,
				alpha
				);
		
		
	}
	
}
