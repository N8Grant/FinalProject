package application.view.controller;	// Package that the class is contained in 

/*
 * Import Section
 */
import java.io.IOException;		// Exception for input and output
import java.net.URL;		// Import for use if URL connection
import java.util.ResourceBundle;	// Used for the location of FXML files

import javax.xml.parsers.ParserConfigurationException;	// Exception for parser error

import org.xml.sax.SAXException;	// Exception for writer error

import application.Main;	// Import for use of function in main
import javafx.event.ActionEvent;	// Used for basic action of buttons
import javafx.fxml.FXML;		// Used for FXML handling
import javafx.fxml.FXMLLoader;		// Used to load FXML files
import javafx.scene.Scene;		// The scene which the window is outputted to
import javafx.scene.control.Button;		// Button GUI element
import javafx.scene.control.RadioButton;	// Button which can be selected
import javafx.scene.control.ToggleGroup;	// Groups all toggle buttons together
import javafx.scene.layout.AnchorPane;		// Base pane used for GUI
import javafx.stage.Stage;	// Import for screen manipulation

public class BusManageController extends Main
{
	/*
	 * GUI element variables
	 */
    @FXML
    private ResourceBundle resources;		// Controller Resources

    @FXML
    private URL location;					// Location in storage

    @FXML
    private ToggleGroup busView;			// The group of toggle buttons

    @FXML
    private RadioButton completedTrips;		// Toggle button for completed trips
    
    @FXML 
    private Button cancelButton;			// Button to return to main menu
    
    @FXML
    private Button viewBusInfo;				// Button to select the currently chosen radio button
    		
    @FXML
    private RadioButton checkAvailable;		// Toggle button for available button
    
    @FXML
    private RadioButton bussesOut;			// Toggle button for busses that are out
   
    @FXML
    private RadioButton finances;			// Toggle button for income statement or 
    										// balance sheet
    @FXML
    void returntoMain(ActionEvent event) throws IOException 
    /*
     * Precondition:  User clicks on the home button
     * Postcondition: Returns to main menu if user confirms the 
     * 				  warning popup
     */
    {
    	/*
    	 * Loads new stage and window
    	 */
    	Stage stage;
		AnchorPane root;
		stage = (Stage) cancelButton.getScene().getWindow();
					
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/MainMenu.fxml"));
		root = (AnchorPane) loader.load();   
			
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
    
    @FXML
    void displayBusInfo(ActionEvent event) throws IOException, ParserConfigurationException, SAXException 
    /*
     * Precondition:  User clicks on view
     * Postcondition: Displays teh selected window
     */
    {
    	/*
    	 * If completed trips is selected
    	 */
    	if(completedTrips.isSelected())
    	{
    		/*
    		 * Loads the completed trips window
    		 */
    		Stage stage;
    		AnchorPane root;
    		stage = (Stage) viewBusInfo.getScene().getWindow();
    							
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(Main.class.getResource("view/BussesOutstanding.fxml"));
    		root = (AnchorPane) loader.load();
    		BussesOutstandingController controller = loader.<BussesOutstandingController>getController();
    		controller.dateLabel.setText("No date selected!!");			
    		Scene scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    	}
    	/*
    	 * Else if finances is selected
    	 */
    	else if (finances.isSelected())
    	{
    		/*
    		 * Loads finance window
    		 */
    		Stage stage;
    		AnchorPane root;
    		stage = (Stage) viewBusInfo.getScene().getWindow();
    							
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(Main.class.getResource("view/FinancesWindow.fxml"));
    		root = (AnchorPane) loader.load();
    		FinanceWindow controller = loader.<FinanceWindow>getController();
    		controller.setValues("$ " + String.format("%.2f", getRevenue(0)), getPieChart(0));			
    		Scene scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    	}
    	/*
    	 * Else if check available is selected
    	 */
    	else if (checkAvailable.isSelected())
    	{
    		/*
    		 * Loads available busses window
    		 */
    		Stage stage;
    		AnchorPane root;
    		stage = (Stage) viewBusInfo.getScene().getWindow();
    							
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(Main.class.getResource("view/AvailableBussesWndow.fxml"));
    		root = (AnchorPane) loader.load();
    		Scene scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    	}
    }

    @FXML
    void initialize() 
    /*
     * Precondition:  There is an error injecting the GUI elements
     * Postcondition: Error message is spit out
     */
    {
        assert busView != null : "fx:id=\"busView\" was not injected: check your FXML file 'BusManagement.fxml'.";
        assert viewBusInfo != null : "fx:id=\"viewBusInfo\" was not injected: check your FXML file 'BusManagement.fxml'.";
        assert bussesOut != null : "fx:id=\"bussesOut\" was not injected: check your FXML file 'BusManagement.fxml'.";
        assert finances != null : "fx:id=\"finances\" was not injected: check your FXML file 'BusManagement.fxml'.";
        assert completedTrips != null : "fx:id=\"completedTrips\" was not injected: check your FXML file 'BusManagement.fxml'.";
    }
}
