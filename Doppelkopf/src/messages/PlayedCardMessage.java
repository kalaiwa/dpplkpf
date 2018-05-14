package messages;

import de.fh_dortmund.swt.doppelkopf.Card;
import de.fh_dortmund.swt.doppelkopf.interfaces.Message;

public class PlayedCardMessage implements Message {

	String msg;
	Card card;
	
	public PlayedCardMessage(Card card) {
		msg = "PlayedCard";
		this.card = card;
	}
	
	@Override
	public String getMessage() {
		return msg;
	}
	
	public Card getCard() {
		return card;
	}
	
}
