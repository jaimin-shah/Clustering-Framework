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
                Thread t=null;
		while(it.hasNext()) {
			
			//delegate the algorithms
			switch(it.next()) {
				case "DBSCAN":
                                    t=new Thread(new Runnable() {

                                        @Override
                                        public void run() {
                                           new DBS(filePath);
                                        }
                                    });
					break;
				case "Cobweb":
                                     t=new Thread(new Runnable() {

                                        @Override
                                        public void run() {
                                          new cobweb(filePath); 
                                        }
                                    });
					break;
				case "KMeans":	
                                     t=new Thread(new Runnable() {

                                        @Override
                                        public void run() {
                                           new kmeans(filePath);
                                        }
                                    });
					break;
				case "Hierarchial":
                                     t=new Thread(new Runnable() {

                                        @Override
                                        public void run() {
                                          new hierarchy(filePath); 
                                        }
                                    });
					break;
				case "Farthest First":
                                     t=new Thread(new Runnable() {

                                        @Override
                                        public void run() {
                                           new farthest(filePath);
                                        }
                                    });
					break;
				case "EM":	
                                     t=new Thread(new Runnable() {

                                        @Override
                                        public void run() {
                                           new em(filePath);
                                        }
                                    });
					break;
                           
			}
                     t.start();
		}
	}
}
