import java.awt.BorderLayout;

import javax.swing.JFrame;

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
		this.setLayout(new BorderLayout());
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		ImagesPanel iP = new ImagesPanel(m);
		TransformationSelectionPanel tSP = new TransformationSelectionPanel(m);
		this.add(iP, BorderLayout.CENTER);
		this.add(tSP, BorderLayout.SOUTH);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private static final int FRAME_WIDTH = 1050;
	private static final int FRAME_HEIGHT = 600;
	private static final long serialVersionUID = 1321394L;

}
