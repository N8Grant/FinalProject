package application.view.controller;		// Package that the class is contained in

import java.net.URL;				// Import for use with URL connection
import java.util.ResourceBundle;	// Used for location of windows in memory

import javafx.fxml.FXML;			// Import for FXML file format
import javafx.scene.control.Label;		// Used for text label handling


public class PrinterController extends CheckoutController
{
	/*
	 * GUI Elements
	 */
	@FXML
	private ResourceBundle resources;		// Location of FXML in computer
	
    @FXML
    private URL location;					// Location in storage of FXML
	  
	@FXML
	private Label customerLabel;			// The label for the customers name
	
	@FXML
	private Label idLabel;					// The label for the ID

	@FXML
	private Label grpSzLabel;				// The label for the group size

	@FXML
	private Label busNmLabel;				// Label for the bus numbers

	@FXML
	private Label departLabel;				// Label for the date of departure

	@FXML
	private Label totalLabel;				// Label for the total cost of the trip

	@FXML		
	private Label returnLabel;				// Label for the date of return
	
    @FXML
    private Label destinationLabel;			// Label for the name of the destination

	public void populateReceipt (String nm, String grpSz,
								 String busNm, String dpt, String ret, String tot,
								 String id, String dest)
	/*
	 * Precondition:  User wants to print a receipt
	 * Postcondition: The data is sent to the printer window and the 
	 * 				  label values are set to be printed
	 */
	{
		/*
		 * Sets all of the labels for all of the trips data
		 */
		customerLabel.setText(nm);
		grpSzLabel.setText(grpSz);
		busNmLabel.setText(busNm);
		departLabel.setText(dpt);
		returnLabel.setText(ret);
		totalLabel.setText("$ " + tot);
		idLabel.setText(id);
		destinationLabel.setText(dest);
	}
		
	@FXML
	void initialize() 
	 /*
     * Precondition:  There is an error injecting the GUI elements
     * Postcondition: Error message is spit out
     */
	{
		assert customerLabel != null : "fx:id=\"customerLabel\" was not injected: check your FXML file 'PrintConfirmation.fxml'.";
        assert idLabel != null : "fx:id=\"idLabel\" was not injected: check your FXML file 'PrintConfirmation.fxml'.";
        assert grpSzLabel != null : "fx:id=\"grpSzLabel\" was not injected: check your FXML file 'PrintConfirmation.fxml'.";
        assert busNmLabel != null : "fx:id=\"busNmLabel\" was not injected: check your FXML file 'PrintConfirmation.fxml'.";
        assert departLabel != null : "fx:id=\"departLabel\" was not injected: check your FXML file 'PrintConfirmation.fxml'.";
        assert totalLabel != null : "fx:id=\"totalLabel\" was not injected: check your FXML file 'PrintConfirmation.fxml'.";
        assert returnLabel != null : "fx:id=\"returnLabel\" was not injected: check your FXML file 'PrintConfirmation.fxml'.";
        assert destinationLabel != null : "fx:id=\"returnLabel\" was not injected: check your FXML file 'PrintConfirmation.fxml'.";
	}
}
