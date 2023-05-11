<%@ page language="java" contentType="text/html" 
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="domain.Blog"%>
<%@ page import="domain.Author"%>
 

    
<!DOCTYPE html>
<html>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title>Посты</title>
<head>
<meta charset="UTF-8">
<title>Blogs</title>
</head>
<body>
<jsp:include page="/WEB-INF/jspf/header.jsp" />
<div id="main">
<section>
<aside class="leftAside">
<h3>Список постов</h3>

<table class="table table-bordered">
	<thead class="thead-dark">
		<tr>
		<th scope="col">Код</th>
		<th scope="col">Автор</th>
		<th scope="col">Название поста</th>
		<th scope="col">Описание</th>
		<th scope="col">Дата публикации</th>
		<th scope="col">Редактировать</th>
		<th scope="col">Удалить</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach var="blog" items="${blogs}">
		<tr>
		<td>${blog.getId()}</td>
		<td>${blog.getAuthor()}</td>
		<td>${blog.getTitle()}</td>
		<td>${blog.getContent()}</td>
		<td>${blog.getDate()}</td>
		<td width="5"><a href='<c:url value="/editblog?id=${blog.getId()}" />'role="button" class="btn btn-outline-primary"><img alt="Редактировать" src="images/редактировать.png"></a></td> 
<td width="5"><a href='<c:url value="/deleteblog?id=${blog.getId()}"/>'role="button" class="btn btn-outline-primary"> <img alt="Удалить" src="images/удалить.png"></a></td>
		
		
		</tr>
	</c:forEach>
	</tbody>
</table>

</aside>
</section>
<section>
<article>
<h3>Добавить пост</h3>
<div class="container" id="container-form">
	<form method="POST" action="">
	<div class="row mb-3">
	<label for="title" class="col-sm-2 col-form-label">Заголовок</label>
	<div class="col-sm-10">
	<input type="text" name="title" id="staticText" class="form-control" />
	</div>
	</div>
	
	
	<div class="row mb-3">
	<label for="content" class="col-sm-2 col-form-label">Описание</label>
	<div class="col-sm-10">
	<input type="text" name="content" id="staticContent" class="form-control" />
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
<label for="date" class="col-sm-2 col-form-label" >Дата</label>
<div class="col-sm-10">
	<input type="date" name="date" id="staticDate" class="form-control"/>
	</div>
	</div>
	
	<div class="col text-center">
	<button type="submit" class="btn btn-primary" id="btn-1">Добавить</button>
	</div>
</form>

</div>
</article>
</section>
</div>



<jsp:include page="/WEB-INF/jspf/footer.jsp" />
</body>
</html>
