package application.model; // Package containing bus class

/*
 * Imports Section
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
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
    private SimpleStringProperty departString;	// String of depart date
    private SimpleStringProperty arriveString;	// String of return date
    
    /*
     * The format for the date
     */
    final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	

    /**
     * Default constructor.
     */
    public Trip() 
    {
        this(null, 0, null, null, null);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param Name
     * @param groupSize
     * @param departDate
     * @param arriveDate
     */
    public Trip(String Name, int grpSz, LocalDate dpt, LocalDate arr, String bsNm) 
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
    }
    
    public Trip(String Name, String ID, int grpSz, String busNm, String dpt, String arr) 
    {
    	this.organizationName = new SimpleStringProperty(Name);
    	this.groupSize = new SimpleIntegerProperty(grpSz);
    	this.ID = new SimpleStringProperty(ID);
    	LocalDate dpt1 = LocalDate.parse(dpt, dtf);
        this.depart = new SimpleObjectProperty<LocalDate>(dpt1);
        LocalDate arr1 = LocalDate.parse(dpt, dtf);
        this.arrive = new SimpleObjectProperty<LocalDate>(arr1);
        this.busNumbers = new SimpleStringProperty(busNm);
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
}