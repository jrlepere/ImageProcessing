package main_frame.images_panel;
import java.awt.GridLayout;

import javax.swing.JPanel;

import model.Model;

/**
 * Panel for the loaded and transformed images.
 * @author JLepere2
 * @date 03/29/2017
 */
public class ImagesPanel extends JPanel {

	/**
	 * Creates the panel for housing the image components.
	 * @param m the Model for MVC
	 */
	public ImagesPanel(Model m) {
		this.setLayout(new GridLayout(1, 2));
		
		TransformedImageComponent tIC = new TransformedImageComponent(m);
		LoadedImageComponent lIC = new LoadedImageComponent(m);
		
		this.add(lIC);
		this.add(tIC);
	}
	
	private static final long serialVersionUID = 1353901L;
	
}
