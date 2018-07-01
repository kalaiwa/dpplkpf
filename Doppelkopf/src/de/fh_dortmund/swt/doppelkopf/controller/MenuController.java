package de.fh_dortmund.swt.doppelkopf.controller;

import java.awt.event.ActionListener;

import de.fh_dortmund.swt.doppelkopf.ClientApp;
import de.fh_dortmund.swt.doppelkopf.dataexcange.MenuDataModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MenuController {
	@FXML
	private Button playButton; 
	
	private MenuDataModel model;
	
	public MenuController(MenuDataModel model) {
		this.model = model;
	}
	
	@FXML
	private void handlePlayButtonAction(ActionEvent event) {
		model.setStartPressed(true);
	}
	
	@FXML
	private void handleLeaderboardButtonAction(ActionEvent event) {
		model.setLeaderboardPressed();
	}
}
