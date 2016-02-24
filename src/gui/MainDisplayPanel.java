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
import javax.swing.filechooser.FileFilter;

public class MainDisplayPanel extends JPanel implements ActionListener, ItemListener {
	
	/**
	 * 
	 */
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
	JFileChooser selectFiles = new JFileChooser("\\test_data");
	//check file extension
	Pattern pattern = Pattern.compile(".+\\.(csv|arff|xrff)");
	Matcher match;
	
	//selected file-path uri on which clustering needs to be done
	String selectedFilePath = null;
	
	//controller that dispatches algorithms on data set
	MultiAlgorithmClustering dispatchAlgorithms = null;
	
	//class constructor initialization and building of frame
	public MainDisplayPanel(JFrame fr) {
		
		//Frame tasks
		this.fr = fr;
				
		//this panel tasks
		setLayout(new BorderLayout());
		
		//initialize components
		chkCobweb = new JCheckBox("Cobweb");
		chkDbscan = new JCheckBox("DBSCAN");
		chkKmeans = new JCheckBox("KMeans");
		chkHierarchy = new JCheckBox("Hierarchial");
		chkFarthestFirst = new JCheckBox("Farthest First");
		chkEm = new JCheckBox("EM");
		
		//customize file filter for specific files
		selectFiles.setAcceptAllFileFilterUsed(false);
		selectFiles.addChoosableFileFilter(new clusteringFileFilter());
		
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
				//System.out.println(file.getPath());
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
		
		//create instance of dispatcher occurs only first time a selection is made
		if(dispatchAlgorithms == null) {
			dispatchAlgorithms = new MultiAlgorithmClustering();
			dispatchAlgorithms.addAlgorithms(cb.getText());
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
	
	
	//custom file filter for showing only csv, arff, xrff files
	private class clusteringFileFilter extends FileFilter {

		@Override
		public boolean accept(File f) {
			// TODO Auto-generated method stub
			if(f.isDirectory()) {
				return true;
			}
			match = pattern.matcher(f.getName());
			return match.matches();
		}

		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return "ARFF, CSV and XRFF files";
		}
		
	}
}
