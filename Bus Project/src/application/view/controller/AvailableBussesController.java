package application.view.controller;		// Package that the calss is in

/*
 * Import Section
 */
import application.Main;	// Used for main methods

import java.io.IOException;		// USed to handle input and output errors
import java.net.URL;		// Used for http requests 
import java.util.Optional;	// Used for popup box handling	
import java.util.ResourceBundle;	// USed to help locate files in storage

import javax.xml.parsers.ParserConfigurationException;		// Exception for file parsing

import org.xml.sax.SAXException;		// Used for file writing exception
	
import javafx.event.ActionEvent;	// Used to handle default events
import javafx.fxml.FXML;		// The import for FXML handling
import javafx.fxml.FXMLLoader;	// USed for the loading of FXML files
import javafx.scene.Scene;		// The scene which the windows are displayed on
import javafx.scene.control.Alert;		// USed for popups
import javafx.scene.control.Button;		// USed for button GUI class
import javafx.scene.control.ButtonType;		// Used to distinguish action events
import javafx.scene.control.DatePicker;		// Used for date picker handling
import javafx.scene.control.Label;		// Used for plain text labels
import javafx.scene.control.Alert.AlertType;	// Constants for the different types of alerts
import javafx.scene.layout.AnchorPane;		// The pane used for windows
import javafx.stage.Stage;		// The handling of the monitor

public class AvailableBussesController extends Main
{
	
	/*
	 * GUI Elements
	 */
    @FXML
    private ResourceBundle resources;		// Controller Resources

    @FXML
    private URL location;			// Location in storage

    @FXML
    private DatePicker dateSelect;		// Date picker for availability
    
    @FXML
    private Button selectButton;		// Button that applies selected date

    @FXML
    private Button homeButton;		// Button to return to main menu

    @FXML
    private Label busNumbersLabel;		// Label for the number of busses

    @FXML
    private Label dateError;		// Error for the date picker
    
    @FXML
    void displayBusses(ActionEvent event) throws ParserConfigurationException, SAXException, IOException 
    /*
     * Precondition:  User clicks on select button
     * Postcondition: Number of busses that are available are shown
     */
    {
    	/*
    	 * If a date isn't chosen
    	 */
    	if (dateSelect.getValue() == null)
    	{
    		dateError.setText("Please endter a date!!");		// Print error message
    	}
    	/*
    	 * Else there is a date picked
    	 */
    	else 
    	{
    		/*
    		 * If there are no busses available of the date
    		 */
    		if (getAvailable(dateSelect.getValue()) == null)
    		{
    			busNumbersLabel.setText("Busses Available: 0");
    		}
    		/*
    		 * Else there are one ore more busses available
    		 */
    		else
    		{
    			busNumbersLabel.setText("Busses Available: " + getAvailable(dateSelect.getValue()));
    	    	
    		}
    	}
    }

    @FXML
    void returnHome(ActionEvent event) throws IOException 
    /*
     * Precondition:  User presses on home button
     * Postcondition: User is returned back to main menu if they confirm the dialogue
     */
    {
    	/*
    	 * Alert to show finalize success
    	 */
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Return Home");
		alert.setContentText("Are you sure you want to return home?");
		Optional<ButtonType> result = alert.showAndWait();
		
		/*
		 * If User confirms pop-up dialog box
		 */
		if (result.get() == ButtonType.OK)
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
		/*
		 * Else close the alert
		 */
		else
		{
			alert.close();	   // closes the alert if cancel is pressed
		}
    }

    @FXML
    void initialize() 
    /*
     * Precondition:  There is an error injecting the GUI elements
     * Postcondition: Error message is spit out
     */
    {
        assert dateSelect != null : "fx:id=\"dateSelect\" was not injected: check your FXML file 'Untitled'.";
        assert selectButton != null : "fx:id=\"selectButton\" was not injected: check your FXML file 'Untitled'.";
        assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'Untitled'.";
        assert busNumbersLabel != null : "fx:id=\"busNumbersLabel\" was not injected: check your FXML file 'Untitled'.";

    }
}
