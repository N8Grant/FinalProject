/*
 * INFORMATOIN SECTION
 * Nathan Grant
 * Bus Program
 * June 17, 2016
 */
/**
 * Program Description:
 * This program is designed to manage busses for ABC Bus Company.
 * The program can book trips, keep track of availability, modify or 
 * delete trips, and even keep track of finances. 
 */

package application;	// Package of class

/*
 * Imports Section
 */
import java.io.File;	// Used to make a text file
import java.io.FileWriter;	// Used to write data to file
import java.io.IOException;		// Used for Input/Output error
import java.net.MalformedURLException;	// Error for URL handling 
import java.net.URL;		// URL class for java
import java.net.URLEncoder;		// Used to format strings to URLs
import java.text.DecimalFormat;		// Used to format decimals to two places
import java.time.LocalDate;		// Used for easy date formating and comparison
import java.time.format.DateTimeFormatter;	// Used to format Local Dates
import java.util.ArrayList;		// Used for ArrayList data type
import java.util.Comparator;	// Used to make comparator objects
import java.util.Optional;		// Used for popup windows

import javax.xml.parsers.DocumentBuilder;	// Main document class used for XML data
import javax.xml.parsers.DocumentBuilderFactory;	// Used to construct the document class
import javax.xml.parsers.ParserConfigurationException;	// Used if parser can't be constructed
import javax.xml.transform.OutputKeys;	// Used for XML formatting
import javax.xml.transform.Transformer;   // Used for transformer constructor
import javax.xml.transform.TransformerException;	// Error if format can't be recognized
import javax.xml.transform.TransformerFactory;   // Used to convert XML into DOM
import javax.xml.transform.dom.DOMSource;	// Used for the location of the XML file
import javax.xml.transform.stream.StreamResult;		// Transforms into FileOutputStream
import javax.xml.xpath.XPath;	// Used for XPath helper methods
import javax.xml.xpath.XPathConstants;		// Used for formating constraints
import javax.xml.xpath.XPathExpression;		// Used for file path expressions
import javax.xml.xpath.XPathExpressionException;	// Error indicating that it couldn't be made
import javax.xml.xpath.XPathFactory;	// Used to construct file saver object

import org.w3c.dom.Document;  // Used for XML parser interaction
import org.w3c.dom.Element;   // The elements of the main nodes, things that store primitive 
							  // data such as name etc.
import org.w3c.dom.Node;	// Basic medium for conversions to other parsers
import org.w3c.dom.NodeList;	// List of nodes from file
import org.xml.sax.SAXException;	// Used for file could not be read exception

import application.model.Bus;		// User class for the busses
import application.model.Trip;		// User class for the trips
import javafx.application.Application;	// Used for the override annotation on the launch
import javafx.collections.FXCollections;	// Precursor for observable lists
import javafx.collections.ObservableList;	// Used as a dynamic array list class for use in javaFX
import javafx.collections.transformation.SortedList;	// Used to sort observable lists
import javafx.fxml.FXMLLoader;	// Used to load FXML files
import javafx.scene.Parent;	// Used for window hierarchy 
import javafx.scene.Scene;	// Used for the base scene 
import javafx.scene.chart.PieChart;	// Used for pie chart API
import javafx.scene.control.Alert;		// Used for easy pop up windows
import javafx.scene.control.Alert.AlertType;	// Used for customizinf alerts
import javafx.scene.control.ButtonType;			// Used to control popup interaction
import javafx.stage.Stage;	// Used for the stage windows

public class Main extends Application 
{
	/*
	 * Variable Section
	 */
	protected Stage stage;	// The main stage of the window
	final static int ABCBUSSES = 20;	// Number of busses owned by ABC
	public static ObservableList<Trip> tripData = FXCollections.observableArrayList();		// List of trips
	protected final DateTimeFormatter mdy = DateTimeFormatter.ofPattern("MM-dd-yyyy");		// Date formatter
	
	@Override
	public void start(Stage primaryStage) throws IOException, ParserConfigurationException, SAXException, TransformerException
	/*
	 * Precondition:  User runs program
	 * Postcondition: Program is run
	 */
	{
		createFile();	// Makes sure that there is a file that exist
		filterFile();	// Filter file for trips that have been completed
	
		/*
		 * Loads the main menu
		 */
		FXMLLoader loader;	// Used to load FXML file
		loader = new FXMLLoader(Main.class.getResource("view/MainMenu.fxml"));
		
		/*
		 * Try to load the window and set title
		 */
		try 
		{
			Parent root;
			root = loader.load();
			Scene scene = new Scene(root);
			stage = primaryStage;
			stage.setTitle("Bus Program");
			stage.setScene(scene);
			stage.show();
		} 
		/*
		 * If window file cant be found
		 */
		catch (IOException e) 
		{
			e.printStackTrace();	// prints error to console
		}
	}
	
	public ObservableList<String> getAllNames(ObservableList <Trip> list, int sort)
	/*
	 * Precondition:  User wants a list of all of the names in a given list
	 * Postcondition: Returns a list of type string with all of the names from each trip
	 */
	{
		ObservableList<String> names = FXCollections.observableArrayList();	// List of names
		
		/*
		 * If there are no names in the list
		 */
		if (list.isEmpty() || list == null)
		{
			return names;
		}
		/*
		 * Else iterate through list and add into list of strings
		 */
		else
		{
			/*
			 * if no sort preference 
			 */
			if (sort == 0)
			{
				/*
				 * Iterate through list of trips
				 */
				for (Trip trp: list)
				{
					names.add(trp.getName());	// Add names to string list
				}
				return names;	// return list of all names
			}
			/*
			 * If user wants A to Z sort
			 */
			else if (sort == 1)
			{
				/*
				 * Iterates through trip list
				 */
				for (Trip trp: list)
				{
					names.add(trp.getName());	// add name to temp list
				}
				
				/*
				 * Makes a new comparator class to sort strings from A to Z
				 */
				Comparator<String> A_TO_Z = new Comparator <String> ()
				{
					/*
					 * Returns compares strings, returns 0 if they're the same 
					 */
					public int compare(String str1, String str2)
					{
						/*
						 * Result of comparison between two strings 
						 */
						int res = String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
						
						/*
						 * If strings are the the same
						 */
						if (res == 0)
						{
							res = str1.compareTo(str2);	 // compare to see if they are
														 // equal regardless of case
						}
						return res;		// return sort result
					}
				};	
				return names.sorted(A_TO_Z);	// return sorted list
			}
			/*
			 * If the user wants a Z to A sort
			 */
			else if (sort == 2)
			{
				/*
				 * Iterates through trip list
				 */
				for (Trip trp: list)
				{
					names.add(trp.getName());	// adds name to temp list
				}
				
				/*
				 * Makes a new comparator class to sort strings from Z to A
				 */
				Comparator <String> Z_TO_A = new Comparator <String>()
				{
					/*
					 * Returns compares strings, returns 0 if they're the same 
					 */
					public int compare(String str1, String str2)
					{
						/*
						 * Result of comparison between two strings 
						 */
						int res = String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
						/*
						 * If the two strings are equal
						 */
						if (res == 0)
						{
							res = str1.compareTo(str2);		// compare to see if they are
															// equal with consideration of case
								
						}
						return -res;	// returns the opposite of the sorted result
					}
				};
				return names.sorted(Z_TO_A);	// return sorted list of all names 
			}
		
			else if (sort == 3)
			{
				/*
				 * Makes a new comparator class to sort descending group size
				 */
				Comparator<Trip> DECENDING = new Comparator <Trip>()
				{
					/*
					 * Returns compares int's, returns 0 if they're the same 
					 */
					public int compare(Trip t1, Trip t2) 
					{
						/*
						 * Compares the two integers, returns either -1, 0, or 1
						 */
						int res = Integer.compare(t1.getGroupSize(),t2.getGroupSize());
						if (res == 0)
						{
							res = Integer.compare(t1.getGroupSize(),t2.getGroupSize());
						}
						return -res;	// return the opposite of res
					}
				};
				SortedList<Trip> temp = list.sorted(DECENDING);	// return sorted list of all trips 
			
				/*
				 * Iterates through trip list
				 */
				for (Trip trp: temp)
				{
					names.add(trp.getName());	// adds name to temp list
				}
				
				return names;	// return the list of names
			}
			else if (sort == 4)
			{
				/*
				 * Makes a new comparator class to sort descending group size
				 */
				Comparator<Trip> ASCENDING = new Comparator <Trip>()
				{
					/*
					 * Returns compares int's, returns 0 if they're the same 
					 */
					public int compare(Trip t1, Trip t2) 
					{
						int res = Integer.compare(t1.getGroupSize(),t2.getGroupSize());
						if (res == 0)
						{
							res = Integer.compare(t1.getGroupSize(),t2.getGroupSize());
						}
						return res;
					}
				};
				SortedList<Trip> temp = list.sorted(ASCENDING);	// return sorted list of all trips 
			
				/*
				 * Iterates through trip list
				 */
				for (Trip trp: temp)
				{
					names.add(trp.getName());	// adds name to temp list
				}
				
				return names;	// Return list of sorted names
			}
			/*
			 * Else just return list of names unsorted
			 */
			else
			{
				/*
				 * Iterate through the list of trips
				 */
				for (Trip trp: list)
				{
					names.add(trp.getName());
				}
				return names;	// Return a list of unsorted names
			}
		}	
	}

	public Boolean checkName (String name) throws ParserConfigurationException, SAXException, IOException
	/*
	 * Precondition:  User wants to see if the inputed name was valid or used before
	 * Postcondition: Returns true if the name has been used or false if it hasn't
	 */
	{
		Boolean t = false;	// temp bool value initialized to false
		
		/*
		 * If the XML is empty
		 */
		if (getAllNames(fetchCurrentXML(), 0).isEmpty())
		{
			return t;	// return false
		}
		/*
		 * Else is it is populated
		 */
		else
		{
			/*
			 * Iterate through the list of names
			 */
			for (String nm: getAllNames(fetchCurrentXML(), 0))
			{
				/*
				 * If name in list is equal to checked name
				 */
				if (nm.equals(name))
				{
					t = true;	// set t equal to true
				}	
			}
		}
		return t;	// return bool value
	}

	public String getBusses(LocalDate depart, LocalDate ret, int grpSz) throws ParserConfigurationException, SAXException, IOException
	/*
	 * Precondition:  User wants to get the available busses for a trip
	 * Postcondition: String of all bus numbers for the trip is sent back,
	 * 				  returns null if there are no busses left
	 */
	{	
		/*
		 * A temporary list of integers representing all of the busses
		 */
		ArrayList <Integer> temp = new ArrayList <Integer>();
		
		/*
		 * For loop to insert bus numbers into list
		 */
		for (int i = 1; i <= ABCBUSSES; i++)
		{
			temp.add(i);
		}
		
		/*
		 * For loop to check availability during specified dates
		 */
		for (Bus bus: getDates(fetchCurrentXML()))
		{
			/*
			 * If depart date is after depart date or equal to it
			 * 							and
			 * If arrive is before the return date or equal to it
			 */
			if ((bus.getDepart().isAfter(depart) || 
				 bus.getDepart().isEqual(depart)) &&
			    (bus.getRet().isBefore(ret) ||
				 bus.getRet().isEqual(ret)))
			{
				temp.remove(new Integer(bus.getBusNumber())); // remove bus number
			}
			/*
			 * Else if the depart date is equal the return date and vise versa
			 */
			else if (bus.getDepart().isEqual(ret) || 
					 bus.getRet().isEqual(depart))
			{
				temp.remove(new Integer(bus.getBusNumber())); // remove bus number
			}
		}

		/*
		 * Int for busses needed for the trip
		 */
		int bussesNeeded = Integer.parseInt(getBussesNeeded(grpSz));
			
		String busNumbers = "";	// Blank string 
		
		/*
		 * For loop to add busses needed
		 */
		for (int i = 0; i < bussesNeeded; i++)
		{
			/*
			 * If its the first iteration
			 */
			if (i == 0)
			{
				/*
				 * Try to add firt element to string of bus numbers
				 */
				try
				{
					/* 
					 * If array is empty
					 */
					if (temp.isEmpty())
					{
						insufficentBusses();	// Alert to user
						return null;	// returns null / no busses
					}
					/*
					 * Else add to begining of string
					 */
					else
					{
						busNumbers = temp.get(i) + "";	// first bus available
					}
				}
				/*
				 * Catch index out of bouds
				 */
				catch (IndexOutOfBoundsException e)
				{
					insufficentBusses();	// Alert to user
					return null;	// returns null / no busses
				}
			}
			/*
			 * Else the loop has executed at least once
			 */
			else
			{
				/*
				 * Try to get next bus in list
				 */
				try
				{
					busNumbers = busNumbers + ", " + temp.get(i);	// , next bus
				}
				
				/*
				 * Catch if there are no more ABC Busses left
				 */
				catch (IndexOutOfBoundsException ex)
				{
					busNumbers =  "" + busNumbers;	// Adds bus numbers to front of string
					insufficentBusses();	// Alert to user
					return null;			// returns null / no busses
				}
			}	
		}
		return busNumbers;	// return string of bus numbers
	}
	
	public void insufficentBusses() 
	/*
	 * Precondition:  There aren't enough busses to fulfill users request
	 * Postcondition: Shows error to user to change date
	 */
	{
		/*
		 * Makes a new pop-up dialog box
		 */
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setHeaderText("Insufficent Busses");
		alert.setContentText("There arent enough of our busses to complete this transcation so " +
							 "another date must be chosen.");
		Optional<ButtonType> result = alert.showAndWait();
		 
		/*
		 * If statement to cancel transaction
		 */
		if (result.get() == ButtonType.OK)
		{
			alert.close();		// closes the alert if cancel is pressed
		} 
		else 
		{ 
		    alert.close();	   // closes the alert if cancel is pressed
		}	
	}

	public ObservableList<PieChart.Data> getPieChart(int sort) throws ParserConfigurationException, SAXException, IOException
	/*
	 * Precondition:  User requests financial data 
	 * Postcondition: 
	 */
	{
		/*
		 * ObservableList of pie chart data
		 */
		ObservableList<PieChart.Data> dat = FXCollections.observableArrayList();
		
		/*
		 * If sort option is 0
		 */
		if (sort == 0)
		{
			/*
			 * If there are completed trips
			 */
			if (fetchCompletedXML() != null)
			{
				/*
				 * Iterates through completed trips
				 */
				for (Trip trp: fetchCompletedXML())
				{
					/*
					 * Adds trip to pie chart data
					 */
					dat.add(new PieChart.Data(trp.getName(), trp.getTripCost()));
				}
			}
			/*
			 * Else there are no completed trips
			 */
			else
			{
				return null;	// retrn null
			}
		}
		/*
		 * If user selects weekly revenue
		 */
		else if (sort == 1)
		{
			LocalDate startWeek = LocalDate.now().minusDays(7); // Date 7 days before todays date
			LocalDate endWeek = LocalDate.now().plusDays(7);    // Date 7 days after todays date
			
			/*
			 * Iterates through current trips
			 */
			for (Trip trp: fetchCurrentXML())
			{
				/*
				 * If trip occurs within the next week or in the past week
				 */
				if ((trp.getDepart().isAfter(startWeek) || 
					trp.getDepart().isEqual(startWeek)) && 
					(trp.getArrive().isBefore(endWeek)	||
					 trp.getArrive().isEqual(endWeek)))
				{
					dat.add(new PieChart.Data(trp.getName(), trp.getTripCost()));	// adds data to pie chart
				}
			}
			
			/*
			 * Iterates through completed trips
			 */
			for (Trip trp: fetchCompletedXML())
			{
				/*
				 * If trip occurs within the next week or in the past week
				 */
				if ((trp.getDepart().isAfter(startWeek) || 
					trp.getDepart().isEqual(startWeek)) && 
					(trp.getArrive().isBefore(endWeek)	||
					 trp.getArrive().isEqual(endWeek)))
				{
					dat.add(new PieChart.Data(trp.getName(), trp.getTripCost()));	// adds data to pie chart
				}
			}
		}
		/*
		 * If user selects month revenue
		 */
		else if (sort == 2)
		{
			LocalDate startMonth = LocalDate.now().minusMonths(1);	// Gets date one month before todays date
			LocalDate endMonth = LocalDate.now().plusMonths(1);		// Gets date one month after todays date
			
			/*
			 * Iterates through current trips
			 */
			for (Trip trp: fetchCurrentXML())
			{
				/*
				 * If trip occurs within the next month or in the past month
				 */
				if ((trp.getDepart().isAfter(startMonth) || 
					 trp.getDepart().isEqual(startMonth)) && 
					(trp.getArrive().isBefore(endMonth)	||
					 trp.getArrive().isEqual(endMonth)))
				{
					dat.add(new PieChart.Data(trp.getName(), trp.getTripCost()));	// adds data to pie chart
				}
			}
			/*
			 * Iterates through completed trips
			 */
			for (Trip trp: fetchCompletedXML())
			{
				/*
				 * If trip occurs within the next month or in the past month
				 */
				if ((trp.getDepart().isAfter(startMonth) || 
					trp.getDepart().isEqual(startMonth)) && 
					(trp.getArrive().isBefore(endMonth)	||
					 trp.getArrive().isEqual(endMonth)))
				{
					dat.add(new PieChart.Data(trp.getName(), trp.getTripCost()));	// adds data to pie chart
				}
			}
		}
		/*
		 * If user selects quarter revenue
		 */
		else if (sort == 3)
		{
			LocalDate startMonth = LocalDate.now().minusMonths(3);	// Gets date three month before todays date
			LocalDate endMonth = LocalDate.now().plusMonths(3);		// Gets date three month before todays date
			
			/*
			 * Iterates through current trips
			 */
			for (Trip trp: fetchCurrentXML())
			{
				/*
				 * If trip occurs within the next months or in the past months
				 */
				if ((trp.getDepart().isAfter(startMonth) || 
					trp.getDepart().isEqual(startMonth)) && 
					(trp.getArrive().isBefore(endMonth)	||
					 trp.getArrive().isEqual(endMonth)))
				{
					dat.add(new PieChart.Data(trp.getName(), trp.getTripCost()));	// adds data to pie chart
				}
			}
			/*
			 * Iterates through completed trips
			 */
			for (Trip trp: fetchCompletedXML())
			{
				/*
				 * If trip occurs within the next months or in the past months
				 */
				if ((trp.getDepart().isAfter(startMonth) || 
					trp.getDepart().isEqual(startMonth)) && 
					(trp.getArrive().isBefore(endMonth)	||
					 trp.getArrive().isEqual(endMonth)))
				{
					dat.add(new PieChart.Data(trp.getName(), trp.getTripCost()));	// adds data to pie chart
				}
			}
		}
		return dat;		// returns pie chart
	}
	
	public double getRevenue(int sort) throws ParserConfigurationException, SAXException, IOException
	/*
	 * Precondition:  Get revenue of trips in a given scope
	 * Postcondition: Returns double of total revenue in a given scope
	 */
	{
		double revenue = 0;		// Total revenue
		DecimalFormat df2 = new DecimalFormat(".##");	// Formatter for double for 2 decimal points
		
		/*
		 * If there is no sort preference
		 */
		if (sort == 0)
		{
			/*
			 * Iterates through completed trips
			 */
			for (Trip trp: fetchCompletedXML())
			{
				revenue += trp.getTripCost();	// adds trip cost to revenue
			}
		}
		/*
		 * If  user wants weekly sort
		 */
		else if (sort == 1)
		{
			LocalDate startWeek = LocalDate.now().minusDays(7);
			LocalDate endWeek = LocalDate.now().plusDays(7);
			
			/*
			 * Iterates through current trips
			 */
			for (Trip trp: fetchCurrentXML())
			{
				/*
				 * If trip occurs within the next week or in the past week
				 */
				if ((trp.getDepart().isAfter(startWeek) || 
					trp.getDepart().isEqual(startWeek)) && 
					(trp.getArrive().isBefore(endWeek)	||
					 trp.getArrive().isEqual(endWeek)))
				{
					revenue += trp.getTripCost();	// adds data to pie chart
				}
				
			}
			
			/*
			 * Iterates through completed trips
			 */
			for (Trip trp: fetchCompletedXML())
			{
				/*
				 * If trip occurs within the next week or in the past week
				 */
				if ((trp.getDepart().isAfter(startWeek) || 
					trp.getDepart().isEqual(startWeek)) && 
					(trp.getArrive().isBefore(endWeek)	||
					 trp.getArrive().isEqual(endWeek)))
				{
					revenue += trp.getTripCost();	// adds data to pie chart
				}
			}
		}
		/*
		 * If  user wants month sort
		 */
		else if (sort == 2)
		{
			LocalDate startMonth = LocalDate.now().minusMonths(1);
			LocalDate endMonth = LocalDate.now().plusMonths(1);
			
			/*
			 * Iterates through current trips
			 */
			for (Trip trp: fetchCurrentXML())
			{
				/*
				 * If trip occurs within the next month or in the past month
				 */
				if ((trp.getDepart().isAfter(startMonth) || 
					trp.getDepart().isEqual(startMonth)) && 
					(trp.getArrive().isBefore(endMonth)	||
					 trp.getArrive().isEqual(endMonth)))
				{
					revenue += trp.getTripCost();	// adds data to pie chart
				}
			}
			
			/*
			 * Iterates through completed trips
			 */
			for (Trip trp: fetchCompletedXML())
			{
				/*
				 * If trip occurs within the next month or in the past month
				 */
				if ((trp.getDepart().isAfter(startMonth) || 
					trp.getDepart().isEqual(startMonth)) && 
					(trp.getArrive().isBefore(endMonth)	||
					 trp.getArrive().isEqual(endMonth)))
				{
					revenue += trp.getTripCost();	// adds data to pie chart
				}
			}
		}
		/*
		 * If  user wants quarter sort
		 */
		else if (sort == 3)
		{
			LocalDate startMonth = LocalDate.now().minusMonths(3);
			LocalDate endMonth = LocalDate.now().plusMonths(3);
			
			/*
			 * Iterates through completed trips
			 */
			for (Trip trp: fetchCurrentXML())
			{
				/*
				 * If trip occurs within the next month or in the past month
				 */
				if ((trp.getDepart().isAfter(startMonth) || 
					trp.getDepart().isEqual(startMonth)) && 
					(trp.getArrive().isBefore(endMonth)	||
					 trp.getArrive().isEqual(endMonth)))
				{
					revenue += trp.getTripCost();	// adds data to pie chart
				}
			}
			
			/*
			 * Iterates through completed trips
			 */
			for (Trip trp: fetchCompletedXML())
			{
				/*
				 * If trip occurs within the next month or in the past month
				 */
				if ((trp.getDepart().isAfter(startMonth) || 
					trp.getDepart().isEqual(startMonth)) && 
					(trp.getArrive().isBefore(endMonth)	||
					 trp.getArrive().isEqual(endMonth)))
				{
					revenue += trp.getTripCost();	// adds data to pie chart
				}
			}
		}
		return Double.parseDouble(df2.format(revenue));		// return revenue as a double with 2 decimal points
	}
	
	public double getTripCost (int grpSz, double dist)
	/*
	 * Precondition:  User wants the total cost for their trip
	 * postcondition: Returns double of trip cost, which is adjusted 
	 * 				  according to the distance of the trip
	 */
	{
		double total = 0;	// total cost for the trip
		double cost = 49.99;	// initial cost per tiket
		DecimalFormat df2 = new DecimalFormat(".##");
		
		/*
		 * If trip dstance is over 100 kilometers
		 */
		if (dist > 100) 
		{
			double over = dist - 100;	// the kilometers over 100
			
			cost =  ((2.13/grpSz) * over) + cost;	// Cost of gas divided by the number of people
													// times the distance over 100, all added to
													// the regular ticket cost
		}
		
		total = grpSz * cost;	// group size times cost per ticket
		
		return Double.parseDouble(df2.format(total));	// Return cost
	}
	
	public int getRefund (int grpSz)
	/*
	 * Precondition:  Takes in the raw group size
	 * Postcondition: Group size is re-adjusted to discard people
	 * 				  who can't fit onto another bus
	 */
	{
		int lastBus = grpSz % 20;	// temp variable of remainder
		
		/*
		 * If last bus has more than zero people and less than 10
		 */
		if (lastBus < 10 && lastBus > 0)
		{
			/*
	    	 * Alert to show finalize success
	    	 */
	    	Alert alert = new Alert(AlertType.WARNING);
	    	alert.setTitle("Confirmation Dialog");
			alert.setHeaderText("Refund Warning");
			alert.setContentText("There arent enough people to occupy another bus so their "
							   + "ticket costs wont be included in the total!!");
			Optional<ButtonType> result = alert.showAndWait();
			
			/*
			 * If User confirms pop-up dialog box
			 */
			if (result.get() == ButtonType.OK)
			{
				alert.close();	// closes the alert if cancel is pressed

			}
			/*
			 * Else close the alert
			 */
			else
			{
				alert.close();	   // closes the alert if cancel is pressed
			}
			
			grpSz = grpSz - lastBus;	// Subtracts the number of people in the last bus
		}
		
		return grpSz;	// Return the adjusted group size
	}
	
	public String getStartKey () throws ParserConfigurationException, MalformedURLException,
										 SAXException, IOException
	/*
	 * Precondition:  The program needs a google formatted key for
	 * 				  "Taunton MA"
	 * Postcondition: Gets html request from google and draws geocode 
	 * 				  from the given xml file							
	 */
	{
		String start = null;	// Start key
		
		/*
		 * Formats XML from Google HTML request to be read by program
		 */
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new URL("https://maps.googleapis.com/maps/api/geocode/" +
		"xml?address=Taunton&key=AIzaSyCiYcQJ2lPsoZva8ZQ57VE3ac6eKLVCv1M").openStream());
		
        doc.getDocumentElement().normalize(); 	// Makes elements into a tree
        NodeList nList = doc.getElementsByTagName("result");	// Gets the node under the result element

        /*
         * Iterates through all of the results elements
         */
        for (int trp = 0; trp < nList.getLength(); trp++)
        {
			Node nNode = nList.item(trp);	// Gets the given item number in the node list
			
			/*
			 * If the sub node is an element
			 */
			if (nNode.getNodeType() == Node.ELEMENT_NODE) 
			{
				/*
				 * Casts the given node in the list to an element for 
				 * content handling
				 */
				 Element eElement = (Element) nNode;
				 
				 /*
				  * Gets the string value of the locations id
				  */
				 start = eElement.getElementsByTagName("place_id").item(0).getTextContent();
			}
        }
        
		return start;	// returns the geocode for Taunton
	}
	
	public ObservableList<String> getDestinationChoices(String dest) throws ParserConfigurationException, MalformedURLException, SAXException, IOException,
																		 TransformerException
	/*
	 * Precondition:  User needs suggestions for their destination
	 * Postcondition: Returns an ObservableList of all of the formal
	 * 				  addresses for the given string
	 */
	{
		/*
		 * ObservablelList of type string to contain all of the suggestions
		 */
		ObservableList<String> destination = FXCollections.observableArrayList(); 
		
		/*
		 * Used to take the given destination and format the string so that 
		 * the spaces are handled because they can't be used in an HTML address
		 */
		String encodeDest = URLEncoder.encode(dest, "UTF-8");
		
		/*
		 * Formats XML from Google HTML request to be read by program
		 */
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new URL("https://maps.googleapis.com/maps/api/geocode/" +
		"xml?address=" + encodeDest + "&key=AIzaSyCiYcQJ2lPsoZva8ZQ57VE3ac6eKLVCv1M").openStream());
	
        doc.getDocumentElement().normalize(); 		// Makes elements into a tree
        NodeList nList = doc.getElementsByTagName("result");	// Gets the node under the result element 

        /*
         * Iterates through the node list
         */
        for (int trp = 0; trp < nList.getLength(); trp++)
        {
			Node nNode = nList.item(trp);	// Gets the given item number in the node list
			
			/*
			 * If the sub node is an element
			 */
			if (nNode.getNodeType() == Node.ELEMENT_NODE) 
			{
				/*
				 * Casts the given node in the list to an element for 
				 * content handling
				 */
				 Element eElement = (Element) nNode; 
				 
				 /*
				  * Adds the formatted address of the suggestion to the 
				  * ObservableList
				  */
				 destination.add(eElement.getElementsByTagName("formatted_address").item(0).getTextContent());
			}
        }
        return destination;		// return list of suggestions
	}
	
	public String getDestinationKey(String dest) throws ParserConfigurationException, MalformedURLException, SAXException, IOException, TransformerException
	/*
	 * Precondition:  User has typed in their destination
	 * Postcondition: Takes the destination and returns the geocode
	 */
	{   
		String destKey = null;	// String for the destinations key
		
		/*
		 * Used to take the given destination and format the string so that 
		 * the spaces are handled because they can't be used in an HTML address
		 */
		String encodeDest = URLEncoder.encode(dest, "UTF-8");
		
		/*
		 * Formats XML from Google HTML request to be read by program
		 */
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new URL("https://maps.googleapis.com/maps/api/geocode/xml?address=" +
		encodeDest + "&key=AIzaSyCiYcQJ2lPsoZva8ZQ57VE3ac6eKLVCv1M").openStream());
		
        doc.getDocumentElement().normalize(); 	// Makes elements into a tree
        NodeList nList = doc.getElementsByTagName("result");	// Gets the node under the result element 

        /*
         * Iterates through the node list
         */
        for (int trp = 0; trp < nList.getLength(); trp++)
        {
			Node nNode = nList.item(trp);	// Gets the given item number in the node list
			
			/*
			 * If the sub node is an element
			 */
			if (nNode.getNodeType() == Node.ELEMENT_NODE) 
			{
				/*
				 * Casts the given node in the list to an element for 
				 * content handling
				 */
				 Element eElement = (Element) nNode; 
				 
				 /*
				  * Gets the string value of the locations id
				  */
				 destKey = eElement.getElementsByTagName("place_id").item(0).getTextContent();
			}
        }
		return destKey;		// returns the geocode for the destination
	}
	
	public double getTripDistance(String dest) throws ParserConfigurationException, MalformedURLException, SAXException, IOException, TransformerException
	/*
	 * Precondition:  The distance is needed to calculate the ticket cost
	 * Postcondition: Returns a double of the distance between Taunton
	 * 				  and the given destination in kilometers
	 */
	{
		String distance = null;		// String of the distance
	
		/*
		 * Formats XML from Google HTML request to be read by program
		 */
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new URL("https://maps.googleapis.com/maps/api/distancematrix/xml?origins=place_id:" +
		getStartKey() + "&destinations=place_id:"  + getDestinationKey(dest) +
		"&key=AIzaSyCiYcQJ2lPsoZva8ZQ57VE3ac6eKLVCv1M").openStream());
		
		doc.getDocumentElement().normalize(); 	// Makes elements into a tree
        NodeList nList = doc.getElementsByTagName("distance");	// Gets the node under the distance element 

        /*
         * Iterates through the node list
         */
		for (int trp = 0; trp < nList.getLength(); trp++)
        {
			Node nNode = nList.item(trp);	// Gets the given item number in the node list
			
			/*
			 * If the sub node is an element
			 */
			if (nNode.getNodeType() == Node.ELEMENT_NODE) 
			{
				/*
				 * Casts the given node in the list to an element for 
				 * content handling
				 */
				 Element eElement = (Element) nNode; 
				 
				 /*
				  * Gets the string value of the distance under element "text"
				  */
				 distance = eElement.getElementsByTagName("text").item(0).getTextContent();
			}
        }
		
		/*
		 * Format string to be converted to double
		 */
		int km = distance.indexOf("km");	// finds the index of the km stamp
		String stringDist = distance.substring(0, km - 1);	// Makes a substring of just the number, discarding the
															// km stamp so it can be converted
		stringDist = stringDist.replaceAll(",", "");	// Removes all the commas in the number
														// and replaces them with a blank charSequence
		return Double.parseDouble(stringDist);	// returns a parsed string to a type double
	}
	
	public String getBussesNeeded(int grpSz)
	/*
	 * Precondition:  Program doesn't have value for number of busses needed
	 * Postcondition: String value is passed back with number of busses 
	 * 				  needed in accordance to group size
	 */
	{
		int bussesNeeded = grpSz / 20;	// integer division to find busses needed
		
		/*
		 * If there is more than 10 people for another bus
		 */
		if (grpSz % 20 >= 10)
		{
			bussesNeeded++;		// Book another bus
		}
		
		return Integer.toString(bussesNeeded);	// bussesNeeded -> string
	}
	
	public ArrayList <Bus> getDates(ObservableList <Trip> trip)
	/*
	 * Precondition:  Program has data in type Trip but needs dates for type Bus
	 * Postcondition: List returned with all of the trip dates and bus numbers 
	 */
	{
		ArrayList <Bus> y = new ArrayList <Bus>();	// blank array list for busses
		
		/*
		 * If there are trips
		 */
		if (trip != null)
		{	
			/*
			 * Iterates through list of trips
			 */
			for (Trip trp: trip)
			{
				String delims = "[, ]+";	// Accepted separators are "," or space
				String[] str_array = trp.getBusNumbers().split(delims);
				
				/*
				 * Iterates through all of the busses used on the date
				 */
				for (int i = 0; i < str_array.length; i++)
				{
					y.add(new Bus(Integer.parseInt(str_array[i]), trp.getDepart(), trp.getArrive()));
				}
			}
			return y;	// Return list of all busses schedules 
		}
		else
		{
			return y;	// return blank list
		}	
	}
	
	public ObservableList<Trip> getTripsOnDate(LocalDate date) throws ParserConfigurationException, SAXException, IOException
	/*
	 * Precondition:  User wants to view trips on any given day
	 * Postcondition: Returns an ObservableList of all of the trips 
	 * 				  on the selected date
	 */
	{
		/*
		 * ObservableList of type trip 
		 */
		ObservableList<Trip> tripDates = FXCollections.observableArrayList();
		
		/*
		 * Iterates through all current trips
		 */
		for (Trip trp: fetchCurrentXML())
		{
			/*
			 * If the date is either in between a trip or equal to the depart
			 * or return date
			 */
			if ((date.isAfter(trp.getDepart()) || date.isEqual(trp.getDepart()))  &&
			    (date.isBefore(trp.getArrive()) || date.isEqual(trp.getArrive())))
			{
				tripDates.add(trp);		// add the trip to the list
			}
		}
		/*
		 * Iterates through all completed trips
		 */
		for (Trip trp: fetchCompletedXML())
		{
			/*
			 * If the date is either in between a trip or equal to the depart
			 * or return date
			 */
			if ((date.isAfter(trp.getDepart()) || date.isEqual(trp.getDepart()))  &&
			    (date.isBefore(trp.getArrive()) || date.isEqual(trp.getArrive())))
			{
				tripDates.add(trp);	
			}
		}
		return tripDates;	// add the trip to the list
	}
	
	public static ObservableList<Trip> getSpecificTrip(String name) throws ParserConfigurationException, 
																		   SAXException, IOException, 
																		   XPathExpressionException
	/*
	 * Precondition:  User just wants to view a specific trip
	 * Postcondition: Returns an ObservableList containing just 
	 * 				  the one element
	 */
	{
		/*
		 * ObservableList for one specific trip
		 */
		ObservableList<Trip> specTrip = FXCollections.observableArrayList();
		
		
		/*
		 * XML un-marshaller
		 */
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(getFilePath()); // url to your file
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
	
		/*
		 * Finds the element with the desired name
		 */
		XPathExpression expr = xpath.compile("/Trips/Trip/Name[text()='" + name + "']");
		
		/*
		 * Gets all of the nodes and makes them into a list
		 */
		NodeList nodeList = (NodeList)(expr.evaluate(doc, XPathConstants.NODESET));
			
		if (nodeList.getLength() == 1) 
		{
		    // we've found a 'name' element with value 'Trip'
		    Node parent = nodeList.item(0).getParentNode();
			    
		    Element eElement = (Element) parent; 
		    // This node is the <Trip> node
		    // use it to do anything you want, ie. get the child nodes and print their info
		    specTrip.add(new Trip(eElement.getElementsByTagName("Name").item(0).getTextContent(),
		        	  eElement.getElementsByTagName("ID").item(0).getTextContent(),
		        	  Integer.parseInt(eElement.getElementsByTagName("GroupSize").item(0).getTextContent()),
		        	  eElement.getElementsByTagName("BusNumbers").item(0).getTextContent(),
		        	  eElement.getElementsByTagName("Depart").item(0).getTextContent(),
		        	  eElement.getElementsByTagName("Return").item(0).getTextContent(),
		        	  Double.parseDouble(eElement.getElementsByTagName("Cost").item(0).getTextContent()),
		        	  Double.parseDouble(eElement.getElementsByTagName("Distance").item(0).getTextContent()),
		        	  eElement.getElementsByTagName("Destination").item(0).getTextContent()
		        	  ));
		} 
		/*
		 * Else file cant be found
		 */
		else 
		{
			/*
			 * Makes a new pop-up dialog box
			 */
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText("Cancel Transaction");
			alert.setContentText("Are you sure you want to stop this transaction??");
			Optional<ButtonType> result = alert.showAndWait();
			
			/*
			 * If user strikes the ok button
			 */
			if (result.get() == ButtonType.OK)
			{
				alert.close();	// closes the alert  
			}
			/*
			 * Else the user presses on any other button
			 */
			else
			{
				alert.close();	// closes the alert
			}
		}
		
		return specTrip;	// Returns the specific trip
	}
	
	public static String getFilePath ()
	/*
	 * Precondition:  User wants the file path of the current xml file
	 * Postcondition: Returns the file path of the xml file
	 */
	{
		String filename = "TripFile.xml"; 	// the name of the xml
		String workingDirectory = System.getProperty("user.dir"); 	// workspace location
			
		String absoluteFilePath = null;	// string for absolute file path
			
		//absoluteFilePath = workingDirectory + System.getProperty("file.separator") + filename;
		absoluteFilePath = workingDirectory + File.separator + "src" + File.separator + "application" 
				+ File.separator + "model" + File.separator + filename;
		
		return absoluteFilePath;	// Returns the absolute file path
	}

	public File createFile()
	/*
	 * Precondition:  Program is started 
	 * Postcondition: 
	 */
	{
		/*
		 * Try to create file
		 */
		try 
		{
			File file = new File(getFilePath());	// makes a new file 

			/*
			 * If new file is created
			 */
			if (file.createNewFile()) 
			{
				return file;	// Return the new file
			} 
			/*
			 * Else return null
			 */
			else 
			{
				return null;	// Don't return anything if file alreay exists
			}
		} 
		/*
         * Catch can't create file
         */
		catch (IOException e) 
		{
			return null;	// Doesn't return any value if there is a system error
		}
	}
	
	public ObservableList<Trip> fetchCompletedXML() throws ParserConfigurationException, SAXException, IOException
	/*
	 * Precondition:  User wants to see all of the trips that have already 
	 * 				  have been completed
	 * Postcondition: Returns a list of all trips that have already been completed
	 */
	{
		ObservableList <Trip> tripList = FXCollections.observableArrayList();	// Blank list of trips
		
		/*
		 * Formats XML to be read 
		 */
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(completedTripsFilePath()); 
	    
	    doc.getDocumentElement().normalize(); 	// Makes elements into a tree
        NodeList nList = doc.getElementsByTagName("Trip");	// Gets elements under the "Trip" node
        
        /*
         * Iterates through list of trip nodes
         */
        for (int trp = 0; trp < nList.getLength(); trp++)
	    {
        	
			Node nNode = nList.item(trp);	// Gets the given item number in the node list
				
			/*
			 * If the given node is an element 
			 */
			if (nNode.getNodeType() == Node.ELEMENT_NODE) 
			{
				/*
				 * Casts the given node in the list to an element for 
				 * content handling
				 */
				 Element eElement = (Element) nNode; 
					 
				 /*
				  * Adds info from xml element to trip list
				  */
				 tripList.add(new Trip(eElement.getElementsByTagName("Name").item(0).getTextContent(),
			        	  eElement.getElementsByTagName("ID").item(0).getTextContent(),
			        	  Integer.parseInt(eElement.getElementsByTagName("GroupSize").item(0).getTextContent()),
			        	  eElement.getElementsByTagName("BusNumbers").item(0).getTextContent(),
			        	  eElement.getElementsByTagName("Depart").item(0).getTextContent(),
			        	  eElement.getElementsByTagName("Return").item(0).getTextContent(),
			        	  Double.parseDouble(eElement.getElementsByTagName("Cost").item(0).getTextContent()),
			        	  Double.parseDouble(eElement.getElementsByTagName("Distance").item(0).getTextContent()),
			        	  eElement.getElementsByTagName("Destination").item(0).getTextContent()
			        	  ));
			}
	    }
        return tripList;   // returns list of trips
	}	
	
	public static ObservableList <Trip> fetchCurrentXML() throws ParserConfigurationException, SAXException, IOException
	/*
	 * Precondition:  User wants to see all of the trips that are scheduled
	 * Postcondition: Returns a list of all trips that haven't been completed yet
	 */
	{
		ObservableList <Trip> tripList = FXCollections.observableArrayList();	// Blank list of trips
		
		/*
		 * Formats XML to be read 
		 */
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(getFilePath()); 
	        
	    doc.getDocumentElement().normalize(); 	// Makes elements into a tree
	    NodeList nList = doc.getElementsByTagName("Trip");	// Gets the elements under the Trip element

	    /*
         * Iterates through list of trip nodes
         */
	    for (int trp = 0; trp < nList.getLength(); trp++)
	    {
			Node nNode = nList.item(trp);	// Gets the given item number in the node list
			
			/*
			 * If the given node is an element 
			 */
			if (nNode.getNodeType() == Node.ELEMENT_NODE) 
			{
				/*
				 * Casts the given node in the list to an element for 
				 * content handling
				 */
				 Element eElement = (Element) nNode; 
					 
				 /*
				  * Adds info from xml element to trip list
				  */
				 tripList.add(new Trip(eElement.getElementsByTagName("Name").item(0).getTextContent(),
			        	  eElement.getElementsByTagName("ID").item(0).getTextContent(),
			        	  Integer.parseInt(eElement.getElementsByTagName("GroupSize").item(0).getTextContent()),
			        	  eElement.getElementsByTagName("BusNumbers").item(0).getTextContent(),
			        	  eElement.getElementsByTagName("Depart").item(0).getTextContent(),
			        	  eElement.getElementsByTagName("Return").item(0).getTextContent(),
			        	  Double.parseDouble(eElement.getElementsByTagName("Cost").item(0).getTextContent()),
			        	  Double.parseDouble(eElement.getElementsByTagName("Distance").item(0).getTextContent()),
			        	  eElement.getElementsByTagName("Destination").item(0).getTextContent()
			        	  ));
			}
        }    
	    return tripList;		// returns list of trips
	}
	
	public String completedTripsFilePath()
	/*
	 * Precondition:  User wants the file path of the current xml file
	 * Postcondition: Returns the file path of the xml file
	 */
	{
		String filename = "CompletedFile.xml"; // the name of the xml
		String workingDirectory = System.getProperty("user.dir"); // workspace location
			
		String absoluteFilePath = null;	// string for absolute file path
			
		//absoluteFilePath = workingDirectory + System.getProperty("file.separator") + filename;
		absoluteFilePath = workingDirectory + File.separator + "src" + File.separator + "application" 
						+ File.separator + "model" + File.separator + filename;
		return absoluteFilePath;	// returns the aabsolut file path
	}
	
	public void filterFile() throws ParserConfigurationException, SAXException, IOException
	/*
	 * Precondition:  Some trips have already been completed in the time the program 
	 * 				  hasn't been executed
	 * Postcondition: Current file is filtered and completed trips are moved to another file
	 */
	{
		/*
		 * Temporary ObservableList for completed trips
		 */
		ObservableList<Trip> temp = FXCollections.observableArrayList();
		
		/*
		 * Iterates through Current trips
		 */
		for (Trip trp: fetchCurrentXML())
		{
			/*
			 * If the return date is before today's date
			 */
			if (trp.getArrive().isBefore(LocalDate.now()))
			{
				temp.add(trp);	// Add trip to temp trip list
				deleteTrip(trp.getName());	// Delete the trip from the current file
			}	
		}
		
		/*
		 * Try to parse file
		 */
		try
		{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
	        Document dom = documentBuilder.parse(completedTripsFilePath());
	        
	        Element root = dom.getDocumentElement();	// Get the main element of the file
	
	        /*
	         * Iterate through the list of trips
	         */
	        for (Trip old: temp)
	        {
	        	/*
	        	 * Get the last appended element of the file
	        	 */
	        	Element Details = dom.createElement("Trip");
	        	root.appendChild(Details);
	        	
	        	/*
	        	 * Create and add another name element
	        	 */
	        	Element name = dom.createElement("Name");
	            name.appendChild(dom.createTextNode(String.valueOf(old.getName())));
	            Details.appendChild(name);
	            
	            /*
	        	 * Create and add another ID element
	        	 */
	            Element id = dom.createElement("ID");
	            id.appendChild(dom.createTextNode(String.valueOf(old
	                    .getId())));
	            Details.appendChild(id);

	            /*
	        	 * Create and add another group size element
	        	 */
	            Element grp = dom.createElement("GroupSize");
	            grp.appendChild(dom.createTextNode(String.valueOf(old.getGroupSize())));
	            Details.appendChild(grp);
	            
	            /*
	        	 * Create and add another bus numbers element
	        	 */
	            Element bus = dom.createElement("BusNumbers");
	            bus.appendChild(dom.createTextNode(String.valueOf(old.getBusNumbers())));
	            Details.appendChild(bus);
	            
	            /*
	        	 * Create and add another depart element
	        	 */
	            Element dpt = dom.createElement("Depart");
	            dpt.appendChild(dom.createTextNode(String.valueOf(old.getDepart())));
	            Details.appendChild(dpt);
	            
	            /*
	        	 * Create and add another return element
	        	 */
	            Element arr = dom.createElement("Return");
	            arr.appendChild(dom.createTextNode(String.valueOf(old.getArrive())));
	            Details.appendChild(arr);
	            
	            /*
	             * Create and add another trip cost element
	             */
	            Element cst = dom.createElement("Cost");
	            cst.appendChild(dom.createTextNode(String.valueOf(old.getTripCost())));
	            Details.appendChild(cst);
	            
	            /*
	             * Create and add another trip distance element
	             */
	            Element dist = dom.createElement("Distance");
	            dist.appendChild(dom.createTextNode(String.valueOf(old.getTripDistance())));
	            Details.appendChild(dist);

	            /*
	             * Create and add another trip destination element
	             */
	            Element dest = dom.createElement("Destination");
	            dest.appendChild(dom.createTextNode(String.valueOf(old.getTripDestination())));
	            Details.appendChild(dest);
	            
		        root.appendChild(Details);		// Append the data to the end of the list		        
	        }
	        /*
	         * Try to save the new list to the file
	         */
	        try 
	        {
	        	// Instantiate new transformer objects
		        TransformerFactory tranFactory = TransformerFactory.newInstance();
		        Transformer transformer = tranFactory.newTransformer();
	
		        // format the XML nicely
		        transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
	
		        transformer.setOutputProperty(
		                "{http://xml.apache.org/xslt}indent-amount", "4");
		        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		      
		        /*
	             * Sends the DOM to the file
	             */
		        DOMSource source = new DOMSource(dom);
		     
		        StreamResult result = new StreamResult(completedTripsFilePath());
	            transformer.transform(source, result);
	        } 
	        catch (TransformerException te) 
	        {
	            System.out.println(te.getMessage());
	            writeBlankCompleteFile(tripData);
	        }
	    } 
		/*
		 * Catch corrupted file
		 */
	    catch (ParserConfigurationException pce)
	    {
	        System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
	        writeBlankCompleteFile(tripData);	// Write if XML file is blank
	    }
		/*
		 * This class can contain basic error or warning information 
		 * from either the XML parser or the application: a parser writer or 
		 * application writer can subclass it to provide additional functionality. 
		 */
		catch (SAXException sax)
		{
			System.out.print(sax.getMessage());	// Prints error to console
			writeBlankCompleteFile(tripData);	// Write if XML file is blank
		}
		/*
		 * Catch Input/Output file error
		 */
		catch (IOException e)
		{
			writeBlankCompleteFile(tripData);	// Write if XML file is blank
		}
	
	}
	
	public void writeBlankCompleteFile(ObservableList<Trip> list)
	/*
	 * Precondition:  There is no data in the completed file to append to
	 * Postcondition: Initializes the document and adds the element to the 
	 * 				  first position in the node list
	 */
	{
		/*
		 * Try to parse XML
		 */
		try
	    {
			/*
			 * XML file builder
			 */
	    	DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder build = dFact.newDocumentBuilder();
			Document doc = build.newDocument();
			
	        Element root = doc.createElement("Trips");	// Makes the Element "Trips"
	       
	        doc.appendChild(root);	// Makes root the child of the document itself
	        
	        /*
	         * Iterates through the list of trips
	         */
	        for (Trip dtl : list) 
	        {
	        	/*
	        	 * Gets the Individual trip child
	        	 */
		        Element Details = doc.createElement("Trip");
		        root.appendChild(Details);
		        
		        /*
		         * Outputs the the customers name to the xml file
		         */
	        	Element name = doc.createElement("Name");
	            name.appendChild(doc.createTextNode(String.valueOf(dtl
	                    .getName())));
	            Details.appendChild(name);
	            
	            /*
	             * Outputs the the customers ID to the xml file
	             */
	            Element id = doc.createElement("ID");
	            id.appendChild(doc.createTextNode(String.valueOf(dtl
	                    .getId())));
	            Details.appendChild(id);
	
	            /*
	             * Outputs the groups size
	             */
	            Element grp = doc.createElement("GroupSize");
	            grp.appendChild(doc.createTextNode(String.valueOf(dtl.getGroupSize())));
	            Details.appendChild(grp);
	            
	            /*
	             * Outputs the bus number
	             */
	            Element bus = doc.createElement("BusNumbers");
	            bus.appendChild(doc.createTextNode(String.valueOf(dtl.getBusNumbers())));
	            Details.appendChild(bus);
	            
	            /*
	             * Outputs the date of departure
	             */
	            Element dpt = doc.createElement("Depart");
	            dpt.appendChild(doc.createTextNode(String.valueOf(dtl.getDepart())));
	            Details.appendChild(dpt);
	            
	            /*
	             * Outputs the date of return
	             */
	            Element arr = doc.createElement("Return");
	            arr.appendChild(doc.createTextNode(String.valueOf(dtl.getArrive())));
	            Details.appendChild(arr);
	            
	            /*
	             * Outputs the cost of the trip
	             */
	            Element cst = doc.createElement("Cost");
	            cst.appendChild(doc.createTextNode(String.valueOf(dtl.getTripCost())));
	            Details.appendChild(cst);
	            
	            /*
	             * Create and add another trip distance element
	             */
	            Element dist = doc.createElement("Distance");
	            dist.appendChild(doc.createTextNode(String.valueOf(dtl.getTripDistance())));
	            Details.appendChild(dist);
	            
	            /*
	             * Create and add another trip distance element
	             */
	            Element dest = doc.createElement("Destination");
	            dest.appendChild(doc.createTextNode(String.valueOf(dtl.getTripDestination())));
	            Details.appendChild(dest);
	            
	            root.appendChild(Details);	// Append the new node to the root
	        }
	
	        /*
	         * Try to write the document to file
	         */
	        try
	        {
		        // Save the document to the disk file
		        TransformerFactory tranFactory = TransformerFactory.newInstance();
		        Transformer aTransformer = tranFactory.newTransformer();
		        /*
		         * Format the XML nicely
		         */
		        aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
	
		        aTransformer.setOutputProperty(
		                "{http://xml.apache.org/xslt}indent-amount", "4");
		        aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
		        DOMSource src= new DOMSource(doc);
		        
		        /*
		         * Try to write to given filepath
		         */
		        try 
		        {
		            // location and name of XML file you can change as per need
		            FileWriter fos = new FileWriter(completedTripsFilePath());
		            StreamResult result = new StreamResult(fos);
		            aTransformer.transform(src, result);
		        } 
		        /*
		         * File can't be written
		         */
		        catch (IOException e) 
		        {
		            e.printStackTrace();
		        }
		    } 
	        /*
	         * Error in transforming the document
	         */
		    catch (TransformerException ex) 
		    {
		        System.out.println("Error outputting document");
		    } 
	    }
		/*
		 * Error in building the document
		 */
		catch (ParserConfigurationException ex) 
		{
		    System.out.println("Error building document");
		}  
	}
	
	public void writeBlankXmlFile(ObservableList<Trip> list) throws IOException, SAXException
	/*
	 * Precondition:  There is no data in the current file to append to
	 * Postcondition: Initializes the document and adds the element to the 
	 * 				  first position in the node list
	 */
	{
		/*
		 * Try to build document
		 */
		try
	    {
			/*
			 * XML file builder
			 */
	    	DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder build = dFact.newDocumentBuilder();
			Document doc = build.newDocument();
			
	        Element root = doc.createElement("Trips");	// Finds the child "Trips"
	        doc.appendChild(root);
	        
	        /*
	         * Iterates through list of trips
	         */
	        for (Trip dtl : list) 
	        {
	        	/*
	        	 * Gets the Individual trip child
	        	 */
		        Element Details = doc.createElement("Trip");
		        root.appendChild(Details);
		        
		        /*
		         * Outputs the the customers name to the xml file
		         */
	        	Element name = doc.createElement("Name");
	            name.appendChild(doc.createTextNode(String.valueOf(dtl
	                    .getName())));
	            Details.appendChild(name);
	            
	            /*
	             * Outputs the the customers ID to the xml file
	             */
	            Element id = doc.createElement("ID");
	            id.appendChild(doc.createTextNode(String.valueOf(dtl
	                    .getId())));
	            Details.appendChild(id);
	
	            /*
	             * Outputs the groups size
	             */
	            Element grp = doc.createElement("GroupSize");
	            grp.appendChild(doc.createTextNode(String.valueOf(dtl.getGroupSize())));
	            Details.appendChild(grp);
	            
	            /*
	             * Outputs the bus number
	             */
	            Element bus = doc.createElement("BusNumbers");
	            bus.appendChild(doc.createTextNode(String.valueOf(dtl.getBusNumbers())));
	            Details.appendChild(bus);
	            
	            /*
	             * Outputs the date of departure
	             */
	            Element dpt = doc.createElement("Depart");
	            dpt.appendChild(doc.createTextNode(String.valueOf(dtl.getDepart())));
	            Details.appendChild(dpt);
	            
	            /*
	             * Outputs the date of return
	             */
	            Element arr = doc.createElement("Return");
	            arr.appendChild(doc.createTextNode(String.valueOf(dtl.getArrive())));
	            Details.appendChild(arr);
	            
	            /*
	             * Outputs the cost of the trip
	             */
	            Element cst = doc.createElement("Cost");
	            cst.appendChild(doc.createTextNode(String.valueOf(dtl.getTripCost())));
	            Details.appendChild(cst);
	            
	            /*
	             * Create and add another trip distance element
	             */
	            Element dist = doc.createElement("Distance");
	            dist.appendChild(doc.createTextNode(String.valueOf(dtl.getTripDistance())));
	            Details.appendChild(dist);
	            
	            /*
	             * Create and add another trip destination element
	             */
	            Element dest = doc.createElement("Destination");
	            dest.appendChild(doc.createTextNode(String.valueOf(dtl.getTripDestination())));
	            Details.appendChild(dest);
	            
	            root.appendChild(Details);	// Append the new node to the root
	        }
	
	        /*
	         * Try to write document to file
	         */
	        try
	        {
		        // Save the document to the disk file
		        TransformerFactory tranFactory = TransformerFactory.newInstance();
		        Transformer aTransformer = tranFactory.newTransformer();
		        /*
		         * Format the XML nicely
		         */
		        aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
	
		        aTransformer.setOutputProperty(
		                "{http://xml.apache.org/xslt}indent-amount", "4");
		        aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
		        DOMSource src= new DOMSource(doc);
		        
		        /*
		         * Try to write to given file location 
		         */
		        try 
		        {
		            // location and name of XML file you can change as per need
		            FileWriter fos = new FileWriter(getFilePath());
		            StreamResult result = new StreamResult(fos);
		            aTransformer.transform(src, result);
		        } 
		        /*
		         * Invalid location exception
		         */
		        catch (IOException e) 
		        {
		            e.printStackTrace();
		        }
		    } 
	        /*
	         * Catch writing error
	         */
		    catch (TransformerException ex) 
		    {
		        System.out.println("Error outputting document");
	
		    } 
	    }
		/*
		 * Catch error building document
		 */
		catch (ParserConfigurationException ex) 
		{
		    System.out.println("Error building document");
		}  
	}

	public void addToXML(ObservableList<Trip> list) throws IOException
	/*
	 * Precondition:  There is already data in the XML file 
	 * Postcondition: Given array list is appended to the bottom of the XML file
	 */
	{	
		/*
		 * Try to open file and marshall to XML
		 */
		try
		{
			/*
			 * XML file builder
			 */
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
	        Document dom = documentBuilder.parse(getFilePath());
	        
	        Element root = dom.getDocumentElement();	// Get the main element of the file
	
	        /*
	         * Iterate through the list of trips
	         */
	        for (Trip trp: list)
	        {
	        	/*
	        	 * Get the last appended element of the file
	        	 */
	        	Element Details = dom.createElement("Trip");
	        	root.appendChild(Details);
	        	
	        	/*
	        	 * Create and add another name element
	        	 */
	        	Element name = dom.createElement("Name");
	            name.appendChild(dom.createTextNode(String.valueOf(trp.getName())));
	            Details.appendChild(name);
	            
	            /*
	        	 * Create and add another ID element
	        	 */
	            Element id = dom.createElement("ID");
	            id.appendChild(dom.createTextNode(String.valueOf(trp
	                    .getId())));
	            Details.appendChild(id);

	            /*
	        	 * Create and add another group size element
	        	 */
	            Element grp = dom.createElement("GroupSize");
	            grp.appendChild(dom.createTextNode(String.valueOf(trp.getGroupSize())));
	            Details.appendChild(grp);
	            
	            /*
	        	 * Create and add another bus numbers element
	        	 */
	            Element bus = dom.createElement("BusNumbers");
	            bus.appendChild(dom.createTextNode(String.valueOf(trp.getBusNumbers())));
	            Details.appendChild(bus);
	            
	            /*
	        	 * Create and add another depart element
	        	 */
	            Element dpt = dom.createElement("Depart");
	            dpt.appendChild(dom.createTextNode(String.valueOf(trp.getDepart())));
	            Details.appendChild(dpt);
	            
	            /*
	        	 * Create and add another return element
	        	 */
	            Element arr = dom.createElement("Return");
	            arr.appendChild(dom.createTextNode(String.valueOf(trp.getArrive())));
	            Details.appendChild(arr);
	            
	            /*
	             * Create and add another trip cost element
	             */
	            Element cst = dom.createElement("Cost");
	            cst.appendChild(dom.createTextNode(String.valueOf(trp.getTripCost())));
	            Details.appendChild(cst);
	            
	            /*
	             * Create and add another trip distance element
	             */
	            Element dist = dom.createElement("Distance");
	            dist.appendChild(dom.createTextNode(String.valueOf(trp.getTripDistance())));
	            Details.appendChild(dist);
	            
	            /*
	             * Create and add another trip distance element
	             */
	            Element dest = dom.createElement("Destination");
	            dest.appendChild(dom.createTextNode(String.valueOf(trp.getTripDestination())));
	            Details.appendChild(dest);
	            
		        root.appendChild(Details);		// Append the data to the end of the list
		        
	        }
	        /*
	         * Try to save the new list to the file
	         */
	        try 
	        {
	        	// Instantiate new transformer objects
		        TransformerFactory tranFactory = TransformerFactory.newInstance();
		        Transformer transformer = tranFactory.newTransformer();
	
		        // format the XML nicely
		        transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
	
		        transformer.setOutputProperty(
		                "{http://xml.apache.org/xslt}indent-amount", "4");
		        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		      
		        /*
	             * Sends the DOM to the file
	             */
		        DOMSource source = new DOMSource(dom);
		        StreamResult result = new StreamResult(getFilePath());
	            transformer.transform(source, result);
	        } 
	        /*
	         * Catch document writing error
	         */
	        catch (TransformerException te) 
	        {
	            writeBlankXmlFile(tripData);
	        }
	    } 
		/*
		 * Catch corrupted file
		 */
	    catch (ParserConfigurationException pce)
	    {
	        System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
	        /*
	         * Try to write a new file
	         */
	        try 
	        {
				writeBlankXmlFile(tripData);	// Write if XML file is blank
			} 
	        /*
	         * Catch can't find file exception
	         */
	        catch (SAXException e) 
	        {
				e.printStackTrace();	// Print error to console
			}
	    }
		/*
		 * This class can contain basic error or warning information 
		 * from either the XML parser or the application: a parser writer or 
		 * application writer can subclass it to provide additional functionality. 
		 */
		catch (SAXException sax)
		{
			System.out.print(sax.getMessage());	// Prints error to console
			/*
			 * Try to write a blank file
			 */
			try 
			{
				writeBlankXmlFile(tripData);	// Write if XML file is blank
			} 
			/*
	         * Catch can't find file exception
	         */
			catch (SAXException e) 
			{
				e.printStackTrace();
			}
		}
		/*
		 * Catch Input/Output file error
		 */
		catch (IOException e)
		{
			/*
			 * Try to write blank file 
			 */
			try 
			{
				writeBlankXmlFile(tripData);	// Write if XML file is blank
			} 
			/*
	         * Catch can't find file exception
	         */
			catch (SAXException e1) 
			{
				e1.printStackTrace();	// Print error to console
			}
		}
	}
	
	public static void deleteTrip(String trpName) throws ParserConfigurationException, 
														 SAXException, IOException 
	/*
	 * Precondition:  User wants to remove a trip from the database
	 * Postcondition: User is deleted from the file 
	 */
	{
		/*
		 * Try to marshall XML file
		 */
		try 
		{
			/*
			 * XML file builder
			 */
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(getFilePath());

            NodeList tripList = doc.getElementsByTagName("Trip");	// Get elements under Trip tag

            /*
             * If there are trips
             */
            if (tripList != null && tripList.getLength() > 0) 
            {
            	/*
            	 * Iterates through the node list
            	 */
                for (int i = 0; i < tripList.getLength(); i++) 
                {
                    Node node = tripList.item(i);	// Gets the given item number in the node list
                    
                    /*
    				 * Casts the given node in the list to an element for 
    				 * content handling
    				 */
                    Element e = (Element) node;
                    
                    NodeList nodeList = e.getElementsByTagName("Name");	// gets data from "name"
                    String name = nodeList.item(0).getChildNodes().item(0)
                            .getNodeValue();		// Gets the first name in node list
                    
                    /*
                     * If name is equal to name of trip to be deleted
                     */
                    if (name.equals(trpName)) 
                    { 
                    	doc.getFirstChild().removeChild(node);		// removes element from node lsit
                    } 
                }
            } 
            /*
             * Try to write to file
             */
            try 
	        {
	        	// Instantiate new transformer objects
		        TransformerFactory tranFactory = TransformerFactory.newInstance();
		        Transformer transformer = tranFactory.newTransformer();
	
		        // format the XML nicely
		        transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
	
		        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		      
		        /*
	             * Sends the DOM to the file
	             */
		        DOMSource source = new DOMSource(doc);
		     
		        StreamResult result = new StreamResult(getFilePath());
	            transformer.transform(source, result);
	        } 
            /*
             * Catch writing error
             */
	        catch (TransformerException te) 
	        {
	            System.out.println(te.getMessage());	// Print error
	        }
        } 
		/*
		 * Catch document error
		 */
		catch (Exception e)
		{
            System.out.println(e);	// Print error
        }   
	}

	/*
	 * Launches the program
	 */
	public static void main(String[] args) 
	{
		launch(args);
	}
}