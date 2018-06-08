package de.fh_dortmund.swt.doppelkopf.interfaces;

import java.io.Serializable;

public interface Message extends Serializable {

	public String getMessage();
	public String getType();
}
