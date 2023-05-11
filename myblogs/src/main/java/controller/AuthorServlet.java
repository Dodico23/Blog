package controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection; 
import java.sql.ResultSet;

import java.sql.Statement; 
import java.util.ArrayList;


import domain.Author;
import dao.ConnectionProperty;

/**
* 
*/
@WebServlet("/author")
public class AuthorServlet extends HttpServlet {
  
	private static final long serialVersionUID = 1L;
	
	ConnectionProperty prop;
	String SELECT_ALL_USERS = "select * from authors";
	String INSERT_AUTHORS = "INSERT INTO authors (firstname, lastname, email,phone, dataregistration) VALUES (?,?,?,?,?)";
	ArrayList<Author> author = new ArrayList<>();
	String userPath;
	public AuthorServlet() throws FileNotFoundException, IOException {
		prop = new ConnectionProperty();
	}

	/*public void init() {
		userDAO = new USerDAO();
	}*/

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		ConnectionProperty builder = new ConnectionProperty();
		try (Connection conn = builder.getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SELECT_ALL_USERS);
			if(rs != null) {
				author.clear();
				while (rs.next()) {
			
				
					author.add(new Author(rs.getLong("id"),
							rs.getString("firstname"),
							rs.getString("lastname"),
							rs.getString("email"),
							rs.getString("phone"),
							rs.getDate("dataregistration"))
							);
				}
				rs.close();
				request.setAttribute("author", author);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		userPath = request.getServletPath();
		if("/author".equals(userPath)){
			request.getRequestDispatcher("/WEB-INF/view/author.jsp")
					.forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException{
		ConnectionProperty builder = new ConnectionProperty();
		try (Connection conn = builder.getConnection()){
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String dataregistration = request.getParameter("dataregistration");
			
		
			try (PreparedStatement preparedStatement =
						 conn.prepareStatement(INSERT_AUTHORS)){
				preparedStatement.setString(
						1,firstname);
				preparedStatement.setString(2,lastname);
				preparedStatement.setString(3,email);
				preparedStatement.setString(4,phone);
				preparedStatement.setDate(5,Date.valueOf(dataregistration));
				
				int result = preparedStatement.executeUpdate();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
			getServletContext().getRequestDispatcher("/WEB-INF/view/author.jsp")
					.forward(request, response);
		}
			doGet(request,response);
	}
	
	/*protected void doGet( HttpServletRequest request,  HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertAuthor(request, response);
				break;
			
			
			case "/update":
				updateAuthor(request, response);
				break;
			default:
				listAuthor(request, response);
				break;
			}
 } catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}
	
	private void listAuthor( HttpServletRequest request,  HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Author> listAuthor = userDAO.selectAllAuthors();
		request.setAttribute("listAuthor", listAuthor);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/author.jsp");
		dispatcher.forward(request, response);
	}*/



}