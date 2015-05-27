package com.nilsonmassarenti.utils;

/**
 * Class to Utilities of the system 
 * @author Nilson Massarenti
 * @version 0.1
 * @since Release 01 of application
 */

import java.util.Random;

public class General {
	/**
	 * 
	 * @param min Integer - Number min to random
	 * @param max Integer - Number max to random
	 * @param random Random - Object to randomize
	 * @return Integer - random number
	 */
	public Integer randomByRange(int min, int max, Random random) {
		if (min > max) {
			throw new IllegalArgumentException("Min cannot exceed Max.");
		}
		// get the range
		long range = (long) max - (long) min + 1;
		long fraction = (long) (range * random.nextDouble());
		int randomNumber = (int) (fraction + min);
		return randomNumber;
	}
}
