package server.reader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import common.SearchType;
import server.entity.Person;

public abstract class DataReader
{
	String searchCriteria;
	SearchType searchType;
	
	public void setSearchCriteria(String searchCriteria)
	{
		this.searchCriteria = searchCriteria;
	}

	public void setSearchType(SearchType searchType)
	{
		this.searchType = searchType;
	}
	
	protected Set<String> getSkillsFromString()
	{
		String[] skillParts = searchCriteria.toLowerCase().split(",");
		Set<String> skillSet = new HashSet<String>(Arrays.asList(skillParts));
		return skillSet;
	}
	
	public abstract Set<Person> getPersons();
}
