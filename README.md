# Image Processing Tool Overview
- GUI program to visualize output of multiple image processing techniques.
- Original image is shown on the left. Transformed image is shown on the right.
- Parameter selection frames unique for each algorithm.

# How To Run
- The program has been compiled into a jar file with a default image (512x512 lena.jpg) to perform the transformations.
- The src files are available as well, so if you would like to run the program from the command line, that is also acceptable. To do this, run javac Driver.java followed by java Driver from the command line.

# How To Use
- TRANSFORMATIONS: Algorithm selection drop down box is located at the top of the frame. Following are the current transformations available:
  - Nearest Neighbor
  - Linear Interpolation (X & Y)
  - Bilinear Interpolation
  - Negative
  - Global Histogram Equalization
  - Local Histogram Equalization
  - Smoothing Filter
  - Median Filter
  - Sharpening Laplacian Filter
  - High Boost Filter
  - Bit Plane Removal
  - Arithmetic Mean Filter
  - Geometric Mean Filter
  - Harmonic Mean Filter
  - Contraharmonic Mean Filter
  - Maximum Filter
  - Minimum Filter
  - Midpoint Filter
  - Alpha Trimming Mean Filter

- ADDING AN IMAGE: There are two ways to add an image to the program. Regardless, the image will be converted to gray scale and transformed to 512 x 512.
  - Select "File > Load Image" from the menu bar.
  - Select the loaded image on the left of the frame.
- SAVING AN IMAGE: There are two ways to save the transformed image.
  - Select "File > Save Image" from the menu bar.
  - Select the transformed image on the right of the frame.
- BIT PRECISION: The user also has the ability to change the bit precision, in the range [1,8] bits, used for storing the pixels. This is done using the slider at the bottom of the frame.
- BIT PLANE: To view different bit planes, select "View > Bit Plane" and choose the desired bit plane.

# Feedback
Any bugs or feedback can be directed to Jake Lepere - jlepere2@yahoo.com.

# Sample Output
![alt text](https://github.com/jrlepere/ImageTransformationProject/blob/master/imgs/04-11-2018_NearestNeighbor_512-32-512_7.png)

![alt text](https://github.com/jrlepere/ImageTransformationProject/blob/master/imgs/04-11-2018_LocalHistogramEqualization_5_2.png)
