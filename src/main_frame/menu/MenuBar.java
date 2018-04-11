package main_frame.menu;

import javax.swing.JMenuBar;

import model.Model;

/**
 * A class for defining the menu bar for the frame.
 * @author JLepere2
 * @date 04/11/2018
 */
public class MenuBar extends JMenuBar {

	/**
	 * Creates a menu bar for the application.
	 * @param m the Model for MVC.
	 */
	public MenuBar(Model m) {
		
		// add menus
		this.add(new FileMenu(m));
		this.add(new ViewMenu(m));
		
	}
	
	private static final long serialVersionUID = 124134241L;
	
}
