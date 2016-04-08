package application.view.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PrinterController 
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

		@FXML
	    void initialize() 
	    {
	        assert getCustomerLabel() != null : "fx:id=\"customerLabel\" was not injected: check your FXML file 'PrintConfirmation.fxml'.";
	    }
	    
	    public void initialize(URL arg0, ResourceBundle arg1) 
		{
			getCustomerLabel().setText(null);
		}
}
