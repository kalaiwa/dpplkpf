package de.fh_dortmund.swt.doppelkopf.messages;

import de.fh_dortmund.swt.doppelkopf.Card;
import de.fh_dortmund.swt.doppelkopf.interfaces.Message;

public class ToServer_PlayedCardMsg implements Message {

	public static final String type = "PlayedCard";
	Card card;
	
	public ToServer_PlayedCardMsg(Card card) {
		this.card = card;
	}
	
	@Override
	public String getMessage() {
		return card.getOwner() + " played " + card.toString();
	}
	
	public Card getCard() {
		return card;
	}
	
}
