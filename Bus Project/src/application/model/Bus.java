package application.model;

import java.time.LocalDate;

public class Bus 
{
	private int busNumber;
	private boolean status;
	private LocalDate depart;
	private LocalDate ret;
	
	public Bus(int busNum)
	{
		this.busNumber = busNum;
		this.status = false;
		this.depart = null;
		this.ret = null;
	}
	
	
}
