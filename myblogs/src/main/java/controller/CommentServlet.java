package controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

import dao.ConnectionProperty;

import domain.Author;
import domain.Blog;
import domain.Comment;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ConnectionProperty prop;
	String SELECT_ALL_USERS = "select id,firstname,lastname,email,phone, dataregistration  from authors";
	 String SELECT_ALL_BLOGS = "SELECT id, idauthor, title, content,date from blogs";
	String SELECT_ALL_COMMENTS = "SELECT id, content, datecomment, idauthor, idblog FROM comments";
	String INSERT_COMMENT = "INSERT INTO comments (content, datecomment, idauthor, idblog) VALUES (?, ?, ?, ?)";
	
	ArrayList<Author> authors = new ArrayList<>();
		ArrayList<Blog> blogs = new ArrayList<>();
	ArrayList<Comment> comments = new ArrayList<>();
	
	public CommentServlet() {
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
				authors.clear();
				while (rs.next()) {
			
				
					authors.add(new Author(
							rs.getLong("id"),
							rs.getString("firstname"),
							rs.getString("lastname"),
							rs.getString("email"),
							rs.getString("phone"),
							rs.getDate("dataregistration"))
							);
				}
				rs.close();
				request.setAttribute("authors", authors);
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
							findById(idauthor, authors)
					));
				}
				rs.close();
				request.setAttribute("blogs", blogs);
			}
			long idblog;

			stmt = conn.createStatement();
			rs = stmt.executeQuery(SELECT_ALL_COMMENTS);
			if (rs != null) {
				comments.clear();
				while (rs.next()) {
					idauthor = rs.getLong("idauthor");
					idblog = rs.getLong("idblog");
					comments.add(new Comment(rs.getLong("id"),
							rs.getString("content"),
							rs.getDate("datecomment"),
							idauthor,
							findById(idauthor, authors),
							idblog,
							findByIdBlog(idblog, blogs)));
				}
				rs.close();
				request.setAttribute("comments", comments);
			}
		} catch (Exception e) {
			System.out.println(e);
			
		}

		String userPath = request.getServletPath();
		if("/comment".equals(userPath)){
			request.getRequestDispatcher("/WEB-INF/view/comment.jsp")
					.forward(request, response);
		}
	}

	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ConnectionProperty builder = new ConnectionProperty();

		try (Connection conn = builder.getConnection()){
			String content = request.getParameter("content");
			String datecomment = request.getParameter("datecomment");
			

			String author = request.getParameter("author");
			int index1 = author.indexOf('=');
			int index2 = author.indexOf(",");
			String r1 = author.substring(index1+1, index2);
			Long idauthor = Long.parseLong(r1.trim());

			String blog = request.getParameter("blog");
			int index3 = blog.indexOf('=');
			int index4 = blog.indexOf(",");
			String r2 = blog.substring(index3+1, index4);
			Long idblog = Long.parseLong(r2.trim());

			try (PreparedStatement preparedStatement = conn.prepareStatement(INSERT_COMMENT)){
				preparedStatement.setString(1, content);
				preparedStatement.setDate(2, Date.valueOf(datecomment));
				preparedStatement.setLong(3, idauthor);
				preparedStatement.setLong(4, idblog);
				
				
			

				int rows = preparedStatement.executeUpdate();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
			getServletContext().getRequestDispatcher("/WEB-INF/view/comment.jsp")
					.forward(request, response);
		}
		doGet(request, response);
	}

	
	private Author findById(Long id, ArrayList<Author> authors) {
		if (authors != null) {
			for (Author r: authors) {
				if ((r.getId()).equals(id)) {
					return r;
				}
			}
		} else {
			return null;
		}
		return null;
	}

	// Ïîèñê ïîñåòèòåëÿ ïî id
	private Blog findByIdBlog(Long id, ArrayList<Blog> blogs) {
		if (blogs != null) {
			for (Blog r: blogs) {
				if ((r.getId()).equals(id)) {
					return r;
				}
			}
		} else {
			return null;
		}
		return null;
	}
}
   