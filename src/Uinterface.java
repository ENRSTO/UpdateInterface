import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import service.CommandExecutor;
import service.VersionReader;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.Color;
import javax.swing.JRadioButton;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JSeparator;
import java.awt.Font;

public class Uinterface extends JFrame {

	/**
	 *  BY ERNSTO
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static boolean java21 = false;
	// variabili globali 
	private String pathJava;
	private String pathInstallation;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Uinterface frame = new Uinterface();
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}
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
		setTitle("Connector-Installer 1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 831, 582);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextArea textArea = new JTextArea();
		textArea.setBackground(new Color(0, 0, 0));
		textArea.setForeground(new Color(255, 255, 255));

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(20, 157, 774, 367);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		contentPane.add(scrollPane);

		JButton btnNewButton = new JButton("Versione");
		btnNewButton.setBounds(709, 78, 85, 21);
		contentPane.add(btnNewButton);

		JButton btnPing = new JButton("Ping");
		btnPing.setBounds(709, 103, 85, 21);
		contentPane.add(btnPing);

		JButton JavaPathChooser = new JButton("Percorso Java 21");
		JavaPathChooser.setBounds(20, 105, 189, 21);
		contentPane.add(JavaPathChooser);

		JRadioButton rdbtnNewRadioButton = new JRadioButton("Java 21 versione generale");
		rdbtnNewRadioButton.setBounds(20, 71, 189, 21);
		//	rdbtnNewRadioButton.setActionCommand("1");
		contentPane.add(rdbtnNewRadioButton);

		JRadioButton rdbtnVersioniJavaInferiori = new JRadioButton("versioni Java inferiori alla 21");
		rdbtnVersioniJavaInferiori.setBounds(20, 48, 219, 21);
		//	rdbtnVersioniJavaInferiori.setActionCommand("2");
		contentPane.add(rdbtnVersioniJavaInferiori);
		// RADIO BUTTO GROUP 		
		ButtonGroup group1 = new ButtonGroup();
		group1.add(rdbtnNewRadioButton);
		group1.add(rdbtnVersioniJavaInferiori);

		JButton installPath = new JButton("Pacchetto Installazione");
		installPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedDirectory = fileChooser.getSelectedFile();
					System.out.println("Cartella selezionata: " + selectedDirectory.getAbsolutePath());
					// Puoi salvare questo percorso dove desideri
				}
			}
		});
		installPath.setBounds(20, 130, 189, 21);
		contentPane.add(installPath);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(30, 40, 200, 2);
		contentPane.add(separator);
		
		JLabel lblNewLabel = new JLabel("         Installer");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(78, 24, 85, 13);
		contentPane.add(lblNewLabel);

		// LISTENER DEI RADIO 		
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JavaPathChooser.setEnabled(false);
				System.out.println("Selezionata: Java 21 versione generale");
			}
		});

		rdbtnVersioniJavaInferiori.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JavaPathChooser.setEnabled(true);
				System.out.println("Selezionata: versioni Java inferiori alla 21");
			}
		});

		// LISTENER DEL FILE CHOOSER
		JavaPathChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				// Imposta il file chooser per selezionare solo directory
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				// Mostra la finestra di dialogo
				int result = fileChooser.showOpenDialog(null);

				if (result == JFileChooser.APPROVE_OPTION) {
					// Ottieni la directory selezionata
					File selectedDirectory = fileChooser.getSelectedFile();
					System.out.println("Cartella selezionata: " + selectedDirectory.getAbsolutePath());
					// Puoi salvare questo percorso dove desideri
				}
			}
		});

		btnPing.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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

	public static void getVersionJavaSelected (JRadioButton rdbtnNewRadioButton, JRadioButton rdbtnNewRadioButton1) {

	}

	public static String GetVersion() {

		VersionReader versionjava = new VersionReader(new CommandExecutor());
		return versionjava.getJavaVersion();

	}

	public static void makeJarLaunch () {

	}

	public void GetPing(JTextArea textArea) {

		VersionReader ping = new  VersionReader(new CommandExecutor());
		ping.getPing(textArea, "10.200.100.160");
	}
}
