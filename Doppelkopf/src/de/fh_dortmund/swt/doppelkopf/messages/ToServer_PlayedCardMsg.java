package de.fh_dortmund.swt.doppelkopf.messages;

import de.fh_dortmund.swt.doppelkopf.Card;
import de.fh_dortmund.swt.doppelkopf.Client;
import de.fh_dortmund.swt.doppelkopf.interfaces.ToServerMessage;

/**
 * Informs the server of a played Card
 */
public class ToServer_PlayedCardMsg implements ToServerMessage {

	private static final long serialVersionUID = -850857137961301271L;

	public static final String type = "PlayedCard";
	
	private Client sender;
	private Card card;
	
	public ToServer_PlayedCardMsg(Client sender, Card card) {
		this.sender = sender;
		this.card = card;
	}
	
	@Override
	public String getMessage() {
		return card.getOwner().getPlayer().getName() + " played " + card.toString();
	}
	
	public Card getCard() {
		return card;
	}

	@Override
	public Client getSender() {
		return sender;
	}

	@Override
	public String getType() {
		return type;
	}
	
}
