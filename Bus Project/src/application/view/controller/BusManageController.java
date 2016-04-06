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

public class BusManageController 
{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ToggleGroup busView;

    @FXML
    private RadioButton subRent;
    
    @FXML 
    private Button cancelButton;
    
    @FXML
    private Button viewBusInfo;

    @FXML
    private RadioButton bussesOut;

    @FXML
    private RadioButton finances;

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
    void displayBusInfo(ActionEvent event) 
    {
    	if(bussesOut.isSelected() == true)
    	{
    		System.out.print("Yes");
    	}
    	else if (subRent.isSelected() == true)
    	{
    		System.out.print("No");
    	}
    	else if (finances.isSelected() == true)
    	{
    		System.out.print("Maybe");
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
