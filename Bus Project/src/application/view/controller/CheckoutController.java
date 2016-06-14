package application.view.controller;		// Package that the class is in

/*
 * Import Section
 */
import java.io.IOException;		// Exception for input and output
import java.net.URL;		// Import for use if URL connection
import java.time.LocalDate;		// Used for LocalDate class to handle date
import java.util.Optional;		// Used for popup handling
import java.util.ResourceBundle;		// Used for location of windows in memory

import javax.xml.parsers.ParserConfigurationException;		// Exception for bad parser handling
import javax.xml.xpath.XPathExpressionException;		// Exception for transformer error

import org.xml.sax.SAXException;		// Exception for writer error

import application.Main;		// Import for the main classes methods

import javafx.event.ActionEvent;		// Import for JavaFX event handling
import javafx.fxml.FXML;		// Import for FXML file format
import javafx.fxml.FXMLLoader;		// Import for FXML loaders
import javafx.print.PrinterJob;		// Used for printer output
import javafx.scene.Scene;		// Used for the base scene
import javafx.scene.control.Alert;			// Used for easy pop up windows
import javafx.scene.control.Alert.AlertType;		// Used for customizing alerts
import javafx.scene.control.Button;			// Used for button GUI handling
import javafx.scene.control.ButtonType;			// Used to control popup interaction
import javafx.scene.control.Label;			// Used for text label handling
import javafx.scene.layout.AnchorPane;		// The base pane or windows
import javafx.scene.layout.GridPane;		// Used for grid pane manipulation
import javafx.stage.Stage;				// The base stage for the panes to be outputted to

public class CheckoutController extends BookTripController
{
	/*
	 * GUI Element variables
	 */
	@FXML
	private ResourceBundle resources; // The packages that class is inside of

	@FXML
	private URL location;			// the URL location of the trip
	
	@FXML
	private Label totalLabel;		// Label for total amount of money

	@FXML
	private Button editInfo;		// Edit the info that was inputed

	@FXML
	private Label busNumbers;		// The numbers of each individual bus
	
	@FXML
	private Label orgNameLb;		// Label for name

	@FXML
	private Label dptLabel;			// Label for depart date

	@FXML
	private Label bussesLabel;		// Label for number of busses

	@FXML
	private Button print;			// Button to print receipt

	@FXML	
	private Label grpLabel;			// Label for the number of people

	@FXML
	private Button cancelTrans;		// Button to cancel the transaction

	@FXML
	private Label retLabel;			// Label for the date of return

	@FXML
	private Button Confirm;			// Button to finalize transaction

	@FXML
	private GridPane tripInfo;		// GridPane to contain trip info
	
	@FXML
    private Label destnationLabel;		// The label for the destination name


	/*
	 * Temp Variables
	 */
	public String nameVariable;		// temp variable for name for next controller
	public String grpVariable;		// temp variable for groupSize for next controller
	public String dptVariable;		// temp variable for departure for next controller
	public String retVariable;		// temp variable for return for next controller
	public String busNmVariable;	// temp variable for bus numbers for next controller
	public String totVariable;		// temp variable for total for next controller
	public String idVariable;		// temp variable for id for next controller
	public String destVariable;		// temp variable for destination for next controller
	    
    @FXML
    void finalizeTrip(ActionEvent event) throws IOException, SAXException 
    /*
     * Precondition:  User clicks on the finalize trip button
     * Postcondition: If user accepts popup then they are returned to the main menu
     */
    {
    	/*
    	 * Alert to show finalize success
    	 */
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Finalize Transaction");
		alert.setContentText("Trip has been added to the registry!!");
		Optional<ButtonType> result = alert.showAndWait();
		
		/*
		 * If User confirms pop-up dialog box
		 */
		if (result.get() == ButtonType.OK)
		{
			Stage stage;
			AnchorPane root;
			stage = (Stage) Confirm.getScene().getWindow();
						
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
    void printReport(ActionEvent event) throws IOException, XPathExpressionException, 
    									ParserConfigurationException, SAXException 
    /*
     * If user clicks on print report button
     */
    {
    	AnchorPane root;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/PrintConfirmation.fxml"));
		root = (AnchorPane) loader.load();   
		PrinterController controller = loader.<PrinterController>getController();
		controller.populateReceipt(nameVariable, grpVariable, busNmVariable,
								   dptVariable, retVariable, totVariable, idVariable,
								   destVariable);
		
		/*
		 * Makes a printer job to be used by the desired printer
		 */
		PrinterJob job = PrinterJob.createPrinterJob();
    	if (job != null && job.showPrintDialog(stage))
    	{
    	    boolean success = job.printPage(root);
    	    if (success) 
    	    {
    	        job.endJob();
    	    }
    	}
    }

    public void setInfo (String org, String grp, LocalDate arr, LocalDate dpt,
    					 String busNms, String id, double dist, String dest)
    /*
     * Precondition:  User continues onto the next window and the window needs data from
     * 				  the previous controller
     * Postcondition: Data is passed to the next window and outputted to the screen
     */
    {
    	/*
    	 * Variable Setters
    	 */
    	destVariable = dest;
    	nameVariable = org;
    	grpVariable = grp;
    	dptVariable = dpt.toString();
    	retVariable = arr.toString();
    	totVariable = String.format("%.2f", getTripCost(Integer.parseInt(grp), dist));
    	busNmVariable = busNms;
    	idVariable = id;
    	
    	/*
    	 * Label Setters
    	 */
    	orgNameLb.setText(org);
    	grpLabel.setText(grp);
    	dptLabel.setText(dpt.format(mdy));
    	retLabel.setText(arr.format(mdy));
    	bussesLabel.setText(getBussesNeeded(Integer.parseInt(grp)));
    	busNumbers.setText(busNms);
    	totalLabel.setText("$ " + String.format("%.2f", getTripCost(Integer.parseInt(grp), dist)));
    	destnationLabel.setText(dest);
    }
    
    @FXML
    void returnToMainMenu(ActionEvent event) throws IOException, ParserConfigurationException, SAXException 
    /*
     * Precondition:  User must click on the return to main menu button
     * Postcondition: If they confirm the popup then the trip that they were working on
     * 				  will be deleted and they will be sent to the main menu
     */
    {
    	/*
    	 * Makes a confirmaion popup
    	 */
    	 Alert alert = new Alert(AlertType.CONFIRMATION);
		 alert.setTitle("Confirmation Dialog");
		 alert.setHeaderText("Cancel Transaction");
		 alert.setContentText("Are you sure you want to stop this transaction??");

		 Optional<ButtonType> result = alert.showAndWait();
		 /*
		  * If user clicks on ok
		  */
		 if (result.get() == ButtonType.OK)
		 {
			deleteTrip(nameVariable);		// Deletes the trip they were woring on
			
			
			/*
			 * Loads the main menu
			 */
			Stage stage;
			AnchorPane root;
			stage = (Stage) cancelTrans.getScene().getWindow();
						
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MainMenu.fxml"));
			root = (AnchorPane) loader.load();   
				
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		 } 
		 /*
		  * Else they click on cancel
		  */
		 else 
		 {
		     alert.close();			// Closes the alert
		 }
    }
    
    @FXML
    void initialize()
    /*
     * Precondition:  There is an error injecting the GUI elements
     * Postcondition: Error message is spit out
     */
    {
    	assert totalLabel != null : "fx:id=\"totalLabel\" was not injected: check your FXML file 'CheckoutWindow.fxml'.";
        assert editInfo != null : "fx:id=\"editInfo\" was not injected: check your FXML file 'CheckoutWindow.fxml'.";
        assert orgNameLb != null : "fx:id=\"orgNameLb\" was not injected: check your FXML file 'CheckoutWindow.fxml'.";
        assert dptLabel != null : "fx:id=\"dptLabel\" was not injected: check your FXML file 'CheckoutWindow.fxml'.";
        assert bussesLabel != null : "fx:id=\"bussesLabel\" was not injected: check your FXML file 'CheckoutWindow.fxml'.";
        assert print != null : "fx:id=\"print\" was not injected: check your FXML file 'CheckoutWindow.fxml'.";
        assert grpLabel != null : "fx:id=\"grpLabel\" was not injected: check your FXML file 'CheckoutWindow.fxml'.";
        assert cancelTrans != null : "fx:id=\"cancelTrans\" was not injected: check your FXML file 'CheckoutWindow.fxml'.";
        assert retLabel != null : "fx:id=\"retLabel\" was not injected: check your FXML file 'CheckoutWindow.fxml'.";
        assert Confirm != null : "fx:id=\"Confirm\" was not injected: check your FXML file 'CheckoutWindow.fxml'.";
        assert busNumbers != null : "fx:id=\"busNumbers\" was not injected: check your FXML file 'CheckoutWindow.fxml'.";
        assert tripInfo != null : "fx:id=\"tripInfo\" was not injected: check your FXML file 'CheckoutWindow.fxml'.";   
        assert destnationLabel != null : "fx:id=\"destnationLabel\" was not injected: check your FXML file 'CheckoutWindow.fxml'.";
    }
}