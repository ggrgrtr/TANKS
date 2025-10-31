
public class Camera {

	private static double x=50, y=40;
	private static int width = 1024, height = 768;
	

	public static void setWidth(int width) {
		Camera.width = width;
	}

	public static void setHeight(int height) {
		Camera.height = height;
	}

	public static double getX() {
		return x;
	}

	public static int getScreenX (double x) {
		return  (int)(x - Camera.x);
	}
	public static int getScreenY (double y) {
		return (int) (y - Camera.y);
	}

	public static double getY() {
		return y;
	}


	public static int getWidth() {
		return width;
	}


	public static int getHeight() {
		return height;
	}


	public static void move (double deltaX, double deltaY) {
		setPosition (x + deltaX, y + deltaY );
	}
	
	
	public static void setPosition (double x, double y) {
		Camera.x = x; 
		Camera.y = y; 
		
		if (Camera.x < 0) {
			Camera.x = 0;
		}
		if (Camera.y < 0) {
			Camera.y = 0;
		}
		
		if (Camera.x > Map.getWidth() - width) {
			Camera.x = Map.getWidth() - width;
		}
		if (Camera.y > Map.getHeight() - height) {
			Camera.y = Map.getHeight() - height;
		}
		
		
	}
	
}
