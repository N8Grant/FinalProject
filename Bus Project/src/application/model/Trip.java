package application.model;

import java.time.LocalDate;
import java.util.UUID;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Trip 
{
	private StringProperty organizationName;
    private IntegerProperty groupSize;
    private SimpleStringProperty busNumbers;
    private StringProperty ID;
    private ObjectProperty<LocalDate> depart;
    private ObjectProperty<LocalDate> arrive;
    private SimpleStringProperty departString;
    private SimpleStringProperty arriveString;

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
    	String uniqueID = UUID.randomUUID().toString();
    	this.ID = new SimpleStringProperty(uniqueID);
        this.depart = new SimpleObjectProperty<LocalDate>(dpt);
        this.arrive = new SimpleObjectProperty<LocalDate>(arr);
        this.busNumbers = new SimpleStringProperty(bsNm);
    }
    
    public Trip(String Name, String ID, int grpSz, String dpt, String arr) 
    {
    	this.organizationName = new SimpleStringProperty(Name);
    	this.groupSize = new SimpleIntegerProperty(grpSz);
    	this.ID = new SimpleStringProperty(ID);
        this.departString = new SimpleStringProperty(dpt);
        this.arriveString = new SimpleStringProperty(arr);
    }

    public String getId() 
    {
        return ID.get();
    }

    public void setId(String id)
    {
        this.ID.set(id);
    }

    public StringProperty ID() 
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
}