import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Game {

	public static void main(String[] args) {
		
		SettingsForm dialog = new SettingsForm();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		
		
		
		/*ArrayList<String> list = new ArrayList<>();
		list.add("������ �����");
		list.add("������ NPC Manager");
		list.add("������ Rocket Manager");
		SaveLoad.save("array.txt", list);*/
		/*ArrayList<String> list = SaveLoad.load2("array.txt");
		if (list != null) {
			JOptionPane.showMessageDialog(null, list.get(1));
		}*/
		
		
		
		//SaveLoad.loadMap("map.txt");
		
		//SaveLoad.saveMap("map.txt");
		
	
		
		//SaveLoad.save("map.txt", "������ �����");
		//String s = SaveLoad.load("map.txt");
		//JOptionPane.showMessageDialog(null, s);
		 
	}

}
