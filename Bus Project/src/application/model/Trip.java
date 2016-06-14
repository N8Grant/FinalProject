package application.model; // Package containing bus class


/*
 * Imports Section
 */
import java.time.LocalDate;		// Import for date manipulation
import java.time.format.DateTimeFormatter;	// Used to format the date a certain way

import javafx.beans.property.DoubleProperty;	// Used to make 
import javafx.beans.property.IntegerProperty;	// Used for integers in observable lists
import javafx.beans.property.ObjectProperty;		// Used for objects in observable lists
import javafx.beans.property.SimpleDoubleProperty;	// Used for doubles in observable lists
import javafx.beans.property.SimpleIntegerProperty;	// Used for integers in observable lists
import javafx.beans.property.SimpleObjectProperty;	// Used for objects in observable lists
import javafx.beans.property.SimpleStringProperty;	// Used for strings in observable lists
import javafx.beans.property.StringProperty;		// Used for longer strings

public class Trip
{
	/*
	 * Instance Variables
	 */
	private StringProperty organizationName;	// Name of organization
    private IntegerProperty groupSize;			// Size of group
    private SimpleStringProperty busNumbers;	// String of bus numbers
    private SimpleStringProperty tripNote;		// Note or reminder for trip
    private StringProperty ID;					// Unique id for trip
    private ObjectProperty<LocalDate> depart;	// Date of departure
    private ObjectProperty<LocalDate> arrive;	// Date of return
    private DoubleProperty tripCost;			// Cost of trip
    private SimpleStringProperty departString;	// String of depart date
    private SimpleStringProperty arriveString;	// String of return date
    private DoubleProperty tripDistance;		// Distance of the trip
    private SimpleStringProperty destination; 	// The name of the destination for the trip
    final DateTimeFormatter ymd = DateTimeFormatter.ofPattern("yyyy-MM-dd");		// year month day format
    final DateTimeFormatter mdy = DateTimeFormatter.ofPattern("MM-dd-yyyy");		// month day year format

    /**
     * Default constructor.
     */
    public Trip() 
    /*
     * Precondition:  User wants to make a blank Trip object
     * Postcondition: Trip object created with no values
     */
    {
        this(null, 0, null, null, null, 0, 0, null);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param Name
     * @param groupSize
     * @param departDate
     * @param arriveDate
     */
    public Trip(String Name, int grpSz, LocalDate dpt, LocalDate arr, String bsNm,
    			double cst, double dist, String dest) 
    /*
     * Precondition:  Program wants to make an object and has date values
     * 			      in LocalDate form 
     * Postcondition: Trip object is made with given values
     */
    {
    	this.organizationName = new SimpleStringProperty(Name);
    	this.groupSize = new SimpleIntegerProperty(grpSz);
    	String uniqueID = Integer.toString(100 + (int)(Math.random() *
    					  (1000 - 100) + 1));
    	this.ID = new SimpleStringProperty(uniqueID);
        this.depart = new SimpleObjectProperty<LocalDate>(dpt);
        this.arrive = new SimpleObjectProperty<LocalDate>(arr);
        this.busNumbers = new SimpleStringProperty(bsNm);
        this.tripNote = null;
        this.tripCost = new SimpleDoubleProperty(cst);
        this.tripDistance = new SimpleDoubleProperty(dist);
        this.destination = new SimpleStringProperty(dest);
    }
    
    public Trip(String Name, String ID, int grpSz, String busNm, String dpt,
    			String arr, double cst, double dist, String dest) 
    /*
     * Precondition:  Program wants to make an object and has date values
     * 			      in String form 
     * Postcondition: Trip object is made with given values
     */
    {
    	this.organizationName = new SimpleStringProperty(Name);
    	this.groupSize = new SimpleIntegerProperty(grpSz);
    	this.ID = new SimpleStringProperty(ID);
        this.depart = new SimpleObjectProperty<LocalDate>(LocalDate.parse(dpt));
        this.arrive = new SimpleObjectProperty<LocalDate>(LocalDate.parse(arr));
        this.busNumbers = new SimpleStringProperty(busNm);
        this.tripCost = new SimpleDoubleProperty(cst);
        this.tripDistance = new SimpleDoubleProperty(dist);
        this.destination = new SimpleStringProperty(dest);
    }

    public String getId() 
    /*
     * Precondition:  User wants the trip ID number
     * Postcondition: String with user ID is returned
     */
    {
        return ID.get();
    }

    public void setId(String id)
    /*
     * Precondition:  User wants to change the ID number
     * Postcondition: The trips ID number is changed
     */
    {
        this.ID.set(id);
    }

    public String getName() 
    /*
     * Precondition:  User wants the customers name
     * Postcondition: String of the customers name s returned to the user
     */
    {
        return organizationName.get();
    }

    public void setName(String nm) 
    /*
     * Precondition:  User wants to change the customers name
     * Postcondition: The name is changed
     * 
     */
    {
        this.organizationName.set(nm);
    }

    public int getGroupSize() 
    /*
     * Precondition:  User wants the group size
     * Postcondition: Group size is returned as an int
     */
    {
        return groupSize.get();
    }

    public void setGroupSize(int size)
    /*
     * Precondition:  User wants to change the value for group size
     * Postcondition: The trips group size is changed
     */
    {
        this.groupSize.set(size);
    }

    public LocalDate getDepart() 
    /*
     * Precondition:  The user wants the value for the departure date of the trip
     * Postcondition: The date is passed back to the user in LocalDate format
     */
    {
        return depart.get();
    }

    public void setDepart(LocalDate depart) 
    /*
     * Precondition:  The user wants to set the date of departure
     * Postcondition: The date of departure is changed
     */
    {
        this.depart.set(depart);
    }
    
    public String getDepartStr ()
    /*
     * Precondition:  The user wants the date  of departure in a string format
     * Postcondition: The date is sent in a string format
     */
    {
    	return departString.get();
    }
    
    public LocalDate getArrive() 
    /*
     * Precondition:  The user wants the value for the return date of the trip
     * Postcondition: The date is passed back to the user in LocalDate format
     */
    {
        return arrive.get();
    }
    
    public String getArriveStr ()
    /*
     * Precondition:  The user wants the date  of return in a string format
     * Postcondition: The date is sent in a string format
     */
    {
    	return arriveString.get();
    }

    public void setArrive(LocalDate arrive) 
    /*
     * Precondition:  The user wants to set the date of return
     * Postcondition: The return date is changed
     */
    {
        this.arrive.set(arrive);
    }

	public String getBusNumbers()
	/*
	 * Precondition:  The user wants a string of all of the bus numbers that the trip 
	 * 				  is using
	 * Postcondition: The bus numbers re sent back as a string separated by commas
	 */
	{
		return busNumbers.get();
	}

	public void setBusNumbers(String busNumber) 
	/*
	 * Precondition:  The user wants to change the value for the bus numbers
	 * Postcondition: The bus numbers for the trip are changed
	 */
	{
		this.busNumbers.set(busNumber);;
	}
	
	public Double getTripCost()
	/*
	 * Precondition:  The user wants the total cost of teh trip in a double format
	 * Postcondition: THe total trip cost is sent to the user
	 */
	{
		return tripCost.get();
	}
	
	public void setTripCost(double tripCst)
	/*
	 * Precondition:  The user wants to change the trip cost
	 * Postcondition: The double value for total cost is changed 
	 */
	{
		this.tripCost.set(tripCst);
	}
	
	public String getTripNote()
	/*
	 * Precondition:  The user wants the value for the trip notes 
	 * Postcondition: A long string of info will be returned to the user
	 */
	{
		return tripNote.get();
	}
	
	public void setTripNote(String trpNt)
	/*
	 * Precondition:  The user either wants to change the trip note or make a new one
	 * Postcondition: The trip note for the trip is modified
	 */
	{
		this.tripNote.set(trpNt);
	}
	
	public Double getTripDistance()
	/*
	 * Precondition:  The user wants the double value for the distance of the trip
	 * Postcondition: Double value for the trips distance is given to the user
	 */
	{
		return tripDistance.get();
	}
	
	public void setTripDistance(double dist)
	/*
	 * Precondition:  The user wants to modify the distance of the trip
	 * Postcondition: Trips distance is modified
	 */
	{
		this.tripDistance.set(dist);
	}
	
	public String getTripDestination()
	/*
	 * Precondition:  The user wants the String of the trip destination
	 * Postcondition: String of destination is returned to user
	 */
	{
		return destination.get();
	}
	
	public void setTripDestination(String dest)
	/*
	 * Precondition:  User wants to set the trip destination
	 * Postcondition: trips destination is modified
	 */
	{
		destination.set(dest);
	}
}