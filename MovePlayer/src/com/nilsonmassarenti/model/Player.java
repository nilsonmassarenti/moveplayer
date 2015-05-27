package com.nilsonmassarenti.model;

/**
 * Class to Model of Player on the system
 * @author Nilson Massarenti
 * @version 0.1
 * @since Release 01 of application
 */

public class Player {
	private Integer id;
	private String name;
	
	public void addPlayer(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
}
