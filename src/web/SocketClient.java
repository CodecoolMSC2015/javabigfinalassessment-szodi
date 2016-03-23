package web;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Set;

import common.SearchType;
import server.entity.Person;
import server.network.PersonStoreSocketServer;

public class SocketClient
{
	String searchCriteria;
	SearchType searchType;
	
	public SocketClient(String searchCriteria, SearchType searchType)
	{
		this.searchCriteria = searchCriteria;
		this.searchType = searchType;
	}
	
	public Set<Person> getPersons()
	{
		try
		{
			Socket socket = new Socket("localhost", PersonStoreSocketServer.PORT);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			oos.writeObject(searchCriteria);
			oos.writeObject(searchType);
			Set<Person> personSet = null;
			try
			{
				while(ois.read() > -1)
				{
					personSet = (Set<Person>)ois.readObject();
					break;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			ois.close();
			oos.close();
			socket.close();
			return personSet;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public String getSearchCriteria()
	{
		return searchCriteria;
	}

	public void setSearchCriteria(String searchCriteria)
	{
		this.searchCriteria = searchCriteria;
	}

	public SearchType getSearchType()
	{
		return searchType;
	}

	public void setSearchType(SearchType searchType)
	{
		this.searchType = searchType;
	}
}
