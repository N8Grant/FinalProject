package application.view.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import org.xml.sax.SAXException;

import application.Main;
import application.model.Trip;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BookTripController extends Main
{
	// change made
	
	/*
	 * Declares all of the GUI elements
	 * @FXML
	 */
	@FXML
	private ResourceBundle resources;
    @FXML
    private URL location;
	@FXML
	private TextField inputNumPeople;
	@FXML
	private Label nameError;
	@FXML
	private Button continueCheckout;
	@FXML
	private Label returnError;
	@FXML
    private Button Return;
    @FXML
	private TextField destinationName;
    @FXML
	private DatePicker inputDepart;
	@FXML
	private Label departError;
	@FXML
	private Label peopleError;
	@FXML
	private TextField inputName;
	@FXML
	private DatePicker inputReturn;

	/******************/
	/* Temp Variables */
	/******************/
	public String orgName;
	public LocalDate  dpt;
	public LocalDate  arr;
	public int  grpSz;
	
	/*
	 * GUI Controllers
	 */
	@FXML
	void inputNameAction(ActionEvent event) 
	/*
	 * Precondition:  The program wants a value for the 
	 * 			   organizations name
	 * Postcondition: The program gets a value for the 
	 * 			   organizations name
	 */
	{
		 
	}

	@FXML
	void inputDepartAction(ActionEvent event) 
	/* 
	 * Precondition:  Program wants a value fort the date of 
	 * 			   departure
	 * Postcondition: The program gets a value in the form of 
	 * 			   a LocalDate
	 */
	{
		
	}

	@FXML
	void inputReturnAction(ActionEvent event) 
	/*
	 * Precondition:  Program wants value for the date of return
	 * Postcondition: The program gets a value in the form of 
	 * 			      a LocalDate
	 */
	{
		
	}

	@FXML
	void inputPeopleAction(ActionEvent event) 
	/* 
	 * Precondition:  Program wants value for number of people
	 * Postcondition: The program gets a value for group size
	 */
	{
		 
	}
	 
	@FXML
	void getDestinationNm(ActionEvent event)
	{
		 
	}
	    
	@FXML
	void returntoMain(ActionEvent event) throws IOException 
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
	void openCheckout(ActionEvent event) throws IOException, SAXException 
	{
		/*
		 * Resets all values and fields
		 */
		Boolean in = false;
		Boolean ip = false;
		Boolean ir = false;
		Boolean id = false;
		peopleError.setText("");
		nameError.setText("");
		returnError.setText("");
		departError.setText("");
		
		/*
		 * If Statement if all fields are filled out
		 */
		if (inputName.getText() != null || inputDepart.getValue() != null || 
			inputReturn.getValue() != null || inputNumPeople.getText() != null)
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
				grpSz = Integer.parseInt(inputNumPeople.getText());
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
			//if (checkName(inputName.getText()))
			//{
				//nameError.setText("Name already used!!");
				//in = false;
			//}
			/*
			 * Else field is valid
			 */
			//else
			//{
				in = true;
				orgName = inputName.getText();	
			//}
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
			else if (inputDepart.getValue() == null)
			{
				departError.setText("Field cant be empty!!");
			}
			/*
			 * If return isn't selected
			 */
			else if (inputReturn.getValue() == null)
			{
				returnError.setText("Field cant be empty!!");
			}
			/*
			 * If number of people is selected
			 */
			else if (inputNumPeople.getText() == null)
			{
				peopleError.setText("Field cant be empty!!");
			}
		}
		
		/*
		 * If all of the vales are acceptable
		 */
		if (in == true && ip == true && ir == true && id == true)
		{		
			/*
			 * Adds the new information to the observable list
			 * @param Name
			 * @param size
			 * @param depart
			 * @param arrive
			 */
			tripData.add(new Trip (orgName, grpSz, arr, dpt));
			
			/*
			 * Loads the checkout window
			 */
			Stage stage;
			AnchorPane root;
			stage = (Stage) continueCheckout.getScene().getWindow();	
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/CheckoutWindow.fxml"));
			root = (AnchorPane) loader.load();   
			CheckoutController controller = loader.<CheckoutController>getController();
			controller.setInfo(orgName, Integer.toString(grpSz), arr.toString(), dpt.toString());
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}

	public String getName()
	{
		return orgName;
	}
	
	public int getGroupSize ()
	{
		return grpSz;
	}
	
	public LocalDate getDepart ()
	{
		return dpt;
	}
	
	public LocalDate getArr ()
	{
		return arr;
	}
	
	/* 
	 * Initializes all of the GUI elements 
	 */
	@FXML
	void initialize() 
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
	}
}
