import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import service.CommandExecutor;
import service.VersionReader;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class Uinterface extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Uinterface frame = new Uinterface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public  Uinterface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 708, 406);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextArea textArea = new JTextArea();
		
		
		
	//	textArea.setBounds(10, 84, 671, 275);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(10, 84, 671, 275);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		//contentPane.add(textArea);
		contentPane.add(scrollPane);

		JButton btnNewButton = new JButton("Versione");
		btnNewButton.setBounds(10, 53, 85, 21);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Ping");
		btnNewButton_1.setBounds(103, 53, 85, 21);
		contentPane.add(btnNewButton_1);
		
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Azione da eseguire quando il pulsante viene premuto
			    GetPing(textArea);
			    
			}
		});
		
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Azione da eseguire quando il pulsante viene premuto
				String version = GetVersion();
				textArea.append(version);
				System.out.println(version);
			}
		});
	} // Uinterface

	public static String GetVersion() {

		VersionReader versionjava = new VersionReader(new CommandExecutor());
		return versionjava.getJavaVersion();

	}
	
	public void GetPing(JTextArea textArea) {
		
		VersionReader ping = new  VersionReader(new CommandExecutor());
		ping.getPing(textArea, "10.200.100.160");
	}
	
}
