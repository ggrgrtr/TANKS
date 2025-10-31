import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;
import java.util.Scanner;

public class Map {

	private static int [][] matrix;
	
	public static final int BLOCK_SIZE = 32;
	
	public static final int GROUND = 0; 
	public static final int WATER  = 1;
	public static final int WALL   = 2; 
	public static final int BRICK  = 3;
	
	public static void importData (String data) {
		
		Scanner in = new Scanner(data);
		int row = in.nextInt();
		int col = in.nextInt();
		matrix = new int[row][col];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = in.nextInt();
			}
		}
		
	}
	
	public static String exportData () {
		
		StringBuilder s = new StringBuilder();
		
		s.append(matrix.length + " " + matrix[0].length );
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				s.append(" " + matrix[i][j]);
			}
		}
		return s.toString();
	}
	
	public static Point respawn () {
		
		Random r = new Random ();
		int row, col;
		
		do {
			row = r.nextInt(matrix.length - 2)+1;
			col = r.nextInt(matrix[0].length - 2)+1;
		} while ( getObject(row, col) != GROUND   );
		
		Point p = new Point(col, row);
		return p;
	}
	
	public static int getHeight () {
		return matrix.length * BLOCK_SIZE;
	}
	public static int getWidth () {
		return matrix[0].length * BLOCK_SIZE;
	}
	
	public static void setObjectByXY (double x, double y, int object) {
		int col = getColByX(x);
		int row = getRowByY(y);
		
		if (col > 0 && col < matrix[0].length-1 && row > 0 && row < matrix.length - 1 )
		{
			matrix[row][col] = object;
		
		}
		
	}
	
	public static int getObjectByXY (double x, double y) {
		int col = getColByX(x);
		int row = getRowByY(y);
		return getObject(row, col);
		
	}
	
	public static int getRowByY(double y) {
		return (int)y / BLOCK_SIZE;
	}
	public static int getColByX(double x) {
		return (int)x / BLOCK_SIZE;
	}
	
	
	public static void paint (Graphics g) {
	
		
		int row1 = getRowByY( Camera.getY() ) ;
		int row2 = getRowByY( Camera.getY() + Camera.getHeight() -1 );
		int col1 = getColByX(Camera.getX());
		int col2 = getColByX (Camera.getX() + Camera.getWidth() - 1 );
		
		for (int row = row1; row <= row2; row++) {
			for (int col = col1; col <= col2; col++) {
				
				int object = matrix[row][col];
				
				int x = col * BLOCK_SIZE;
				int y = row * BLOCK_SIZE;
				
				ImageHelper.paint(g, 
						Camera.getScreenX(x), 
						Camera.getScreenY(y),
						object);
				
				
				
			}
		}
		
	}
	
	
	
	public static void create (int rows, int cols) {
		matrix = new int[rows][cols];
		
		for (int row = 0; row < rows; row ++) {
			matrix[row][0]      = WALL;
			matrix[row][cols-1] = WALL;
		}
		for (int col = 0; col < cols; col ++) {
			matrix[0][col]      = WALL;
			matrix[rows-1][col] = WALL;
		}
		
		
		Random r = new Random ();
		
		for (int row = 1; row < rows-1; row++) {
			for (int col = 1; col < cols-1; col++) {
				
				if (r.nextInt(10)==0) {
					int object = r.nextInt(3)+1;
					matrix[row][col] = object;
				}
			}
		}
		
		
		
		
	}
	
	public static int getObject(int row, int col) {
		
		if (row < 0 || col < 0 || row >= matrix.length || col >= matrix[0].length ) {
			return -1;
		}
		else {
			return matrix[row][col];
		}
		
	}
	
	
	public static boolean isPassable(int x, int y, boolean flyable) {
		
		int col = Map.getColByX(x);
		int row = Map.getRowByY(y);

		return getObject(row, col) == GROUND
				||
				(getObject(row, col) == WATER && flyable)
				
				;
	}
	
	
}
