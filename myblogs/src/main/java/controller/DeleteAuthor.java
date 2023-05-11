package controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import dao.ConnectionProperty;
import dao.ConnectionBuilder;
import domain.Author;
@WebServlet("/deleteauthor")
public class DeleteAuthor extends HttpServlet {
private static final long serialVersionUID = 1L;
ConnectionProperty prop;
String select_all_author = "SELECT * FROM authors ORDER BY firstname ASC";
String select_author_ById = "SELECT * FROM authors WHERE id = ?";


String delete_author = "DELETE FROM authors WHERE id = ?";
ArrayList<Author> author = new ArrayList<Author>();
ArrayList<Author> deleteauthors = new ArrayList<Author>();
String userPath;
 
 public DeleteAuthor() throws FileNotFoundException, 
IOException {
 super();
 prop = new ConnectionProperty();
 }
protected void doGet(HttpServletRequest request, 
HttpServletResponse response)
throws ServletException, IOException {
response.setContentType("text/html");
ConnectionProperty builder = new ConnectionProperty();
//Загрузка всех должностей
try (Connection conn = builder.getConnection()) {
String strId = request.getParameter("id");
Long id = null; // id редактируемой должности
if(strId != null) {
id = Long.parseLong(strId);
}
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(select_all_author);
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
else
{
System.out.println("Ошибка загрузки author");
}
try (PreparedStatement preparedStatement = 
conn.prepareStatement(select_author_ById)) {
preparedStatement.setLong(1, id);
rs = preparedStatement.executeQuery();
if(rs != null) {
	deleteauthors.clear();
while (rs.next()) {
	deleteauthors.add(new Author(rs.getLong("id"),
			rs.getString("firstname"),
			rs.getString("lastname"),
			rs.getString("email"),
			rs.getString("phone"),
			rs.getDate("dataregistration")));
}
rs.close();
request.setAttribute("authorsDelete", deleteauthors);
}
else
{
System.out.println("Ошибка загрузки author");
}
} catch (Exception e) {
System.out.println(e);
}
} catch (Exception e) {
System.out.println(e);
} 
userPath = request.getServletPath();
if("/deleteauthor".equals(userPath)){
request.getRequestDispatcher("/WEB-INF/view/deleteauthor.jsp").forward(request, response);
}
}
protected void doPost(HttpServletRequest request, 
HttpServletResponse response)
throws ServletException, IOException {
	ConnectionProperty builder = new ConnectionProperty();
try (Connection conn = builder.getConnection()) {
Long id = Long.parseLong(request.getParameter("id"));
try (PreparedStatement preparedStatement = 
conn.prepareStatement(delete_author)) {
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
}
