package de.fh_dortmund.swt.doppelkopf.messages;

import de.fh_dortmund.swt.doppelkopf.Card;
import de.fh_dortmund.swt.doppelkopf.Trick;
import de.fh_dortmund.swt.doppelkopf.interfaces.ToClientMessage;

public class ToClient_PlayedCardMsg implements ToClientMessage {

	private static final long serialVersionUID = 1628009996338977185L;

	public static final String type = "ToClient_PlayedCard";
	
	private String addressee;
	private Trick trick;
	
	public ToClient_PlayedCardMsg(String addressee, Trick trick) {
		this.addressee = addressee;
		this.trick = trick;
	}
	
	@Override
	public String getMessage() {
		Card card = trick.getLatestCard();
		return card.getOwner().getPlayer().getName() + " played " + card.toString();
	}
	
	public Trick getTrick() {
		return trick;
	}

	@Override
	public String getAddressee() {
		return addressee;
	}

	@Override
	public String getType() {
		return type;
	}
}
