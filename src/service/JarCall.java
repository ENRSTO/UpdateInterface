package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JTextArea;

public class JarCall {

	private String jarFile;
	private String[] jarArgs;
	private String pathInstallation;
	private JTextArea area;
	private String configYml;
	private String configYmlPath; 
	private boolean firstinstallation; 
	private boolean installer;
	private String java21pth;



	public JarCall (String pathInstallation, JTextArea area, String configYml,  String configYmlPath , boolean firstinstallation, String java21pth) {
		this.pathInstallation = pathInstallation;
		//	this.jarFile = jarFile;
		//	this.jarArgs = jarArgs; // Parametri da passare al jar
		this.area = area;
		this.configYml = configYml;
		this.configYmlPath = configYmlPath;
		this.firstinstallation = firstinstallation;
		this.java21pth = (java21pth == null) ? java21pth = "" : java21pth;
	}



	public void startProcessCall () throws IOException, InterruptedException  {

		String jarFile = !java21pth.isBlank()? pathInstallation + "/" + "version.jar" : "version.jar";
		String classpath = "./lib/*";
		//  String configFile = "../" + configYml;
		String configFile =  !java21pth.isBlank()?  configYmlPath + "/" + configYml : "../" + configYml;
		String firstInstallation = firstinstallation ? "true" : "false";
		String dataInstallation = "false";

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

		ProcessBuilder pb = processBuilder;
		Process process = pb.start();

		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			area.append(line + "\n");
		}

		int exitCode = process.waitFor();

		// Se il processo si è concluso con successo, scrive un messaggio
		if (exitCode == 0) {
			area.append("Elaborazione completata\n");
		} else {
			area.append("Errore durante l'elaborazione\n");
		}

	} // startProcessCall


}
