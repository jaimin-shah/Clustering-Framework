package core;

import java.io.File;

public class MultiDataClustering {
	
	String selectedAlgorithm = null;
	File[] selectedFiles = null;
	
	public void setAlgorithm(String s) {
		selectedAlgorithm = s;
	}
	
	public String getAlgorithm() {
		return selectedAlgorithm;
	}
	
	public void setSelectedFiles(File[] f) {
		selectedFiles = f;
	}
	
	public File[] getFiles() {
		return selectedFiles;
	}
	
	public void runAlgorithm() {
		System.out.println("Algo selected - " + selectedAlgorithm);
		for(int i = 0; i < selectedFiles.length; i++) {
			System.out.println("File - " + selectedFiles[i].getName().toString());
		}
	}
}
