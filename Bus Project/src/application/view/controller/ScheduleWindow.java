package application.view.controller;			// The package that the class is in

/*
 * Import Section
 */
import java.io.IOException;			// Used to catch input and output errors
import java.net.URL;		// Used to send http requests
import java.util.Optional;		// Used to handle popups
import java.util.ResourceBundle;	// Tracks the locations of windows in storage

import javax.xml.parsers.ParserConfigurationException;		// Used for parser error catching
import javax.xml.xpath.XPathExpressionException;		// Used for file transforming exception

import org.xml.sax.SAXException;		// Used for file writing errors

import application.Main;		// Has all of the methods for the program
import application.model.Trip;		// Used for the trip class for object handling
import javafx.collections.FXCollections;		// Used to make observable array lists
import javafx.collections.ObservableList;	// An array list compatible with javaFX
import javafx.event.ActionEvent;		// Used to handle basic event
import javafx.fxml.FXML;		// Used for FXML file handling
import javafx.fxml.FXMLLoader;	// Used to load FXML files 
import javafx.fxml.Initializable;	// Used to initialize windows with data
import javafx.scene.Scene;		// Used for the base scene
import javafx.scene.control.Alert;		// Used for easy pop up windows
import javafx.scene.control.Button;		// Used for button GUI handling
import javafx.scene.control.ButtonType;		// Used to control popup interaction
import javafx.scene.control.ChoiceBox;		// Used for a preset choice drop down list
import javafx.scene.control.Label;		// Used for text label handling
import javafx.scene.control.ListView;	// Used for list of items
import javafx.scene.control.ScrollPane;		// Makes the list view a scrollable pane
import javafx.scene.control.TextArea;		// Area for messages 
import javafx.scene.control.Alert.AlertType;		// Used for customizing alerts 
import javafx.scene.layout.AnchorPane;		// The base pane or windows
import javafx.stage.Stage;			// The base stage for the panes to be outputted to

public class ScheduleWindow extends Main implements Initializable
{
	/*
	 * GUI element variables
	 */
	@FXML
	private ResourceBundle resources;		// Location of FXML in computer
	
    @FXML
    private URL location;					// Location in storage of FXML
    
	@FXML
	private Label sizeLabel;			// Label for the group size

	@FXML
	private Label idLabel;			// label for the ID

	@FXML
	private Label departLabel;		// Label for the date of departure

	@FXML
	private ScrollPane selector;		// Scroll pane to display all current trips

	@FXML
	private Label arriveLabel;		// Label for the date of return

	@FXML
	private Label busNumbersLabel;		// Label for the bus numbers
	
	@FXML
	private Label nameLabel;		// Label for customers name
	   
	@FXML
	private Label costLabel;		// Label for trips cost
	
	@FXML 
    private Button homeButton;		// Button to return home
	
	@FXML
	private Button editPerson;			// Button to edit the customers trip

	@FXML
	public ListView<String> customerSelect;		// List of all customers placed on top of scroll pane
	
	@FXML
	private ChoiceBox <String> sortSelect;		// Choice box to select type of sort

	@FXML
    private Button loadSelectButton;		// Button to load the selected customer
	
	@FXML
	private Button sortButton;			// Button to sort all of the trips by the selected means
	
	/*
	 * NOT FINISHED
	 */
	@FXML
	private TextArea tripNotes;			// Text area for trip notes
	
	ObservableList<String> sortItems = FXCollections.observableArrayList();	  // Observable list for sorted items
	
	@FXML
    void editPersonInfo(ActionEvent event) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException
	/*
	 * Precondition:  User clicks on the edit button
	 * Postcondition: The user is brought to a window to edit all the customers data
	 */
	{
		/*
		 * Loads ands displays the edit window info
		 */
		Stage stage;
		AnchorPane root;
		stage = (Stage) editPerson.getScene().getWindow();
							
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/EditInfoWindow.fxml"));
		root = (AnchorPane) loader.load(); 
		/*
		 * Iterates through the list with one object
		 */
		for (Trip trp: getSpecificTrip(customerSelect.getSelectionModel().getSelectedItem()))
    	{
			/*
			 * initializes all data
			 */
			EditWindowController controller = loader.<EditWindowController>getController();
			controller.setInfo(trp.getName(), trp.getDepart(), 
							   trp.getArrive(), trp.getGroupSize(), trp.getTripDestination());
			
			deleteTrip(trp.getName());
    	}
				
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
	
    @FXML
    void loadSelect(ActionEvent event) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException 
    /*
     * Precondition:  If user clicks on the select button
     * Postcondition: Shows the data for the selected trip
     */
    { 	
    	/*
    	 * Iterates through list with the selected trip
    	 */
    	for (Trip trp: getSpecificTrip(customerSelect.getSelectionModel().getSelectedItem()))
    	{
    		/*
    		 * Sets all the labels with the trips data
    		 */
    		nameLabel.setText(trp.getName());
    		idLabel.setText(trp.getId());
    		departLabel.setText(trp.getDepart().format(mdy));
    		arriveLabel.setText(trp.getArrive().format(mdy));
    		sizeLabel.setText(Integer.toString(trp.getGroupSize()));
    		busNumbersLabel.setText(trp.getBusNumbers());
    		costLabel.setText("$ " + String.format("%.2f", trp.getTripCost()));
    	}	 
    	
    	editPerson.setDisable(false);		// Disables the edit person button
    }
    
    @FXML
    void sortAction(ActionEvent event) throws ParserConfigurationException, SAXException, IOException
    /*
     * Precondition:  User clicks on the sort button
     * Postcondition: The list view is sorted according to the selected sort 
     */
    {
    	String temp = sortSelect.getValue();	// Temporary value for the value of the selected sort
    	
    	/*
    	 * If alphabetical is selected
    	 */
    	if (temp == "Name A-Z")
    	{
    		customerSelect.setItems(getAllNames(fetchCurrentXML(), 1));
    	}
    	/*
    	 * If opposite of alphabetical is selected
    	 */
    	else if (temp == "Name Z-A")
    	{
    		customerSelect.setItems(getAllNames(fetchCurrentXML(), 2));
    	}
    	/*
    	 * If group size descending is selected
    	 */
    	else if (temp == "Group Size Descending")
    	{
    		customerSelect.setItems(getAllNames(fetchCurrentXML(), 3));
    	}
    	/*
    	 * If group size ascending is selected
    	 */
    	else if (temp == "Group Size Ascending")
    	{
    		customerSelect.setItems(getAllNames(fetchCurrentXML(), 4));
    	}
    	/*
    	 * Else no sort preference
    	 */
    	else
    	{
    		customerSelect.setItems(getAllNames(fetchCurrentXML(), 0));
    	}
    }
	    
	@FXML
	void returnMain(ActionEvent event) throws IOException 
	/*
	 * Precondition:  User clicks on the home button
	 */
	{
		/*
		 * Loads the main menu and then displays it
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
		assert nameLabel != null : "fx:id=\"nameLabel\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert departLabel != null : "fx:id=\"departLabel\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert arriveLabel != null : "fx:id=\"arriveLabel\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert idLabel != null : "fx:id=\"idLabel\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert sizeLabel != null : "fx:id=\"sizeLabel\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert editPerson != null : "fx:id=\"editPerson\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert tripNotes != null : "fx:id=\"tripNotes\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert loadSelectButton != null : "fx:id=\"loadSelectButton\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert customerSelect != null : "fx:id=\"customerSelect\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert sortButton != null : "fx:id=\"sortButton\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert sortSelect != null : "fx:id=\"sortSelect\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert costLabel != null : "fx:id=\"costLabel\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert busNumbersLabel != null : "fx:id=\"busNumbersLabel\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	/*
	 * Precondition:  User wants to open the schedule window
	 * Postcondition: The window is populated with all the data before it is displayed
	 */
	{
		/*
		 * Initialize all elements
		 */
		sortItems.add("Name A-Z");
		sortItems.add("Name Z-A");
		sortItems.add("Group Size Descending");
		sortItems.add("Group Size Ascending");
		sortSelect.setItems(sortItems);
		
		/*
		 * Try to fetch XML
		 */
		try 
		{
			customerSelect.setItems(getAllNames(fetchCurrentXML(), 0));
			
			/*
			 * If ther eare no current trips
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
				 * If user clicks on ok button
				 */
				if (result.get() == ButtonType.OK)
				{
					alert.close();		// Closes the alert
				}
				/*
				 * Else user clicks on cancel button
				 */
				else
				{
					alert.close();		// Closes the alert
				}
			}
		} 
		/*
		 * Catch all exceptions
		 */
		catch (ParserConfigurationException | SAXException | IOException e) 
		{
			e.printStackTrace();
		}
	}
}