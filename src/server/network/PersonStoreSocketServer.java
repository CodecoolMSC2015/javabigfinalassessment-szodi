package server.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;

import common.SearchType;
import server.entity.Person;
import server.reader.CSVReader;
import server.reader.DataReader;

public class PersonStoreSocketServer
{
	public static final int PORT = 1234;
	ServerSocket serverSocket;
	String csvFilePath = "persons.csv";
	DataReader dr = new CSVReader(csvFilePath);
	
	public PersonStoreSocketServer()
	{
		try
		{
			serverSocket = new ServerSocket(PORT);
			start();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void start()
	{
		while(true)
		{
			try
			{
				Socket socket = serverSocket.accept();
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				String searchCriteria = (String)ois.readObject();
				SearchType searchType = (SearchType)ois.readObject();
				dr.setSearchCriteria(searchCriteria);
				dr.setSearchType(searchType);
				Set<Person> personSet = dr.getPersons();
				oos.write(0);
				oos.writeObject(personSet);
				ois.close();
				oos.close();
				socket.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args)
	{
		new PersonStoreSocketServer();
	}
}
