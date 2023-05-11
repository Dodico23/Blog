package controller;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.ConnectionProperty;
import dao.ConnectionBuilder;
import domain.Author;

/**
* Servlet implementation class EditRoleServlet
*/
@WebServlet("/editauthor")
public class EditAuthor extends HttpServlet {
private static final long serialVersionUID = 1L;
ConnectionProperty prop;
String select_all_author = "SELECT * FROM authors ORDER BY firstname ASC";
String select_author_ById = "SELECT * FROM authors WHERE id = ?";
String edit_author = "UPDATE authors SET firstname= ?, lastname= ?, email= ?,phone= ?, dataregistration = ? WHERE id = ?";
ArrayList<Author> author = new ArrayList<Author>();
ArrayList<Author> editauthors = new ArrayList<Author>();
String userPath;
 
 /**
 * @see HttpServlet#HttpServlet()
 */
 public EditAuthor() throws FileNotFoundException, IOException 
{
 super();
 // TODO Auto-generated constructor stub
 prop = new ConnectionProperty();
 }
protected void doGet(HttpServletRequest request, 
HttpServletResponse response) 
throws ServletException, IOException {
response.setContentType("text/html");
ConnectionProperty builder = new ConnectionProperty();
// Загрузка всех должностей
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
	editauthors.clear();
while (rs.next()) {
	editauthors.add(new Author(rs.getLong("id"),
			rs.getString("firstname"),
			rs.getString("lastname"),
			rs.getString("email"),
			rs.getString("phone"),
			rs.getDate("dataregistration")));
}
rs.close();
request.setAttribute("authorsEdit", editauthors);
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
if("/editauthor".equals(userPath)){
request.getRequestDispatcher("/WEB-INF/view/editauthor.jsp").forward(request, response);
}
}
protected void doPost(HttpServletRequest request, 
HttpServletResponse response) throws ServletException, IOException {
	ConnectionProperty builder = new ConnectionProperty();
try (Connection conn = builder.getConnection()) {
String strId = request.getParameter("id");
Long id = null;
if(strId != null) {
id = Long.parseLong(strId);
}
String firstname = request.getParameter("firstname");
String lastname = request.getParameter("lastname");
String email = request.getParameter("email");
String phone = request.getParameter("phone");
String dataregistration = request.getParameter("dataregistration");
try (PreparedStatement preparedStatement = 
conn.prepareStatement(edit_author)) {
	preparedStatement.setString(1,firstname);
	preparedStatement.setString(2,lastname);
	preparedStatement.setString(3,email);
	preparedStatement.setString(4,phone);
	preparedStatement.setDate(5,Date.valueOf(dataregistration));
preparedStatement.setLong(6, id);
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
