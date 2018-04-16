package main_frame.images_panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import io.ImageLoadAndSave;
import model.Model;

/**
 * The Loaded Image Component of the application.
 * @author JLepere2
 * @date 03/28/2018
 */
public class LoadedImageComponent extends JLabel {

	/**
	 * Creates a Loaded Image Component representing the loaded image for processing.
	 * @param m the Model for MVC
	 */
	public LoadedImageComponent(Model m) {
		
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setVerticalAlignment(JLabel.CENTER);
		this.setToolTipText("Select to load a new image.");
		
		m.attachListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				
				// get the loaded pixel matrix.
				int[][] pixelMatrix = m.getPixelMatrix();
				
				// spatial resolution of the image
				int imageSize = pixelMatrix.length;
				
				// Set icon.
				BufferedImage loadedImage = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
				for (int y = 0; y < imageSize; y ++) {
					for (int x = 0; x < imageSize; x ++) {
						loadedImage.setRGB(x, y, pixelMatrix[y][x]);
					}
				}
				
				// set the icon image
				setIconImage(loadedImage);
			}
		});
		
		this.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				ImageLoadAndSave.loadImage(false, m);
			}
		});
		
		ImageLoadAndSave.loadImage(true, m);
		
	}
	
	/**
	 * Set the image for this component.
	 * @param image the image.
	 */
	private void setIconImage(BufferedImage image) {
		this.setIcon(new ImageIcon(image));
	}
	
	private static final long serialVersionUID = 113423452L;
	
}
