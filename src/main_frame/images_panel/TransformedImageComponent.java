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
 * The Transformed Image Component of the application.
 * @author JLepere2
 * @date 03/28/2018
 */
public class TransformedImageComponent extends JLabel {

	/**
	 * Creates a Transformed Image Component representing the transformed image after processing.
	 * @param m the Model for MVC
	 */
	public TransformedImageComponent(Model m) {
		
		this.setToolTipText("Select to save this image.");
		
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setVerticalAlignment(JLabel.CENTER);
		
		m.attachListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int[][] pixelMatrix = m.getTransformation();
				int size = pixelMatrix.length;
				transformedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
				for (int y = 0; y < size; y ++) {
					for (int x = 0; x < size; x ++) {
						transformedImage.setRGB(x, y, pixelMatrix[y][x]);
					}
				}
				setImageIcon();
			}
		});
		
		this.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				ImageLoadAndSave.saveImage(m.getTransformation());
			}
		});
		
	}
	
	private void setImageIcon() {
		this.setIcon(new ImageIcon(transformedImage));
	}
	
	private BufferedImage transformedImage;
	private static final long serialVersionUID = 980987761L;
	
}
