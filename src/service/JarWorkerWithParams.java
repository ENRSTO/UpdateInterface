package service;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.text.DefaultHighlighter;

public class JarWorkerWithParams extends SwingWorker<Void, String> {

	private String pathInstallation;
	private JTextArea area;
	private String configYml;
	private String configYmlPath; 
	private boolean firstinstallation; 
	private String java21pth;
	private JRadioButton installer;

	public JarWorkerWithParams(String pathInstallation, JTextArea area, String configYml, String configYmlPath ,  boolean firstinstallation) {
		this.pathInstallation = pathInstallation;
		this.area = area;
		this.configYml = configYml;
		this.configYmlPath = configYmlPath;
		this.firstinstallation = firstinstallation;
	}

	public JarWorkerWithParams(String pathInstallation, JTextArea area, String configYml,  String configYmlPath , boolean firstinstallation, String java21pth, JRadioButton installer) {
		this.pathInstallation = pathInstallation;
		this.area = area;
		this.configYml = configYml;
		this.configYmlPath = configYmlPath;
		this.firstinstallation = firstinstallation;
		this.java21pth = (java21pth == null) ? java21pth = "" : java21pth;
		this.installer = installer;
	}

	@Override
	protected Void doInBackground() throws Exception {

		String jarFile = !java21pth.isBlank()? pathInstallation + "/" + "version.jar" : "version.jar";
		String classpath = "./lib/*";
		//  String configFile = "../" + configYml;
		String configFile =  !java21pth.isBlank()?  configYmlPath + "/" + configYml : "../" + configYml;
		String firstInstallation = firstinstallation ? "true" : "false";
		String dataInstallation = "false";
		String baseInstallation = "false";

		ProcessBuilder processBuilder;

		String javaPath =  !java21pth.isBlank()?  java21pth+ "/java" : pathInstallation + "/java";

		if (firstinstallation) {
			processBuilder = new ProcessBuilder(
					javaPath, 
					"-jar", jarFile, 
					"-cp", classpath, 
					"-config", configFile, 
					"-firstInstallation", firstInstallation, 
					"-dataInstallation", dataInstallation
					);
		} else {
			processBuilder = new ProcessBuilder(
					javaPath,
					"-jar", jarFile, 
					"-cp", classpath, 
					"-config", configFile, 
					"-dataInstallation", dataInstallation
					);
		}
		if (installer.isSelected()) {
			dataInstallation = "true";
			processBuilder = new ProcessBuilder(
					javaPath,
					"-jar", jarFile, 
					"-cp", classpath, 
					"-config", configFile, 
					"-baseInstallation", baseInstallation,
					"-dataInstallation", dataInstallation
					);
			
		}

		area.append("\n");
		area.append("\n");
		if (firstinstallation) {
			area.append("E' una prima installazione e \n");
		}
		area.append("lancio questo comando per eseguire l'aggiornamento: \n");
		area.append(processBuilder.command().toString());

		processBuilder.redirectErrorStream(true);

		Process process = processBuilder.start();

		// Usa un PipedWriter per intercettare e scrivere l'output
		System.out.println("Processo avviato. Attendo il completamento...");
		area.append("Processo avviato. Attendo il completamento...\n");


		new Thread(() -> {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				String line;
				while ((line = reader.readLine()) != null) {
					publish(line); // Pubblica l'output standard
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();

		boolean finished = process.waitFor(15, TimeUnit.SECONDS); // Timeout di 15 secondi

		if (!finished) {
			area.append("\n");
			System.out.println("Il processo è ancora in esecuzione. Timeout superato.");
			area.append("Il processo è ancora in esecuzione. Timeout superato.\n");
			process.destroy(); // Forza la chiusura del processo
		} else {
			area.append("\n");
			System.out.println("Processo terminato correttamente.");
			area.append("Processo terminato correttamente.\n");
		}
		installer.setSelected(true);
		return null;

	} // doInBackgroundS

	@Override
	protected void process(List<String> chunks) {
		SwingUtilities.invokeLater(() -> {
			for (String line : chunks) {
				area.append(line + "\n");
				area.setCaretPosition(area.getDocument().getLength());
			}
		});
	} // process

	@Override
	protected void done() {
		SwingUtilities.invokeLater(() -> {
			area.append("\n");
			area.append("\n");
			area.append("Aggiornamento eseguito  - Operazione completata!\n");
			area.setCaretPosition(area.getDocument().getLength());
			installer.setSelected(true);
		});
	}
}
