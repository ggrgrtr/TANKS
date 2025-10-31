import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class SaveLoad {

	
	private static int slot = 0;
	private static Font f = new Font(Font.SANS_SERIF, Font.BOLD, 36);
	
	
	
	public static void paint (Graphics g) {
		g.setFont(f);
		g.setColor(Color.WHITE);
		g.drawString("Слот №"+slot, 200, 300);
	}
	
	
	public static void changeSlot (int slot) {
		if (slot >= 0 && slot <= 9) {
			SaveLoad.slot = slot;
		}
	}
	
	public static void loadMap () {
		loadMap("./save/"+slot+".txt");
	}
	
	public static void saveMap () {
		saveMap("./save/"+slot+".txt");
	}
	
	
	public static void loadMap (int slot) {
		loadMap("./save/"+slot+".txt");
	}
	
	public static void saveMap (int slot) {
		saveMap("./save/"+slot+".txt");
	}
	
	
	public static void loadMap (String file) {
		String s = load(file);
		if (s != null) {
			Map.importData(s);
		}
	}
	
		
	public static void saveMap (String file) {
		save(file,  Map.exportData() );
	}
	
	
	
	
	public static void save(String file, String text) {
		File f = new File (file);
		PrintWriter pw = null;
		
		try {
			pw = new PrintWriter(f);
			pw.println(text);
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Файл не найден");
		}
		finally {
			if (pw !=null) {
				pw.close();
			}
		}
		
	}
	
	public static void save(String file, ArrayList<String> list) {
		File f = new File (file);
		PrintWriter pw = null;
		
		try {
			pw = new PrintWriter(f);
			for (String string : list) {
				pw.println(string);
			}
			
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Файл не найден");
		}
		finally {
			if (pw !=null) {
				pw.close();
			}
		}
		
	}
	
	public static ArrayList<String> load2(String file) {
		
		File f = new File(file);
		Scanner in = null;
		ArrayList<String> s = null;
		try {
			in = new Scanner(f);
			s = new ArrayList<>();
			while (in.hasNextLine()) {
				s.add(in.nextLine());
			}

			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Файл не найден");
		}
		finally {
			if (in !=null) {
				in.close();
			}
		}

		return s;
	}
	
	
	public static String load(String file) {
		
		File f = new File(file);
		Scanner in = null;
		String s = null;
		try {
			in = new Scanner(f);
			s = in.nextLine();
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Файл не найден");
		}
		finally {
			if (in !=null) {
				in.close();
			}
		}

		return s;
	}
	
	
	
}
