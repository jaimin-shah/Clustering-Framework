package core;

import java.io.File;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import clusterers.*;

public class MultiDataClustering {
	
	String selectedAlgorithm = null;
	File[] selectedFiles = null;
	String currentFile;
	
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
		/*for(int i = 0; i < selectedFiles.length; i++) {
			System.out.println("File - " + selectedFiles[i].getName().toString());
		}*/
		HashSet<Runnable> hs = new HashSet<Runnable>();
		
		ExecutorService executor = Executors.newFixedThreadPool(5);
		CountDownLatch pendingFiles = new CountDownLatch(selectedFiles.length);
		
		//run selected algorithm on all files selected
		switch(selectedAlgorithm) {
			case "KMeans":
				
				/*problem every thread takes the first file name always 
				 * event when done as t = new thread(new Runnable....)
				 * t.start()
				 * for(int i = 0; i < selectedFiles.length; i++) {
					currentFile = selectedFiles[i].getPath();
					
					executor.execute(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							//synchronized(currentFile) {
								System.out.println(currentFile);
								new kmeans(currentFile);
							//}
							pendingFiles.countDown();
						}
						
					});
				}*/
				
				/*for(int i = 0; i < selectedFiles.length; i++) {
					final String data = selectedFiles[i].getPath();
					hs.add(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							new kmeans(data);
						}
						
					});
				}*/
				
				break;
			case "Hierarchial" :
				for(int i = 0; i < selectedFiles.length; i++) {
					
				}
				
				break;
			case "Em":
				for(int i = 0; i < selectedFiles.length; i++) {
					
				}
				
				break;
			case "CobWeb":
				for(int i = 0; i < selectedFiles.length; i++) {
					
				}
				
				break;
			case "DBSCAN":
				for(int i = 0; i < selectedFiles.length; i++) {
					
				}
				
				break;
			case "Farthest First":
				for(int i = 0; i < selectedFiles.length; i++) {
					
				}
				
				break;
		}
		
		try {
			pendingFiles.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		executor.shutdown();
	}
}
