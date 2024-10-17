package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public class PingWorker extends SwingWorker<Void, String>{

	private String command;
	private String host;
	private JTextArea area;

	public PingWorker(String command, String host, JTextArea area) {
		this.command = command;
		this.host = host;
		this.area = area;
	}

	@Override
	protected Void doInBackground() throws Exception {
		Process process = Runtime.getRuntime().exec(command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            publish(line);
            System.out.println(line);
        }
        reader.close();
        return null;
	}
	
	@Override
    protected void process(List<String> chunks) {
        for (String line : chunks) {
            area.append(line + "\n");
            area.setCaretPosition(area.getDocument().getLength());
        }
    } 

}
