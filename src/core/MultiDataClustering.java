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
	
	ExecutorService executor;
	
	public MultiDataClustering() {
		executor = Executors.newFixedThreadPool(5);
		System.out.println("5 worker threads started..");
	}
	
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
		CountDownLatch pendingFiles = new CountDownLatch(selectedFiles.length);
		
		//run selected algorithm on all files selected
		switch(selectedAlgorithm) {
			case "KMeans":
				
				for(int i = 0; i < selectedFiles.length; i++) {
					final String s = selectedFiles[i].getPath();
					executor.execute(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							new kmeans(s);
							pendingFiles.countDown();
						}
					});
				}
				
				
				break;
			case "Hierarchial" :
				
				for(int i = 0; i < selectedFiles.length; i++) {
					final String s = selectedFiles[i].getPath();
					executor.execute(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							new hierarchy(s);
							pendingFiles.countDown();
						}
					});
				}
				
				break;
			case "Em":
				for(int i = 0; i < selectedFiles.length; i++) {
					final String s = selectedFiles[i].getPath();
					executor.execute(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							new em(s);
							pendingFiles.countDown();
						}
					});
				}
				
				break;
			case "CobWeb":
				for(int i = 0; i < selectedFiles.length; i++) {
					final String s = selectedFiles[i].getPath();
					executor.execute(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							new cobweb(s);
							pendingFiles.countDown();
						}
					});
				}
				
				break;
			case "DBSCAN":
				for(int i = 0; i < selectedFiles.length; i++) {
					final String s = selectedFiles[i].getPath();
					executor.execute(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							new DBS(s);
							pendingFiles.countDown();
						}
					});
				}
				
				break;
			case "Farthest First":
				for(int i = 0; i < selectedFiles.length; i++) {
					final String s = selectedFiles[i].getPath();
					executor.execute(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							new farthest(s);
							pendingFiles.countDown();
						}
					});
				}
				
				break;
		}
		
		try {
			pendingFiles.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stopWorkerThreads() {
		System.out.println("shutting 5 workers");
		executor.shutdown();
	}
}
