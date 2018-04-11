package main_frame.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import io.ImageLoadAndSave;
import model.Model;

/**
 * Load component in main menu bar.
 * @author JLepere2
 * @date 04/11/2018
 */
public class FileMenu extends JMenu {

	/**
	 * Creates the File menu component.
	 * @param m the Model for MVC.
	 */
	public FileMenu(Model m) {
		super("File");
		
		// load file menu item
		JMenuItem loadImage = new JMenuItem("Load Image");
		loadImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImageLoadAndSave.loadImage(false, m);
			}
		});
		this.add(loadImage);
		
		// save file menu item
		JMenuItem saveImage = new JMenuItem("Save Image");
		saveImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImageLoadAndSave.saveImage(m.getTransformation());
			}
		});
		this.add(saveImage);
		
	}
	
	private static final long serialVersionUID = 1646978L;
	
}
