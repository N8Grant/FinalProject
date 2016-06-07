package application.model; // Package containing bus class

/*
 * Imports Section
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Trip
{
	/*
	 * Variables
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
    /*
     * The format for the date
     */
    final DateTimeFormatter ymd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    final DateTimeFormatter mdy = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    /**
     * Default constructor.
     */
    public Trip() 
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
     * Getter for string of trip ID
     */
    {
        return ID.get();
    }

    public void setId(String id)
    /*
     * Setter for trip ID
     */
    {
        this.ID.set(id);
    }

    public StringProperty ID() 
    /*
     * Returns a string representation of this StringProperty object
     */
    {
        return ID;
    }
    public String getName() 
    {
        return organizationName.get();
    }

    public void setName(String nm) 
    {
        this.organizationName.set(nm);
    }

    public StringProperty nameProperty()
    /*
     * Returns a string representation of this StringProperty object
     */
    {
        return organizationName;
    }

    public int getGroupSize() 
    {
        return groupSize.get();
    }

    public void setGroupSize(int size)
    {
        this.groupSize.set(size);
    }

    public IntegerProperty groupSize() 
    /*
     * Returns a int representation of this StringProperty object
     */
    {
        return groupSize;
    }

    public LocalDate getDepart() 
    {
        return depart.get();
    }

    public void setDepart(LocalDate depart) 
    {
        this.depart.set(depart);
    }
    
    public String getDepartStr ()
    {
    	return departString.get();
    }

    public ObjectProperty<LocalDate> departProperty() 
    /*
     * Returns a object representation of this StringProperty object
     */
    {
        return depart;
    }
    
    public LocalDate getArrive() 
    {
        return arrive.get();
    }
    
    public String getArriveStr ()
    {
    	return arriveString.get();
    }

    public void setArrive(LocalDate arrive) 
    {
        this.arrive.set(arrive);
    }

    public ObjectProperty<LocalDate> arriveProperty() 
    /*
     * Returns a object representation of this StringProperty object
     */
    {
        return arrive;
    }

	public String getBusNumbers()
	{
		return busNumbers.get();
	}
	public StringProperty busNumbersProperty()
	{
        return busNumbers;
    }

	public void setBusNumbers(String busNumber) 
	{
		this.busNumbers.set(busNumber);;
	}
	
	public Double getTripCost()
	{
		return tripCost.get();
	}
	
	public DoubleProperty tripCostProperty ()
	{
		return tripCost;
	}
	
	public void setTripCost(double tripCst)
	{
		this.tripCost.set(tripCst);
	}
	
	public String getTripNote()
	{
		return tripNote.get();
	}
	
	public StringProperty tripNoteProperty()
	{
		return tripNote;
	}
	
	public void setTripNote(String trpNt)
	{
		this.tripNote.set(trpNt);
	}
	
	public Double getTripDistance()
	{
		return tripDistance.get();
	}
	
	public DoubleProperty tripDistanceProperty()
	{
		return tripDistance;
	}
	
	public void setTripDistance(double dist)
	{
		this.tripDistance.set(dist);
	}
	
	public String getTripDestination()
	{
		return destination.get();
	}
	
	public SimpleStringProperty getDestinationProperty()
	{
		return destination;
	}
	
	public void setTripDestination(String dest)
	{
		destination.set(dest);
	}
	
}