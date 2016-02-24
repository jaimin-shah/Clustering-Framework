package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class AlternateAppMode extends JPanel implements ActionListener {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 6763236354614599803L;
	
	//this frame
	JFrame fr;
	
	//panels
	//components holding panels
	JPanel componentsPane = new JPanel(new FlowLayout());
	
	//buttons
	//button switch to one data many algorithm analysis mode
	JButton btnswitchMode = new JButton("Switch to Many data one Algorithm");
	
	//Run Clustering
	JButton btnRun = new JButton("Run Clustering");
	
	//labels
	//display mode of operation
	JLabel lblguiType = new JLabel("Many Data one algorithm Mode", JLabel.CENTER);
	
	//radio buttons
	//select an algorithm
	JRadioButton radioKmeans, radioXmeans, radioDbscan, radioFarthestFirst;
	JRadioButton radioEm, radioCobweb;
	ButtonGroup groupAlgorithms = new ButtonGroup();
	
	public AlternateAppMode(JFrame fr) {
		this.fr = fr;
		
		//set layout of this panel
		setLayout(new BorderLayout());
		
		//initialize components
		radioKmeans = new JRadioButton("KMeans");
		radioXmeans = new JRadioButton("XMeans");
		radioEm = new JRadioButton("Em");
		radioCobweb = new JRadioButton("CobWeb");
		radioFarthestFirst = new JRadioButton("Farthest First");
		radioDbscan = new JRadioButton("DBSCAN");
		
		//add component listeners
		addRespectiveListeners();
		
		//add components to panel
		addComponents();
	}

	//add component's listeners
	private void addRespectiveListeners() {
		// TODO Auto-generated method stub
		btnswitchMode.addActionListener(this);
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
		groupAlgorithms.add(radioXmeans);
		
		componentsPane.add(radioCobweb);
		componentsPane.add(radioDbscan);
		componentsPane.add(radioEm);
		componentsPane.add(radioFarthestFirst);
		componentsPane.add(radioKmeans);
		componentsPane.add(radioXmeans);
		
		//button to run clustering algorithm
		componentsPane.add(btnRun);
		
		//add component's panel to this gui-panel
		add(componentsPane, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnswitchMode) {
			fr.remove(this);
			fr.add(new MainDisplayPanel(fr));
			fr.revalidate();
		}
	}
}
