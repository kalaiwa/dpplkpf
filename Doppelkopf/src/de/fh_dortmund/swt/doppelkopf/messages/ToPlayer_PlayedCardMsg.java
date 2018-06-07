package de.fh_dortmund.swt.doppelkopf.messages;

import de.fh_dortmund.swt.doppelkopf.Card;
import de.fh_dortmund.swt.doppelkopf.Trick;
import de.fh_dortmund.swt.doppelkopf.interfaces.Message;

public class ToPlayer_PlayedCardMsg implements Message {

	public static final String type = "ToPlayer_PlayedCard";
	private Trick trick;
	
	public ToPlayer_PlayedCardMsg(Trick trick) {
		this.trick = trick;
	}
	
	@Override
	public String getMessage() {
		Card card = trick.getLatestCard();
		return card.getOwner() + " played " + card.toString();
	}
	
	public Trick getTrick() {
		return trick;
	}

}
