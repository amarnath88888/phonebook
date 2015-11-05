package com.contacts;

import org.apache.log4j.Logger;

public class Contacts {
	private String name;
	private String number;
	
	static final Logger logger = Logger.getLogger(Contacts.class);
	
	public String getName()	{
		return name;
	}
	
	public String getNumber()	{
		return number;
	}
	
	public void setName(String name)	{
		this.name=name;
	}
	
	public void setNumber(String number)	{
		this.number=number;
	}
	public String toString() {
        return("Name="+this.name + " & Number=" +  this.number);
    }
}
