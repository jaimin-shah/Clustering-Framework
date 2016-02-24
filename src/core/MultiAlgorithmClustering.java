package core;

import java.net.URI;
import clusterers.*;
import java.util.HashSet;

import javax.swing.JCheckBox;
import javax.swing.text.html.HTMLDocument.Iterator;

public class MultiAlgorithmClustering {
	
	//location of file
	String filePath;
	
	//algorithms to be run on data
	HashSet<String> selectedAlgorithms;
	
	public MultiAlgorithmClustering() {
		selectedAlgorithms = new HashSet<String>();
	}
	
	//add algos to list
	public void addAlgorithms(String s) {
		selectedAlgorithms.add(s);
	}
	
	//remove algos from list
	public void removeAlgorithms(String s) {
		selectedAlgorithms.remove(s);
	}
	
	//is list of algorithms empty?
	public boolean isAlgorithmsListEmpty() {
		return selectedAlgorithms.isEmpty();
	}
	
	//file-path getter
	public String getFilePath() {
		return filePath;
	}
	
	//fil path setter
	public void setFilePath(String f) {
		filePath = f;
	}
	
	//run algorithms
	public void runAlgorithms() {
		
		java.util.Iterator<String> it = selectedAlgorithms.iterator();
		while(it.hasNext()) {
			
			//delegate the algorithms
			switch(it.next()) {
				case "DBSCAN":
					new DBS(filePath);
					break;
				case "Cobweb":
					new cobweb(filePath);
					break;
				case "KMeans":					
					new kmeans(filePath);
					break;
				case "Hierarchial":	
					new hierarchy(filePath);
					break;
				case "Farthest First":
					new farthest(filePath);
					break;
				case "EM":	
					new em(filePath);
					break;
			}
		}
	}
}
