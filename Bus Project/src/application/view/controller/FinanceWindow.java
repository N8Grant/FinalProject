package application.view.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FinanceWindow 
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
    private Button returnButton;

    @FXML
    void initialize() 
    {
        assert financeChart != null : "fx:id=\"financeChart\" was not injected: check your FXML file 'FinancesWindow.fxml'.";
        assert totalLabel != null : "fx:id=\"totalLabel\" was not injected: check your FXML file 'FinancesWindow.fxml'.";
        assert returnButton != null : "fx:id=\"returnButton\" was not injected: check your FXML file 'FinancesWindow.fxml'.";

    }
}
