package de.fh_dortmund.swt.doppelkopf.messages;

import de.fh_dortmund.swt.doppelkopf.enumerations.State;
import de.fh_dortmund.swt.doppelkopf.interfaces.Message;

public class ToPlayer_StateMsg implements Message {

	public static final String type = "ToPlayer_StateMsg";
	private State state;
	
	public ToPlayer_StateMsg(State state) {
		this.state = state;
	}
	
	@Override
	public String getMessage() {
		return "Game is in state " + state.getRoundNo();
	}

	public State getState() {
		return state;
	}
}
