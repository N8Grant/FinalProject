package application.view.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import application.Main;
import application.model.Trip;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ScheduleWindow extends MainMenuController
{
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label sizeLabel;

	@FXML
	private Label idLabel;

	@FXML
	private Label departLabel;

	@FXML
	private ScrollPane selector;

	@FXML
	private Label arriveLabel;

	@FXML
	private Label nameLabel;
	    
	@FXML 
    private Button homeButton;
	
	@FXML
	private Button editPerson;

	@FXML
	public ListView<String> customerSelect;

	@FXML
    private Button loadSelectButton;

	int t = 0;
	
	@FXML
    void editPersonInfo(ActionEvent event)
	{
		
    }
	
    @FXML
    void loadSelect(ActionEvent event) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException 
    {
    	if (t == 0)
    	{
    		customerSelect.setItems(setCustomerSelect(fetchXML()));
    		loadSelectButton.setText("Select");
    		t++;
    	}
    	else
    	{
    		editPerson.setDisable(false);
    		for (Trip trp: getSpecificTrip(customerSelect.getSelectionModel().getSelectedItem()))
    		{
    			System.out.print(trp.toString());
    			nameLabel.setText(trp.getName());
    			idLabel.setText(trp.getId());
    			departLabel.setText(trp.getDepartStr());
    			arriveLabel.setText(trp.getArriveStr());
    			sizeLabel.setText(Integer.toString(trp.getGroupSize()));
    		}	 
    	}
    }
    
	
	    
	@FXML
	void returnMain(ActionEvent event) throws IOException 
	{
		t = 0;
		Stage stage;
		AnchorPane root;
		stage = (Stage) homeButton.getScene().getWindow();
							
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/MainMenu.fxml"));
		root = (AnchorPane) loader.load();   
					
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	    
	@FXML
	void initialize() 
	{
	    assert sizeLabel != null : "fx:id=\"sizeLabel\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
	    assert idLabel != null : "fx:id=\"idLabel\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
	    assert departLabel != null : "fx:id=\"departLabel\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
	    assert selector != null : "fx:id=\"selector\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
	    assert arriveLabel != null : "fx:id=\"arriveLabel\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
	    assert nameLabel != null : "fx:id=\"nameLabel\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
	    assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert customerSelect != null : "fx:id=\"customerSelect\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert loadSelectButton != null : "fx:id=\"loadSelectButton\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";

	}
}