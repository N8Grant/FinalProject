package application.view.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.Trip;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BussesOutstandingController extends Main
{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML 
    public Label dateLabel;
    
    @FXML
    private Label dateError;

    @FXML
    private GridPane displayGrid;

    @FXML
    private DatePicker dateSelect;

    @FXML
    private Button viewTrips;

    @FXML
    private Button homeButton;
    
    Label nameLabel;
    Label idLabel;
    Label busNumLabel;

    @FXML
    void displayTrips(ActionEvent event) 
    {
    	if (dateSelect.getValue() == null)
    	{
    		dateError.setText("Enter a date!!");
    	}
    	else
    	{
    		displayGrid.getChildren().removeAll(nameLabel);
    		displayGrid.getChildren().removeAll(idLabel);
    		displayGrid.getChildren().removeAll(busNumLabel);
    		dateLabel.setText("Date: " + dateSelect.getValue().toString());
    		
    		int r = 0;
    		
    		for (Trip trp: getTripsOnDate(dateSelect.getValue()))
    		{
    			nameLabel = new Label(trp.getName());
    			nameLabel.setTextFill(Color.web("#f2ff00"));
    			displayGrid.add(nameLabel, 0, r);
    			idLabel = new Label(trp.getId());
    			idLabel.setTextFill(Color.web("#f2ff00"));
    			displayGrid.add(idLabel, 1, r);
    			busNumLabel = new Label(trp.getBusNumbers());
    			busNumLabel.setTextFill(Color.web("#f2ff00"));
    			displayGrid.add(busNumLabel, 2, r);
    			r++;
    		}
    	}
    }

    @FXML
    void returnHome(ActionEvent event) throws IOException 
    {
    	Stage stage;
		AnchorPane root;
		stage = (Stage) homeButton.getScene().getWindow();
					
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/MainMenu.fxml"));
		root = (AnchorPane) loader.load();   
			
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void initialize() {
        assert dateLabel != null : "fx:id=\"dateLabel\" was not injected: check your FXML file 'BussesOutstanding.fxml'.";
        assert displayGrid != null : "fx:id=\"displayGrid\" was not injected: check your FXML file 'BussesOutstanding.fxml'.";
        assert dateSelect != null : "fx:id=\"dateSelect\" was not injected: check your FXML file 'BussesOutstanding.fxml'.";
        assert viewTrips != null : "fx:id=\"viewTrips\" was not injected: check your FXML file 'BussesOutstanding.fxml'.";
        assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'BussesOutstanding.fxml'.";
        assert dateError != null : "fx:id=\"dateError\" was not injected: check your FXML file 'BussesOutstanding.fxml'.";

    }
}
