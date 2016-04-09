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
import java.util.HashMap;

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
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import clusterers.DBS;
import clusterers.cobweb;
import clusterers.em;
import clusterers.farthest;
import clusterers.hierarchy;
import clusterers.kmeans;
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
	JPanel strayPanel = new JPanel(new FlowLayout());
	
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
		radioKmeans = new JRadioButton("KMEANS");
		radioHierarchial = new JRadioButton("HIERARCHICAL");
		radioEm = new JRadioButton("EM");
		radioCobweb = new JRadioButton("COBWEB");
		radioFarthestFirst = new JRadioButton("FARTHEST FIRST");
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
		
		//parameter setting buttons
		ClustererParameterPanelFactory.btnCob.addActionListener(this);
		ClustererParameterPanelFactory.btnDbs.addActionListener(this);
		ClustererParameterPanelFactory.btnEm.addActionListener(this);
		ClustererParameterPanelFactory.btnFarthest.addActionListener(this);
		ClustererParameterPanelFactory.btnHie.addActionListener(this);
		ClustererParameterPanelFactory.btnKms.addActionListener(this);
		
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
		
		componentsPane.add(strayPanel);
		
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
			String algo = evntSrc.getText();
			
			addParametersPane(algo);
			componentsPane.revalidate();
			
			dispatchAlgorithms.setAlgorithm(algo, ClustererParameterPanelFactory.getDefaultParameters(algo));
			
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
		else {
			//event of a button for parameter setting
			System.out.println(((JButton)e.getSource()).getText());
			try {
			handleParameterSetEvent(((JButton)e.getSource()).getText());
			}
			catch(NumberFormatException exxc) {
				JOptionPane.showMessageDialog(fr, "Invalid Parameter. Expected number", "FATAL ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void addParametersPane(String algo) {
		// TODO Auto-generated method stub
		componentsPane.remove(strayPanel);
		strayPanel.removeAll();
		strayPanel.add(ClustererParameterPanelFactory.getParameterPanel(algo));
			    
		componentsPane.add(strayPanel);
	}
	
	private void handleParameterSetEvent(String button) throws NumberFormatException {
		// TODO Auto-generated method stub
		switch(button) {
			case "Set Kmeans":
				String iter = ClustererParameterPanelFactory.k_iter.getText(), 
						clust = ClustererParameterPanelFactory.k_no_clus.getText(), 
						seed = ClustererParameterPanelFactory.k_seed.getText();
				
				if(iter.length() != 0 && clust.length() != 0 && seed.length() != 0) {
					dispatchAlgorithms.setAlgorithm("KMEANS", kmeans.setParameters(Double.parseDouble(clust), Double.parseDouble(iter), Double.parseDouble(seed)));
				}
				
				break;
			case "Set DBSCAN":
				String epsilon = ClustererParameterPanelFactory.d_eplison.getText(), 
						minpoint = ClustererParameterPanelFactory.d_minpoint.getText();
				if(epsilon.length() != 0 && minpoint.length() != 0) {
					dispatchAlgorithms.setAlgorithm("DBSCAN", DBS.setParameters(Double.parseDouble(epsilon), Double.parseDouble(minpoint)));
				}
				
				break;
			case "Set COBWEB":
				String acuity = ClustererParameterPanelFactory.c_acutiy.getText(), 
						cutoffVal = ClustererParameterPanelFactory.c_cutoff.getText(),
						seedIng = ClustererParameterPanelFactory.c_seed.getText();
				if(acuity.length() != 0 && cutoffVal.length() != 0) {
					dispatchAlgorithms.setAlgorithm("COBWEB", cobweb.setParameters(Double.parseDouble(seedIng), Double.parseDouble(acuity), Double.parseDouble(cutoffVal)));
				}
				
				break;
				
			case "Set EM":
				String seed1 = ClustererParameterPanelFactory.e_seed.getText(), 
						clusts = ClustererParameterPanelFactory.e_no_clus.getText(), 
						maxiter = ClustererParameterPanelFactory.e_maxiter.getText(), 
						minstdDev = ClustererParameterPanelFactory.e_minstdev.getText(); 
				
				if(seed1.length() != 0 && clusts.length() != 0 && maxiter.length() != 0 && minstdDev.length() != 0) {
					dispatchAlgorithms.setAlgorithm("EM", em.setParameters(Double.parseDouble(maxiter), Double.parseDouble(seed1), Double.parseDouble(clusts), Double.parseDouble(minstdDev)));
				}
				break;
				
			case "Set Farthest":
				String clusters1 = ClustererParameterPanelFactory.f_no_clus.getText(), 
						seed2 = ClustererParameterPanelFactory.f_seed.getText();
				
				if(clusters1.length() != 0 && seed2.length() != 0) {
					dispatchAlgorithms.setAlgorithm("FARTHEST FIRST", farthest.setParameters(Double.parseDouble(seed2), Double.parseDouble(clusters1)));
				}
				break;
				
			case "Set HIERARCHY":
				String clusters = ClustererParameterPanelFactory.h_no_clus.getText();
				System.out.println("VIOLA");
				if(clusters.length() != 0) {
					dispatchAlgorithms.setAlgorithm("HIERARCHICAL", hierarchy.setParameters(Double.parseDouble(clusters)));
				}
				
				break;
		}
	}

}
