package main_frame.menu;

import javax.swing.JMenu;
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
		
		// View menu component
		JMenu viewMenu = new JMenu("View");
		//viewMenu.setMnemonic(KeyEvent.VK_V);
		
		
		viewMenu.add(new BitPlaneMenu(m));
		
		// add menus
		this.add(viewMenu);
		
	}
	
	private static final long serialVersionUID = 124134241L;
	
}
