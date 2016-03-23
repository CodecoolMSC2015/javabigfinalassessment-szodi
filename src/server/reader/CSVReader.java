package server.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import common.SearchType;
import server.entity.Person;
import server.entity.Skill;

public class CSVReader extends DataReader
{
	String csvFilePath;
	List<Person> persons;
	Set<String> skills;
	
	public CSVReader(String csvFilePath)
	{
		this.csvFilePath = csvFilePath;
	}

	@Override
	public Set<Person> getPersons()
	{
		skills = getSkillsFromString();
		Set<Person> personSet = null;
		try
		{
			Map<Person, List<Skill>> personSkillMap = new HashMap<Person, List<Skill>>();
			BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
			br.readLine();
			String line = "";
			while((line = br.readLine()) != null )
			{
				String[] parts = line.split(",");
				Person person = Person.parse(parts);
				String personSkillString = person.getSkillset().get(0).getName().toLowerCase(); 
				if (skills.contains(personSkillString))
				{
					if (personSkillMap.containsKey(person))
					{
						List<Skill> skillList = personSkillMap.get(person);
						Skill skill = person.getSkillset().get(0);
						if (!skillList.contains(skill))
						{
							skillList.add(skill);
							personSkillMap.put(person, skillList);
						}
					}
					else
					{
						personSkillMap.put(person, person.getSkillset());
					}
				}
			}
			br.close();
			personSet = new HashSet<Person>();
			for (Person person : personSkillMap.keySet())
			{
				if (searchType == SearchType.MANDATORY)
				{
					Set<String> skillSet = new HashSet<String>();
					for (Skill skill : person.getSkillset())
					{
						skillSet.add(skill.getName().toLowerCase());
					}
					if (isSubSet(skills, skillSet))
					{
						personSet.add(person);
					}
				}
				else
				{
					personSet.add(person);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return personSet;
	}
	
	private static boolean isSubSet(Set<String> set1, Set<String> set2)
	{
		for (String string : set1)
		{
			if (!set2.contains(string))
			{
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args)
	{
		DataReader dr = new CSVReader("persons.csv");
		dr.setSearchCriteria("Oracle");
		dr.setSearchType(SearchType.MANDATORY);
		Set<Person> persons = dr.getPersons();
		for (Person person : persons)
		{
			System.out.println(person);
		}
	}
}
