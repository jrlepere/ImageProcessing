package main_frame;
import java.awt.BorderLayout;

import javax.swing.JFrame;

import model.Model;

/**
 * MainFrame for the GUI.
 * @author JLepere2
 * @date 03/29/2018
 */
public class MainFrame extends JFrame{

	/**
	 * Creates the Main Frame for the application.
	 * @param m the Model for MVC.
	 */
	public MainFrame(Model m) {
		
		this.setTitle(FRAME_TITLE);
		this.setLayout(new BorderLayout());
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		this.add(new AlgorithmSelectionPanel(m), BorderLayout.NORTH);
		this.add(new ImagesPanel(m), BorderLayout.CENTER);
		this.add(new ResolutionSelectionPanel(m), BorderLayout.SOUTH);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	private static final String FRAME_TITLE = "Image Processing Tool";
	private static final int FRAME_WIDTH = 1050;
	private static final int FRAME_HEIGHT = 620;
	private static final long serialVersionUID = 1321394L;

}
