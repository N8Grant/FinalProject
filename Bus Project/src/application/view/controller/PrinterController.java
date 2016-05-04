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
	
	@FXML
	private Label idLabel;

	@FXML
	private Label grpSzLabel;

	@FXML
	private Label busNmLabel;

	@FXML
	private Label departLabel;

	@FXML
	private Label totalLabel;

	@FXML
	private Label returnLabel;

	public Label getCustomerLabel() 
	{
		return customerLabel;
	}

	public void populateReceipt (String nm, String grpSz,
								 String busNm, String dpt, String ret)
	{
		customerLabel.setText(nm);
		grpSzLabel.setText(grpSz);
		busNmLabel.setText(busNm);
		departLabel.setText(dpt);
		returnLabel.setText(ret);
	}
		
	@FXML
	void initialize() 
	{
		assert customerLabel != null : "fx:id=\"customerLabel\" was not injected: check your FXML file 'PrintConfirmation.fxml'.";
        assert idLabel != null : "fx:id=\"idLabel\" was not injected: check your FXML file 'PrintConfirmation.fxml'.";
        assert grpSzLabel != null : "fx:id=\"grpSzLabel\" was not injected: check your FXML file 'PrintConfirmation.fxml'.";
        assert busNmLabel != null : "fx:id=\"busNmLabel\" was not injected: check your FXML file 'PrintConfirmation.fxml'.";
        assert departLabel != null : "fx:id=\"departLabel\" was not injected: check your FXML file 'PrintConfirmation.fxml'.";
        assert totalLabel != null : "fx:id=\"totalLabel\" was not injected: check your FXML file 'PrintConfirmation.fxml'.";
        assert returnLabel != null : "fx:id=\"returnLabel\" was not injected: check your FXML file 'PrintConfirmation.fxml'.";
	}
	    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
			
	}
}
