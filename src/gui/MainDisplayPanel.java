/*Displays the main screen that appears after launching application.
 * 
 * */
package gui;

import core.MultiAlgorithmClustering;
import clusterers.*;

import java.awt.AWTEvent;
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
	
	
	//file chooser
	JFileChooser selectFiles = new JFileChooser("E:\\Program Files\\Weka-3-6\\data\\");
    
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
		chkCobweb = new JCheckBox("COBWEB");
		chkDbscan = new JCheckBox("DBSCAN");
		chkKmeans = new JCheckBox("KMEANS");
		chkHierarchy = new JCheckBox("HIERARCHICAL");
		chkFarthestFirst = new JCheckBox("FARTHEST FIRST");
		chkEm = new JCheckBox("EM");        
             
        
		//customize file filter for specific files
		selectFiles.setAcceptAllFileFilterUsed(false);
		selectFiles.addChoosableFileFilter(new ClusteringFileSelectionFilter());
		
		//add all component's respective listeners	
		addRespectiveListeners();
		
		//add this panel's components		
		addComponents();
		
		//add this panel to gui frame
		fr.add(this, BorderLayout.CENTER);

	}

	//assign component's listeners
	private void addRespectiveListeners() {
		// TODO Auto-generated method stub
		btnRun.addActionListener(this);
		btnChooseFile.addActionListener(this);
		btnswitchMode.addActionListener(this);
		btnClearSelection.addActionListener(this);
		
		ClustererParameterPanelFactory.btnCob.addActionListener(this);
		ClustererParameterPanelFactory.btnDbs.addActionListener(this);
		ClustererParameterPanelFactory.btnEm.addActionListener(this);
		ClustererParameterPanelFactory.btnFarthest.addActionListener(this);
		ClustererParameterPanelFactory.btnHie.addActionListener(this);
		ClustererParameterPanelFactory.btnKms.addActionListener(this);
		
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
			
			ClustererParameterPanelFactory.btnCob.removeActionListener(this);
			ClustererParameterPanelFactory.btnDbs.removeActionListener(this);
			ClustererParameterPanelFactory.btnEm.removeActionListener(this);
			ClustererParameterPanelFactory.btnFarthest.removeActionListener(this);
			ClustererParameterPanelFactory.btnHie.removeActionListener(this);
			ClustererParameterPanelFactory.btnKms.removeActionListener(this);
			
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
			try {
				handleParameterSetEvent(((JButton)e.getSource()).getText());
			}
			catch(NumberFormatException exxc) {
				JOptionPane.showMessageDialog(fr, "Invalid Parameter. Expected number", "FATAL ERROR", JOptionPane.ERROR_MESSAGE);
			}
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
		componentsPane.remove(ClustererParameterPanelFactory.getParameterPanel(algo));
	}

	private void addParametersPane(String algo) {
		// TODO Auto-generated method stub		
		componentsPane.add(ClustererParameterPanelFactory.getParameterPanel(algo));
	    dispatchAlgorithms.addAlgorithms(algo, ClustererParameterPanelFactory.getDefaultParameters(algo));
		
	}
	
	private void handleParameterSetEvent(String button) throws NumberFormatException {
		// TODO Auto-generated method stub
		switch(button) {
			case "Set Kmeans":
				String iter = ClustererParameterPanelFactory.k_iter.getText(), clust = ClustererParameterPanelFactory.k_no_clus.getText(), seed = ClustererParameterPanelFactory.k_seed.getText();
				
				if(iter.length() != 0 && clust.length() != 0 && seed.length() != 0) {
					dispatchAlgorithms.addAlgorithms("KMEANS", kmeans.setParameters(Double.parseDouble(clust), Double.parseDouble(iter), Double.parseDouble(seed)));
				}
				
				break;
			case "Set DBSCAN":
				String epsilon = ClustererParameterPanelFactory.d_eplison.getText(), minpoint = ClustererParameterPanelFactory.d_minpoint.getText();
				if(epsilon.length() != 0 && minpoint.length() != 0) {
					dispatchAlgorithms.addAlgorithms("DBSCAN", DBS.setParameters(Double.parseDouble(epsilon), Double.parseDouble(minpoint)));
				}
				
				break;
			case "Set COBWEB":
				String acuity = ClustererParameterPanelFactory.c_acutiy.getText(), cutoffVal = ClustererParameterPanelFactory.c_cutoff.getText(),
				seedIng = ClustererParameterPanelFactory.c_seed.getText();
				if(acuity.length() != 0 && cutoffVal.length() != 0) {
					dispatchAlgorithms.addAlgorithms("COBWEB", cobweb.setParameters(Double.parseDouble(seedIng), Double.parseDouble(acuity), Double.parseDouble(cutoffVal)));
				}
				
				break;
				
			case "Set EM":
				String seed1 = ClustererParameterPanelFactory.e_seed.getText(), clusts = ClustererParameterPanelFactory.e_no_clus.getText(), maxiter = ClustererParameterPanelFactory.e_maxiter.getText(), minstdDev = ClustererParameterPanelFactory.e_minstdev.getText(); 
				
				if(seed1.length() != 0 && clusts.length() != 0 && maxiter.length() != 0 && minstdDev.length() != 0) {
					dispatchAlgorithms.addAlgorithms("EM", em.setParameters(Double.parseDouble(maxiter), Double.parseDouble(seed1), Double.parseDouble(clusts), Double.parseDouble(minstdDev)));
				}
				break;
				
			case "Set Farthest":
				String clusters1 = ClustererParameterPanelFactory.f_no_clus.getText(), seed2 = ClustererParameterPanelFactory.f_seed.getText();
				
				if(clusters1.length() != 0 && seed2.length() != 0) {
					dispatchAlgorithms.addAlgorithms("FARTHEST FIRST", farthest.setParameters(Double.parseDouble(seed2), Double.parseDouble(clusters1)));
				}
				break;
				
			case "Set HIERARCHY":
				String clusters = ClustererParameterPanelFactory.h_no_clus.getText();
				System.out.println("VIOLA maindisp");
				if(clusters.length() != 0) {
					dispatchAlgorithms.addAlgorithms("HIERARCHICAL", hierarchy.setParameters(Double.parseDouble(clusters)));
				}
				
				break;
		}
	}

}
