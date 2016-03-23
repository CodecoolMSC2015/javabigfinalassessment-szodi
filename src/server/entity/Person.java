package server.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Person implements Serializable
{
	private static final long serialVersionUID = 6359032046273653307L;
	
	String name;
	String email;
	List<Skill> skillset;
	
	public Person(String name, String email)
	{
		this.name = name;
		this.email = email;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public List<Skill> getSkillset()
	{
		return skillset;
	}

	public void setSkillset(List<Skill> skillset)
	{
		this.skillset = skillset;
	}
	
	public void addSkill(Skill skill)
	{
		skillset.add(skill);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (email == null)
		{
			if (other.email != null)
				return false;
		}
		else if (!email.equals(other.email))
			return false;
		return true;
	}
	
	public static Person parse(String[] csvData)
	{
		String name = csvData[0];
		String email = csvData[1];
		String skillName = csvData[2];
		String skillDescription = csvData[3];
		String skillRate = csvData[4];
		Person person;
		if (csvData.length > 5)
		{
			Employee employee = new Employee(name, email);
			String strSalary = csvData[5];
			String jobTitle = csvData[6];
			int salary = Integer.parseInt(strSalary);
			employee.setSalary(salary);
			employee.setJobTitle(jobTitle);
			person = employee;
		}
		else
		{
			person = new Person(name, email);
		}
		person.setSkillset(new ArrayList<Skill>());
		double rate = Double.parseDouble(skillRate);
		Skill skill = new Skill(skillName, skillDescription, rate);
		person.addSkill(skill);
		return person;
	}
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Name : ");
		sb.append(name);
		sb.append("; E-mail : ");
		sb.append(email);
		sb.append("; Skills : ");
		for (Skill skill : skillset)
		{
			sb.append(skill.toString());
		}
		return sb.toString();
	}
}
