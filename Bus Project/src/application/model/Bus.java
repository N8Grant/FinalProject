package application.model;	// The package that this class is in

/*
 * Import Section
 */
import java.time.LocalDate;		// Import for using easy date modifying

public class Bus 
{
	/*
	 * Instance Variable 
	 */
	private int busNumber;		// The number of the bus
	private boolean status;		// The status of a bus true for taken 
	private LocalDate depart;	// LocalDate for the departure 
	private LocalDate ret;		// LocalDate for the return
	
	/*
	 * Constructor
	 */	
	public Bus (int busNum, LocalDate dpt, LocalDate ret)
	/*
	 * Precondition:  User wants to make another bus
	 * Postcondition: New Bus object is created
	 */
	{
		this.busNumber = busNum;
		this.depart = dpt;
		this.ret = ret;
	}
	
	/*
	 * Getters and Setters
	 */
	public int getBusNumber ()
	/*
	 * Precondition:  User wants to get the busses number
	 * Postcondition: The number of the bus is returned to the 
	 * 				  user
	 */
	{
		return busNumber;	// Return the busses number
	}
	
	public boolean getStatus()
	/*
	 * Precondition:  User wants bus status
	 * Postcondition: Boolean is sent to user, true if occupied
	 * 				  false if its not 
	 */
	{
		return status;		// returns bus status
	}
	
	public void setStatus (boolean stat)
	/*
	 * Precondition:  Bus has been occupied or freed up
	 * Postcondition: Status of the bus is changed
	 */
	{
		this.status = stat;
	}

	public LocalDate getDepart()
	/*
	 * Precondition:  User wants the local date value for the depart 
	 * 				  date 
	 * Postcondition: User is sent the date of departure for the bus
	 */
	{
		return depart;		// Returns the local date
	}

	public void setDepart(LocalDate depart) 
	/*
	 * Precondition:  User wants to change the date of departure
	 * Postcondition: The date is changed 
	 */
	{
		this.depart = depart;
	}

	public LocalDate getRet() 
	/*
	 * Precondition:  User wants the local date value for the return
	 * 				  date 
	 * Postcondition: User is sent the date of return for the bus
	 */
	{
		return ret;		// Returns the local date
	}

	public void setRet(LocalDate ret) 
	/*
	 * Precondition:  User wants to change the date of return
	 * Postcondition: The date is changed  
	 */
	{
		this.ret = ret;
	}
}
