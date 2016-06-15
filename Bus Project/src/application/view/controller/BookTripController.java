package application.view.controller;	// Package that the class is contained in

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

public class BookTripController extends Main
{
	/*
	 * Declares all of the GUI elements
	 * @FXML
	 */
	@FXML
	private ResourceBundle resources;		// Location of FXML in computer
	
    @FXML
    private URL location;					// Location in storage of FXML
    
    @FXML
    private Label destinationError;			// Error message for destination
    
	@FXML
	private TextField inputNumPeople;		// Input box for number of people
	
	@FXML
	private Label nameError;				// Error message under name box
	
	@FXML
	private Button continueCheckout;		// Button to continue to next window
	
	@FXML
	private Label returnError;				// Error under return date picker
	
	@FXML
    private Button Return;					// Button to return to main menu
	
	@FXML
    private TextField destinationName;		// TextField for the name of the trips destination
	
	@FXML
	private DatePicker inputDepart;			// Date picker for departure date
	
	@FXML
	private Label departError;				// Error under depart date picker
	
	@FXML
	private Label peopleError;				// Error under number people box
	
	@FXML
	private TextField inputName;			// Input box for customer / organization name
	
	@FXML
	private DatePicker inputReturn;			// input box for date of return

	/******************/
	/* Temp Variables */
	/******************/
	public String orgName;		// String for name to be passes to next window
	public LocalDate  dpt;		// LocalDate for depart to be passed to the next window
	public LocalDate  arr;		// LocalDate for return to be passed to the next window
	public int  grpSz;			// Int for group size to be passed to the next window
	public String destination;	// String for the destination of the trip to be passed 			
								// to the next window
	
	/*
	 * GUI Controllers
	 */   
	@FXML
	void returntoMain(ActionEvent event) throws IOException 
	/*
	 * Precondition:  User clicks on the home button
	 * Postcondition: User is sent to the home page if they confirm their action
	 */
	{
		/*
		 * Makes a new pop-up dialog box
		 */
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Cancel Transaction");
		alert.setContentText("Are you sure you want to stop this transaction??");
		Optional<ButtonType> result = alert.showAndWait();
		 
		/*
		 * If statement to cancel transaction
		 */
		if (result.get() == ButtonType.OK)
		{
			/* 
			 * Loads the main menu again
			 */
			Stage stage;
			AnchorPane root;
			stage = (Stage) Return.getScene().getWindow();			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MainMenu.fxml"));
			root = (AnchorPane) loader.load();   	
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} 
		else 
		{ 
		    alert.close();	   // closes the alert if cancel is pressed
		}		
	}
	
	@FXML
    void updateSuggestions(KeyEvent event) throws MalformedURLException, ParserConfigurationException, SAXException, IOException, TransformerException
    /*
     * Precondition:  User types in the destination box
     * Postcondition: The Suggestion box is updated with possible suggestions 
     */
	{
		TextFields.bindAutoCompletion(destinationName, getDestinationChoices(destinationName.getText()));
    }

	 
	@FXML
	void openCheckout(ActionEvent event) throws IOException, SAXException, ParserConfigurationException, TransformerException
	/*
	 * Precondition:  User clicks on the confirm button
	 * Postcondition: If all requirements are filled out then it goes to the checkout window
	 */
	{
		/*
		 * Resets all values and fields
		 */
		Boolean in = false;
		Boolean ip = false;
		Boolean ir = false;
		Boolean id = false;
		Boolean ides = false;
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
			Boolean temp = true;		// Temp variable if string can be converted
			
			/*
			 * Try to convert group size to int
			 */
			try
			{
				grpSz = Integer.parseInt(inputNumPeople.getText());
			}
			/*
			 * Catch formating error
			 */
			catch (NumberFormatException e)
			{
				temp = false;
				peopleError.setText("Must input a number!!");
			}
			
			/*
			 * If the number entered is less than 0
			 */
			if (grpSz <= 0 && temp == true)
			{
				peopleError.setText("Enter number greater than 0!");
			}
			/*
			 * Else if user entered number below 10
			 */
			else if ((grpSz > 0) && (grpSz < 10) && temp == true)
			{
				peopleError.setText("Enter number greater than 10!!");
			}
			/*
			 * Else field is valid
			 */
			else
			{
				ip = true;
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
				dpt = inputDepart.getValue();
				
				arr = inputReturn.getValue();
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
			/*
			 * Else field is valid
			 */
			else
			{
				in = true;
				orgName = inputName.getText();	
			}
			
			/*
			 * Destination field is automatically valid if filled out
			 */
			ides = true;
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
			 * If number of people isn't filled out
			 */
			if (inputNumPeople.getText() == null)
			{
				peopleError.setText("Field cant be empty!!");
			}
			/*
			 * If destination name is empty
			 */
			if (destinationName.getText() == null)
			{
				destinationError.setText("Field cant be empty!!");
			}	
		}
		
		String bsNms;	// String of all of the bus numbers for the trip
		
		/*
		 * Gets the bus numbers for the trip before the confirmation 
		 * because it has to be checked for null values where there aren't 
		 * enough busses left for the trip they want to book
		 */
		try
		{
			bsNms = getBusses(dpt, arr, grpSz);
		}
		/*
		 * Catch no data entered exception
		 */
		catch (NumberFormatException e)
		{
			bsNms = null;
		}
		
		/*
		 * If all of the vales are acceptable
		 */
		if (in == true && ip == true && ir == true && id == true && ides == true && bsNms != null)
		{	
			tripData.clear();	// Clears the tripList before values are added
			
			/*
			 * Gets calculated data before adding a new Trip to tripList
			 */
			double distance = getTripDistance(destination);
			grpSz = getRefund(grpSz);
			double tripCost = getTripCost(grpSz, distance);
			
			/*
			 * Adds the new information to the observable list
			 * @param Name
			 * @param size
			 * @param depart
			 * @param arrive
			 */
			tripData.add(new Trip (orgName, grpSz, dpt, arr, bsNms,
								   tripCost, distance, destination));
			
			/*
			 * Adds the trip list to the xml file
			 */
			addToXML(tripData);
			
			/*
			 * Loads the checkout window
			 */
			Stage stage;
			AnchorPane root;
			stage = (Stage) continueCheckout.getScene().getWindow();	
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/CheckoutWindow.fxml"));
			root = (AnchorPane) loader.load();   
			Scene scene = new Scene(root);
			stage.setScene(scene);
			CheckoutController controller = loader.<CheckoutController>getController();
			controller.setInfo(orgName, Integer.toString(grpSz), arr, 
							   dpt, bsNms, tripData.get(0).getId(), distance, destination);
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
		assert inputNumPeople != null : "fx:id=\"inputNumPeople\" was not injected: check your FXML file 'BookTrip.fxml'.";
	    assert nameError != null : "fx:id=\"nameError\" was not injected: check your FXML file 'BookTrip.fxml'.";
	    assert continueCheckout != null : "fx:id=\"continueCheckout\" was not injected: check your FXML file 'BookTrip.fxml'.";
	    assert returnError != null : "fx:id=\"returnError\" was not injected: check your FXML file 'BookTrip.fxml'.";
	    assert Return != null : "fx:id=\"Return\" was not injected: check your FXML file 'BookTrip.fxml'.";
	    assert destinationName != null : "fx:id=\"destinationName\" was not injected: check your FXML file 'BookTrip.fxml'.";
	    assert inputDepart != null : "fx:id=\"inputDepart\" was not injected: check your FXML file 'BookTrip.fxml'.";
	    assert departError != null : "fx:id=\"departError\" was not injected: check your FXML file 'BookTrip.fxml'.";
	    assert peopleError != null : "fx:id=\"peopleError\" was not injected: check your FXML file 'BookTrip.fxml'.";
	    assert inputName != null : "fx:id=\"inputName\" was not injected: check your FXML file 'BookTrip.fxml'.";
	    assert inputReturn != null : "fx:id=\"InputReturn\" was not injected: check your FXML file 'BookTrip.fxml'.";
	    assert destinationError != null : "fx:id=\"destinationError\" was not injected: check your FXML file 'BookTrip.fxml'.";  
	}
}
