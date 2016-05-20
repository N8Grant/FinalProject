package application.view.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BusManageController extends Main
{
	/*
	 * GUI element variables
	 */
    @FXML
    private ResourceBundle resources;		// Controller Resources

    @FXML
    private URL location;					// Location in storage

    @FXML
    private ToggleGroup busView;			// The group of toggle buttons

    @FXML
    private RadioButton subRent;			// Toggle button for busses from other
    										// company
    @FXML 
    private Button cancelButton;			// Button to return to main menu
    
    @FXML
    private Button viewBusInfo;				// Button to select the currently chosen
    										// radio button
    @FXML
    private RadioButton bussesOut;			// Toggle button for busses that are out
   
    @FXML
    private RadioButton finances;			// Toggle button for income statement or 
    										// balance sheet
    @FXML
    void returntoMain(ActionEvent event) throws IOException 
    {
    	Stage stage;
		AnchorPane root;
		stage = (Stage) cancelButton.getScene().getWindow();
					
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/MainMenu.fxml"));
		root = (AnchorPane) loader.load();   
			
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
    
    @FXML
    void displayBusInfo(ActionEvent event) throws IOException 
    {
    	if(bussesOut.isSelected() == true)
    	{
    		Stage stage;
    		AnchorPane root;
    		stage = (Stage) viewBusInfo.getScene().getWindow();
    							
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(Main.class.getResource("view/BussesOutstanding.fxml"));
    		root = (AnchorPane) loader.load();
    		BussesOutstandingController controller = loader.<BussesOutstandingController>getController();
    		controller.dateLabel.setText("No date selected!!");			
    		Scene scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    	}
    	else if (subRent.isSelected() == true)
    	{
    		System.out.print("No");
    	}
    	else if (finances.isSelected() == true)
    	{
    		Stage stage;
    		AnchorPane root;
    		stage = (Stage) viewBusInfo.getScene().getWindow();
    							
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(Main.class.getResource("view/FinancesWindow.fxml"));
    		root = (AnchorPane) loader.load();
    		FinanceWindow controller = loader.<FinanceWindow>getController();
    		controller.setValues("$ " + String.format("%.2f", getRevenue()), getPieChart());			
    		Scene scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    	}
    }

    @FXML
    void initialize() 
    {
        assert busView != null : "fx:id=\"busView\" was not injected: check your FXML file 'BusManagement.fxml'.";
        assert subRent != null : "fx:id=\"subRent\" was not injected: check your FXML file 'BusManagement.fxml'.";
        assert viewBusInfo != null : "fx:id=\"viewBusInfo\" was not injected: check your FXML file 'BusManagement.fxml'.";
        assert bussesOut != null : "fx:id=\"bussesOut\" was not injected: check your FXML file 'BusManagement.fxml'.";
        assert finances != null : "fx:id=\"finances\" was not injected: check your FXML file 'BusManagement.fxml'.";
    }
}
