package de.fh_dortmund.swt.doppelkopf.controller;

import de.fh_dortmund.swt.doppelkopf.dataexcange.LoginDataModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
	@FXML
	private Button loginButton; 
	@FXML
	private Button closeButton; 
	@FXML
	private PasswordField passwordField;
	@FXML
	private TextField usernameTextField;
	
	private LoginDataModel model;
	
	public LoginController(LoginDataModel model) {
		this.model = model;
	}
	
	@FXML
	private void handleLoginButtonAction(ActionEvent event) {
		if(passwordField.getText() != null && usernameTextField.getText() != null) {
			model.setPassword(passwordField.getText());
			model.setUsername(usernameTextField.getText());
			model.setLoginPressed();
		}
	}
	
	@FXML
	private void handleCloseButtonAction(ActionEvent event) {
		model.setClosePressed();
	}
}