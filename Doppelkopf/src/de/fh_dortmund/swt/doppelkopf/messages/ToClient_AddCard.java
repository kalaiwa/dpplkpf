package de.fh_dortmund.swt.doppelkopf.messages;

import de.fh_dortmund.swt.doppelkopf.Card;
import de.fh_dortmund.swt.doppelkopf.interfaces.ToClientMessage;

public class ToClient_AddCard implements ToClientMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6688409705852279728L;

	public static final String type = "ToClient_AddCard";
	
	private String addressee;
	private Card card;
	
	public ToClient_AddCard(String string, Card card) {
		this.addressee = string;
		this.card = card;
	}
	
	@Override
	public String getMessage() {
		return "Added Card: " + card.toString();
	}

	@Override
	public String getAddressee() {
		return addressee;
	}
	
	public Card getCard() {
		return card;
	}

	@Override
	public String getType() {
		return type;
	}
}
