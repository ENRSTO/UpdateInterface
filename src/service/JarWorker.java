package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public class JarWorker extends SwingWorker<Void, String>{

	private String jarFile;
    private String pathInstallation;
    private JTextArea area;

    public JarWorker(String pathInstallation, String jarFile, JTextArea area) {
        this.pathInstallation = pathInstallation;
        this.jarFile = jarFile;
        this.area = area;
    }

    @Override
    protected Void doInBackground() throws Exception {
        // Costruisci il comando per eseguire solo il jar senza argomenti
        String[] command = {"java", "-jar", jarFile};

        // Imposta la directory di lavoro
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(new java.io.File(pathInstallation));

        // Avvia il processo
        Process process = processBuilder.start();

        // Leggi l'output del processo
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            publish(line);  // Pubblica ogni riga di output
        }

        reader.close();
        return null;
    }

    @Override
    protected void process(List<String> chunks) {
        for (String line : chunks) {
            area.append(line + "\n");
            area.setCaretPosition(area.getDocument().getLength());  // Mantiene lo scroll alla fine
        }
    }
}


