package application.view.controller;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
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

    @FXML
    private RadioButton quarterly;

    @FXML
    private ToggleGroup timeFrame;

    @FXML
    private RadioButton monthly;

    @FXML
    private RadioButton weekly;

    @FXML
    private Button applyTime;
    
    final Label caption = new Label("");
    
    DecimalFormat df2 = new DecimalFormat(".##");
    
    public void setValues(String total, ObservableList<PieChart.Data> data)
    {
    	if (data == null || data.isEmpty())
    	{
    		/*
	    	 * Alert to show finalize success
	    	 */
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setTitle("Confirmation Dialog");
			alert.setHeaderText("Confirm Dialog");
			alert.setContentText("There are no completed trips so there is no revenue, " +
								 "however you can select one of the given projections for revenue");
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == ButtonType.OK)
			{
				alert.close();
			}
			else
			{
				alert.close();
			}
    	}
    	financeChart.getData().clear();
    	totalLabel.setText(total);
    	financeChart.setData(data);
    }
    
    @FXML
    void applyTime(ActionEvent event) throws NumberFormatException, ParserConfigurationException, SAXException, IOException 
    {	
    	if (quarterly.isSelected())
    	{
    		totalLabel.setText("$ " + Double.parseDouble(df2.format(getRevenue(3))));
    		financeChart.setData(getPieChart(3));
    	}
    	else if (monthly.isSelected())
    	{
    		totalLabel.setText("$ " + Double.parseDouble(df2.format(getRevenue(2))));
    		financeChart.setData(getPieChart(2));
    	}
    	else if (weekly.isSelected())
    	{
    		totalLabel.setText("$ " + Double.parseDouble(df2.format(getRevenue(1))));
    		financeChart.setData(getPieChart(1));
    	}
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
        assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'FinancesWindow.fxml'.";
        assert quarterly != null : "fx:id=\"quarterly\" was not injected: check your FXML file 'FinancesWindow.fxml'.";
        assert timeFrame != null : "fx:id=\"timeFrame\" was not injected: check your FXML file 'FinancesWindow.fxml'.";
        assert monthly != null : "fx:id=\"monthly\" was not injected: check your FXML file 'FinancesWindow.fxml'.";
        assert weekly != null : "fx:id=\"weekly\" was not injected: check your FXML file 'FinancesWindow.fxml'.";
        assert applyTime != null : "fx:id=\"applyTime\" was not injected: check your FXML file 'FinancesWindow.fxml'.";
    }
}
