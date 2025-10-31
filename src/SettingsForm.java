import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.SliderUI;
import javax.swing.event.ChangeEvent;

public class SettingsForm extends JDialog {

	private static String userName;
	
	public static String getUserName() {
		return userName;
	}


	private final JPanel contentPanel = new JPanel();
	private JTextField playerName;
	private JTextField heightText;


	/**
	 * Create the dialog.
	 */
	public SettingsForm() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
			JSpinner widthSpinner = new JSpinner();
			widthSpinner.setModel(new SpinnerNumberModel(50, 50, 200, 1));
			widthSpinner.setBounds(35, 37, 46, 20);
			contentPanel.add(widthSpinner);
		
		
		playerName = new JTextField();
		playerName.setBounds(35, 140, 150, 20);
		contentPanel.add(playerName);
		playerName.setColumns(10);
		
		heightText = new JTextField();
		heightText.setBounds(110, 37, 86, 20);
		contentPanel.add(heightText);
		heightText.setColumns(10);
		
		JLabel sliderText = new JLabel("");
		
		sliderText.setBounds(191, 68, 46, 14);
		contentPanel.add(sliderText);
		
		
		JSlider tanksQuantinyslider = new JSlider();
		tanksQuantinyslider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				
				sliderText.setText( ""+tanksQuantinyslider.getValue());
				//    
			}
		});
		tanksQuantinyslider.setValue(30);
		tanksQuantinyslider.setMaximum(200);
		tanksQuantinyslider.setMinimum(10);
		tanksQuantinyslider.setBounds(34, 93, 362, 23);
		contentPanel.add(tanksQuantinyslider);
		
		JLabel lblNewLabel = new JLabel("\u0428\u0438\u0440\u0438\u043D\u0430");
		lblNewLabel.setBounds(35, 12, 46, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u0412\u044B\u0441\u043E\u0442\u0430");
		lblNewLabel_1.setBounds(110, 12, 46, 14);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u041A\u043E\u043B\u0438\u0447\u0435\u0441\u0442\u0432\u043E \u0442\u0430\u043D\u043A\u043E\u0432");
		lblNewLabel_2.setBounds(35, 68, 161, 14);
		contentPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("\u0418\u043C\u044F \u0438\u0433\u0440\u043E\u043A\u0430");
		lblNewLabel_3.setBounds(35, 115, 86, 14);
		contentPanel.add(lblNewLabel_3);
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u0421\u0442\u0430\u0440\u0442");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						int width = Integer.parseInt( widthSpinner.getValue().toString());
						
						int height = 0;
						
						try {
							height = Integer.parseInt( heightText.getText());
						}
						catch (Exception e2) {
							height = 200;
							JOptionPane.showMessageDialog(null, "Высота принимается = 200");
						}
						
						
						int quantity = tanksQuantinyslider.getValue();
						
						userName = playerName.getText();
						
						
						// Запустить игру
						Map.create(height, width);
						NPCManager.create(quantity);
						Window w = new Window();
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("\u0412\u044B\u0445\u043E\u0434");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						System.exit(0);
						
						
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
