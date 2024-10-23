import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import service.CommandExecutor;
import service.ServiceProvider;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
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
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

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
					String className = getLookAndFeelClassName("Nimbus");
					UIManager.setLookAndFeel(getLookAndFeelClassName(className)); 
				} catch(Exception ignored){}
				Uinterface frame = new Uinterface();
				frame.setVisible(true);
			} // run

			private String getLookAndFeelClassName(String nameSnippet) {
				LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
				for (LookAndFeelInfo info : plafs) {
					if (info.getName().contains(nameSnippet)) {
						return info.getClassName();
					}
				}
				return null;
			}
		});
	} // main

	/**
	 * Create the frame.
	 */
	public  Uinterface() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Uinterface.class.getResource("/Utils/LogoSMI.png")));
		setResizable(false);
		setTitle("iGCN - 1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 820, 635);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextArea textArea = new JTextArea();
		textArea.setBackground(Color.DARK_GRAY);
		textArea.setForeground(new Color(255, 255, 255));

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(20, 199, 774, 368);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		contentPane.add(scrollPane);

		JButton btnNewButton = new JButton("Versione");
		btnNewButton.setBounds(709, 143, 85, 21);
		contentPane.add(btnNewButton);

		JButton btnPing = new JButton("Ping");
		btnPing.setBounds(709, 168, 85, 21);
		contentPane.add(btnPing);

		JButton JavaPathChooser = new JButton("Percorso Java 21");
		JavaPathChooser.setBounds(20, 137, 189, 21);
		contentPane.add(JavaPathChooser);

		JRadioButton rdbtnNewRadioButton = new JRadioButton("Java 21 versione generale");
		rdbtnNewRadioButton.setBounds(20, 103, 189, 21);
		//	rdbtnNewRadioButton.setActionCommand("1");
		contentPane.add(rdbtnNewRadioButton);

		JRadioButton rdbtnVersioniJavaInferiori = new JRadioButton("versioni Java inferiori alla 21");
		rdbtnVersioniJavaInferiori.setBounds(20, 80, 219, 21);
		//	rdbtnVersioniJavaInferiori.setActionCommand("2");
		contentPane.add(rdbtnVersioniJavaInferiori);
		// RADIO BUTTO GROUP 		
		ButtonGroup group1 = new ButtonGroup();
		group1.add(rdbtnNewRadioButton);
		group1.add(rdbtnVersioniJavaInferiori);

		JLabel lblPathInstallation = new JLabel("");
		lblPathInstallation.setBounds(213, 130, 405, 17);
		contentPane.add(lblPathInstallation);

		JButton installPath = new JButton("Pacchetto Installazione");
		installPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedDirectory = fileChooser.getSelectedFile();
					System.out.println("Cartella selezionata: " + selectedDirectory.getAbsolutePath());
					pathJava = selectedDirectory.getAbsolutePath();
					lblPathInstallation.setText(pathJava);
				}
			}
		});
		installPath.setBounds(20, 168, 189, 21);
		contentPane.add(installPath);

		JSeparator separator = new JSeparator();
		separator.setBounds(20, 72, 200, 2);
		contentPane.add(separator);

		JLabel lblNewLabel = new JLabel("  Installer");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(20, 57, 85, 13);
		contentPane.add(lblNewLabel);

		JButton btnCopy = new JButton("Copia ");
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				execCopy(pathInstallation, pathJava, textArea);
			}
		});
		btnCopy.setBounds(709, 113, 85, 21);
		contentPane.add(btnCopy);

		JLabel lblPath21 = new JLabel("");
		lblPath21.setBounds(213, 107, 405, 17);
		contentPane.add(lblPath21);
		
		JLabel lblNewLabel_1 = new JLabel("     Connector-Installer");
		lblNewLabel_1.setForeground(Color.DARK_GRAY);
		lblNewLabel_1.setBackground(SystemColor.activeCaption);
		lblNewLabel_1.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNewLabel_1.setBounds(0, 0, 816, 29);
		lblNewLabel_1.setOpaque(true);
		contentPane.add(lblNewLabel_1);

		// LISTENER DEI RADIO 		
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JavaPathChooser.setEnabled(false);
				lblPath21.setText("");
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
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				// Mostra la finestra di dialogo
				int result = fileChooser.showOpenDialog(null);

				if (result == JFileChooser.APPROVE_OPTION) {
					// Ottieni la directory selezionata
					File selectedDirectory = fileChooser.getSelectedFile();
					System.out.println("Cartella selezionata: " + selectedDirectory.getAbsolutePath());
					pathInstallation = selectedDirectory.getAbsolutePath();
					lblPath21.setText(pathInstallation);
				}
			}
		});

		//ACTION LISTENER

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


	public static void makeJarLaunch () {

	}

	public static String GetVersion() {

		ServiceProvider versionjava = new ServiceProvider(new CommandExecutor());
		return versionjava.getJavaVersion();

	}

	public static void execCopy(String DirStart, String DirEnd, JTextArea textarea) {
		// verifico se le directory sono state dichiarate 
		if ((DirEnd == null || DirStart == null ) || (DirEnd == null && DirEnd.isBlank()) || (DirEnd == null && DirEnd.isEmpty()) || 
				(DirStart == null && DirStart.isBlank()) || (DirStart == null && DirStart.isEmpty()))  {
			JOptionPane.showMessageDialog(
					null, "Mancano le directory di partenza e Destinazione", "", JOptionPane.ERROR_MESSAGE);
		}
		ServiceProvider executeCopyDirectory = new ServiceProvider();
		executeCopyDirectory.executeCopyDir(DirStart, DirEnd, textarea);

	}

	public void GetPing(JTextArea textArea) {

		ServiceProvider ping = new  ServiceProvider(new CommandExecutor());
		ping.getPing(textArea, "10.200.100.160");

	}
}
