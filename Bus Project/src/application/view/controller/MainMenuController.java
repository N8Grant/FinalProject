package application.view.controller;		// Package that the class is contained in

/*
 * Import Section
 */
import java.io.IOException;			//  Exception for input and output
import java.util.Optional;			// Used for popup handling

import javax.xml.parsers.ParserConfigurationException;	// Exception for bad parser handling

import org.xml.sax.SAXException;		// Exception for writer error

import application.Main;		// Import for the main classes methods
import javafx.event.ActionEvent;	// Import for JavaFX event handling
import javafx.fxml.FXML;	// Import for FXML file format
import javafx.fxml.FXMLLoader;	// Import for FXML loaders
import javafx.scene.Scene;		// Used for the base scene
import javafx.scene.control.Alert;	// Used for easy pop up windows
import javafx.scene.control.Alert.AlertType;	// Used for customizing alerts
import javafx.scene.control.Button;		// Used for button GUI handling
import javafx.scene.control.ButtonType;		// Used to control popup interaction
import javafx.scene.layout.AnchorPane;	// The base pane or windows
import javafx.stage.Stage;		// The base stage for the panes to be outputted to


public class MainMenuController extends Main
{
	/*
	 * GUI Element Variables
	 */
	@FXML
	private Button bookTrip;		// Button for user to book trip

	@FXML
	private Button quitButton;		// Button for user to terminate program

	@FXML
	private Button checkSchedule;	// Button to check bus schedule

	@FXML
	private Button busManagement;	// Button to manage company
	    	
	@FXML
	void bookTrip(ActionEvent event) throws IOException 
	/*
	 * Precondition:  User clicks on the book trip button
	 * Postcondition: User is sent to the book trip window
	 */
	{
		/*
		 *  Loads the book trip window and displays it
		 */
	    Stage stage;
	    AnchorPane root;
	    stage = (Stage) bookTrip.getScene().getWindow();
				
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(Main.class.getResource("view/BookTrip.fxml"));
	    root = (AnchorPane) loader.load();   
	   
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
	}
	 
	@FXML
	void checkSchedule(ActionEvent event) throws IOException, ParserConfigurationException, SAXException
	/*
	 * Precondition:  User clicks on the schedule button
	 * Postcondition: The check schedule window is loaded if there are trips to be 
	 * 				  displayed
	 */
	{
		/*
		 * If there are no current trips
		 */
		if (fetchCurrentXML().isEmpty())
		{
			/*
	    	 * Alert to show finalize success
	    	 */
	    	Alert alert = new Alert(AlertType.ERROR);
	    	alert.setTitle("Error Dialog");
			alert.setHeaderText("No Trips");
			alert.setContentText("There are no current trips to view, book a trip to continue!!");
			Optional<ButtonType> result = alert.showAndWait();
			
			/*
			 * If user clicks on the ok button
			 */
			if (result.get() == ButtonType.OK)
			{
				alert.close();		// closes the alert
			}
			/* 
			 * Else user clicks on the cancel button
			 */
			else
			{
				alert.close();		// closes the alert
			}
		}
		/*
		 *  Else there are current trips
		 */
		else
		{
			/*
			 * Loads the schedule window and displays it to the screen
			 */
			Stage stage;
			AnchorPane root;
			stage = (Stage) busManagement.getScene().getWindow();
			 		
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/ScheduleWindow.fxml"));
			root = (AnchorPane) loader.load();  
			 
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}
	 
	@FXML
	void manageBusses(ActionEvent event) throws IOException  
	/*
	 * Precondition:  User clicks on the manage busses button
	 * Postcondition: The manage busses window is loaded and
	 * 				  displayed to the screen
	 */
	{ 
		/*
		 * Loads the manage window and displays it to the screen
		 */
		Stage stage;
		AnchorPane root;
		stage = (Stage) busManagement.getScene().getWindow();
					
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/BusManagement.fxml"));
		root = (AnchorPane) loader.load();  
		 
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	 
	@FXML
	void exitProgram(ActionEvent event)
	/*
	 * Precondition:  User clicks on the exit program button
	 * Postcondition: If the user accepts the popup then the program terminates
	 */
	{
		/*
		 * Makes a new pop-up dialog box
		 */
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Quit Program");
		alert.setContentText("Are you sure you want to exit??");
		Optional<ButtonType> result = alert.showAndWait();
		
		/*
		 * If user clicks on the ok button
		 */
		if (result.get() == ButtonType.OK)
		{
			System.exit(0);		// Terminates the wndow
		} 
		/*
		 * Else user clicks on cancel
		 */
		else 
		{
		    alert.close();		// Alert is closed
	    }
	}
	  
	@FXML
	private void initialize() 
	 /*
     * Precondition:  There is an error injecting the GUI elements
     * Postcondition: Error message is spit out
     */
	{
	     assert bookTrip != null : "fx:id=\"bookTrip\" was not injected: check your FXML file 'MainMenu.fxml'.";
	     assert quitButton != null : "fx:id=\"quitButton\" was not injected: check your FXML file 'MainMenu.fxml'.";
	     assert checkSchedule != null : "fx:id=\"checkSchedule\" was not injected: check your FXML file 'MainMenu.fxml'.";
	     assert busManagement != null : "fx:id=\"finances\" was not injected: check your FXML file 'MainMenu.fxml'.";
	}
}

