package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import core.MultiDataClustering;

public class AlternateAppMode extends JPanel implements ActionListener {			

	private static final long serialVersionUID = 6763236354614599803L;
	
	//this frame
	JFrame fr;
	
	//file chooser
	JFileChooser selectFiles = new JFileChooser("E:\\Program Files\\Weka-3-6\\data\\");
	
	//panels
	//components holding panels
	JPanel componentsPane = new JPanel(new FlowLayout());
	
	//buttons
	//button switch to one data many algorithm analysis mode
	JButton btnswitchMode = new JButton("Switch to Many data one Algorithm");
	
	//Run Clustering
	JButton btnRun = new JButton("Run Clustering");
	JButton btnChooseFiles = new JButton("Choose Files");
	
	//labels
	//display mode of operation
	JLabel lblguiType = new JLabel("Many Data one algorithm Mode", JLabel.CENTER);
	
	//radio buttons
	//select an algorithm
	JRadioButton radioKmeans, radioHierarchial, radioDbscan, radioFarthestFirst;
	JRadioButton radioEm, radioCobweb;
	ButtonGroup groupAlgorithms = new ButtonGroup();
	
	String selectedAlgorithm = null;
	
	int noOfFilesRequest = 1;
	
	
	//file chooser returns a no. of files
	File[] f;
	
	/*controller on which algorithm will be dispatched
	 * will start 5 worker threads shut them when gui mode changed
	 */	
	MultiDataClustering dispatchAlgorithms = new MultiDataClustering();
	
	
	public AlternateAppMode(JFrame fr) {
		this.fr = fr;
		
		//shut the workers if window is closed
		this.fr.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent w) {
				dispatchAlgorithms.stopWorkerThreads();
			}
		});
		
		//set layout of this panel
		setLayout(new BorderLayout());
		
		//radio buttons
		//initialize components
		radioKmeans = new JRadioButton("KMeans");
		radioHierarchial = new JRadioButton("Hierarchial");
		radioEm = new JRadioButton("Em");
		radioCobweb = new JRadioButton("CobWeb");
		radioFarthestFirst = new JRadioButton("Farthest First");
		radioDbscan = new JRadioButton("DBSCAN");
		
		selectFiles.setMultiSelectionEnabled(true);
		
		//customize file filter for specific files
		selectFiles.setAcceptAllFileFilterUsed(false);
		selectFiles.addChoosableFileFilter(new ClusteringFileSelectionFilter());
		
		//disable run button
		btnRun.setEnabled(false);
		
		//add component listeners
		addRespectiveListeners();
		
		//add components to panel
		addComponents();
	}

	//add component's listeners
	private void addRespectiveListeners() {
		// TODO Auto-generated method stub
		btnChooseFiles.addActionListener(this);
		btnRun.addActionListener(this);
		btnswitchMode.addActionListener(this);
		
		radioKmeans.addActionListener(this);
		radioCobweb.addActionListener(this);
		radioDbscan.addActionListener(this);
		radioFarthestFirst.addActionListener(this);
		radioEm.addActionListener(this);
		radioHierarchial.addActionListener(this);
		
	}

	private void addComponents() {
		// TODO Auto-generated method stub
		
		//add to this gui-model panel
		add(lblguiType, BorderLayout.NORTH);
		add(btnswitchMode, BorderLayout.SOUTH);
		
		//add components to components panel
		componentsPane.add(new JLabel("Select an algorithm :- "));
		
		//create group of radio buttons
		groupAlgorithms.add(radioCobweb);
		groupAlgorithms.add(radioDbscan);
		groupAlgorithms.add(radioEm);
		groupAlgorithms.add(radioFarthestFirst);
		groupAlgorithms.add(radioKmeans);
		groupAlgorithms.add(radioHierarchial);
		
		componentsPane.add(radioCobweb);
		componentsPane.add(radioDbscan);
		componentsPane.add(radioEm);
		componentsPane.add(radioFarthestFirst);
		componentsPane.add(radioKmeans);
		componentsPane.add(radioHierarchial);
		
		componentsPane.add(new JLabel("MAX 5 Files at a time"));
		
		componentsPane.add(btnChooseFiles);
		
		//button to run clustering algorithm
		componentsPane.add(btnRun);
		
		//add component's panel to this gui-panel
		add(componentsPane, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnswitchMode) {
			//stop the worker threads that were started
			dispatchAlgorithms.stopWorkerThreads();
			
			fr.remove(this);
			fr.add(new MainDisplayPanel(fr));
			fr.revalidate();
		}
		else if(e.getSource() == radioKmeans || e.getSource() == radioDbscan
				|| e.getSource() == radioHierarchial || e.getSource() == radioEm
				|| e.getSource() == radioFarthestFirst
				|| e.getSource() == radioCobweb) {
			
			JRadioButton evntSrc = (JRadioButton)e.getSource();
			dispatchAlgorithms.setAlgorithm(evntSrc.getText());
			
			//check if files was choosen? if yes enable run button
			if(dispatchAlgorithms.getFiles() != null && dispatchAlgorithms.getFiles().length != 0) {
				btnRun.setEnabled(true);
			}
		}
		else if(e.getSource() == btnChooseFiles) {
			/*
			 * file chooser will return File[], pass to dispachAlgorithms object
			 * setter as soon as approved.
			 */
			int opt = selectFiles.showDialog(fr, "Done");
			if(opt == JFileChooser.APPROVE_OPTION) {
				f = selectFiles.getSelectedFiles();
				if(f.length > 5) {
					JOptionPane.showMessageDialog(fr, "Max. 5 files at a time!!", 
							"ERROR", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					//check if algo was selected? if yes enable button
					if(dispatchAlgorithms.getAlgorithm() != null) {
						btnRun.setEnabled(true);
					}
					dispatchAlgorithms.setSelectedFiles(f);
				}
			}
		}
		else if(e.getSource() == btnRun) {
			/*
			 * selected algorithm and files are attributes of dispatch algorithms
			 */
			if(dispatchAlgorithms == null || dispatchAlgorithms.getAlgorithm() == null) {
				JOptionPane.showMessageDialog(fr, "No algorithm Selected!!",
						"FATAL ERROR", JOptionPane.ERROR_MESSAGE);
			}
			else if(dispatchAlgorithms == null || dispatchAlgorithms.getFiles().length == 0) {
				JOptionPane.showMessageDialog(fr, "No files Selected!!",
						"FATAL ERROR", JOptionPane.ERROR_MESSAGE);
			}
			else {
				//fire algorithms
				dispatchAlgorithms.runAlgorithm();
			}
		}
	}

}
