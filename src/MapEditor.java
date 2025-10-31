import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JOptionPane;

public class MapEditor {

	static public boolean editor = false;
	static private int offsetX = 200, offsetY = 50;
	static private int current = 1;
	
	public static void nextBlock() {
		current++;
		if (current > 3) {
			current = 0;
		}
	}
	
	public static void processMouseClick (int x, int y) {
		
		if ( x >= offsetX && 
				y >= offsetY  &&
				y <= Map.BLOCK_SIZE + offsetY &&
				x < Map.BLOCK_SIZE * 4 + offsetX
				
				) {
			//JOptionPane.showMessageDialog(null, "!!!");
			current = (x - offsetX) / Map.BLOCK_SIZE; 
			
		}
		
	}
	
	public static void placeBlock(int x, int y) {
		int globalX, globalY;
		globalX = (int) (x + Camera.getX());
		globalY = (int) (y + Camera.getY());
		Map.setObjectByXY(globalX, globalY, current);
		
		
	}
	
	public static void prevBlock() {
		current--;
		if (current < 0) {
			current = 3;
		}
	}
	
	public static void paint (Graphics g) {
		if (editor) {
			
			for (int i = 0; i < 4; i++) {
				ImageHelper.paint(g, offsetX + i*Map.BLOCK_SIZE, offsetY, i);
				
				if (i == current) {
					g.setColor(Color.RED);
				}
				else {
					g.setColor(Color.DARK_GRAY);
				}
				
				g.drawRect(offsetX + i*Map.BLOCK_SIZE, offsetY, Map.BLOCK_SIZE, Map.BLOCK_SIZE);
			}
			
			
		}
	}
	
	
}
