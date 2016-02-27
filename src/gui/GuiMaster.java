package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GuiMaster extends JFrame {
	
	//top menu-bar
	JMenuBar mainMenuBar = new JMenuBar();
	JMenu fileMenu = new JMenu("File");
	
	public GuiMaster() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(695, 240, 600, 600);
		setLayout(new BorderLayout());
		setTitle("Clustering Framework");
		
		//add the top menu bar
		mainMenuBar.add(fileMenu);
		add(mainMenuBar, BorderLayout.NORTH);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
                setlook();
		SwingUtilities.invokeLater(new Runnable() {
                    
			
			@Override
			public void run() {
				/* Create the heavy frame and inject it to light
				 * Panel for display of gui ..
				 */
				GuiMaster gui = new GuiMaster();
				MainDisplayPanel p = new MainDisplayPanel(gui);
				gui.setVisible(true);
			}
		});
	}
        static void setlook()
        {
            try {
                // Set System L&F
                UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
                } 
                catch (UnsupportedLookAndFeelException e) {
                   // handle exception
                }
                catch (ClassNotFoundException e) {
                   // handle exception
                }
                catch (InstantiationException e) {
                   // handle exception
                }
                catch (IllegalAccessException e) {
                   // handle exception
                }
        }

}
