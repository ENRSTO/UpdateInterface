import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import service.CommandExecutor;
import service.ExecuteJarAndRedirectOutput;
import service.JarCall;
import service.JarWorker;
import service.JarWorkerWithParams;
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
import java.awt.Window.Type;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

public class Uinterface extends JFrame {

	/**
	 *  BY ERNSTO
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static boolean java21 = false;
	// variabili globali 
	private String pathJava;
	private static String pathInstallation;
	private String stringaInstallation;
	private final  String firstInstallation = " -firstInstallation true -dataInstallation false";  // string per la prima installazione 
	private String basicString = "java -jar "; // stringa base per java21
	private String java21path;		
	private static String configYml = "";
	private static String configYmlPath = "";
	private static File selectedYML;

	//	+ "version.jar -cp ./lib/* "; 

	// java -jar version.jar -cp ./lib/* -config ../config.yml -firstInstallation true -baseInstallation false -dataInstallation true


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			try {
				// Imposta il Look and Feel desiderato
				String className = getLookAndFeelClassName("Windows");
				if (className != null) {
					UIManager.setLookAndFeel(className);
				} else {
					System.err.println("Look and Feel 'Nimbus' non trovato, utilizzo il predefinito.");
				}
			} catch (Exception e) {
				e.printStackTrace(); // Mostra eventuali errori di caricamento
			}

			// Crea e mostra l'interfaccia utente
			Uinterface frame = new Uinterface();
			frame.setVisible(true);
		});
	} // main

	private static String getLookAndFeelClassName(String nameSnippet) {
		LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
		for (LookAndFeelInfo info : plafs) {
			if (info.getName().contains(nameSnippet)) {
				return info.getClassName();
			}
		}
		return null;
	} // getLookAndFeelClassName

	/**
	 * Create the frame.
	 */
	public  Uinterface() {
		setType(Type.UTILITY);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Uinterface.class.getResource("/Utils/LogoSMI.png")));
		setResizable(false);
		setTitle("GCN - 1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 972, 677);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextArea textArea = new JTextArea();
		textArea.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		textArea.setEditable(false);
		textArea.setBackground(Color.DARK_GRAY);
		textArea.setForeground(new Color(255, 255, 255));

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		scrollPane.setBounds(153, 213, 799, 412);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		contentPane.add(scrollPane);

		JRadioButton Installer = new JRadioButton("Installer");
		Installer.setBounds(433, 54, 137, 24);
		contentPane.add(Installer);
		
		JRadioButton rdbtnInstaller = new JRadioButton("Prima Installazione");
		rdbtnInstaller.setBounds(166, 54, 137, 24);
		contentPane.add(rdbtnInstaller);

		JRadioButton rdbtnUpdate = new JRadioButton("Aggiornamento");
		rdbtnUpdate.setBounds(307, 54, 122, 24);
		contentPane.add(rdbtnUpdate);

		ButtonGroup groupInstallerUpdater = new ButtonGroup();
		groupInstallerUpdater.add(rdbtnInstaller);
		groupInstallerUpdater.add(rdbtnUpdate);
		groupInstallerUpdater.add(Installer);

		JButton JavaPathChooser = new JButton("Percorso Java 21");
		JavaPathChooser.setBounds(168, 118, 165, 21);
		contentPane.add(JavaPathChooser);

		JRadioButton rdbtnJava21 = new JRadioButton("Java 21 versione generale");
		rdbtnJava21.setBounds(407, 89, 189, 21);
		//	rdbtnNewRadioButton.setActionCommand("1");
		contentPane.add(rdbtnJava21);

		JRadioButton rdbtnVersioniJavaInferiori = new JRadioButton("versioni Java inferiori alla 21");
		rdbtnVersioniJavaInferiori.setBounds(177, 89, 219, 21);
		//	rdbtnVersioniJavaInferiori.setActionCommand("2");
		contentPane.add(rdbtnVersioniJavaInferiori);
		// RADIO BUTTO GROUP 		
		ButtonGroup groupJava = new ButtonGroup();
		groupJava.add(rdbtnJava21);
		groupJava.add(rdbtnVersioniJavaInferiori);

		JLabel lblPath = new JLabel("");
		lblPath.setBounds(339, 147, 358, 16);
		contentPane.add(lblPath);

		JLabel lblPathConfigYML = new JLabel("");
		lblPathConfigYML.setBounds(339, 174, 358, 16);
		contentPane.add(lblPathConfigYML);

		JButton installPath = new JButton("Pacchetto Installazione");
		installPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedDirectory = fileChooser.getSelectedFile();
					System.out.println("Cartella selezionata: " + selectedDirectory.getAbsolutePath());
					pathInstallation = selectedDirectory.getAbsolutePath();
					lblPath.setText(pathInstallation);
				}
			}
		});

		installPath.setBounds(168, 144, 165, 21);
		contentPane.add(installPath);

		JSeparator separator = new JSeparator();
		separator.setBounds(162, 78, 260, 2);
		contentPane.add(separator);

		JLabel lblPath21 = new JLabel("");
		lblPath21.setBackground(SystemColor.inactiveCaptionBorder);
		lblPath21.setBounds(339, 122, 358, 17);
		contentPane.add(lblPath21);

		JLabel lblNewLabel_1 = new JLabel("   Installer");
		lblNewLabel_1.setForeground(Color.DARK_GRAY);
		lblNewLabel_1.setBackground(SystemColor.activeCaption);
		lblNewLabel_1.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNewLabel_1.setBounds(0, 0, 854, 29);
		lblNewLabel_1.setOpaque(true);
		contentPane.add(lblNewLabel_1);

		JButton fileYMLchooser = new JButton("File configurazione");
		fileYMLchooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedDirectory = fileChooser.getSelectedFile();
					selectedYML = selectedDirectory;
					System.out.println("File selezioneto :" + selectedDirectory.getName());
					configYml = selectedDirectory.getName();
					configYmlPath = selectedDirectory.getParent();
					lblPathConfigYML.setText(configYml);
				}
			} // actionPerformed
		});
		fileYMLchooser.setBounds(168, 171, 165, 21);
		contentPane.add(fileYMLchooser);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setForeground(UIManager.getColor("Button.background"));
		panel.setBounds(0, 30, 142, 627);
		contentPane.add(panel);
		panel.setLayout(null);



		JButton btnAvvio = new JButton("Avvio");
		btnAvvio.setBounds(22, 31, 98, 26);
		panel.add(btnAvvio);

		JButton btnCopy = new JButton("Copia ");
		btnCopy.setBounds(22, 63, 98, 26);
		panel.add(btnCopy);

		JButton btnNewButton = new JButton("Versione");
		btnNewButton.setBounds(22, 96, 98, 26);
		panel.add(btnNewButton);

		JButton btnPing = new JButton("Ping");
		btnPing.setBounds(22, 129, 98, 26);
		panel.add(btnPing);

		JButton test = new JButton("TEST");
		test.setBounds(22, 162, 98, 26);
		panel.add(test);
		
		JButton cleanBtn = new JButton("Pulisci");
		cleanBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.selectAll();
				textArea.replaceSelection("");
			}
		});
		cleanBtn.setActionCommand("");
		cleanBtn.setBounds(22, 194, 98, 26);
		panel.add(cleanBtn);
		
		JButton ymlBtn = new JButton("leggi YML");
		ymlBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServiceProvider.readYmlFile(selectedYML);
			}
		});
		ymlBtn.setActionCommand("");
		ymlBtn.setBounds(22, 229, 98, 26);
		panel.add(ymlBtn);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(766, 78, 128, 0);
		contentPane.add(separator_1);
		
		JLabel Logo = new JLabel("");
		Logo.setIcon(new ImageIcon(Uinterface.class.getResource("/Utils/GCN.png")));
		Logo.setBounds(856, 0, 112, 29);
		contentPane.add(Logo);
		
		test.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!pathInstallation.isEmpty()) {
					String[] jarArgs = null;
					JarWorker worker = new JarWorker(pathInstallation,  "provetta.jar" , textArea);
					worker.execute(); 
				} // if 
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
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				execCopy(pathInstallation, pathJava, textArea);
			}
		});
		btnAvvio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// prima installazione con java 21 base e config.yml  				
				if (rdbtnInstaller.isSelected() && rdbtnJava21.isSelected() && !configYml.isBlank() && !pathInstallation.isBlank()) {
					JarWorkerWithParams worker = new JarWorkerWithParams(pathInstallation, textArea, configYml, configYmlPath ,true, java21path, Installer);
					worker.execute(); 
					return;
				} // if 
				// prima installazione con java < 21 base e config.yml  
				if (rdbtnInstaller.isSelected() && rdbtnVersioniJavaInferiori.isSelected() && !configYml.isBlank() && !pathInstallation.isBlank() && !java21path.isBlank()) {
					JarWorkerWithParams worker = new JarWorkerWithParams(pathInstallation, textArea, configYml , configYmlPath, true, java21path, Installer);
					worker.execute(); 
					return;
				} // if 
				
				// aggiornamento con java21 configYMl 
				if (rdbtnUpdate.isSelected() && rdbtnJava21.isSelected() && (!configYml.isBlank() || !configYml.isBlank()) && !pathInstallation.isEmpty()) {
					JarWorkerWithParams worker = new JarWorkerWithParams(pathInstallation, textArea, configYml, configYmlPath , false);
					worker.execute(); 
					return;
				}
				// aggiornamento con java <  21  configYMl 
				if (rdbtnUpdate.isSelected() && rdbtnVersioniJavaInferiori.isSelected() && !configYml.isBlank() && !pathInstallation.isBlank() && !java21path.isBlank()) {
					JarWorkerWithParams worker = new JarWorkerWithParams(pathInstallation, textArea, configYml , configYmlPath, false, java21path, Installer);
					worker.execute(); 
					return;
				}
				// Installazione senza file di configurazione  apre console su 
				if (rdbtnInstaller.isSelected() && rdbtnJava21.isSelected() && configYml.isBlank()) {
					ServiceProvider.makeJarLaunchWithoutConfig(pathInstallation);
				}
				
				if (Installer.isSelected() && !configYml.isBlank() && !pathInstallation.isBlank() && !java21path.isBlank() ) {
					JarWorkerWithParams worker = new JarWorkerWithParams(pathInstallation, textArea, configYml , configYmlPath, false, java21path, Installer);
					worker.execute(); 
					System.out.println("ciaooo ");
				}
			} // action 
		});

		// LISTENER DEI RADIO 		
		rdbtnJava21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JavaPathChooser.setEnabled(false);
				lblPath21.setText("");
				System.out.println("Selezionata: Java 21 versione generale");
				java21path = null;
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
					java21path = selectedDirectory.getAbsolutePath();
					lblPath21.setText(java21path);
				}
			}
		});
	} // Uinterface

	public static void getVersionJavaSelected (JRadioButton rdbtnNewRadioButton, JRadioButton rdbtnNewRadioButton1) {

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
