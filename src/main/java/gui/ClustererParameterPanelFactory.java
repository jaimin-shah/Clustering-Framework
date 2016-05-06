package gui;

import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clusterers.DBS;
import clusterers.cobweb;
import clusterers.em;
import clusterers.farthest;
import clusterers.hierarchy;
import clusterers.kmeans;

public class ClustererParameterPanelFactory {
	
	//panels to hold algo parameters
	static JPanel pnlCobweb, pnlDbs, pnlKmeans, pnlHierarchy, pnlFarthestFirst, pnlEm;
	
	//parameter fields hierarchical-h, farthest-f, kmeans-k, dbscan-d,  em-e, cobweb-c
	static JTextField h_no_clus, f_seed, f_no_clus, k_iter,k_seed,k_no_clus, d_eplison, d_minpoint, 
				e_minstdev, e_seed, e_no_clus, e_maxiter, c_acutiy,c_seed,c_cutoff;
	
	//and their button handlers
    static JButton btnCob, btnKms, btnDbs, btnHie, btnFarthest, btnEm;
    
    //map for panel and algorithm parameters
    static HashMap<String, JPanel> rm;
    static HashMap<String, HashMap<String, Double>> default_para;
    
    static {
    	
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
		pnlHierarchy.add(new JLabel("HIERARCHICAL --- "));
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
        rm.put("COBWEB", pnlCobweb);
        rm.put("KMEANS", pnlKmeans );
        rm.put("HIERARCHICAL", pnlHierarchy );
        rm.put("FARTHEST FIRST", pnlFarthestFirst);
        rm.put("EM", pnlEm);
        
        //their mapped parameters
        default_para = new HashMap<String, HashMap<String, Double>>();
        default_para.put("DBSCAN",DBS.getDefaults() );
        default_para.put("COBWEB", cobweb.getDefaults());
        default_para.put("KMEANS",kmeans.getDefaults());
        default_para.put("HIERARCHICAL",hierarchy.getDefaults());
        default_para.put("FARTHEST FIRST", farthest.getDefaults());
        default_para.put("EM", em.getDefaults());
    }
    
    static JPanel getParameterPanel(String algo) {
    	return rm.get(algo);
    }
    
    static HashMap<String, Double> getDefaultParameters(String algo) {
    	return default_para.get(algo);
    }
}
