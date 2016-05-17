package application.view.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FinanceWindow extends Main
{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PieChart financeChart;

    @FXML
    private Label totalLabel;

    @FXML
    private Button homeButton;
    
    final Label caption = new Label("");
    
    ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
    
    public void setValues(String total, ObservableList<PieChart.Data> data)
    {
    	totalLabel.setText(total);
    	financeChart.setData(data);
    	pieData = data;
    }
    
    @FXML
    void showSingleRevenue(MouseEvent event) 
    {
    	for (PieChart.Data data: financeChart.getData())
        {
    		caption.setTranslateX(event.getSceneX());
    		caption.setTranslateY(event.getSceneY());
        	caption.setText(String.valueOf(data.getPieValue()));
        }  
    }
    
    @FXML
    void returnHome(ActionEvent event) throws IOException 
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
        assert financeChart != null : "fx:id=\"financeChart\" was not injected: check your FXML file 'FinancesWindow.fxml'.";
        assert totalLabel != null : "fx:id=\"totalLabel\" was not injected: check your FXML file 'FinancesWindow.fxml'.";
        assert homeButton != null : "fx:id=\"returnButton\" was not injected: check your FXML file 'FinancesWindow.fxml'.";

    }
}
