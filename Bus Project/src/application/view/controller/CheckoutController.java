package application.view.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import application.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CheckoutController extends BookTripController implements Initializable
{
	/*
	 * GUI Element variables
	 */
	@FXML
	private ResourceBundle resources; // The packages that class is inside of

	@FXML
	private URL location;			// the url location of the trip
	
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

	/*
	 * Temp Variables
	 */
	public String name;
	    
    @FXML
    void finalizeTrip(ActionEvent event) throws IOException, SAXException 
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
		 * If User confrims pop-up dialog box
		 */
		if (result.get() == ButtonType.OK)
		{
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
    	controller.populateReceipt(name);
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
    
    @FXML
    void editInfo(ActionEvent event) 
    /*
     * 
     */
    {
    	//FXMLLoader fxmlLoader = new FXMLLoader();
		//BookTripController controller = fxmlLoader.<BookTripController>getController();
    	//System.out.print(controller.getName());
    }

    public void setInfo (String org, String grp, LocalDate arr, LocalDate dpt, String busNms)
    {
    	name = org;
    	orgNameLb.setText(org);
    	grpLabel.setText(grp);
    	dptLabel.setText(dpt.toString());
    	retLabel.setText(arr.toString());
    	bussesLabel.setText(getBussesNeeded(Integer.parseInt(grp)));
    	busNumbers.setText(busNms);
    }
    
    @FXML
    void returnToMainMenu(ActionEvent event) throws IOException 
    {
    	 Alert alert = new Alert(AlertType.CONFIRMATION);
		 alert.setTitle("Confirmation Dialog");
		 alert.setHeaderText("Cancel Transaction");
		 alert.setContentText("Are you sure you want to stop this transaction??");

		 Optional<ButtonType> result = alert.showAndWait();
		 if (result.get() == ButtonType.OK)
		 {
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
		 else 
		 {
		     alert.close();
		 }
    }
    
    @FXML
    void initialize()
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
    }

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
	
	}
}