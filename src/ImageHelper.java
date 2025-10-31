import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class ImageHelper {

	private static BufferedImage sprites;
	private static BufferedImage [] frames = new BufferedImage[100];
	
	public static BufferedImage[] getFrames() {
		return frames;
	}


	static {
		getSprites();
		cropImage (  
				new Rectangle (	2*Map.BLOCK_SIZE, 
								0, 
								Map.BLOCK_SIZE, 
								Map.BLOCK_SIZE),
				Map.GROUND );
		
		cropImage (  
				new Rectangle (	3*Map.BLOCK_SIZE, 
								0, 
								Map.BLOCK_SIZE, 
								Map.BLOCK_SIZE),
				Map.WATER );
		
		cropImage (  
				new Rectangle (	0, 
								Map.BLOCK_SIZE, 
								Map.BLOCK_SIZE, 
								Map.BLOCK_SIZE),
				Map.WALL );	
		
		cropImage (  
				new Rectangle (	Map.BLOCK_SIZE, 
								Map.BLOCK_SIZE, 
								Map.BLOCK_SIZE, 
								Map.BLOCK_SIZE),
				Map.BRICK );
		
		
		// Машина 4, 5, 6, 7
		for (int i = 0; i <= 3; i++) {
			cropImage (  
					new Rectangle (	i*Map.BLOCK_SIZE, 
									5*Map.BLOCK_SIZE, 
									Map.BLOCK_SIZE, 
									Map.BLOCK_SIZE),
					7-i );
		}
		
		// Красный танк 8 - 17
		for (int i = 0; i <= 9; i++) {
			cropImage (  
					new Rectangle (	i*Map.BLOCK_SIZE, 
									2*Map.BLOCK_SIZE, 
									Map.BLOCK_SIZE, 
									Map.BLOCK_SIZE),
					17-i );
		}
		
		// Дуло 18
		cropImage (  
				new Rectangle (	0*Map.BLOCK_SIZE, 
								3*Map.BLOCK_SIZE, 
								Map.BLOCK_SIZE, 
								Map.BLOCK_SIZE),
				18 );
		
		
		// Снаряд 19
		cropImage (  
				new Rectangle (	0*Map.BLOCK_SIZE, 
								4*Map.BLOCK_SIZE, 
								Map.BLOCK_SIZE, 
								Map.BLOCK_SIZE),
				19 );
		
		int frame = 20;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				cropImage (  
						new Rectangle (	(j+4)*Map.BLOCK_SIZE, 
										(i+3)*Map.BLOCK_SIZE, 
										Map.BLOCK_SIZE, 
										Map.BLOCK_SIZE),
						frame );
				
				frame++;
				
			}
		}
		
		// Синий танк 45 - 54
				for (int i = 0; i <= 9; i++) {
					cropImage (  
							new Rectangle (	i*Map.BLOCK_SIZE, 
											10*Map.BLOCK_SIZE, 
											Map.BLOCK_SIZE, 
											Map.BLOCK_SIZE),
							54-i );
				}
		// Дуло 55
			cropImage (  
						new Rectangle (	2*Map.BLOCK_SIZE, 
										3*Map.BLOCK_SIZE, 
										Map.BLOCK_SIZE, 
										Map.BLOCK_SIZE),
						55 );
		// Ракета 56
			cropImage (  
					new Rectangle (	1*Map.BLOCK_SIZE, 
									4*Map.BLOCK_SIZE, 
									Map.BLOCK_SIZE, 
									Map.BLOCK_SIZE),
					56 );
		
		
	}
	
	public static void cropImage (Rectangle r, int index) {
		
		frames[index] = sprites.getSubimage(r.x, r.y, r.width, r.height);
		
	}
	
	public static void paint (Graphics g, int x, int y, int index) {
		g.drawImage( frames[index]   , x, y, null);
	}
	
	public static void paint (Graphics g, int x, int y, int index, double alpha) {
		
		if (alpha == 0) {
			paint ( g,  x,  y,  index);
		}
		else {
			AffineTransform transorm = new AffineTransform();
			transorm.translate(x, y);
			transorm.rotate(alpha, 
					frames[index].getWidth()/2, 
					frames[index].getHeight()/2);
			
			Graphics2D g2d = (Graphics2D)g; 
			
			g2d.drawImage(frames[index], transorm, null);
		}
	
	}
	
	
	private static void getSprites() {
		
		File f = new File ("sprites.png");
		
		try {
			sprites = ImageIO.read(f);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Не могу загрузить файл sprites.png");
			System.exit(0);
		}
		
	}
	
}
