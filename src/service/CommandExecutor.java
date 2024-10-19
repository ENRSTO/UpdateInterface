package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public class CommandExecutor {

	StringBuilder result = new StringBuilder();
	// metodo
	public String executeCommand(String command) {

		// Esegue il comando di sistema
		try {
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line).append("\n");
			}
			  // Chiude il reader
            reader.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result.toString();
	} // executeCommand
	
	
	public void executeCommandPing(String command, String host, JTextArea area) {
		
		  SwingWorker<Void, String> worker = new PingWorker(command, host, area);
		    worker.execute();
	} // executeCommandPing
	

}
