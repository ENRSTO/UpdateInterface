package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.yaml.snakeyaml.Yaml;

import Utils.CopyDir;

public class ServiceProvider {

	private static CommandExecutor command;

	public ServiceProvider() {
	} // ServiceProvider

	public ServiceProvider(CommandExecutor command) {
		this.command = command;
	}  // ServiceProvider

	public String SetSelectedDirectoryInstallation (String pathInstallation) {

		String comando = "cmd /c cd " + pathInstallation;
		return command.executeCommand(comando);

	}

	public String getJavaVersion() {
		// Comando per ottenere la versione di Java
		String comando = "cmd /c java -version";
		return command.executeCommand(comando);
	}

	public void getPing (JTextArea area, String host) {

		String 	comando =  "cmd /c ping " + host;
		command.executeCommandPing(comando, host, area);

	} // getPing

	public void executeCopyDir (String dirStart, String DirEnd, JTextArea area) {

		Path sourceDir = Paths.get(dirStart);
		Path targetDir = Paths.get(DirEnd);

		try {
			CopyDir.copyDirectory(sourceDir, targetDir);
			String MsgSuccess = "Copia completata con successo!";
			area.setText(MsgSuccess);
			System.out.println(MsgSuccess);
		} catch (IOException e) {
			e.printStackTrace();
			area.setText("NAAAA!!!");
			System.out.println("NAAAA!");
		}

	} // executeCopyDir

	public static void makeJarLaunch (String pathInstallation, String configYml) {

		String directoryPath = pathInstallation;

		// Nome del file JAR e altri parametri
		String jarFile = "version.jar";
		String classpath = "./lib/*";
		String configFile = "../" + configYml;
		String firstInstallation = "true";
		String dataInstallation = "false";

		ProcessBuilder processBuilder = new ProcessBuilder(
				"java", 
				"-jar", jarFile, 
				"-cp", classpath, 
				"-config", configFile, 
				"-firstInstallation", firstInstallation, 
				"-dataInstallation", dataInstallation
				);

		// Imposta la directory di lavoro
		processBuilder.directory(new java.io.File(pathInstallation));

		try {
			// Avvia il processo
			Process process = processBuilder.start();

			// Leggi l'output del processo
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}

			// Attendi la fine del processo
			int exitCode = process.waitFor();
			System.out.println("Processo terminato con codice: " + exitCode);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

	} // makeJarLaunch

	public static void makeJarLaunch2(String pathInstallation, JTextArea textArea) {
		String jarFile = "provetta.jar";

		// Imposta il ProcessBuilder con la directory di lavoro e il comando
		ProcessBuilder processBuilder = new ProcessBuilder(
				"java",
				"-Xrs",
				"-jar", jarFile
				);
		processBuilder.directory(new java.io.File(pathInstallation));

		try {
			// Avvia il processo
			Process process = processBuilder.start();

			// Lettura dell'InputStream (stdout) e dell'ErrorStream (stderr)
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			// Thread per leggere e visualizzare l'output standard
			new Thread(() -> leggiOutputProcesso(inputReader, textArea)).start();

			// Thread per leggere e visualizzare gli errori (stderr)
			new Thread(() -> leggiOutputProcesso(errorReader, textArea)).start();

			// Attendi che il processo termini
			int exitCode = process.waitFor();
			SwingUtilities.invokeLater(() -> textArea.append("Processo terminato con codice: " + exitCode + "\n"));

		} catch (IOException | InterruptedException e) {
			SwingUtilities.invokeLater(() -> textArea.append("Errore: " + e.getMessage() + "\n"));
		}
	}

	// Metodo per leggere l'output dal processo e aggiornarlo dinamicamente nella JTextArea
	public static void leggiOutputProcesso(BufferedReader reader, JTextArea textArea) {
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				// Variabile temporanea per il passaggio nella lambda
				String finalLine = line;
				SwingUtilities.invokeLater(() -> {
					textArea.append(finalLine + "\n");
					// Scorri automaticamente verso il basso
					textArea.setCaretPosition(textArea.getDocument().getLength());
				});
			}
		} catch (IOException e) {
			SwingUtilities.invokeLater(() -> textArea.append("Errore nella lettura dell'output: " + e.getMessage() + "\n"));
		}
	}

	//	public static void makeJarLaunch2 (String pathInstallation, JTextArea textArea) {
	//		
	//		 String directoryPath = pathInstallation;
	//		 
	//		    // Nome del file JAR e altri parametri
	//	        String jarFile = "provetta.jar";
	//	        
	//	        ProcessBuilder processBuilder = new ProcessBuilder(
	//	                "java", 
	//	                "-jar", jarFile
	//	        );
	//	        
	//	        // Imposta la directory di lavoro
	//	        processBuilder.directory(new java.io.File(pathInstallation));
	//	        
	//	        try {
	//	            // Avvia il processo
	//	            Process process = processBuilder.start();
	//
	//	            // Leggi l'output del processo
	//	            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	//	            
	//	            new Thread(() -> leggiOutputProcesso(reader, textArea)).start();
	//	            
	//	            int exitCode = process.waitFor();
	//	            SwingUtilities.invokeLater(() -> textArea.append("Processo terminato con codice: " + exitCode + "\n"));
	//	        } catch (IOException | InterruptedException e) {
	//	        	 SwingUtilities.invokeLater(() -> textArea.append("Errore: " + e.getMessage() + "\n"));
	//	        }
	//
	//	} // makeJarLaunch2
	//	
	//	public static void leggiOutputProcesso(BufferedReader reader, JTextArea textArea) {
	//		
	//		try {
	//			String line;
	//			while ((line = reader.readLine()) != null) {
	//				// Aggiungi la linea al JTextArea nel thread della GUI
	//				String finalLine = line;
	//				SwingUtilities.invokeLater(() -> {
	//					textArea.append(finalLine + "\n");
	//					textArea.setCaretPosition(textArea.getDocument().getLength());
	//				});
	//			} // while 
	//		} catch (IOException e) {
	//			SwingUtilities.invokeLater(() -> textArea.append("Errore nella lettura dell'output: " + e.getMessage() + "\n"));
	//		} //catch
	//	} // leggiOutputProcesso
	//	
	public static void makeJarLaunch3 (String pathInstallation, JTextArea textArea) {

		String directoryPath = pathInstallation;

		// Nome del file JAR e altri parametri
		String jarFile = "provetta.jar";

		ProcessBuilder processBuilder = new ProcessBuilder(
				"java", 
				"-jar ", jarFile
				);

		// Imposta la directory di lavoro
		processBuilder.directory(new java.io.File(pathInstallation));

		try {
			// Avvia il processo
			Process process = processBuilder.start();

			// Leggi l'output del processo
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}

			// Attendi la fine del processo
			int exitCode = process.waitFor();
			System.out.println("Processo terminato con codice: " + exitCode);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

	} // makeJarLaunch2


	// metodo per richiamare la console e lanciare lì tutto il cinema
	public static void makeJarLaunchWithoutConfig(String pathInstallation) {

		try { 
			// For Windows 
			String command = "cmd.exe /c start cmd.exe /K cd /d " + pathInstallation; 
			Runtime.getRuntime().exec(command); 

		} catch (IOException e) { 
			e.printStackTrace(); 
		} // try 

	} // makeJarLaunchWithoutConfig

	public static void readYmlFile (File selectedDirectory) {

		Yaml yaml = new Yaml();
		try (InputStream inputStream = new FileInputStream(selectedDirectory)) {

			// Carica i dati in una mappa (puoi anche usare classi personalizzate)
			Map<String, Object> data = yaml.load(inputStream);

			Map<String, Object> databaseConfig = (Map<String, Object>) data.get("databaseConfiguration");

			// Accedi alla sotto-mappa "dataSource" dentro "databaseConfiguration"
			Map<String, Object> dataSource = (Map<String, Object>) databaseConfig.get("dataSource");

			// Stampa i dati caricati
			System.out.println("Contenuto del file YAML:");
			System.out.println(data);
			System.out.println(dataSource.get("url"));
			System.out.println(dataSource.get("user"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	} // readYmlFile


} // service Provider
