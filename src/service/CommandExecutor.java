package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JTextArea;

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
	}
	
	public void executeCommandPing(String command, String host, JTextArea area) {
		
		 try {

             // Esegui il comando
             Process process = Runtime.getRuntime().exec(command);

             // Leggi l'output del comando
             BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
             String line;
             while ((line = reader.readLine()) != null) {
                 area.append(line + "\n");  // Aggiungi ogni riga di output alla JTextArea
             }

             // Chiudi il reader
             reader.close();
         } catch (Exception ex) {
             area.append("Errore durante l'esecuzione del ping: " + ex.getMessage() + "\n");
         }
		
	}

}
