package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.ConnectionProperty;
import domain.Author;
import domain.Blog;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/editblog")
public class EditBlog extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	ConnectionProperty prop;
	String SELECT_ALL_USERS = "select *  from authors ORDER BY id ASC";
	 String SELECT_ALL_BLOGS = "SELECT * from blogs ORDER BY id ASC";
	 String SELECT_BLOG_BY_ID = "SELECT * FROM blogs WHERE id = ?";
	 String EDIT_BLOGS = "UPDATE blogs SET idauthor=?, title=?, content=?,date=? WHERE id=?";
	 
	 ArrayList<Author> author = new ArrayList<>();
		ArrayList<Blog> blogs = new ArrayList<>();
		ArrayList<Blog> editblog = new ArrayList<>();
		
		 public EditBlog() {
		        super();
		        prop = new ConnectionProperty();
		    }
	
		 
		 
		 protected void doGet(HttpServletRequest request, HttpServletResponse response)
		            throws ServletException, IOException {
		        response.setContentType("text/html");
		        ConnectionProperty builder = new ConnectionProperty();

		        try (Connection conn = builder.getConnection()) {
		            String strId = request.getParameter("id");
		            Long idBlogSelected = null;
		            if (strId != null) {
		            	idBlogSelected = Long.parseLong(strId);
		            }

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
						request.setAttribute("author", author);
					}
					stmt = conn.createStatement();
					long idauthor;
					
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

		            try (PreparedStatement preparedStatement = conn.prepareStatement(SELECT_BLOG_BY_ID)) {
		                preparedStatement.setLong(1, idBlogSelected);
		                rs = preparedStatement.executeQuery();
		                if (rs != null) {
		                	editblog.clear();
		                    while (rs.next()) {
		                    	
		                    	editblog.add(new Blog(rs.getLong("id"),
		    							rs.getString("title"),
		    							rs.getString("content"),
		    							rs.getDate("date"),
		    							rs.getLong("idauthor")));
		                    }
		                    rs.close();
		                    request.setAttribute("blogEdit", editblog);
		                } 
		            } catch (Exception e) {
		                System.out.println(e);
		            }
		        } catch (Exception e) {
		            System.out.println(e);
		        }

		        String userPath = request.getServletPath();
		        if("/editblog".equals(userPath)){
		            request.getRequestDispatcher("/WEB-INF/view/editblog.jsp")
		                    .forward(request, response);
		        }
		    }

		    protected void doPost(HttpServletRequest request, HttpServletResponse response)
		            throws ServletException, IOException {
		        ConnectionProperty builder = new ConnectionProperty();

		        try (Connection conn = builder.getConnection()){
		            String strId = request.getParameter("id");
		            Long id = null;
		            if (strId != null) {
		                id = Long.parseLong(strId);
		            }

		            String title = request.getParameter("title");
					String content = request.getParameter("content");
					String date = request.getParameter("date");
				
					String author = request.getParameter("author");
					int index1 = author.indexOf('=');
					int index2 = author.indexOf(",");
					String r1 = author.substring(index1+1, index2);
					Long idauthor = Long.parseLong(r1.trim());

		            PreparedStatement preparedStatement = conn.prepareStatement(EDIT_BLOGS);
		            preparedStatement.setLong(5, id);
		            preparedStatement.setLong(1, idauthor);
					preparedStatement.setString(2, title);
					preparedStatement.setString(3, content);
					preparedStatement.setDate(4,Date.valueOf(date));
		            

		            int result = preparedStatement.executeUpdate();
		        } catch (Exception e) {
		            System.out.println(e);
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
	

