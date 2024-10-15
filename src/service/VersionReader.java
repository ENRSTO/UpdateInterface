package service;

import javax.swing.JTextArea;

public class VersionReader {

	private static CommandExecutor command;

	public VersionReader(CommandExecutor command) {
		this.command = command;
	} 

	public String getJavaVersion() {
		// Comando per ottenere la versione di Java
		String comando = "cmd /c java -version";
		return command.executeCommand(comando);
	}
	
	public void getPing (JTextArea area, String host) {
		
		String 	comando =  "cmd /c ping " + host;
		command.executeCommandPing(comando, host, area);
		
	}

}
