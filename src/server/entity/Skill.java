package server.entity;

import java.io.Serializable;

public class Skill implements Serializable
{
	private static final long serialVersionUID = -5964038087259622746L;
	
	String name;
	String description;
	double rate;
	
	public Skill(String name, String description, double rate)
	{
		this.name = name;
		this.description = description;
		this.rate = rate;
	}

	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}

	public double getRate()
	{
		return rate;
	}
	
	@Override
	public String toString()
	{
		return "[" + name + ":" + rate + "]";
	}
}
