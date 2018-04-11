package io;

import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Model;

/**
 * Methods for loading and saving an image.
 * @author JLepere2
 * @date 04/11/2018
 */
public class ImageLoadAndSave {

	/**
	 * Loads an image and returns a buffered image.
	 * @param defaultImage true to load the default image, false to allow user to choose.
	 * @param m the Model for MVC.
	 */
	public static void loadImage(boolean defaultImage, Model m) {
		
		try {
			
			// initially loaded image before preprocessing
			BufferedImage image;
			if (defaultImage) {
				
				// load the default image
				image = ImageIO.read(ImageLoadAndSave.class.getResourceAsStream(defaultImagePath));
				
			} else {
				
				// allow the user to load an image
				JFileChooser imageChooser = new JFileChooser();
				imageChooser.setFileFilter(new FileNameExtensionFilter("JPEG FILES", "jpg", "jpeg"));
				if (imageChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File imageFile = imageChooser.getSelectedFile();
					image = ImageIO.read(imageFile);
				} else return;
				
			}
			
			/*
			 * Load the image and creates the loaded gray scale image buffer.
			 * https://groups.google.com/forum/#!msg/comp.lang.java.programmer/EwqPncLARFQ/1M7UZKs2xNQJ
			 */
			BufferedImage loadedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		    ColorConvertOp op = new ColorConvertOp(image.getColorModel().getColorSpace(), loadedImage.getColorModel().getColorSpace(), null);
		    op.filter(image, loadedImage);
		    
		    /*
		     * Loads the pixel matrix from the gray scale values.
		     * 
		     * Each rgb pixel is in the following format:
		     * 	aaaaaaaa | rrrrrrrr | gggggggg | bbbbbbbb
		     * 
		     * Since the image was set to gray scale (BufferedImage.TYPE_BYTE_GRAY),
		     *  the r, g and b components are all the same. Therefore, we need only
		     *  extract of these values to store in the matrix. This processing is done
		     *  in other parts of the program.
		     * 
		     * Converts the image to 512x512 by nearest neighbor technique.
		     */
		 	int imageSize = 512; // TODO: un hard code
		 	int loadedImageHeight = loadedImage.getHeight();
		 	int loadedImageWidth = loadedImage.getWidth();
		 	double heightRatio = ((double) loadedImageHeight) / imageSize;
		 	double widthRatio = ((double) loadedImageWidth) / imageSize;
		 	int[][] pixelMatrix = new int[imageSize][imageSize];
			for (int y = 0; y < imageSize; y ++) {
				for (int x = 0; x < imageSize; x ++) {
					int p = loadedImage.getRGB((int) (x * widthRatio), (int) (y * heightRatio));
					pixelMatrix[y][x] = p;
				}
			}
			
			// Alert Model.
			m.setPixelMatrix(pixelMatrix);
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, LOADING_ERROR_MESSAGE, "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	
	}
	
	/**
	 * Saves an image.
	 * @param pixelMatrix the pixel matrix.
	 */
	public static void saveImage(int[][] pixelMatrix) {
		
		// save chooser
		JFileChooser saveChooser = new JFileChooser();
		
		// show chooser
		if (saveChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			
			// get file
			File imageFile = saveChooser.getSelectedFile();
			try {
				
				// convert matrix to buffered image
				int imageSize = pixelMatrix.length;
				BufferedImage image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
				for (int y = 0; y < imageSize; y ++) {
					for (int x = 0; x < imageSize; x ++) {
						image.setRGB(x, y, pixelMatrix[y][x]);
					}
				}
				
				// write to file
				ImageIO.write(image, "jpg", imageFile);
				
			} catch (IOException e1) {
				
				JOptionPane.showMessageDialog(null, "There was an error saving the image.", "Error Saving Image", JOptionPane.ERROR_MESSAGE);
				
			}
		}
	}
	
	private static final String defaultImagePath = "lena.jpg";
	private static final String LOADING_ERROR_MESSAGE = "There was an error loading the image. Make sure the image is a JPG file.";
	
}
