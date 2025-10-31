import javax.swing.JFrame;

public class Window extends JFrame {

	public Window () {
		setExtendedState(MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible (true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		GamePanel panel = new GamePanel();
		add (panel);
		addKeyListener(panel);
		panel.addMouseListener(panel);
		panel.addMouseWheelListener(panel);
		panel.addMouseMotionListener(panel);
		
		
		revalidate();		
	}
	
}
