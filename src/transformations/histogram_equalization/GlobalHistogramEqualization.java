package transformations.histogram_equalization;

import transformations.TransformationAlgorithm;

/**
 * Global histogram equalization algorithm for contrast enhancement.
 * @author JLepere2
 * @date 04/10/2018
 */
public class GlobalHistogramEqualization implements TransformationAlgorithm {
	
	public int[][] transform(int[][] image) {
		
		/*
		 * Variables:
		 * size - size of the image (width and height)
		 * N - number of pixels
		 * gvCount - the gray scale [0, gvCount)
		 */
		int size = image.length;
		int N = size * size;
		int gvCount = (int) Math.pow(2, 8);
		
		// array for computing original gray scale value to new gray scale value   
		int[] histoHelper = new int[gvCount];
		
		// count the number of pixels for each gray scale value
		for (int y = 0; y < size; y ++) {
			for (int x = 0; x < size; x ++) {
				histoHelper[image[y][x] & 0xff] += 1;
			}
		}
		
		// compute the running histogram for each gray scale value
		for (int i = 1; i < gvCount; i++) {
			histoHelper[i] += histoHelper[i-1];
		}
		
		// compute gray scale conversion
		for (int i = 0; i < gvCount; i ++) {
			histoHelper[i] = ((gvCount-1) * histoHelper[i]) / N;
		}
		
		// new image matrix declaration
		int[][] newImage = new int[size][size];
		for (int y = 0; y < size; y ++) {
			for (int x = 0; x < size; x ++) {
				int gv = histoHelper[image[y][x] & 0xff];
				newImage[y][x] = 0xff000000 + (gv << 16) + (gv << 8) + gv;
			}
		}
		
		return newImage;
	}

	public void showSelectionFrame(boolean visible) {}
	
	public String toString() {
		return "Global Histrogram Equalization";
	}
	
}
