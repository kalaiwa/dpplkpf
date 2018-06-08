package de.fh_dortmund.swt.doppelkopf;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import de.fh_dortmund.swt.doppelkopf.enumerations.CardColour;
import de.fh_dortmund.swt.doppelkopf.enumerations.CardValue;
import de.fh_dortmund.swt.doppelkopf.enumerations.Suit;
import javafx.util.Pair;

@SuppressWarnings("restriction")
public class Card implements Serializable{

	private static final long serialVersionUID = -1319404831682683859L;

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
	private Client owner;

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

	public Client getOwner() {
		return owner;
	}
	public void setOwner(Client owner) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colour == null) ? 0 : colour.hashCode());
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (colour != other.colour)
			return false;
		if (suit != other.suit)
			return false;
		if (value != other.value)
			return false;
		return true;
	}

}
