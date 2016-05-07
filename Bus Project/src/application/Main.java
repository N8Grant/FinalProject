package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import application.model.Bus;
import application.model.Trip;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application 
{
	protected Stage stage;
	final static int ABCBUSSES = 14;
	
	/*
     * The data as an observable list of Trips.
     */
	public static ObservableList<Trip> tripData = FXCollections.observableArrayList();
	protected final DateTimeFormatter mdy = DateTimeFormatter.ofPattern("MM-dd-yyyy");
	
	@Override
	public void start(Stage primaryStage) throws IOException
	{
		createFile();	// makes sure that there is a file that exist
	
		/*
		 * Loads the main menu
		 */
		FXMLLoader loader;
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
			e.printStackTrace();
		}
	}
	
	public ObservableList<String> getAllNames(ObservableList <Trip> list, int sort)
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
							res = str1.compareTo(str2);		// compare to see if they are
															// equal regaurdless of case
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

	public Boolean checkName (String name)
	{
		Boolean t = false;	// temp bool value initialized to false
		
		/*
		 * If the XML is empty
		 */
		if (getAllNames(fetchXML(), 0).isEmpty())
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
			for (String nm: getAllNames(fetchXML(), 0))
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

	public String getBusses(LocalDate depart, LocalDate ret, int grpSz)
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
		for (Bus bus: getDates(fetchXML()))
		{
			/*
			 * If depart date is after depart date or equal to it
			 * 							or
			 * If arrive is before the return date or equal to it
			 */
			if ((bus.getDepart().isAfter(depart) || 
				 bus.getDepart().isEqual(depart)) &&
				(bus.getRet().isBefore(ret) ||
				 bus.getRet().isEqual(ret)))
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
				busNumbers = temp.get(i) + "";	// first bus available
			}
			else
			{
				/*
				 * Try to get next bus in list
				 */
				try
				{
					busNumbers = busNumbers + ", " + temp.get(i);	// , next bus
				}
				/**
				 * 
				 * 
				 * NEEDS WORK
				 * 
				 * 
				 */
				
				/*
				 * Catch if there are no more ABC Busses left
				 */
				catch (IndexOutOfBoundsException ex)
				{
					busNumbers =  "" + busNumbers;
					getSubRent((bussesNeeded - 1));
					//", You are going to need " +
					//(bussesNeeded - i) + " more busses from ABD Busses";
				}
			}	
		}
		return busNumbers;	// return string of bus numbers
	}
	
	/*
	 * 
	 * NEEDS WORK
	 * 
	 */
	public String getSubRent (int subNeeded)
	{
		
		return null;
	}
	
	public double getTripCost (int grpSz)
	{
		double cost = 0;	// Double for cost
		DecimalFormat df2 = new DecimalFormat(".##");
		/*
		 *  If the last bus is filled 
		 */
		if (grpSz % 20 >= 10)
		{
			cost = grpSz * 49.99;
		}
		/*
		 * Else it isn't
		 */
		else
		{
			cost = (grpSz - (grpSz % 20)) * 49.99;
		}
		return Double.parseDouble(df2.format(cost));	// Return cost
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
			return y;	// Return list of open dates
		}
		else
		{
			return y;
		}
		
	}
	
	public static ObservableList<Trip> getSpecificTrip(String name) throws ParserConfigurationException, 
																		   SAXException, IOException, 
																		   XPathExpressionException
	{
		/*
		 * ObservableList for one specific trip
		 */
		ObservableList<Trip> specTrip = FXCollections.observableArrayList();
		try
		{
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
			        	  Double.parseDouble(eElement.getElementsByTagName("Cost").item(0).getTextContent())
			        	  ));
			} 
			else 
			{
			   System.out.print("cant find it");
			}
		}
		catch (ParserConfigurationException par)
		{
			par.getMessage();
		}
		catch (SAXException sax)
		{
			sax.getMessage();
		}
		catch (IOException e)
		{
			e.getMessage();
		}
		catch (XPathExpressionException xp)
		{
			xp.getMessage();
		}
		return specTrip;
	}
	
	public static String getFilePath ()
	{
		String filename = "TripFile.xml"; // the name of the xml
		String workingDirectory = System.getProperty("user.dir"); // workspace location
			
		String absoluteFilePath = null;	// string for absolute file path
			
		//absoluteFilePath = workingDirectory + System.getProperty("file.separator") + filename;
		absoluteFilePath = workingDirectory + File.separator + "src" + File.separator + "application" 
				+ File.separator + "model" + File.separator + filename;
		return absoluteFilePath;
	}

	public File createFile()
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
				System.out.println("File is created!");
				return file;	// Return the new file
			} 
			/*
			 * Else return null
			 */
			else 
			{
				System.out.println("File is already existed!");
				return null;
			}
		} 
		/*
         * Catch can't create file
         */
		catch (IOException e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static ObservableList <Trip> fetchXML()
	{
		ObservableList <Trip> tripList = FXCollections.observableArrayList();	// get a list of trips
		
		try 
		{
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(getFilePath()); 
	        doc.getDocumentElement().normalize(); 
	        NodeList nList = doc.getElementsByTagName("Trip");

	        for (int trp = 0; trp < nList.getLength(); trp++)
	        {
				Node nNode = nList.item(trp);
				// System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) 
				{
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
				        	  Double.parseDouble(eElement.getElementsByTagName("Cost").item(0).getTextContent())
				        	  ));
				}
	        }
	        return tripList;
	
	    }
		catch (Exception e) 
		{
	        e.printStackTrace();
	        System.out.print("Error fetching data!!");
	        return tripList;
	    }
	}
	
	public void writeBlankXmlFile(ObservableList<Trip> list) throws IOException, SAXException
	{
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
	            
	            root.appendChild(Details);	// Append the new node to the root
	        }
	
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
		        try 
		        {
		            // location and name of XML file you can change as per need
		            FileWriter fos = new FileWriter(getFilePath());
		            StreamResult result = new StreamResult(fos);
		            aTransformer.transform(src, result);
		        } 
		        catch (IOException e) 
		        {
		            e.printStackTrace();
		        }
		    } 
		    catch (TransformerException ex) 
		    {
		        System.out.println("Error outputting document");
	
		    } 
	    }
		catch (ParserConfigurationException ex) 
		{
		    System.out.println("Error building document");
		}  
	}

	public void addToXML(ObservableList<Trip> list) throws IOException
	{	
		/*
		 * Try to open file and marshall to XML
		 */
		try
		{
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
	        catch (TransformerException te) 
	        {
	            System.out.println(te.getMessage());
	            // writeBlankXmlFile(tripData);
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

	/*
	 * Launches the program
	 */
	public static void main(String[] args) 
	{
		launch(args);
	}
}
