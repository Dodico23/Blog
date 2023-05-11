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
<table>
	<thead>
		<tr>
		<th scope="col">Код</th>
		<th scope="col">Автор</th>
		<th scope="col">Пост</th>
		<th scope="col">Комментарий</th>
		<th scope="col">Дата Комментария</th>
		
		
		
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
		
		
		
		</tr>
	</c:forEach>
	</tbody>
</table>
</aside>
</section>
<section>
<article>
<h3>Редактирование Комментария</h3>
    <div class="container" id="container-form">
        <form method="POST" action="">
            <div class="row mb-3">
                <label for="id" class="col-sm-2 col-form-label">Код комментария</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" readonly
                           value="${commentEdit[0].getId()}" />
                </div>
            </div>

            <div class="row mb-3">
                <label for="content" class="col-sm-2 col-form-label">Комментарий</label>
                <div class="col-sm-10">
                    <input type="text" name="content" class="form-control" id="staticContent"
                           value="${commentEdit[0].getContent()}" />
                </div>
            </div>

            <div class="row mb-3">
                <label for="datecomment" class="col-sm-2 col-form-label">Дата комментария</label>
                <div class="col-sm-10">
                    <input type="date" name="datecomment" class="form-control" id="staticDateComment"
                           value="${commentEdit[0].getDateComment()}" />
                </div>
            </div>

           <div class="row mb-3">
                <label for="author" class="col-sm-2 col-form-label">Автор</label>
                <div class="col-sm-10">
                    <select name = "author" class="form-control" id="staticAuthor" >
                        <option>Выберите автора</option>
		<c:forEach var="author" items="${author}">
			<option value="${author}">
			<c:out	value="${author.getFirstName()} "></c:out>
			</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="row mb-3">
                <label for="blog" class="col-sm-2 col-form-label">Пост</label>
                <div class="col-sm-10">
                    <select name = "blog" class="form-control" id="staticBlog" >
                        <option>Выберите пост</option>
		<c:forEach var="blog" items="${blogs}">
			<option value="${blog}">
			<c:out	value="${blog.getTitle()} "></c:out>
			</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            

            <div class="col text-center">
                <button type="submit" class="btn btn-primary" id="btn-1">Редактировать</button>
                <a href='<c:url value="/comment" />' role="button" id="btn-2" class="btn btn-secondary">Отменить/Возврат</a>
            </div>
        </form>
    </div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jsp" />
</body>
</html>