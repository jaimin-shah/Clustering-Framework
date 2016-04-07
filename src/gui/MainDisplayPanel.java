/*Displays the main screen that appears after launching application.
 * 
 * */
package gui;

import core.MultiAlgorithmClustering;
import clusterers.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainDisplayPanel extends JPanel implements ActionListener, ItemListener {
	
	private static final long serialVersionUID = 2443223770957129907L;
	
	//this frame
	JFrame fr;

	//other user defined classes
	//alternate app operation interface
	AlternateAppMode alternateMode;
	
	//Panels
	//component holding panel null layout
	JPanel componentsPane = new JPanel(new FlowLayout());
	
        //map
        HashMap rm,default_para;
	//buttons
	//button switch to one data many algorithm analysis mode
	JButton btnswitchMode = new JButton("Switch to Many data one Algorithm");
	JButton btnChooseFile = new JButton("Choose File");
	JButton btnRun = new JButton("Run Clustering");
	//clear selection of file
	JButton btnClearSelection = new JButton("Clear File selection");
	
	//Labels
	//display mode of operation
	JLabel lblguiType = new JLabel("One Data many algorithms Mode", JLabel.CENTER);
	
	//File path selection
	JLabel lblFilePath = new JLabel("No File Selected  ");
	
	//Check boxes
	//check boxes of algorithms to be selected
	JCheckBox chkCobweb, chkDbscan, chkKmeans, chkHierarchy, chkFarthestFirst, chkEm;
	
	//dialog
	JPanel pnlCobweb, pnlDbs, pnlKmeans, pnlHierarchy, pnlFarthestFirst, pnlEm;
	
	//file chooser
	JFileChooser selectFiles = new JFileChooser("E:\\Program Files\\Weka-3-6\\data\\");
	
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
    
	//selected file-path on which clustering needs to be done
	String selectedFilePath = null;
	
	//controller that dispatches algorithms on data set
	MultiAlgorithmClustering dispatchAlgorithms = null;
	
	//class constructor initialization and building of frame
	public MainDisplayPanel(JFrame fr) {
		
		//Frame tasks
		this.fr = fr;

		//close worker threads if close button on frame is pressed
		this.fr.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent w) {
				if(dispatchAlgorithms != null) {
					dispatchAlgorithms.stopWorkerThreads();
				}
			}
		});
				
		//this panel tasks
		setLayout(new BorderLayout());
		
		//initialize components
		chkCobweb = new JCheckBox("Cobweb");
		chkDbscan = new JCheckBox("DBSCAN");
		chkKmeans = new JCheckBox("KMeans");
		chkHierarchy = new JCheckBox("Hierarchial");
		chkFarthestFirst = new JCheckBox("Farthest First");
		chkEm = new JCheckBox("EM");
		
        
                
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
        
		//customize file filter for specific files
		selectFiles.setAcceptAllFileFilterUsed(false);
		selectFiles.addChoosableFileFilter(new ClusteringFileSelectionFilter());
		
		//add all component's respective listeners	
		addRespectiveListeners();
		
		//add this panel's components		
		addComponents();
		
		//add this panel to gui frame
		fr.add(this, BorderLayout.CENTER);
		
		//generate dialog components
		instantiateDialog();
	}

	private void instantiateDialog() {
		// TODO Auto-generated method stub
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
                rm=new HashMap();
                rm.put("DBSCAN",pnlDbs );
                rm.put("Cobweb", pnlCobweb);
                rm.put("KMeans",pnlKmeans );
                rm.put("Hierarchial",pnlHierarchy );
                rm.put("Farthest First", pnlFarthestFirst);
                rm.put("EM", pnlEm);
                
                default_para=new HashMap();
                default_para.put("DBSCAN",DBS.getDefaults() );
                default_para.put("Cobweb", cobweb.getDefaults());
                default_para.put("KMeans",kmeans.getDefaults());
                default_para.put("Hierarchial",hierarchy.getDefaults());
                default_para.put("Farthest First", farthest.getDefaults());
                default_para.put("EM", em.getDefaults());
	}

	//assign component's listeners
	private void addRespectiveListeners() {
		// TODO Auto-generated method stub
		btnRun.addActionListener(this);
		btnChooseFile.addActionListener(this);
		btnswitchMode.addActionListener(this);
		btnClearSelection.addActionListener(this);
		
		btnCob.addActionListener(this);
		btnDbs.addActionListener(this);
		btnEm.addActionListener(this);
		btnFarthest.addActionListener(this);
		btnHie.addActionListener(this);
		btnKms.addActionListener(this);
		
		chkCobweb.addItemListener(this);
		chkDbscan.addItemListener(this);
		chkEm.addItemListener(this);
		chkFarthestFirst.addItemListener(this);
		chkKmeans.addItemListener(this);
		chkHierarchy.addItemListener(this);
	}

	private void addComponents() {
		//add to current gui mode panel 
		add(lblguiType, BorderLayout.NORTH);
		add(btnswitchMode, BorderLayout.SOUTH);
		
		//add to components panel
		
		componentsPane.add(new JLabel("Select algorithm/s :- "));
		
		//algo check boxes
		//chkCobWeb.setLocation(52, 87);
		componentsPane.add(chkCobweb);
		
		//chkDbscan.setLocation(52, 147);
		componentsPane.add(chkDbscan);
		
		//chkKmeans.setLocation(221, 87);
		componentsPane.add(chkKmeans);
		
		//chkXmeans.setLocation(221, 147);
		componentsPane.add(chkHierarchy);
		
		//chkFarthestFirst.setLocation(382, 87);
		componentsPane.add(chkFarthestFirst);
		
		//chkEm.setLocation(382, 147);
		componentsPane.add(chkEm);
		
		//button and label
		//lblFilePath.setLocation(161, 229);
		componentsPane.add(new JLabel("File:- "));
		componentsPane.add(lblFilePath);
		
		//btnChooseFile.setLocation(52, 225);
		componentsPane.add(btnChooseFile);
		componentsPane.add(btnClearSelection);
		componentsPane.add(btnRun);
		
		//add components panel to this panel's center
		add(componentsPane, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
				
		if(e.getSource() == btnChooseFile) {
			//show file explorer to select file
			int opt = selectFiles.showDialog(fr, "Select");
			if(opt == JFileChooser.APPROVE_OPTION) {
				File file = selectFiles.getSelectedFile();
				lblFilePath.setText(file.getName());
				selectedFilePath = file.getPath();
				componentsPane.revalidate();
			}
		}
		else if(e.getSource() == btnRun) {
			//run selected algorithms
			if(selectedFilePath == null) {
				//pressed run without selecting a file
				JOptionPane.showMessageDialog(fr, "No File Selected!!", 
						"FATAL ERROR", JOptionPane.ERROR_MESSAGE);
			}
			else {
				//run the selected algorithms
				
				if(dispatchAlgorithms == null) {
					//no algorithm was selected
					JOptionPane.showMessageDialog(fr, "Select atleast one algorithm!!",
							"FATAL ERROR", JOptionPane.ERROR_MESSAGE);
				}
				else {
					if(dispatchAlgorithms.isAlgorithmsListEmpty()) {
						//still no algorithms selected
						JOptionPane.showMessageDialog(fr, "Select atleast one algorithm!!",
								"FATAL ERROR", JOptionPane.ERROR_MESSAGE);
					}
					else {
						//dispatch algorithms with selected file
						dispatchAlgorithms.setFilePath(selectedFilePath);
						dispatchAlgorithms.runAlgorithms();
					}
				}
			}
		}
		else if(e.getSource() == btnswitchMode) {
			//switch gui
			if(dispatchAlgorithms != null) {
				dispatchAlgorithms.stopWorkerThreads();
			}
			fr.remove(this);
			fr.add(new AlternateAppMode(fr));
			fr.revalidate();
			
		}
		else if(e.getSource() == btnClearSelection) {
			//clear selection of file
			selectedFilePath = null;
			lblFilePath.setText("No File Selected  ");
		}
		else {
			//event of a button for parameter setting
			System.out.println(((JButton)e.getSource()).getText());
			handleParameterSetEvent(((JButton)e.getSource()).getText());
		}
	}


	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		JCheckBox cb = (JCheckBox) e.getItem();
		
		//create instance of dispatcher, occurs only first time a selection is made
		if(dispatchAlgorithms == null) {
			dispatchAlgorithms = new MultiAlgorithmClustering();
						
			addParametersPane(cb.getText());
			componentsPane.revalidate();
		}
		else {
			if(cb.isSelected()) {
								
				addParametersPane(cb.getText());
				componentsPane.repaint();
				componentsPane.revalidate();
			}
			else {
				dispatchAlgorithms.removeAlgorithms(cb.getText());
				removeParametersPane(cb.getText());
				componentsPane.revalidate();
				componentsPane.repaint();
			}
			
		}		
	}

	private void removeParametersPane(String algo) {
		// TODO Auto-generated method stub
		componentsPane.remove((Component) rm.get(algo));
	}

	private void addParametersPane(String algo) {
		// TODO Auto-generated method stub
		
		componentsPane.add((Component) rm.get(algo));
	    	dispatchAlgorithms.addAlgorithms(algo, (HashMap<String, Double>) default_para.get(algo));
	    		
		
	}
	
	private void handleParameterSetEvent(String button) {
		// TODO Auto-generated method stub
		switch(button) {
			case "Set Kmeans":
				String iter = k_iter.getText(), clust = k_no_clus.getText(), seed = k_seed.getText();
				
				if(iter.length() != 0 && clust.length() != 0 && seed.length() != 0) {
					dispatchAlgorithms.addAlgorithms("KMeans", kmeans.setParameters(Double.parseDouble(clust), Double.parseDouble(iter), Double.parseDouble(seed)));
				}
				
				break;
			case "Set DBSCAN":
				String epsilon = d_eplison.getText(), minpoint = d_minpoint.getText();
				if(epsilon.length() != 0 && minpoint.length() != 0) {
					dispatchAlgorithms.addAlgorithms("DBSCAN", DBS.setParameters(Double.parseDouble(epsilon), Double.parseDouble(minpoint)));
				}
				
				break;
			case "Set COBWEB":
				String acuity = c_acutiy.getText(), cutoffVal = c_cutoff.getText(),
				seedIng = c_seed.getText();
				if(acuity.length() != 0 && cutoffVal.length() != 0) {
					dispatchAlgorithms.addAlgorithms("Cobweb", cobweb.setParameters(Double.parseDouble(seedIng), Double.parseDouble(acuity), Double.parseDouble(cutoffVal)));
				}
				
				break;
				
			case "Set EM":
				String seed1 = e_seed.getText(), clusts = e_no_clus.getText(), maxiter = e_maxiter.getText(), minstdDev = e_minstdev.getText(); 
				
				if(seed1.length() != 0 && clusts.length() != 0 && maxiter.length() != 0 && minstdDev.length() != 0) {
					dispatchAlgorithms.addAlgorithms("EM", em.setParameters(Double.parseDouble(maxiter), Double.parseDouble(seed1), Double.parseDouble(clusts), Double.parseDouble(minstdDev)));
				}
				break;
				
			case "Set Farthest":
				String clusters1 = f_no_clus.getText(), seed2 = f_seed.getText();
				
				if(clusters1.length() != 0 && seed2.length() != 0) {
					dispatchAlgorithms.addAlgorithms("Farthest First", farthest.setParameters(Double.parseDouble(seed2), Double.parseDouble(clusters1)));
				}
				break;
				
			case "Set HIERARCHY":
				String clusters = h_no_clus.getText();
				System.out.println("VIOLA");
				if(clusters.length() != 0) {
					dispatchAlgorithms.addAlgorithms("Hierarchial", hierarchy.setParameters(Double.parseDouble(clusters)));
				}
				
				break;
		}
	}

}
