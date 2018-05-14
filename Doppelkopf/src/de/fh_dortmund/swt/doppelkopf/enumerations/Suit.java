
package de.fh_dortmund.swt.doppelkopf.enumerations;

public enum Suit{

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