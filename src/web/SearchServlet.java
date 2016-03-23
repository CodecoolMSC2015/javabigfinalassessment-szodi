package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.SearchType;
import server.entity.Person;

public class SearchServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String searchCriteria = request.getParameter("searchCriteria");
		String searchType = request.getParameter("searchType");
		
		PrintWriter out = response.getWriter();
		if (searchCriteria == null || searchCriteria.equals(""))
		{
			out.print("Search criteria is mandatory");
			request.getRequestDispatcher("index.html").include(request, response);
		}
		else if (searchType == null || searchType.equals("") || !(searchType.toLowerCase().equals("mandatory") || searchType.toLowerCase().equals("optional")))
		{
			out.print("Search type is mandatory");
			request.getRequestDispatcher("index.html").include(request, response);
		}
		else
		{
			HttpSession session = request.getSession();
			List<Person> personList = null;
//			List<Person> personList = (List<Person>)session.getAttribute(searchCriteria);
			SearchType type = (searchType.equals("mandatory") ? SearchType.MANDATORY : SearchType.OPTIONAL);
			if (personList == null)
			{
				SocketClient client = new SocketClient(searchCriteria, type);
				Set<Person> personSet = client.getPersons();
				personList = new ArrayList<Person>(personSet);
				personList.sort(new RateComparator(searchCriteria, type));
				session.setAttribute(searchCriteria, personList);
			}
			for (Person person : personList)
			{
				out.write(person.toString() + "<br/>");
			}
		}
	}
}
