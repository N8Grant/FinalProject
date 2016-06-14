package application.view.controller;		// Package that the class is contained in

/*
 * Import Section
 */
import java.io.IOException;		//  Exception for input and output
import java.net.URL;		// Import for use with URL connection
import java.util.ResourceBundle;		// Used for location of windows in memory

import javax.xml.parsers.ParserConfigurationException;	// Exception for bad parser handling

import org.xml.sax.SAXException;	// Exception for writer error

import application.Main;		// Import for the main classes methods
import application.model.Trip;		// Import for user class
import javafx.event.ActionEvent;	// Import for JavaFX event handling
import javafx.fxml.FXML;		// Import for FXML file format
import javafx.fxml.FXMLLoader;		// Import for FXML loaders
import javafx.scene.Scene;			// Used for the base scene
import javafx.scene.control.Button;		// Used for button GUI handling
import javafx.scene.control.DatePicker;		// Used for date picker compatibility
import javafx.scene.control.Label;		// Used for text label handling
import javafx.scene.layout.AnchorPane;		// The base pane or windows
import javafx.scene.layout.GridPane;		// Used for grid pane handling
import javafx.scene.paint.Color;			// Used to change text color
import javafx.stage.Stage;		// The base stage for the panes to be outputted to

public class BussesOutstandingController extends Main
{
	/*
	 * GUI Elements
	 */
    @FXML
    private ResourceBundle resources;		// Location of FXML in computer

    @FXML
    private URL location;			// Location in storage of FXML

    @FXML 
    public Label dateLabel;		// The label for the date picked
    
    @FXML
    private Label dateError;		// The error for the date picker

    @FXML
    private GridPane displayGrid;		// Grid for all of the information

    @FXML
    private DatePicker dateSelect;		// Date picker for user to select date

    @FXML	
    private Button viewTrips;		// Button to apply date entered

    @FXML
    private Button homeButton;		// Button to return to main menu
    
    /*
     * Temporary Labels for the Grid Pane
     */
    Label nameLabel;		// Temp Label for name 
    Label idLabel;			// Temp label for ID
    Label busNumLabel;		// Temp label for the bus numbers

    @FXML
    void displayTrips(ActionEvent event) throws ParserConfigurationException, SAXException, IOException 
    /*
     * Precondition:  User clicks on the display trips button
     * Postcondition: All of the trips on the given day, present or past, are displayed
     */
    {
    	/*
    	 * If no date is selected
    	 */
    	if (dateSelect.getValue() == null)
    	{
    		dateError.setText("Enter a date!!");
    	}
    	/*
    	 * Else the user enters a date
    	 */
    	else
    	{
    		/*
    		 * Tries to delete all elements in the display grid
    		 */
    		displayGrid.getChildren().removeAll(nameLabel);
    		displayGrid.getChildren().removeAll(idLabel);
    		displayGrid.getChildren().removeAll(busNumLabel);
    		dateLabel.setText("Date: " + dateSelect.getValue().toString());
    		
    		int r = 0;		// Int to track the row to display trips in
    		
    		/*
    		 * If there are no trips on a date
    		 */
    		if (getTripsOnDate(dateSelect.getValue()).isEmpty())
    		{
    			dateError.setText("No trips on this date!!");
    		}
    		/*
    		 * Else there are trips 
    		 */
    		else
    		{
    			/*
    			 * Iterates through all trips on given date
    			 */
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
    }

    @FXML
    void returnHome(ActionEvent event) throws IOException 
    /*
     * Precondition:  User clicks on the home button
     * Postcondition: The user is returned to the home 
     * 			      menu if they accept the popup
     */
    {
    	/*
    	 * Loads and sets the  home window
    	 */
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
    void initialize() 
    /*
     * Precondition:  There is an error injecting the GUI elements
     * Postcondition: Error message is spit out
     */
    {
        assert dateLabel != null : "fx:id=\"dateLabel\" was not injected: check your FXML file 'BussesOutstanding.fxml'.";
        assert displayGrid != null : "fx:id=\"displayGrid\" was not injected: check your FXML file 'BussesOutstanding.fxml'.";
        assert dateSelect != null : "fx:id=\"dateSelect\" was not injected: check your FXML file 'BussesOutstanding.fxml'.";
        assert viewTrips != null : "fx:id=\"viewTrips\" was not injected: check your FXML file 'BussesOutstanding.fxml'.";
        assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'BussesOutstanding.fxml'.";
        assert dateError != null : "fx:id=\"dateError\" was not injected: check your FXML file 'BussesOutstanding.fxml'.";
    }
}
