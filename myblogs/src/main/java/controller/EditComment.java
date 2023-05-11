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
@WebServlet("/editcomment")
public class EditComment extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	ConnectionProperty prop;
	String SELECT_ALL_USERS = "select id,firstname,lastname,email,phone, dataregistration  from authors";
	 String SELECT_ALL_BLOGS = "SELECT id, idauthor, title, content,date from blogs";
	String SELECT_ALL_COMMENTS = "SELECT id, content, datecomment, idauthor, idblog FROM comments";
	 String SELECT_COMMENT_BY_ID = "SELECT id, content, datecomment, idauthor, idblog FROM comments WHERE id = ?";
	 String EDIT_COMMENTS = "UPDATE comments SET content=?, datecomment=?, idauthor=?, idblog=? WHERE id=?";
	 
	 ArrayList<Author> author = new ArrayList<>();
		ArrayList<Blog> blogs = new ArrayList<>();
		ArrayList<Comment> comments = new ArrayList<>();
		ArrayList<Comment> editcomment = new ArrayList<>();
		
		 public EditComment() {
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
									findById(idauthor, author),
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
		                	editcomment.clear();
		                    while (rs.next()) {
		                    	idauthor = rs.getLong("idauthor");
								idblog = rs.getLong("idblog");
		                    	editcomment.add(new Comment(rs.getLong("id"),
		                    			rs.getString("content"),
										rs.getDate("datecomment"),
										idauthor,
										idblog));
		                    }
		                    rs.close();
		                    request.setAttribute("commentEdit", editcomment);
		                } 
		            } catch (Exception e) {
		                System.out.println(e);
		            }
		        } catch (Exception e) {
		            System.out.println(e);
		        }

		        String userPath = request.getServletPath();
		        if("/editcomment".equals(userPath)){
		            request.getRequestDispatcher("/WEB-INF/view/editcomment.jsp")
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


		            PreparedStatement preparedStatement = conn.prepareStatement(EDIT_COMMENTS);
		            preparedStatement.setString(1, content);
					preparedStatement.setDate(2, Date.valueOf(datecomment));
					preparedStatement.setLong(3, idauthor);
					preparedStatement.setLong(4, idblog);
		            preparedStatement.setLong(5, id);

		            int rows = preparedStatement.executeUpdate();
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
	

