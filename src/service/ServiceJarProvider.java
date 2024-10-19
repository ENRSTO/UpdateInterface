package service;

import javax.swing.JTextArea;

public class ServiceJarProvider {
	
	private String pathInstallation;
	private JTextArea area;
	private String configYml;
	private String configYmlPath; 
	private boolean firstinstallation; 
	private boolean installer;
	private String java21pth;
	
	public ServiceJarProvider(String pathInstallation, JTextArea area, String configYml,
			String configYmlPath, boolean firstinstallation, boolean installer, String java21pth) {
		super();
		this.pathInstallation = pathInstallation;
		this.area = area;
		this.configYml = configYml;
		this.configYmlPath = configYmlPath;
		this.firstinstallation = firstinstallation;
		this.installer = installer;
		this.java21pth = java21pth;
	}

	public void execSwitch () {
		
		// è un aggiornamento 
		if (!firstinstallation) {
	//		JarWorkerWithParams worker = new JarWorkerWithParams(pathInstallation, area, configYml , configYmlPath, false, java21pth);
	//		worker.execute(); 
			//textArea.setText("Processo completato!");
			return;
			
		}
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	

}
