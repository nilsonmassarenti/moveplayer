package com.nilsonmassarenti.view;

/**
 * Class to start the game application
 * @author Nilson Massarenti
 * @version 0.1
 * @since Release 01 of application
 */

import com.nilsonmassarenti.controller.ControllerGame;

public class MainApp {
	
	/**
	 * Method main of application
	 * @param args
	 */
	public static void main(String[] args) {
		//Create the object game and start play
		System.out.println("Starting game\n");
		ControllerGame game = new ControllerGame();
		game.playGame();
		System.out.println("Closing game\n");
	}

}
