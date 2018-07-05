
package de.fh_dortmund.swt.doppelkopf.enumerations;

import java.io.Serializable;

/**
 * Colour of a card in Doppelkopf terms - A card is either a trump card,
 *  so its original colour is obsolete, or non-trumps,
 *  where it's valued by its original Colour  
 */
public enum Suit implements Serializable{

	CLUB("C"),
	SPADE("S"),
	HEART("H"),
	DIAMOND("D"),
	TRUMP("T");

	private String initial;

	private Suit(String initial) {
		this.initial = initial;
	}

	@Override
	public String toString() {
		return initial;
	}

}