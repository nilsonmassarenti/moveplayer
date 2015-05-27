package com.nilsonmassarenti.model;
/**
 * Class to Model of PlayerGame on the system
 * @author Nilson Massarenti
 * @version 0.1
 * @since Release 01 of application
 */
public class PlayerGame {
	private Player player;
	private Integer waitingPlayer;
	private Integer yellowCard;
	private Integer curPositionX;
	private Integer curPositionY;
	
	
	public Player getPlayer() {
		return player;
	}
	public Integer getWaitingPlayer() {
		return waitingPlayer;
	}
	public Integer getCurPositionX() {
		return curPositionX;
	}
	public Integer getCurPositionY() {
		return curPositionY;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public void setWaitingPlayer(Integer waitingPlayer) {
		this.waitingPlayer = waitingPlayer;
	}
	public void setCurPositionX(Integer curPositionX) {
		this.curPositionX = curPositionX;
	}
	public void setCurPositionY(Integer curPositionY) {
		this.curPositionY = curPositionY;
	}
	public Integer getYellowCard() {
		return yellowCard;
	}
	public void setYellowCard(Integer yellowCard) {
		this.yellowCard = yellowCard;
	}
	
	
}
