package de.fh_dortmund.swt.doppelkopf.controller;

import de.fh_dortmund.swt.doppelkopf.dataexcange.LobbyDataModel;
import de.fh_dortmund.swt.doppelkopf.dataexcange.MenuDataModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LobbyController {
	@FXML
	private Label titleLabel; 
	
	private LobbyDataModel model;
	
	public LobbyController(LobbyDataModel model) {
		this.model = model;
	}
	
	@FXML
	private void handleCancelButtonAction(ActionEvent event) {
		model.setCancelPressed();
	}
}
