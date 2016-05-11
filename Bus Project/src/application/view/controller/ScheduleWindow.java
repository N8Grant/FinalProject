package application.view.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import application.Main;
import application.model.Trip;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ScheduleWindow extends Main implements Initializable
{
	/*
	 * GUI element variables
	 */
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
	private Label busNumbersLabel;
	
	@FXML
	private Label nameLabel;
	   
	@FXML
	private Label costLabel;
	
	@FXML 
    private Button homeButton;
	
	@FXML
	private Button editPerson;

	@FXML
	public ListView<String> customerSelect;
	
	@FXML
	private ChoiceBox <String> sortSelect;

	@FXML
    private Button loadSelectButton;
	
	@FXML
	private Button sortButton;
	
	@FXML
	private TextArea tripNotes;
	
	ObservableList<String> sortItems = FXCollections.observableArrayList();
	
	@FXML
    void editPersonInfo(ActionEvent event) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException
	{
		Stage stage;
		AnchorPane root;
		stage = (Stage) editPerson.getScene().getWindow();
							
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/EditInfoWindow.fxml"));
		root = (AnchorPane) loader.load(); 
		for (Trip trp: getSpecificTrip(customerSelect.getSelectionModel().getSelectedItem()))
    	{
			EditWindowController controller = loader.<EditWindowController>getController();
			controller.setInfo(trp.getName(), trp.getDepart(), 
							   trp.getArrive(), trp.getGroupSize());
			
			deleteTrip(trp.getName());
    	}
				
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
	
    @FXML
    void loadSelect(ActionEvent event) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException 
    { 	
    	for (Trip trp: getSpecificTrip(customerSelect.getSelectionModel().getSelectedItem()))
    	{
    		nameLabel.setText(trp.getName());
    		idLabel.setText(trp.getId());
    		departLabel.setText(trp.getDepart().format(mdy));
    		arriveLabel.setText(trp.getArrive().format(mdy));
    		sizeLabel.setText(Integer.toString(trp.getGroupSize()));
    		busNumbersLabel.setText(trp.getBusNumbers());
    		costLabel.setText("$ " + String.format("%.2f", trp.getTripCost()));
    	}	 
    	editPerson.setDisable(false);
    }
    
    @FXML
    void sortAction(ActionEvent event)
    {
    	String temp = sortSelect.getValue();
    	if (temp == "Name A-Z")
    	{
    		customerSelect.setItems(getAllNames(fetchXML(), 1));
    	}
    	else if (temp == "Name Z-A")
    	{
    		customerSelect.setItems(getAllNames(fetchXML(), 2));
    	}
    	else if (temp == "Group Size Decending")
    	{
    		customerSelect.setItems(getAllNames(fetchXML(), 3));
    	}
    	else if (temp == "Group Size Acending")
    	{
    		customerSelect.setItems(getAllNames(fetchXML(), 4));
    	}
    	else
    	{
    		customerSelect.setItems(getAllNames(fetchXML(), 0));
    	}
    }
	    
	@FXML
	void returnMain(ActionEvent event) throws IOException 
	{
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
		assert nameLabel != null : "fx:id=\"nameLabel\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert departLabel != null : "fx:id=\"departLabel\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert arriveLabel != null : "fx:id=\"arriveLabel\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert idLabel != null : "fx:id=\"idLabel\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert sizeLabel != null : "fx:id=\"sizeLabel\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert editPerson != null : "fx:id=\"editPerson\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert tripNotes != null : "fx:id=\"tripNotes\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert loadSelectButton != null : "fx:id=\"loadSelectButton\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert customerSelect != null : "fx:id=\"customerSelect\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert sortButton != null : "fx:id=\"sortButton\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert sortSelect != null : "fx:id=\"sortSelect\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert costLabel != null : "fx:id=\"costLabel\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
        assert busNumbersLabel != null : "fx:id=\"busNumbersLabel\" was not injected: check your FXML file 'ScheduleWindow.fxml'.";
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		customerSelect.setItems(getAllNames(fetchXML(), 0));
		sortItems.add("Name A-Z");
		sortItems.add("Name Z-A");
		sortItems.add("Group Size Decending");
		sortItems.add("Group Size Acending");
		sortSelect.setItems(sortItems);
	}
}