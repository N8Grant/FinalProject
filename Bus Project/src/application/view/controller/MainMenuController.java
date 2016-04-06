package application.view.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainMenuController extends Main
{
	/*
	 * GUI Element Variables
	 */
	@FXML
	private Button bookTrip;

	@FXML
	private Button quitButton;

	@FXML
	private Button checkSchedule;

	@FXML
	private Button busManagement;
	    
	@FXML
	void bookTrip(ActionEvent event) throws IOException 
	
	{
	    Stage stage;
	    AnchorPane root;
	    stage = (Stage) bookTrip.getScene().getWindow();
				
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(Main.class.getResource("view/BookTrip.fxml"));
	    root = (AnchorPane) loader.load();   
	   
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
	}
	 
	@FXML
	void checkSchedule(ActionEvent event) throws IOException
	{
		Stage stage;
		AnchorPane root;
		stage = (Stage) busManagement.getScene().getWindow();
		 		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/ScheduleWindow.fxml"));
		ScheduleWindow controller = loader.<ScheduleWindow>getController();
		controller.setCustomerSelect(getAllNames(fetchXML()));
		root = (AnchorPane) loader.load();  
		 
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	 
	@FXML
	void manageBusses(ActionEvent event) throws IOException  
	{ 
		Stage stage;
		AnchorPane root;
		stage = (Stage) busManagement.getScene().getWindow();
					
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/BusManagement.fxml"));
		root = (AnchorPane) loader.load();  
		 
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	 
	@FXML
	void exitProgram(ActionEvent event)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Quit Program");
		alert.setContentText("Are you sure you want to exit??");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
		{
			System.exit(0);
		} 
		else 
		{
		    alert.close();
	    }
	}
	  
	@FXML
	private void initialize() 
	{
	     assert bookTrip != null : "fx:id=\"bookTrip\" was not injected: check your FXML file 'MainMenu.fxml'.";
	     assert quitButton != null : "fx:id=\"quitButton\" was not injected: check your FXML file 'MainMenu.fxml'.";
	     assert checkSchedule != null : "fx:id=\"checkSchedule\" was not injected: check your FXML file 'MainMenu.fxml'.";
	     assert busManagement != null : "fx:id=\"finances\" was not injected: check your FXML file 'MainMenu.fxml'.";
	}
}

