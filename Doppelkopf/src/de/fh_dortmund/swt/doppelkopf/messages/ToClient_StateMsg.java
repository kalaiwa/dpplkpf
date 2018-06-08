package de.fh_dortmund.swt.doppelkopf.messages;

import de.fh_dortmund.swt.doppelkopf.enumerations.State;
import de.fh_dortmund.swt.doppelkopf.interfaces.ToClientMessage;

public class ToClient_StateMsg implements ToClientMessage {

	private static final long serialVersionUID = -1621405473434913986L;

	public static final String type = "ToClient_StateMsg";
	
	private String addressee;
	private State state;
	
	public ToClient_StateMsg(String addressee, State state) {
		this.state = state;
		this.addressee = addressee;
	}
	
	@Override
	public String getMessage() {
		return "Game is in state " + state.getStateName();
	}

	public State getState() {
		return state;
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
