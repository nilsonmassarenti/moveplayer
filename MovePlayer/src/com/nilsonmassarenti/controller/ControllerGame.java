package com.nilsonmassarenti.controller;

/**
 * Class to Manager the application game
 * @author Nilson Massarenti
 * @version 0.1
 * @since Release 01 of application
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.nilsonmassarenti.model.Player;
import com.nilsonmassarenti.model.PlayerGame;
import com.nilsonmassarenti.utils.General;

public class ControllerGame {
	//Object of the generalUtils - provide the necessary utilities to system
	//Object of Random - provide randomize in methods
	private General generalUtils = new General();
	private Random random = new Random();
	
	//Object List<PlayerGame> is a list of the Players 
	//and information of game to this player
	List<PlayerGame> listPlayerGame = new ArrayList<PlayerGame>();
	
	/**
	 * This is main method where the core application is here
	 */
	public void playGame() {
		printMessage("Generating players");
		//Generate the Players to play a game and shuffle players in the list
		generatePlayers(10);
		Collections.shuffle(listPlayerGame);
		
		//check if players was select
		if (listPlayerGame.size() > 0) {
			printMessage(listPlayerGame.size() + " players generated");
			//declare the variables
			Integer x, y, maxX, maxY, minX, minY;
			//start the game and run until rest 1 player
			while (listPlayerGame.size() != 1) {
				//timer to change players of place in this case 1000ms = 1 second
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				printMessage("----------------------------");
				//for to move the players of place
				for (int j = 0; j < listPlayerGame.size(); j++) {
					PlayerGame playerGame = listPlayerGame.get(j);
					//check if player has a two yellows cards 
					//and if wait the necessary times
					//else execute continue moving
					if (playerGame.getYellowCard() == 2
							&& playerGame.getWaitingPlayer() <= 10) {
						if (playerGame.getWaitingPlayer() == 10) {
							refereeClearToPlay(playerGame, j);

						}
						playerGame.setWaitingPlayer(playerGame
								.getWaitingPlayer() + 1);
					} else {
						//check the informations to next movimentation
						if (playerGame.getCurPositionX() < 100) {
							maxX = playerGame.getCurPositionX() + 1;
						} else {
							maxX = playerGame.getCurPositionX();
						}
						if (playerGame.getCurPositionX() > 0) {
							minX = playerGame.getCurPositionX() - 1;
						} else {
							minX = playerGame.getCurPositionX();
						}
						if (playerGame.getCurPositionY() < 100) {
							maxY = playerGame.getCurPositionY() + 1;
						} else {
							maxY = playerGame.getCurPositionY();
						}
						if (playerGame.getCurPositionY() > 0) {
							minY = playerGame.getCurPositionY() - 1;
						} else {
							minY = playerGame.getCurPositionY();
						}
						//get a new movimentation in X and Y
						x = generalUtils.randomByRange(minX, maxX, random);
						y = generalUtils.randomByRange(minY, maxY, random);
						//validate if movimentation is right
						//else try again in next loop
						if (validMove(x, y, j) >= 0) {
							playerGame.setCurPositionX(x);
							playerGame.setCurPositionY(y);
							if (validMove(x, y, j) > 0) {
								refereeEjectPlayer(playerGame);
							}
						} else {
							j--;
						}
					}
					printMessage(playerGame.getPlayer().getName()
							+ " - POS X: " + playerGame.getCurPositionX()
							+ " - POS Y: " + playerGame.getCurPositionY());
				}
			}
			//show the winner of game
			PlayerGame playerGame = listPlayerGame.get(0);
			printMessage("----------------------------");
			printMessage(playerGame.getPlayer().getName() + " - Wins");
		} else {
			printMessage("Players not generated");
		}
	}

	/**
	 * This method is specific to generate a players
	 * @param quantity Integer
	 */
	private void generatePlayers(Integer quantity) {
		//declare variables
		int position[][] = new int[100][100];
		Integer x, y;
		//start the loop to create a player
		for (int i = 1; i <= quantity; i++) {
			x = generalUtils.randomByRange(1, 100, random) - 1;
			y = generalUtils.randomByRange(1, 100, random) - 1;
			//verifify if position X and Y is open to put a player
			//case cannot put try again
			if (position[x][y] == 0) {
				position[x][y] = i;
				//set the informations of player 
				Player player = new Player();
				player.addPlayer(i, "Player " + i);
				PlayerGame playerGame = new PlayerGame();
				playerGame.setPlayer(player);
				playerGame.setCurPositionX(x);
				playerGame.setCurPositionY(y);
				playerGame.setYellowCard(0);
				listPlayerGame.add(playerGame);
			} else {
				--i;
			}
		}
	}
	
	/**
	 * This method is responsible to validate the move a player 
	 * and check the yellow card
	 * 
	 * @param x Integer - position X
	 * @param y Integer - postion Y
	 * @param player Player - Player playing 
	 * @return Integer - Valid player with restrictions
	 */
	private Integer validMove(int x, int y, int player) {
		//Declare Variables
		Integer yellowCard = 0;
		//Search in the list if the position is valid
		for (int j = 0; j < listPlayerGame.size(); j++) {
			if (j != player) {
				PlayerGame playerGame = listPlayerGame.get(j);
				if (playerGame.getCurPositionX() == x
						&& playerGame.getCurPositionY() == y) {
					return -1;
				}
				yellowCard = refereeYellowCard(playerGame, x, y);
			}
		}
		return yellowCard;
	}

	/**
	 * This method is responsible to give the yellow card to player
	 * 
	 * @param playerGame PlayerGame - The game of player
	 * @param x Integer - Position X
	 * @param y Integer - Position Y
	 * @return Integer - return the yellow card
	 */
	private Integer refereeYellowCard(PlayerGame playerGame, int x, int y) {
		Integer yellowCard = 0;
		//check if player is 2 meters of the player
		if ((playerGame.getCurPositionX() - x <= 2 && playerGame
				.getCurPositionX() - x >= -2)
				&& (playerGame.getCurPositionY() - y <= 2 && playerGame
						.getCurPositionY() - y >= -2)) {
			yellowCard++;
		}
		return yellowCard;
	}
	
	/**
	 * This method authorize the player back to the game
	 * 
	 * @param playerGame PlayGame - Information of Player
	 * @param position - Position in array
	 */
	private void refereeClearToPlay(PlayerGame playerGame, Integer position) {
		Integer back = -1;
		int x = 0;
		int y = 0;
		//when back -1 not set a new position of player
		while (back == -1) {
			x = generalUtils.randomByRange(1, 100, random) - 1;
			y = generalUtils.randomByRange(1, 100, random) - 1;

			back = validMove(x, y, position);
		}
		//set new position of player
		playerGame.setCurPositionX(x);
		playerGame.setCurPositionY(y);
		printMessage(playerGame.getPlayer().getName() + " - Backing to game.");
	}
	
	/**
	 * This method eject player of the game
	 * 
	 * @param playerGame PlayerGame - Player of game
	 */
	public void refereeEjectPlayer(PlayerGame playerGame){
		printMessage(playerGame.getPlayer()
				.getName() + ": yellow card");
		playerGame.setYellowCard(playerGame
				.getYellowCard() + 1);
		//check if the second card to eject a player to 10 seconds
		if (playerGame.getYellowCard() == 2) {
			printMessage(playerGame.getPlayer().getName() + " - Second Yellow Card - Ejected");
			//set this positions to player not play during the eject time
			playerGame.setCurPositionX(999);
			playerGame.setCurPositionY(999);
			playerGame.setWaitingPlayer(0);
		}
		//check if the fourth yellow card and delete player of game
		if (playerGame.getYellowCard() == 4) {
			printMessage(playerGame.getPlayer().getName() + " - fourth Yellow Card - Deleted");
			listPlayerGame.remove(playerGame);
		}
	}
	
	/**
	 * This method is to print a message in screen
	 * 
	 * @param message - The message to print
	 */
	private void printMessage(String message) {
		System.out.println(message);
	}
}
