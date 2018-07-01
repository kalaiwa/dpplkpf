package de.fh_dortmund.swt.doppelkopf.controller;

import java.util.HashMap;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class ScreenController {
    private HashMap<String, Pane> screenMap = new HashMap<>();
    private Scene main;

    public ScreenController(Scene main) {
        this.main = main;
    }

    public void addScreen(String name, Pane pane){
         screenMap.put(name, pane);
    }

    public void removeScreen(String name){
        screenMap.remove(name);
    }

    public void activate(String name){
    	if(main != null) {
    		 main.setRoot( screenMap.get(name) );
    	}
    	else main = new Scene(screenMap.get(name));
    }
}