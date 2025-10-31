import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener {

	private Font f = new Font(Font.SANS_SERIF, Font.BOLD, 32);
	private boolean pause = false;
	
	private Sprite tank;
	private long oldTime;
	private Tank player;
	
	int mouseX, mouseY;
	
	boolean miniMap = false;
	
	private boolean left = false, up = false, down = false, right = false;
	
	public GamePanel () {
		tank = new Sprite(4, 7);
		player = NPCManager.getPlayer();
		oldTime = System.currentTimeMillis();
		//tank.active = false;
	}
	
	private void processKeyboard () {
		player.stop();
		if (right) {
			player.right();
			player.go();
		}
		else if (left) {
			player.left();
			player.go();
		}
		else if (up) {
			player.up();
			player.go();
		}
		else if (down) {
			player.down();
			player.go();
		}
	}
	
	
	private void update () {
		
		long time = System.currentTimeMillis();
		int ms = (int)(time - oldTime);
		oldTime = time;
		
		if (pause) {
			return;
		}
		
		processKeyboard();
		
		Camera.setWidth( getWidth() );
		Camera.setHeight( getHeight() );
		
		if (MapEditor.editor) {
			if (mouseX < 50) {
				Camera.move(-5, 0);   
			}
			else if (mouseX > Camera.getWidth() - 50) {
				Camera.move( 5, 0);
			}
			
			if (mouseY < 50) {
				Camera.move( 0, -5);
			}
			else if (mouseY > Camera.getHeight() - 50) {
				Camera.move( 0, 5);
			}
			
		}
		else {
			Camera.setPosition( player.getX() - getWidth() / 2    , 
				player.getY() - getHeight() /2 );
		}
		
		
		
		
		tank.update(ms);
		//player.update(ms);
		
		NPCManager.update(ms);
		RocketManager.update(ms);
		ExplosionManager.update(ms);
		
		
	}
	
	
	@Override
	public void paint(Graphics g) {	
		update ();
		
		
		Map.paint(g);
		
		tank.paint(g);
		player.paint(g);
		
		RocketManager.paint(g);
		NPCManager.paint(g);
		ExplosionManager.paint(g);
		MapEditor.paint(g);
		SaveLoad.paint(g);
		
		if (miniMap) {
			NPCManager.paintMiniMap(g);
		}
		
		g.setColor(Color.RED);
		g.setFont(f);
		g.drawString(SettingsForm.getUserName(), 300, 50);
		
		if (pause) {
			g.setColor(Color.GRAY);
			g.setFont(f);
			g.drawString("[Enter] для выхода", getWidth()/2 - 50, getHeight()/2);
		}
		
		
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent key) {
		
		if (pause) {
			if (key.getKeyCode() == KeyEvent.VK_ENTER) {
				System.exit(0);
			}
		}
		
		
		
		if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
			tank.setAlpha(0);
		}
		else if (key.getKeyCode() == KeyEvent.VK_LEFT) {
			tank.setAlpha(Math.PI);
		}
		else if (key.getKeyCode() == KeyEvent.VK_UP) {
			tank.setAlpha(3*Math.PI/2);
		}
		else if (key.getKeyCode() == KeyEvent.VK_DOWN) {
			tank.setAlpha( Math.PI/2);
		}
		
		else if (key.getKeyCode() == KeyEvent.VK_A) {
			left = true;
		}
		else if (key.getKeyCode() == KeyEvent.VK_W) {
			up = true;
		}
		else if (key.getKeyCode() == KeyEvent.VK_D) {
			right = true;
		}
		else if (key.getKeyCode() == KeyEvent.VK_S) {
			down = true;
		}
		else if (key.getKeyCode() == KeyEvent.VK_SPACE) {
			player.fire();
		}
		
		else if (key.getKeyCode() == KeyEvent.VK_PAUSE || key.getKeyCode() == KeyEvent.VK_ESCAPE) {
			pause = !pause;
		}
		else if (key.getKeyCode() == KeyEvent.VK_F1) {
			player.setRocket(true);
		}
		else if (key.getKeyCode() == KeyEvent.VK_F2) {
			player.setRocket(false);
		}
		else if (key.getKeyCode() == KeyEvent.VK_E) {
			MapEditor.editor = !MapEditor.editor;
		}
		
		else if (key.getKeyCode() == KeyEvent.VK_F12) {
			// Сохранение в выбранный слот
			SaveLoad.saveMap();
		}
		else if (key.getKeyCode() == KeyEvent.VK_F11) {
			// Загрузка из выбранного слота
			SaveLoad.loadMap();
		}
		else if (key.getKeyCode() >= KeyEvent.VK_0 && key.getKeyCode() <= KeyEvent.VK_9 ) {
			int slot = key.getKeyCode() - KeyEvent.VK_0;
			SaveLoad.changeSlot(slot);
		}
		
		else if (key.getKeyCode() == KeyEvent.VK_M) {
			
			miniMap = !miniMap;
		}
		
		
		
		
	}

	@Override
	public void keyReleased(KeyEvent key) {
		
		if (key.getKeyCode() == KeyEvent.VK_A) {
			left = false;
		}
		else if (key.getKeyCode() == KeyEvent.VK_W) {
			up = false;
		}
		else if (key.getKeyCode() == KeyEvent.VK_D) {
			right = false;
		}
		else if (key.getKeyCode() == KeyEvent.VK_S) {
			down = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if (MapEditor.editor) {
			if (e.getButton() == 1) {
				MapEditor.processMouseClick(e.getX(), e.getY());
			}
			else if (e.getButton() == 3)
			{
				// Установить блок
				MapEditor.placeBlock(e.getX(), e.getY());
			}
		}
		else {
			// режим игры
			if (e.getButton() == 1) {
				player.fire();
			}
		}

		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {

		if (MapEditor.editor) {
			if (e.getWheelRotation() < 0) {
				MapEditor.prevBlock();
			}
			else {
				MapEditor.nextBlock();
			}
		}
		else {
			player.changeWeapon();	
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (MapEditor.editor) {
			MapEditor.placeBlock(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (MapEditor.editor) {
			mouseX = e.getX();
			mouseY = e.getY();
		}
		else {
			player.rotateGunToXY( e.getX(),   e.getY());
		}
		
	}
	
}
