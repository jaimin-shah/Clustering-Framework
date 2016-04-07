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
	
	/*duplicated needs to be removed later*/
	//map for panel and algorithm parameters
    HashMap<String, JPanel> rm;
    HashMap<String, HashMap<String, Double>> default_para;
	
	//panels to hold algo parameters
	JPanel pnlCobweb, pnlDbs, pnlKmeans, pnlHierarchy, pnlFarthestFirst, pnlEm;
	
	//hierarchy
    JTextField h_no_clus;
    
    //farthest
    JTextField f_seed,f_no_clus;
    
    //kmeans
    JTextField k_iter,k_seed,k_no_clus;

    //DBS
    JTextField d_eplison,d_minpoint;
    
    //em
    JTextField e_minstdev,e_seed,e_no_clus,e_maxiter;
    
    //cobweb
    JTextField c_acutiy,c_seed,c_cutoff;
	
    //and their button handlers
    JButton btnCob, btnKms, btnDbs, btnHie, btnFarthest, btnEm;
    /*remove till here*/
	
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
		
		//duplicates need to be removed later
		//respective parameters
		//kmeans
        k_iter = new JTextField("500",7);
        k_seed = new JTextField("10",7);
        k_no_clus = new JTextField("3",7);
        btnKms = new JButton("Set Kmeans");
        
        //DBS
        d_eplison = new JTextField("0.9",7);
        d_minpoint = new JTextField("6",7);
        btnDbs = new JButton("Set DBSCAN");
        
        //cobweb
        c_acutiy = new JTextField("1.0",7);
        c_cutoff = new JTextField("0.0028209479177387815",7);
        c_seed = new JTextField("42",7);
        btnCob = new JButton("Set COBWEB");
        
        //em
        e_minstdev = new JTextField("1.0E-6",7);  
        e_maxiter = new JTextField("100",7);  
        e_no_clus = new JTextField("-1",7);  
        e_seed = new JTextField("100",7);  
        btnEm = new JButton("Set EM");
        
        //farthest
        f_no_clus = new JTextField("2",7);  
        f_seed = new JTextField("1",7);
        btnFarthest = new JButton("Set Farthest");
        
        //hierarchy
        h_no_clus = new JTextField("2",7);
		btnHie = new JButton("Set HIERARCHY");
		
		selectFiles.setMultiSelectionEnabled(true);
		
		//customize file filter for specific files
		selectFiles.setAcceptAllFileFilterUsed(false);
		selectFiles.addChoosableFileFilter(new ClusteringFileSelectionFilter());
		
		//disable run button
		btnRun.setEnabled(false);
		
		//add component listeners
		addRespectiveListeners();
		
		//create panels to hold algorithm parameters
		instantiateRespectiveAlgorithms();
		
		//add components to panel
		addComponents();
	}

	private void instantiateRespectiveAlgorithms() {
		// TODO Auto-generated method stub
		//duplicate from MainDisplayPanel needs to be removed later
		pnlCobweb = new JPanel();
		pnlDbs = new JPanel();
		pnlKmeans = new JPanel();
		pnlHierarchy = new JPanel();
		pnlFarthestFirst = new JPanel();
		pnlEm = new JPanel();
		
		//cobweb
		pnlCobweb.add(new JLabel("COBWEB --- "));
		pnlCobweb.add(new JLabel("ACUTIY"));
		pnlCobweb.add(c_acutiy);
		pnlCobweb.add(new JLabel("CUTOFF"));
		pnlCobweb.add(c_cutoff);
		pnlCobweb.add(new JLabel("SEED"));
		pnlCobweb.add(c_seed);
		pnlCobweb.add(btnCob);
		
		//Dbscan
		pnlDbs.add(new JLabel("DBSCAN --- "));
		pnlDbs.add(new JLabel("EPLISON"));
		pnlDbs.add(d_eplison);
		pnlDbs.add(new JLabel("MIN_POINTS"));
		pnlDbs.add(d_minpoint);
		pnlDbs.add(btnDbs);
		
		//kmeans
		pnlKmeans.add(new JLabel("KMEANS --- "));
		pnlKmeans.add(new JLabel("MAX IERATIONS"));
		pnlKmeans.add(k_iter);
		pnlKmeans.add(new JLabel("NO OF CLUSTERS"));
		pnlKmeans.add(k_no_clus);
		pnlKmeans.add(new JLabel("SEED"));
		pnlKmeans.add(k_seed);
		pnlKmeans.add(btnKms);
		
		//hierarchy
		pnlHierarchy.add(new JLabel("HIERARCHIAL --- "));
		pnlHierarchy.add(new JLabel("NO OF CLUSTERS"));
		pnlHierarchy.add(h_no_clus);
		pnlHierarchy.add(btnHie);
		
		//farthest first
		pnlFarthestFirst.add(new JLabel("Farthest First --- "));
		pnlFarthestFirst.add(new JLabel("SEED"));
		pnlFarthestFirst.add(f_seed);
		pnlFarthestFirst.add(new JLabel("NO OF CLUSTERS"));
		pnlFarthestFirst.add(f_no_clus);
		pnlFarthestFirst.add(btnFarthest);
		
		//expectation maximization
		pnlEm.add(new JLabel("EXPECTATION MAXIMIZATION --- "));
		pnlEm.add(new JLabel("MAX IERATIONS"));
		pnlEm.add(e_maxiter);
		pnlEm.add(new JLabel("MIN STD. DEVIATION"));
		pnlEm.add(e_minstdev);
		pnlEm.add(new JLabel("NO OF CLUSTERS"));
		pnlEm.add(e_no_clus);
		pnlEm.add(new JLabel("SEED"));
		pnlEm.add(e_seed);
		pnlEm.add(btnEm);
                
        //hashmap to add/remove panel
        rm = new HashMap<String, JPanel>();
        rm.put("DBSCAN", pnlDbs );
        rm.put("CobWeb", pnlCobweb);
        rm.put("KMeans", pnlKmeans );
        rm.put("Hierarchial", pnlHierarchy );
        rm.put("Farthest First", pnlFarthestFirst);
        rm.put("Em", pnlEm);
        
        //their mapped parameters
        default_para = new HashMap<String, HashMap<String, Double>>();
        default_para.put("DBSCAN", DBS.getDefaults() );
        default_para.put("CobWeb", cobweb.getDefaults());
        default_para.put("KMeans", kmeans.getDefaults());
        default_para.put("Hierarchial", hierarchy.getDefaults());
        default_para.put("Farthest First", farthest.getDefaults());
        default_para.put("Em", em.getDefaults());
	}

	//add component's listeners
	private void addRespectiveListeners() {
		// TODO Auto-generated method stub
		btnChooseFiles.addActionListener(this);
		btnRun.addActionListener(this);
		btnswitchMode.addActionListener(this);
		
		//parameter setting buttons
		btnCob.addActionListener(this);
		btnDbs.addActionListener(this);
		btnEm.addActionListener(this);
		btnFarthest.addActionListener(this);
		btnHie.addActionListener(this);
		btnKms.addActionListener(this);
		
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
			
			dispatchAlgorithms.setAlgorithm(algo, default_para.get(algo));
			
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
		strayPanel.add(rm.get(algo));
			    
		componentsPane.add(strayPanel);
	}
	
	private void handleParameterSetEvent(String button) throws NumberFormatException {
		// TODO Auto-generated method stub
		switch(button) {
			case "Set Kmeans":
				String iter = k_iter.getText(), clust = k_no_clus.getText(), seed = k_seed.getText();
				
				if(iter.length() != 0 && clust.length() != 0 && seed.length() != 0) {
					dispatchAlgorithms.setAlgorithm("KMeans", kmeans.setParameters(Double.parseDouble(clust), Double.parseDouble(iter), Double.parseDouble(seed)));
				}
				
				break;
			case "Set DBSCAN":
				String epsilon = d_eplison.getText(), minpoint = d_minpoint.getText();
				if(epsilon.length() != 0 && minpoint.length() != 0) {
					dispatchAlgorithms.setAlgorithm("DBSCAN", DBS.setParameters(Double.parseDouble(epsilon), Double.parseDouble(minpoint)));
				}
				
				break;
			case "Set COBWEB":
				String acuity = c_acutiy.getText(), cutoffVal = c_cutoff.getText(),
				seedIng = c_seed.getText();
				if(acuity.length() != 0 && cutoffVal.length() != 0) {
					dispatchAlgorithms.setAlgorithm("CobWeb", cobweb.setParameters(Double.parseDouble(seedIng), Double.parseDouble(acuity), Double.parseDouble(cutoffVal)));
				}
				
				break;
				
			case "Set EM":
				String seed1 = e_seed.getText(), clusts = e_no_clus.getText(), maxiter = e_maxiter.getText(), minstdDev = e_minstdev.getText(); 
				
				if(seed1.length() != 0 && clusts.length() != 0 && maxiter.length() != 0 && minstdDev.length() != 0) {
					dispatchAlgorithms.setAlgorithm("Em", em.setParameters(Double.parseDouble(maxiter), Double.parseDouble(seed1), Double.parseDouble(clusts), Double.parseDouble(minstdDev)));
				}
				break;
				
			case "Set Farthest":
				String clusters1 = f_no_clus.getText(), seed2 = f_seed.getText();
				
				if(clusters1.length() != 0 && seed2.length() != 0) {
					dispatchAlgorithms.setAlgorithm("Farthest First", farthest.setParameters(Double.parseDouble(seed2), Double.parseDouble(clusters1)));
				}
				break;
				
			case "Set HIERARCHY":
				String clusters = h_no_clus.getText();
				System.out.println("VIOLA");
				if(clusters.length() != 0) {
					dispatchAlgorithms.setAlgorithm("Hierarchial", hierarchy.setParameters(Double.parseDouble(clusters)));
				}
				
				break;
		}
	}

}
