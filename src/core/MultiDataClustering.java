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
}
