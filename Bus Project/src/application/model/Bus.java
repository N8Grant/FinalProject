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
		this.setDepart(null);
		this.setRet(null);
	}
	
	public Bus (int busNum, LocalDate dpt, LocalDate ret)
	{
		this.busNumber = busNum;
		this.depart = dpt;
		this.ret = ret;
	}
	
	public int getBusNumber ()
	{
		return busNumber;	
	}
	
	public boolean getStatus()
	{
		return status;
	}
	
	public void setStatus (boolean stat)
	{
		this.status = stat;
	}

	public LocalDate getDepart()
	{
		return depart;
	}

	public void setDepart(LocalDate depart) 
	{
		this.depart = depart;
	}

	public LocalDate getRet() 
	{
		return ret;
	}

	public void setRet(LocalDate ret) 
	{
		this.ret = ret;
	}
}
