package application.view.controller;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.controlsfx.control.textfield.TextFields;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EditWindowController extends Main
{
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField inputName;

    @FXML
    private DatePicker inputDepart;

    @FXML
    private DatePicker inputReturn;

    @FXML
    private TextField inputNumPeople;

    @FXML
    private Button commitChanges;
    
    @FXML
    private Button deleteTrip;

    @FXML
    private Label nameError;

    @FXML
    private Label peopleError;

    @FXML
    private Label returnError;

    @FXML
    private Label departError;
    
    @FXML
    private Label destinationError;

    @FXML
    private TextField destinationName;

    /*
     * 
     */
	private String name;
	private LocalDate depart;
	private LocalDate arrive;
	private int groupSize;
	private String destination;

	@FXML
	void deleteTrip(ActionEvent event) throws IOException 
	{
		/*
    	 * Alert to show finalize success
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
	{
		TextFields.bindAutoCompletion(destinationName, getDestinationChoices(destinationName.getText()));
    }
	
    @FXML
    void commitChanges(ActionEvent event) throws IOException, ParserConfigurationException, SAXException, TransformerException 
    {
    	/*
		 * Resets all values and fields
		 */
		Boolean in = false;
		Boolean ip = false;
		Boolean ir = false;
		Boolean id = false;
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
		
		String bsNms = getBusses(depart, arrive, groupSize);
		
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
			if(result.get() == ButtonType.OK)
			{
				tripData.clear();
				
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
			else
			{
				alert.close();
			}
			
		}
    }

    @FXML
    void initialize() 
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
    {
    	name = nm;
    	depart = dpt;
    	arrive = ret;
    	groupSize = grpSz;
    	
    	inputName.setText(nm);
    	inputDepart.setValue(dpt);
    	inputReturn.setValue(ret);
    	inputNumPeople.setText(Integer.toString(grpSz));
    	destinationName.setText(dest);
    }
}    