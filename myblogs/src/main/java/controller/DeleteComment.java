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
import domain.Comment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/deletecomment")
public class DeleteComment extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	ConnectionProperty prop;
	String SELECT_ALL_USERS = "select id,firstname,lastname,email,phone, dataregistration  from authors";
	 String SELECT_ALL_BLOGS = "SELECT id, idauthor, title, content,date from blogs";
	String SELECT_ALL_COMMENTS = "SELECT id, content, datecomment, idauthor, idblog FROM comments";
	 String SELECT_COMMENT_BY_ID = "SELECT id, content, datecomment, idauthor, idblog FROM comments WHERE id = ?";
	 String DELETE_COMMENTS = "DELETE FROM comments  WHERE id=?";
	 
	 ArrayList<Author> authors = new ArrayList<>();
		ArrayList<Blog> blogs = new ArrayList<>();
		ArrayList<Comment> comments = new ArrayList<>();
		ArrayList<Comment> deletecomment = new ArrayList<>();
		
		 public DeleteComment() {
		        super();
		        prop = new ConnectionProperty();
		    }
	
		 
		 
		 protected void doGet(HttpServletRequest request, HttpServletResponse response)
		            throws ServletException, IOException {
		        response.setContentType("text/html");
		        ConnectionProperty builder = new ConnectionProperty();

		        try (Connection conn = builder.getConnection()) {
		            String strId = request.getParameter("id");
		            Long idCommentSelected = null;
		            if (strId != null) {
		            	idCommentSelected = Long.parseLong(strId);
		            }

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

		            try (PreparedStatement preparedStatement = conn.prepareStatement(SELECT_COMMENT_BY_ID)) {
		                preparedStatement.setLong(1, idCommentSelected);
		                rs = preparedStatement.executeQuery();
		                if (rs != null) {
		                	deletecomment.clear();
		                    while (rs.next()) {
		                    	idauthor = rs.getLong("idauthor");
								idblog = rs.getLong("idblog");
								deletecomment.add(new Comment(rs.getLong("id"),
		                    			rs.getString("content"),
										rs.getDate("datecomment"),
										rs.getLong("idauthor"),
										rs.getLong("idblog")));
		                    }
		                    rs.close();
		                    request.setAttribute("commentDelete", deletecomment);
		                } 
		            } catch (Exception e) {
		                System.out.println(e);
		            }
		        } catch (Exception e) {
		            System.out.println(e);
		        }

		        String userPath = request.getServletPath();
		        if("/deletecomment".equals(userPath)){
		            request.getRequestDispatcher("/WEB-INF/view/deletecomment.jsp")
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

		            try (PreparedStatement preparedStatement = conn.prepareStatement(DELETE_COMMENTS)) {
		                preparedStatement.setLong(1, id);
		                int result = preparedStatement.executeUpdate();
		            } catch (Exception e) {
		                System.out.println(e);
		            }
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
	

