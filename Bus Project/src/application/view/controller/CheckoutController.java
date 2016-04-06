package application.view.controller;

import java.io.IOException;
import java.util.Optional;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import application.Main;
import application.model.Trip;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class CheckoutController extends Main
{
	@FXML
	private Label totalLabel;

	@FXML
	private Button editInfo;

	@FXML
	private Label orgNameLb;

	@FXML
	private Label dptLabel;

	@FXML
	private Label bussesLabel;

	@FXML
	private Button print;

	@FXML
	private Label grpLabel;

	@FXML
	private Button cancelTrans;

	@FXML
	private Label retLabel;

	@FXML
	private Button Confirm;

	@FXML
	private GridPane tripInfo;
	    
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
		
		if (result.get() == ButtonType.OK)
		{
			/*
			 * Marshalls data to an xml file
			 */
			addToXML(getTripList());
			
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
			alert.close();	   // closes the alert if cancel is pressed
		}
    }

    @FXML
    void printReport(ActionEvent event) throws IOException, XPathExpressionException, 
    									ParserConfigurationException, SAXException 
    {
    	AnchorPane root;
					
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/PrintConfirmation.fxml"));
		PrinterController controller = loader.<PrinterController>getController();
		BookTripController iterate = loader.<BookTripController>getController();
		root = (AnchorPane) loader.load();
		for (Trip trp: getSpecificTrip(iterate.orgName))
		{
			controller.customerLabel.setText(trp.getName());
		}	 
		
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
    {
    	FXMLLoader fxmlLoader = new FXMLLoader();
		BookTripController controller = fxmlLoader.<BookTripController>getController();
    	System.out.print(controller.getName());
    }

    public void setInfo (String org, String grp, String arr, String dpt)
    {
    	orgNameLb.setText(org);
    	grpLabel.setText(grp);
    	dptLabel.setText(dpt);
    	retLabel.setText(arr);
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
        assert tripInfo != null : "fx:id=\"tripInfo\" was not injected: check your FXML file 'CheckoutWindow.fxml'.";
    }
}