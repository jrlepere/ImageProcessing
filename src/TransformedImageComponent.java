import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
				transformedImage = new BufferedImage(size, size, BufferedImage.TYPE_BYTE_GRAY);
				for (int y = 0; y < size; y ++) {
					for (int x = 0; x < size; x ++) {
						transformedImage.setRGB(x, y, pixelMatrix[y][x]);
					}
				}
				setImageIcon();
			}
		});
		
		JFileChooser saveChooser = new JFileChooser();
		
		this.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				if (saveChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					File imageFile = saveChooser.getSelectedFile();
					try {
						int[][] pixelMatrix = m.getTransformation();
						int imageSize = pixelMatrix.length;
						BufferedImage image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
						for (int y = 0; y < imageSize; y ++) {
							for (int x = 0; x < imageSize; x ++) {
								image.setRGB(x, y, pixelMatrix[y][x]);
							}
						}
						ImageIO.write(image, "jpg", imageFile);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
	}
	
	private void setImageIcon() {
		this.setIcon(new ImageIcon(transformedImage));
	}
	
	private BufferedImage transformedImage;
	private static final long serialVersionUID = 980987761L;
	
}
