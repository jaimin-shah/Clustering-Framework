/*Displays the main screen that appears after launching application.
 * 
 * */
package gui;

import core.MultiAlgorithmClustering;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.net.URI;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class MainDisplayPanel extends JPanel implements ActionListener, ItemListener {
	
	private static final long serialVersionUID = 2443223770957129907L;
	
	//this frame
	JFrame fr;

	//other user defined classes
	//alternate app operation interface
	AlternateAppMode alternateMode;
	
	//Panels
	//component holding panel null layout
	JPanel componentsPane = new JPanel(new GridLayout(9, 9,10,10));
	
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
		chkCobweb = new JCheckBox("Cobweb");
		chkDbscan = new JCheckBox("DBSCAN");
		chkKmeans = new JCheckBox("KMeans");
		chkHierarchy = new JCheckBox("Hierarchial");
		chkFarthestFirst = new JCheckBox("Farthest First");
		chkEm = new JCheckBox("EM");
		
                //kmeans
                k_iter=new JTextField("500",7);
                k_seed=new JTextField("10",7);
                k_no_clus=new JTextField("2",7);
                
                //DBS
                d_eplison=new JTextField("0.9",7);
                d_minpoint=new JTextField("6",7);
                
                //cobweb
                c_acutiy=new JTextField("1.0",7);
                c_cutoff=new JTextField("0.0028209479177387815",7);
                c_seed=new JTextField("42",7);
                
                //em
                e_minstdev=new JTextField("1.0E-6",7);  
                e_maxiter=new JTextField("100",7);  
                e_no_clus=new JTextField("-1",7);  
                e_seed=new JTextField("100",7);  
                
                //farthest
                f_no_clus=new JTextField("2",7);  
                f_seed=new JTextField("1",7);
                
                //hierarchy
                h_no_clus=new JTextField("2",7);
                
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
		movetonextrow(8);
                
		//algo check boxes
		//chkCobWeb.setLocation(52, 87);
		componentsPane.add(chkCobweb);
                componentsPane.add(new JLabel("ACUTIY"));
                componentsPane.add(c_acutiy);
                componentsPane.add(new JLabel("CUTOFF"));
                componentsPane.add(c_cutoff);
                componentsPane.add(new JLabel("SEED"));
                componentsPane.add(c_seed);
                movetonextrow(2);
		
		//chkDbscan.setLocation(52, 147);
		componentsPane.add(chkDbscan);
                componentsPane.add(new JLabel("EPLISON"));
                componentsPane.add(d_eplison);
                componentsPane.add(new JLabel("MIN_POINTS"));
                componentsPane.add(d_minpoint);
                movetonextrow(4);
                
		//chkKmeans.setLocation(221, 87);
		componentsPane.add(chkKmeans);
		componentsPane.add(new JLabel("MAX IERATIONS"));
                componentsPane.add(k_iter);
                componentsPane.add(new JLabel("NO OF CLUSTERS"));
                componentsPane.add(k_no_clus);
                componentsPane.add(new JLabel("SEED"));
                componentsPane.add(k_seed);
                movetonextrow(2);
                
		//chkXmeans.setLocation(221, 147);
		componentsPane.add(chkHierarchy);
		componentsPane.add(new JLabel("NO OF CLUSTERS"));
                componentsPane.add(h_no_clus);
                movetonextrow(6);
                
		//chkFarthestFirst.setLocation(382, 87);
		componentsPane.add(chkFarthestFirst);
		componentsPane.add(new JLabel("SEED"));
                componentsPane.add(f_seed);
                componentsPane.add(new JLabel("NO OF CLUSTERS"));
                componentsPane.add(f_no_clus);
                movetonextrow(4);
                
		//chkEm.setLocation(382, 147);
		componentsPane.add(chkEm);
                componentsPane.add(new JLabel("MAX IERATIONS"));
		componentsPane.add(e_maxiter);
                componentsPane.add(new JLabel("MIN STD. DEVIATION"));
                componentsPane.add(e_minstdev);
                componentsPane.add(new JLabel("NO OF CLUSTERS"));
                componentsPane.add(e_no_clus);
                componentsPane.add(new JLabel("SEED"));
                componentsPane.add(e_seed);
                
		//button and label
		//lblFilePath.setLocation(161, 229);
		componentsPane.add(new JLabel("File:- "));
		componentsPane.add(lblFilePath);
		movetonextrow(7);
                
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
	}

	
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		JCheckBox cb = (JCheckBox) e.getItem();
		
		//create instance of dispatcher, occurs only first time a selection is made
		if(dispatchAlgorithms == null) {
			dispatchAlgorithms = new MultiAlgorithmClustering();
			dispatchAlgorithms.addAlgorithms(cb.getText());
			
			//request parameters
			requestParameters(cb.getText());
		}
		else {
			if(cb.isSelected()) {
				dispatchAlgorithms.addAlgorithms(cb.getText());
			}
			else {
				dispatchAlgorithms.removeAlgorithms(cb.getText());
			}
			
		}		
	}
        
	private void requestParameters(String algo) {
		// TODO Auto-generated method stub
        	
        	
		
	}

		void movetonextrow(int n)
        {
            for(int i=0;i<n;i++)
            {
                componentsPane.add(new JLabel(""));
            }
        }

}
