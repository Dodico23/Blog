<%@ page language="java" contentType="text/html" 
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="domain.Blog"%>
<%@ page import="domain.Author"%>
<%@ page import="domain.Comment"%>


    
<!DOCTYPE html>
<html>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/style.css">
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title>Комментарии</title>
<head>
<meta charset="UTF-8">
<title>Comments</title>
</head>
<body>
<jsp:include page="/WEB-INF/jspf/header.jsp" />
<div id="main">
<section>
<aside class="leftAside">
<h3>Список комментариев</h3>

<table class="table table-bordered">
	<thead class="thead-dark">
		<tr>
		<th scope="col">Код</th>
		<th scope="col">Автор</th>
		<th scope="col">Пост</th>
		<th scope="col">Комментарий</th>
		<th scope="col">Дата Комментария</th>
		<th scope="col">Редактировать</th>
		<th scope="col">Удалить</th>
		
		
		</tr>
	</thead>
	<tbody>
	<c:forEach var="comment" items="${comments}">
		<tr>
		<td>${comment.getId()}</td>
		<td>${comment.getAuthor()}</td>
		<td>${comment.getBlog()}</td>
		<td>${comment.getContent()}</td>
		<td>${comment.getDateComment()}</td>
		<td width="5"><a href='<c:url value="/editcomment?id=${comment.getId()}" />'role="button" class="btn btn-outline-primary"><img alt="Редактировать" src="images/редактировать.png"></a></td> 
<td width="5"><a href='<c:url value="/deletecomment?id=${comment.getId()}"/>'role="button" class="btn btn-outline-primary"> <img alt="Удалить" src="images/удалить.png"></a></td>
		
		
		
		</tr>
	</c:forEach>
	</tbody>
</table>

</aside>
</section>
<section>
<article>
<h3>Добавить комментарий</h3>
<div class="container" id="container-form">
	<form method="POST" action="">
	<div class="row mb-3">
	<label for="content" class="col-sm-2 col-form-label">Комментарий</label>
	<div class="col-sm-10">
	<input type="text" name="content" class="form-control" id="staticContent"/>
	</div>
	</div>
	
	<div class="row mb-3">
	<label for="author"  class="col-sm-2 col-form-label">Автор</label>
	<div class="col-sm-10">
	<select name ="author" id="staticAuthor" class="form-control" >
		<option>Выберите автора</option>
		<c:forEach var="author" items="${authors}">
			<option value="${author}">
			<c:out	value="${author.getFirstName()} "></c:out>
			</option>
			</c:forEach>
	</select>			
	</div>
	</div>
	
	<div class="row mb-3">
	<label for="blog"  class="col-sm-2 col-form-label">Пост</label>
	<div class="col-sm-10">
	<select name ="blog" id="staticBlog" class="form-control" >
		<option>Выберите пост</option>
		<c:forEach var="blog" items="${blogs}">
			<option value="${blog}">
			<c:out	value="${blog.getTitle()} "></c:out>
			</option>
			</c:forEach>
	</select>			
	</div>
	</div>
	<div class="row mb-3">
<label for="datecomment" class="col-sm-2 col-form-label">Дата</label>
	<div class="col-sm-10">
	<input type="date" name="datecomment" class="form-control" id="staticDateComments" />
	</div>
	</div>
	
	
	<div class="col text-center">
	<button type="submit" class="btn btn-primary" id="btn-1">Добавить</button>
	</div>
	</form>
</div> 
</div>
</section>
</article>
<jsp:include page="/WEB-INF/jspf/footer.jsp" />
</body>
</html>
