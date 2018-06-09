package de.fh_dortmund.swt.doppelkopf;

import java.io.Serializable;

import de.fh_dortmund.swt.doppelkopf.enumerations.CardColour;
import de.fh_dortmund.swt.doppelkopf.enumerations.CardValue;
import de.fh_dortmund.swt.doppelkopf.enumerations.Suit;

public class Trick implements Serializable{

	private static final long serialVersionUID = -3153340372431595294L;

	private Card[] cards = new Card[4];
	private int lastPlayedCard = -1;
	private Suit suitToFollow;

	public Trick() {

	}

	public Suit getSuitToFollow() 
	{
		return suitToFollow;
	}

	public int getPoints() {
		int sum = 0;
		for (Card card : cards) {
			sum += card.getPoints();
		}
		return sum;
	}

	public Card[] getCards() {
		return cards;
	}

	/**
	 * @return owner of highestCard aka the winner
	 */
	public Client evaluate() {
		//First Card is the highest by default
		Card highestCard = cards[0];

		for (int i = 1; i < 4; i++) {
			//If card's colour  = suit or trump, checks if card is 'stronger' than previously highest card
			if(cards[i].getSuit().equals(suitToFollow) || cards[i].isTrump()) {
				if(cards[i].getTrickStrength() > highestCard.getTrickStrength())
					highestCard = cards[i];
			}
		}
		return highestCard.getOwner();
	}

	/**
	 * Adds card and sets suit to follow if it was the first card in Trick
	 */
	public void addCard(Card card) {
		if(lastPlayedCard==-1) suitToFollow = card.isTrump()? Suit.TRUMP : card.getColour().toSuit();
		cards[++lastPlayedCard] = card;

	}

	/** 
	 * @return caught Foxes if re was game winner
	 */
	public int caughtFoxes() {
		boolean re = evaluate().isRe();
		int foxes = 0;
		for(int i = 0; i < 4; i++) {
			Card card = cards[i];
			if(card.getColour().equals(CardColour.DIAMOND) && card.getValue().equals(CardValue.ACE)) {
				if(card.getOwner().isRe() && !re) foxes--;
				else if(!card.getOwner().isRe() && re) foxes++;
			}
		}
		return foxes;
	}

	@Override
	public String toString() {
		String str = "Current trick: ";
		for (Card card : cards) {
			if(card!=null) str += card.toString() + "   ";
		}
		return str;
	}
	
	public Card getLatestCard() {
		Card latestCard = cards[0];
		for (int i = 0; i < cards.length; i++) {
			if(cards[i]!=null) latestCard = cards[i];
		}
		return latestCard;
	}

}
