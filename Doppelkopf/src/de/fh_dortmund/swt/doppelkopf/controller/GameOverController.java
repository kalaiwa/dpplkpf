package de.fh_dortmund.swt.doppelkopf.controller;

import de.fh_dortmund.swt.doppelkopf.dataexcange.GameOverDataModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GameOverController {
	GameOverDataModel model;
	
	@FXML
	Button backToMenuButton;
	
	@FXML
	Label gameScoreLabel;
	
	public GameOverController(GameOverDataModel model) {
		this.model = model;
		
		model.registerPropertyChangeListener(li -> {
			if(li.getPropertyName().equals("GameScoreProperty")) {
				Platform.runLater(new Runnable() {
	                 @Override public void run() {
	                	 gameScoreLabel.setText((String)li.getNewValue());	
	                 }
	             });
				
			}
		});
	}
	
	@FXML
	private void handleBackToMenuButtonAction(ActionEvent event) {
		model.setBackToMenuPressed();
	}
	
	@FXML
	private void handleNextGameButtonAction(ActionEvent event) {
		model.setNextGamePressed();
	}
}
