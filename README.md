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
- BIT PRECISION: The user also has the ability to change the bit precision, in the range [1,8] bits, used for storing the pixels. This is done using the slider at the bottom of the frame.
- ADDING: Adding new images is also enabled. This can be done by selecting the image on the left of the frame. A selection frame then appears and allows the user to navigate their system and load a new JPG image. For purposes of the project, the image is then transformed to 512x512 and gray scaled.
- SAVING: Saving the images is also a possibility. Similar to loading a new image, the user can select the transformed image. A prompt will appear giving the option to save.

# Feedback
Any bugs or feedback can be directed to Jake Lepere - jlepere2@yahoo.com.

# Sample Output
![alt text](https://github.com/jrlepere/ImageTransformationProject/blob/master/imgs/NearestNeighbor_512-32-512_7.png)
