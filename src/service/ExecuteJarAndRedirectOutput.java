package service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class ExecuteJarAndRedirectOutput {

	private String jarFile;
	private String[] jarArgs;
	private String pathInstallation;
	private JTextArea area;
	private String configYml;
	private String configYmlPath; 
	private boolean firstinstallation; 
	private String java21pth;


	public ExecuteJarAndRedirectOutput(String pathInstallation, JTextArea area, String configYml, String configYmlPath ,  boolean firstinstallation) {
		this.pathInstallation = pathInstallation;
		//	this.jarFile = jarFile;
		//	this.jarArgs = jarArgs; // Parametri da passare al jar
		this.area = area;
		this.configYml = configYml;
		this.configYmlPath = configYmlPath;
		this.firstinstallation = firstinstallation;
	}

	public ExecuteJarAndRedirectOutput(String pathInstallation, JTextArea area, String configYml,  String configYmlPath , boolean firstinstallation, String java21pth) {
		this.pathInstallation = pathInstallation;
		//	this.jarFile = jarFile;
		//	this.jarArgs = jarArgs; // Parametri da passare al jar
		this.area = area;
		this.configYml = configYml;
		this.configYmlPath = configYmlPath;
		this.firstinstallation = firstinstallation;
		this.java21pth = java21pth;
	}



	public void exec () {

		ProcessBuilder processBuilder;

		String jarFile = !java21pth.isBlank()? pathInstallation + "/" + "version.jar" : "version.jar";
		String classpath = "./lib/*";
		//  String configFile = "../" + configYml;
		String configFile =  !java21pth.isBlank()?  configYmlPath + "/" + configYml : "../" + configYml;
		String firstInstallation = firstinstallation ? "true" : "false";
		String dataInstallation = "false";
		String javaPath =  !java21pth.isBlank()?  java21pth+ "/java" : "java";


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
		} // if - else

		new Thread(() -> {
			try {
				//   ProcessBuilder pb = new ProcessBuilder("java", "-jar", "mio_programma.jar", "parametro1", "parametro2");

				Process process = processBuilder.start();


				// Reindirizza l'output verso la JTextArea
				OutputStream out = new OutputStream() {
					@Override
					public void write(int b) throws IOException {
						SwingUtilities.invokeLater(() -> {
							area.append(String.valueOf((char) b));
							// Flush e aggiornamento dell'area di visualizzazione
	                        area.setCaretPosition(area.getDocument().getLength()); 
						
						});
					}
				};

				// Copia lo stream di output del processo nello stream personalizzato
				StreamCopier.copyStream(process.getInputStream(), out);
				StreamCopier.copyStream(process.getErrorStream(), out);

				int exitVal = process.waitFor();
				System.out.println("Process completed with exit value: " + exitVal);
			} catch (Exception e) {
				  SwingUtilities.invokeLater(() -> {
		                area.append("Errore durante l'esecuzione: " + e.getMessage());
		            });
			}
		}).start();

	} // exec

	// Classe helper per copiare lo stream di input in un altro stream
	private static class StreamCopier {
		public static void copyStream(InputStream in, OutputStream out) throws IOException {
			byte[] buffer = new byte[102400];
			int len;
			while ((len = in.read(buffer)) >= 0) {
				out.write(buffer, 0, len);
			}
			in.close();
			out.close();
		}
	} // StreamCopier



} // ExecuteJarAndRedirectOutput
