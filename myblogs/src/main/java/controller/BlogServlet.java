package controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;


import java.sql.Statement;



import dao.ConnectionProperty;

import domain.Author;
import domain.Blog;

/**
* 
*/
@WebServlet("/blog")
public class BlogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ConnectionProperty prop;
	String SELECT_ALL_USERS = "select id,firstname,lastname,email,phone, dataregistration  from authors";
	 String SELECT_ALL_BLOGS = "SELECT id, idauthor, title, content,date from blogs";
	 String INSERT_BLOGS = "INSERT INTO blogs(idauthor, title, content,date) VALUES (?,?,?,?)";
	 
	 ArrayList<Author> author = new ArrayList<>();
		ArrayList<Blog> blogs = new ArrayList<>();
	
		String userPath;
		public BlogServlet()  {
			prop = new ConnectionProperty();
		}

	

	
	
	
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
			
				
					author.add(new Author(
							rs.getLong("id"),
							rs.getString("firstname"),
							rs.getString("lastname"),
							rs.getString("email"),
							rs.getString("phone"),
							rs.getDate("dataregistration"))
							);
				}
				rs.close();
				request.setAttribute("authors", author);
			}

			
			
			long idauthor;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SELECT_ALL_BLOGS);
			if(rs != null) {
				blogs.clear();
				while (rs.next()) {
					idauthor = rs.getLong("idauthor");
					blogs.add(new Blog(rs.getLong("id"),
							rs.getString("title"),
							rs.getString("content"),
							rs.getDate("date"),
							idauthor,
							findById(idauthor, author)
					));
				}
				rs.close();
				request.setAttribute("blogs", blogs);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		String userPath = request.getServletPath();
		if("/blog".equals(userPath)){
			request.getRequestDispatcher("/WEB-INF/view/blog.jsp")
					.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConnectionProperty builder = new ConnectionProperty();
		try (Connection conn = builder.getConnection()){
			
			
			
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			String date = request.getParameter("date");
			
			String author = request.getParameter("author");
			int index1 = author.indexOf('=');
			int index2 = author.indexOf(",");
			String r1 = author.substring(index1+1, index2);
			Long idauthor = Long.parseLong(r1.trim());
			
			try (PreparedStatement preparedStatement = conn.prepareStatement(INSERT_BLOGS)){
			preparedStatement.setLong(1, idauthor);
			preparedStatement.setString(2, title);
			preparedStatement.setString(3, content);
			preparedStatement.setDate(4,Date.valueOf(date));
			
			int rows = preparedStatement.executeUpdate();
		
	} catch (Exception e) {
		System.out.println(e);
	}
		} catch (Exception e) {
			System.out.println(e);
		getServletContext().getRequestDispatcher("/WEB-INF/view/blog.jsp")
				.forward(request, response);
	}
		doGet(request, response);
	}
	
	private Author findById(Long id, ArrayList<Author> authors) {
		if(authors != null) {
			for(Author r: authors) {
				if((r.getId()).equals(id)) {
					return r;
				}
			}
		} else {
			return null;
		}
		return null;
	}
}


/*	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
				default:
					listBlog(request, response);
					break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listBlog(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Author> listAuthor = daoAuthor.selectAllAuthors();
		List<Blog> listBlog = daoBlog.selectAllBlogs();
		request.setAttribute("listBlog", listBlog);
		request.setAttribute("listAuthor", listAuthor);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/blog.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
   */