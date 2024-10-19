package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JTextArea;

import Utils.CopyDir;

public class ServiceProvider {

	private static CommandExecutor command;

	public ServiceProvider() {

	} // ServiceProvider

	public ServiceProvider(CommandExecutor command) {
		this.command = command;
	}  // ServiceProvider

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

}
