package application.view.controller;		// Package that the class is in

/*
 * Import Section
 */
import java.io.IOException;			//  Exception for input and output
import java.net.MalformedURLException;		// Exception for bad URL connection
import java.net.URL;				// Import for use with URL connection
import java.time.LocalDate;			// Used for LocalDate class to handle date
import java.util.Optional;			// Used for popup handling
import java.util.ResourceBundle;	// Used for location of windows in memory

import javax.xml.parsers.ParserConfigurationException;	// Exception for bad parser handling
import javax.xml.transform.TransformerException;	// Exception for transformer error

import org.controlsfx.control.textfield.TextFields;		// Textfield hander
import org.xml.sax.SAXException;		// Exception for writer error

import application.Main;		// Import for the main classes methods
import application.model.Trip;	// Import for user class
import javafx.event.ActionEvent;	// Import for JavaFX event handling
import javafx.fxml.FXML;	// Import for FXML file format
import javafx.fxml.FXMLLoader;	// Import for FXML loaders
import javafx.scene.Scene;		// Used for the base scene
import javafx.scene.control.Alert;	// Used for easy pop up windows
import javafx.scene.control.Alert.AlertType;	// Used for customizing alerts
import javafx.scene.control.Button;		// Used for button GUI handling
import javafx.scene.control.ButtonType;		// Used to control popup interaction
import javafx.scene.control.DatePicker;		// Used for date picker compatibility
import javafx.scene.control.Label;		// Used for text label handling
import javafx.scene.control.TextField;	// Used for text field information handling
import javafx.scene.input.KeyEvent;		// Handles user key strikes
import javafx.scene.layout.AnchorPane;	// The base pane or windows
import javafx.stage.Stage;		// The base stage for the panes to be outputted to


public class EditWindowController extends Main
{
	/*
	 * GUI Elements
	 */
	@FXML
	private ResourceBundle resources;		// Location of FXML in computer
	
    @FXML
    private URL location;					// Location in storage of FXML

    @FXML
    private TextField inputName;			// Input box for customer / organization name

    @FXML
    private DatePicker inputDepart;			// Date picker for departure date

    @FXML
    private DatePicker inputReturn;			// input box for date of return

    @FXML	
    private TextField inputNumPeople;		// Input box for number of people

    @FXML
    private Button commitChanges;			// Button to apply the changes made
    
    @FXML
    private Button deleteTrip;				// Button to delete the selected trip

    @FXML
    private Label nameError;			// Label for name error

    @FXML
    private Label peopleError;			// Error label for the number of people

    @FXML
    private Label returnError;			// Error for the return date

    @FXML
    private Label departError;			// Error for the depart date
    
    @FXML
    private Label destinationError;		// Error for the destination name

    @FXML
    private TextField destinationName;		// TextField to input destination name

    /*
     * Temp Variables
     */
	private String name;		// Temp variable for customer name
	private LocalDate depart;	// Temp variable for depart date
	private LocalDate arrive;	// Temp variable for return date
	private int groupSize;		// Temp variable for group size
	private String destination;	// Temp variable for the destination name

	@FXML
	void deleteTrip(ActionEvent event) throws IOException 
	/*
	 * Precondition:  User clicks on the delete trip button
	 * Postcondition: The selected trip is deleted if user confirms popup
	 */
	{
		/*
    	 * Alert to show delete customer
    	 */
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Confirm Dialog");
		alert.setContentText("Are you sure you want to delete this trip?");
		Optional<ButtonType> result = alert.showAndWait();
		
		/*
		 * If User confrims pop-up dialog box
		 */
		if (result.get() == ButtonType.OK)
		{
			/*
			 * Loads the schedule window
			 */
			Stage stage;
			AnchorPane root;
			stage = (Stage) deleteTrip.getScene().getWindow();	
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/ScheduleWindow.fxml"));
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
    void updateOptions(KeyEvent event) throws MalformedURLException, ParserConfigurationException, SAXException, IOException, TransformerException 
	/*
	 * Precondition:  The user presses a button on their keyboard
	 * Postcondition: The suggestion box is updated in response to the users input
	 */
	{
		TextFields.bindAutoCompletion(destinationName, getDestinationChoices(destinationName.getText()));
    }
	
    @FXML
    void commitChanges(ActionEvent event) throws IOException, ParserConfigurationException, SAXException, TransformerException 
    /*
     * Precondition:  User clicks on the commit changes button
     * Postcondition: The changes are made to the trip if they meet certain criteria and 
     * 				  if all fields are filled out
     */
    {
    	/*
		 * Resets all values and errors
		 */
		Boolean in = false;
		Boolean ip = false;
		Boolean ir = false;
		Boolean id = true;
		Boolean idest = false;
		peopleError.setText("");
		nameError.setText("");
		returnError.setText("");
		departError.setText("");
		destinationError.setText("");
		
		/*
		 * If Statement if all fields are filled out
		 */
		if (inputName.getText() != null || inputDepart.getValue() != null || 
			inputReturn.getValue() != null || inputNumPeople.getText() != null ||
			destinationName.getText() != null)
		{
			/*
			 * If the number entered is less than 0
			 */
			if (Integer.parseInt(inputNumPeople.getText()) <= 0)
			{
				peopleError.setText("Enter number greater than 0!");
			}
			/*
			 * Else field is valid
			 */
			else
			{
				ip = true;
				groupSize = Integer.parseInt(inputNumPeople.getText());
			}
			
			/*
			 * If date is before todays date
			 */
			if (inputDepart.getValue().isBefore(LocalDate.now()))
			{
				departError.setText("Enter a date after " + LocalDate.now().toString());
			}
			/*
			 * If return is before depart
			 */
			else if (inputReturn.getValue().isBefore(inputDepart.getValue()))
			{
				returnError.setText("Enter a date after " + inputDepart.getValue());
			}
			/*
			 * Else field is valid
			 */
			else 
			{
				depart = inputDepart.getValue();
				
				arrive = inputReturn.getValue();
				ir = true;
				id = true;
			}
			
			/*
			 * If name has been used
			 */
			if (checkName(inputName.getText()) == true ||
				checkName(inputName.getText()) == null)
			{
				nameError.setText("Name already used!!");
				in = false;
			}
			// Else field is valid
			else
			{
				in = true;
				name = inputName.getText();	
			}
			
			idest = true;
			destination = destinationName.getText();
		}
		
		/*
		 * If some fields are left empty
		 */
		else if (inputName.getText() == null || inputDepart.getValue() == null || 
				 inputReturn.getValue() == null || inputNumPeople.getText() == null)
		{
			/*
			 * If name is empty
			 */
			if (inputName.getText() == null)
			{
				nameError.setText("Field cant be empty!!");
			}
			/*
			 * If depart isn't selected
			 */
			if (inputDepart.getValue() == null)
			{
				departError.setText("Field cant be empty!!");
			}
			/*
			 * If return isn't selected
			 */
			if (inputReturn.getValue() == null)
			{
				returnError.setText("Field cant be empty!!");
			}
			/*
			 * If number of people is selected
			 */
			if (inputNumPeople.getText() == null)
			{
				peopleError.setText("Field cant be empty!!");
			}
			if (destinationName.getText() == null)
			{
				destinationError.setText("Feild cant be empty!!");
			}
		}
		
		/*
		 * The program must get a value for the bus numbers before the user
		 * can continue because there may be no busses left
		 */
		String bsNms = null;	// Bus numbers is set to null unless change otherwise
		
		/*
		 * If all fields are correctly filled out
		 */
		if (in == true && ip == true && ir == true && id == true)
		{
			bsNms = getBusses(depart, arrive, groupSize);		// Gets the bus numbers
		}
		
		/*
		 * If all of the vales are acceptable
		 */
		if (in == true && ip == true && ir == true && id == true && bsNms != null)
		{	
			/*
	    	 * Alert to show finalize success
	    	 */
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setTitle("Confirmation Dialog");
			alert.setHeaderText("Confirm Dialog");
			alert.setContentText("Are you sure you want to modify this trip?");
			Optional<ButtonType> result = alert.showAndWait();
			
			/*
			 * If user clicks on the ok button
			 */
			if(result.get() == ButtonType.OK)
			{
				tripData.clear();		// Clears the trip list so trips arent saved more than once
				
				/*
				 * Gets values for the trip distance and cost
				 */
				double distance = getTripDistance(destination);
				double tripCost = getTripCost(groupSize, distance);
				
				/*
				 * Adds the new information to the observable list
				 * @param Name
				 * @param size
				 * @param depart
				 * @param arrive
				 */
				tripData.add(new Trip (name, groupSize, depart, arrive, bsNms,
									   tripCost, distance, destination));
				
				/*
				 * Marshals data to an XML file
				 */
				addToXML(tripData);
				
				/*
				 * Loads the checkout window
				 */
				Stage stage;
				AnchorPane root;
				stage = (Stage) commitChanges.getScene().getWindow();	
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("view/ScheduleWindow.fxml"));
				root = (AnchorPane) loader.load();   
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}
			/*
			 * Else user clicks on close
			 */
			else
			{
				alert.close();		// Closes the popup
			}
		}
    }

    @FXML
    void initialize() 
    /*
     * Precondition:  There is an error injecting the GUI elements
     * Postcondition: Error message is spit out
     */
    {
        assert inputName != null : "fx:id=\"inputName\" was not injected: check your FXML file 'EditInfoWindow.fxml'.";
        assert inputDepart != null : "fx:id=\"inputDepart\" was not injected: check your FXML file 'EditInfoWindow.fxml'.";
        assert inputReturn != null : "fx:id=\"inputReturn\" was not injected: check your FXML file 'EditInfoWindow.fxml'.";
        assert inputNumPeople != null : "fx:id=\"inputNumPeople\" was not injected: check your FXML file 'EditInfoWindow.fxml'.";
        assert commitChanges != null : "fx:id=\"continueCheckout\" was not injected: check your FXML file 'EditInfoWindow.fxml'.";
        assert nameError != null : "fx:id=\"nameError\" was not injected: check your FXML file 'EditInfoWindow.fxml'.";
        assert peopleError != null : "fx:id=\"peopleError\" was not injected: check your FXML file 'EditInfoWindow.fxml'.";
        assert returnError != null : "fx:id=\"returnError\" was not injected: check your FXML file 'EditInfoWindow.fxml'.";
        assert departError != null : "fx:id=\"departError\" was not injected: check your FXML file 'EditInfoWindow.fxml'.";
        assert destinationName != null : "fx:id=\"destinationName\" was not injected: check your FXML file 'EditInfoWindow.fxml'.";
        assert destinationError != null : "fx:id=\"destinationError\" was not injected: check your FXML file 'EditInfoWindow.fxml'.";
    }
    
    public void setInfo (String nm, LocalDate dpt, LocalDate ret, int grpSz, String dest)
    /*
     * Precondition:  User wanted to modify a customer
     * Postcondition: Window is filled with given data
     */
    {
    	/*
    	 * Sets all variables 
    	 */
    	name = nm;
    	depart = dpt;
    	arrive = ret;
    	groupSize = grpSz;
    	
    	/*
    	 * Pre fills all of the text boxes to the 
    	 * values of the current trip
    	 */
    	inputName.setText(nm);
    	inputReturn.setValue(ret);
    	inputNumPeople.setText(Integer.toString(grpSz));
    	destinationName.setText(dest);
    	
    	/*
    	 * If the date of departure is before todays date
    	 */
    	if (dpt.isBefore(LocalDate.now()))
    	{
    		/*
    		 * Makes it so the depart date is un-editable
    		 */
    		inputDepart.setEditable(false);
    	}
    	/*
    	 * Else it is after todays date
    	 */
    	else
    	{
    		inputDepart.setValue(dpt);		
    	}
    }
}    