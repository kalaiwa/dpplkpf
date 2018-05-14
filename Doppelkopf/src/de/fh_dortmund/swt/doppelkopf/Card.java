package de.fh_dortmund.swt.doppelkopf;

import java.util.Arrays;
import java.util.List;

import de.fh_dortmund.swt.doppelkopf.enumerations.CardColour;
import de.fh_dortmund.swt.doppelkopf.enumerations.CardValue;
import de.fh_dortmund.swt.doppelkopf.enumerations.Suit;
import javafx.util.Pair;

public class Card {

	public static final List<Pair<CardColour, CardValue>> trumps = Arrays.asList(

			new Pair<CardColour, CardValue>(CardColour.HEART, CardValue.TEN),
			
			new Pair<CardColour, CardValue>(CardColour.CLUB, CardValue.QUEEN),
			new Pair<CardColour, CardValue>(CardColour.SPADE, CardValue.QUEEN),
			new Pair<CardColour, CardValue>(CardColour.DIAMOND, CardValue.QUEEN),
			new Pair<CardColour, CardValue>(CardColour.HEART, CardValue.QUEEN),
			
			new Pair<CardColour, CardValue>(CardColour.CLUB, CardValue.JACK),
			new Pair<CardColour, CardValue>(CardColour.SPADE, CardValue.JACK),
			new Pair<CardColour, CardValue>(CardColour.DIAMOND, CardValue.JACK),
			new Pair<CardColour, CardValue>(CardColour.HEART, CardValue.JACK),

			new Pair<CardColour, CardValue>(CardColour.DIAMOND, CardValue.KING),
			new Pair<CardColour, CardValue>(CardColour.DIAMOND, CardValue.TEN),
			new Pair<CardColour, CardValue>(CardColour.DIAMOND, CardValue.ACE));
	
//	private int trickStrength;
	private final CardColour colour;
	private final Suit suit;
	private final CardValue value;
	private Player owner;

	public Card(CardColour c, CardValue v) {
		colour = c;
		value = v;

		if(isTrump()) suit = Suit.TRUMP;
		else suit = colour.toSuit();
	}

	public int getTrickStrength() {
		if(suit.equals(Suit.TRUMP)) {
			return 15-trumps.indexOf(new Pair<CardColour, CardValue>(colour, value));
		}
		switch(value) {
		case ACE: return 3;
		case TEN: return 2;
		case KING: return 1;
		default: return 0;
		}
		
	}
//	public void setTrickStrength(int trickStrength) {
//		this.trickStrength = trickStrength;
//	}

	public Player getOwner() {
		return owner;
	}
	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public CardColour getColour() {
		return colour;
	}

	public CardValue getValue() {
		return value;
	}

	public int getPoints() {
		return value.getPoints();
	}

	public boolean isTrump() {
		return trumps.contains(new Pair<CardColour, CardValue>(colour, value));
	}
	
	public Suit getSuit() {
		return suit;
	}


	@Override
	public String toString() {
		return colour.toString() + value.toString();
	}

}
