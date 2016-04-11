package application.view.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class PrinterController extends CheckoutController implements Initializable
{
	  @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private Label customerLabel;

	    public Label getCustomerLabel() 
	    {
			return customerLabel;
		}

	    public void populateReceipt (String nm)
	    {
	    	customerLabel.setText(nm);
	    }
		@FXML
	    void initialize() 
	    {
	        assert customerLabel != null : "fx:id=\"customerLabel\" was not injected: check your FXML file 'PrintConfirmation.fxml'.";
	    }
	    
		public void initialize(URL arg0, ResourceBundle arg1) 
		{
			populateReceipt(name);
		}
}
