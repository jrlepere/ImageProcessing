import java.awt.GridLayout;

import javax.swing.JPanel;

import model.Model;

/**
 * Panel for selecting the transformation to perform on the loaded image.
 * @author JLepere2
 * @date 03/29/2018
 */
public class ResolutionSelectionPanel extends JPanel {

	/**
	 * Creates the selection panel for making transformations on the loaded image.
	 * @param m the Model for MVC.
	 */
	public ResolutionSelectionPanel(Model m) {
		
		this.setLayout(new GridLayout(2, 1));
		
		this.add(new ZoomInSlider(m));
		this.add(new BitPrecisionSlider(m));
		
	}
	
	public static final int WEST_DESCRIPTION_WIDTH = 100;
	public static final int DESCRIPTION_HEIGHT = 50;
	private static final long serialVersionUID = 2232421L;

}
