package application.view.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class AvailableBussesController 
{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dateSelect;

    @FXML
    private Button selectButton;

    @FXML
    private Button homeButton;

    @FXML
    private Label busNumbersLabel;

    @FXML
    void displayBusses(ActionEvent event) 
    {

    }

    @FXML
    void returnHome(ActionEvent event) 
    {
    	

    }

    @FXML
    void initialize() 
    {
        assert dateSelect != null : "fx:id=\"dateSelect\" was not injected: check your FXML file 'Untitled'.";
        assert selectButton != null : "fx:id=\"selectButton\" was not injected: check your FXML file 'Untitled'.";
        assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'Untitled'.";
        assert busNumbersLabel != null : "fx:id=\"busNumbersLabel\" was not injected: check your FXML file 'Untitled'.";

    }
}
