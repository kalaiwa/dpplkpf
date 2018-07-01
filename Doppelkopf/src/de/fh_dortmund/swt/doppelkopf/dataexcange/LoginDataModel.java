package de.fh_dortmund.swt.doppelkopf.dataexcange;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoginDataModel {
	private PropertyChangeSupport propertyChangeSupport;
	private String username;
	private String password;
	
	public LoginDataModel() {
		propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public void setLoginPressed() {
		propertyChangeSupport.firePropertyChange("LoginPressedProperty", false, true);
	}


	public void setClosePressed() {
		propertyChangeSupport.firePropertyChange("ClosePressedProperty", false, true);
	}

	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void registerPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}
}
