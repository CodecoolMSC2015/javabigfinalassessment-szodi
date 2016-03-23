package server.entity;

public class Employee extends Person
{
	private static final long serialVersionUID = -2597819673895467803L;
	
	int salary;
	String jobTitle;
	
	public Employee(String name, String email)
	{
		super(name, email);
	}

	public int getSalary()
	{
		return salary;
	}

	public void setSalary(int salary)
	{
		this.salary = salary;
	}

	public String getJobTitle()
	{
		return jobTitle;
	}

	public void setJobTitle(String jobTitle)
	{
		this.jobTitle = jobTitle;
	}
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Name : ");
		sb.append(name);
		sb.append("; E-mail : ");
		sb.append(email);
		sb.append("; Salary : ");
		sb.append(salary);
		sb.append("; JobTitle : ");
		sb.append(jobTitle);
		sb.append("; Skills : ");
		for (Skill skill : skillset)
		{
			sb.append(skill.toString());
		}
		return sb.toString();
	}	
}
