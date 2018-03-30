import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
		
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setVerticalAlignment(JLabel.CENTER);
		
		m.attachListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				System.out.println("Updating Transformed Image");
				int[][] pixelMatrix = m.getTransformation();
				int height = pixelMatrix.length;
				int width = pixelMatrix[0].length;
				transformedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
				for (int y = 0; y < height; y ++) {
					for (int x = 0; x < width; x ++) {
						transformedImage.setRGB(x, y, pixelMatrix[y][x]);
					}
				}
				setImageIcon();
			}
		});
		
	}
	
	private void setImageIcon() {
		this.setIcon(new ImageIcon(transformedImage));
	}
	
	private BufferedImage transformedImage;
	private static final long serialVersionUID = 980987761L;
	
}
