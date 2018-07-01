package de.fh_dortmund.swt.doppelkopf.dataexcange;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LobbyDataModel {
	private PropertyChangeSupport propertyChangeSupport;
	
	public LobbyDataModel() {
		propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public void setCancelPressed() {
		propertyChangeSupport.firePropertyChange("CancelPressedProperty", false, true);
	}

	public void registerPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}
}
