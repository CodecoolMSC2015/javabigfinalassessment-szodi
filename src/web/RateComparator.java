package web;

import java.util.Comparator;
import java.util.List;

import common.SearchType;
import server.entity.Person;
import server.entity.Skill;

public class RateComparator implements Comparator<Person>
{
	String searchCriteria;
	SearchType searchType;
	
	public RateComparator(String searchCriteria, SearchType searchType)
	{
		this.searchCriteria = searchCriteria;
		this.searchType = searchType;
	}

	public int compare(Person person1, Person person2)
	{
		double rate1 = 0.0;
		double rate2 = 0.0;
		if (searchType == SearchType.OPTIONAL)
		{
			rate1 = getMaxRate(person1.getSkillset());
			rate2 = getMaxRate(person2.getSkillset());
		}
		else if (searchType == SearchType.MANDATORY)
		{
			rate1 = getAverageRate(person1.getSkillset());
			rate2 = getAverageRate(person2.getSkillset());
		}
		if (rate1 > rate2)
		{
			return -1;
		}
		else if (rate1 < rate2)
		{
			return 1;
		}
		return 0;
	}
	
	private double getMaxRate(List<Skill> skills)
	{
		double max = skills.get(0).getRate();
		for (int i = 1; i < skills.size(); i++)
		{
			double actualRate = skills.get(i).getRate();
			if (actualRate > max)
			{
				max = actualRate;
			}
		}
		return max;
	}
	
	private double getAverageRate(List<Skill> skills)
	{
		double sum = 0.0;
		for (Skill skill : skills)
		{
			sum += skill.getRate();
		}
		return sum / skills.size();
	}
}
