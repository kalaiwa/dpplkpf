package de.fh_dortmund.swt.doppelkopf.dataexcange;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MenuDataModel {
	private boolean isStartPressed;
	private boolean isScorePressed;
	private PropertyChangeSupport propertyChangeSupport;
	
	public MenuDataModel() {
		isStartPressed = isScorePressed = false;
		propertyChangeSupport = new PropertyChangeSupport(this);
	}
	
	public boolean isStartPressed() {
		return isStartPressed;
	}

	public void setStartPressed(boolean isStartPressed) {
		boolean oldValue = this.isStartPressed;
		this.isStartPressed = isStartPressed;
		propertyChangeSupport.firePropertyChange("StartPressedProperty", isStartPressed, oldValue);
	}

	public boolean isScorePressed() {
		return isScorePressed;
	}

	public void setScorePressed(boolean isScorePressed) {
		boolean oldValue = this.isScorePressed;
		this.isScorePressed = isScorePressed;
		propertyChangeSupport.firePropertyChange("ScorePressedProperty", isScorePressed, oldValue);
	}

	public void registerPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void setLeaderboardPressed() {
		propertyChangeSupport.firePropertyChange("LeaderboardPressedProperty", false, true);
	}
}
