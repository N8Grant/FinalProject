package application.view.controller;		// Package that the class is in

/*
 * Import Section
 */
import java.io.IOException;			//  Exception for input and output
import java.net.URL;				// Import for use with URL connection
import java.text.DecimalFormat;		// Used to format doubles to two decimal points
import java.util.Optional;			// Used for popup handling
import java.util.ResourceBundle;	// Used for location of windows in memory

import javax.xml.parsers.ParserConfigurationException;	// Exception for bad parser handling

import org.xml.sax.SAXException;		// Exception for writer error

import application.Main;		// Import for the main classes methods
import javafx.collections.ObservableList;		//  Used for the observable lists
import javafx.event.ActionEvent;	// Import for JavaFX event handling
import javafx.fxml.FXML;	// Import for FXML file format
import javafx.fxml.FXMLLoader;	// Import for FXML loaders
import javafx.scene.Scene;		// Used for the base scene
import javafx.scene.chart.PieChart;		// Used for the pie chart
import javafx.scene.control.Alert;	// Used for easy pop up windows
import javafx.scene.control.Alert.AlertType;	// Used for customizing alerts
import javafx.scene.control.Button;		// Used for button GUI handling
import javafx.scene.control.ButtonType;		// Used to control popup interaction
import javafx.scene.control.Label;		// Used for text label handling
import javafx.scene.control.RadioButton;	// Used for the radio button options
import javafx.scene.control.ToggleGroup;	// USed to group together radio buttons
import javafx.scene.layout.AnchorPane;	// The base pane or windows
import javafx.stage.Stage;		// The base stage for the panes to be outputted to


public class FinanceWindow extends Main
{
	/*
	 * Import Section
	 */
	@FXML
	private ResourceBundle resources;		// Location of FXML in computer
	
    @FXML
    private URL location;					// Location in storage of FXML
    
    @FXML
    private PieChart financeChart;		// The Pie Chart for all the money data

    @FXML
    private Label totalLabel;			// the label for the total amount of money

    @FXML
    private Button homeButton;			// The button to return to the main menu

    @FXML
    private RadioButton quarterly;		// Radio button to view quarterly income

    @FXML
    private ToggleGroup timeFrame;		// The Group of all of the radio buttons

    @FXML
    private RadioButton monthly;		// Radio button for monthly scope

    @FXML
    private RadioButton weekly;			// Radio button for weekly scope

    @FXML
    private Button applyTime;			// Button to view selected time frame	
    
    @FXML		
    private Label selectError;			// Error for no scope selected
    
    /*
     * Decimal formatter for two decimal points
     */
    DecimalFormat df2 = new DecimalFormat(".##");
    
    public void setValues(String total, ObservableList<PieChart.Data> data)
    /*
     * Precondition:  User wants to see company finances
     * Postcondition: Window is populated with values and pie chart is initialized
     */
    {
    	/*
    	 * If there is no completed trips
    	 */
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
			/*
			 * If user confirms dialog
			 */
			if(result.get() == ButtonType.OK)
			{
				alert.close();		// Closes the alert
			}
			/*
			 * Else the user closes out of the popup
			 */
			else
			{
				alert.close();		// Closes the alert
			}
    	}
    	/*
    	 * Else there are completed trips
    	 */
    	else
    	{
    		/*
    		 * Sets the pie chart data
    		 */
    		financeChart.getData().clear();
        	totalLabel.setText(total);
        	financeChart.setData(data);
    	}
    }
    
    @FXML
    void applyTime(ActionEvent event) throws NumberFormatException, ParserConfigurationException, SAXException, IOException 
    /*
     * Precondition:  User clicks on the apply button
     * Postcondition: Changes the data displayed depending on the scope selected
     */
    {	
    	selectError.setText("");		// gets rid of any error messages
    	
    	/*
    	 * If quarterly scope is selected
    	 */
    	if (quarterly.isSelected())
    	{
    		/*
    		 * If there are no trips in the next quarter
    		 */
    		if (getRevenue(3) == 0)
    		{
    			/*
    			 * Makes a new pop-up dialog box
    			 */
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Error Message");
    			alert.setHeaderText("Error");
    			alert.setContentText("There are no trips within the next quarter so no projections " +
    								 "can be made.");
    			Optional<ButtonType> result = alert.showAndWait();
    			 
    			/*
    			 * If statement to continue
    			 */
    			if (result.get() == ButtonType.OK)
    			{
    				alert.close();		// closes the alert if cancel is pressed
    			} 
    			/*
    			 * Else continue
    			 */
    			else 
    			{ 
    			    alert.close();	   // closes the alert if cancel is pressed
    			}	
    		}
    		/*
    		 * Else there are
    		 */
    		else
    		{
    			totalLabel.setText("$ " + Double.parseDouble(df2.format(getRevenue(3))));
        		financeChart.setData(getPieChart(3));
    		}
    	}
    	/*
    	 * If monthly scope is selected
    	 */
    	else if (monthly.isSelected())
    	{
    		/*
    		 * If there are no trips in the next month
    		 */
    		if (getRevenue(2) == 0)
    		{
    			/*
    			 * Makes a new pop-up dialog box
    			 */
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Error Message");
    			alert.setHeaderText("Error");
    			alert.setContentText("There are no trips within the next month so no projections " +
    								 "can be made.");
    			Optional<ButtonType> result = alert.showAndWait();
    			 
    			/*
    			 * If statement to continue
    			 */
    			if (result.get() == ButtonType.OK)
    			{
    				alert.close();		// closes the alert if cancel is pressed
    			} 
    			/*
    			 * Else continue
    			 */
    			else 
    			{ 
    			    alert.close();	   // closes the alert if cancel is pressed
    			}	
    		}
    		/*
    		 * Else there are
    		 */
    		else 
    		{
    			totalLabel.setText("$ " + Double.parseDouble(df2.format(getRevenue(2))));
        		financeChart.setData(getPieChart(2));
    		}
    	}
    	/*
    	 * If weekly scope is selected
    	 */
    	else if (weekly.isSelected())
    	{
    		/*
    		 * If there are no trips in the next week
    		 */
    		if (getRevenue(1) == 0)
    		{
    			/*
    			 * Makes a new pop-up dialog box
    			 */
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Error Message");
    			alert.setHeaderText("Error");
    			alert.setContentText("There are no trips within the next week so no projections " +
    								 "can be made.");
    			Optional<ButtonType> result = alert.showAndWait();
    			 
    			/*
    			 * If statement to continue
    			 */
    			if (result.get() == ButtonType.OK)
    			{
    				alert.close();		// closes the alert if cancel is pressed
    			} 
    			/*
    			 * Else continue
    			 */
    			else 
    			{ 
    			    alert.close();	   // closes the alert if cancel is pressed
    			}	
    		}
    		/*
    		 * Else there are
    		 */
    		else
    		{
    			totalLabel.setText("$ " + Double.parseDouble(df2.format(getRevenue(1))));
        		financeChart.setData(getPieChart(1));
    		}
    	}
    	/*
    	 * Else there is no button selected
    	 */
    	else 
    	{
    		selectError.setText("Select a scope first!!");		// Prints error
    	}
    }
   
    
    @FXML
    void returnHome(ActionEvent event) throws IOException 
    /*
     * Precondition:  User clicks on the home button
     * Postcondition: If user confirms popup then they are sent to the main menu
     */
    {
    	/*
    	 * Loads and diaplays the main menu
    	 */
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
    /*
     * Precondition:  There is an error injecting the GUI elements
     * Postcondition: Error message is spit out
     */
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
