<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ page import="domain.Author"%>
<%@ page import="domain.Blog"%>

<!DOCTYPE html>
<html>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" href="css/bootstrap.min.css">

<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title>Редактирование Автора</title>
<head>
<meta charset="UTF-8">
<title>Authors</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="css/bootstrap.min.css">
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


</tr>
</c:forEach>
</tbody>
</table>

	
</aside>
</section>
<section>
<article>
<h3>Редактирование поста</h3>
 <div class="container" id="container-form">
        <form method="POST" action="">
            <div class="row mb-3">
                <label for="id" class="col-sm-2 col-form-label">Код поста</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" readonly
                           value="${blogEdit[0].getId()}" />
                </div>
            </div>

            <div class="row mb-3">
                <label for="title" class="col-sm-2 col-form-label">Заголовок</label>
                <div class="col-sm-10">
                    <input type="text" name="title" class="form-control" id="staticTitle"
                     value="${blogEdit[0].getTitle()}" />
                </div>
            </div>
            
            <div class="row mb-3">
                <label for="content" class="col-sm-2 col-form-label">Описание</label>
                <div class="col-sm-10">
                    <input type="text" name="content" class="form-control" id="staticContent"
                     value="${blogEdit[0].getContent()}" />
                </div>
            </div>
            
             
            

<div class="row mb-3">
                <label for="author" class="col-sm-2 col-form-label">Автор</label>
                <div class="col-sm-10">
                    <select name="author" id="staticAuthor" class="form-control" >
                        <option>Выберите автора</option>
                        <c:forEach var="author" items="${author}" >
                            <option value="${author}" >
                                <c:out value="${author.getFirstName()}"></c:out>
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
<div class="row mb-3">
                <label for="date" class="col-sm-2 col-form-label">Дата</label>
                <div class="col-sm-10">
                    <input type="date" name="date" class="form-control" id="staticDate"
                     value="${blogEdit[0].getDate()}" />
                </div>
            </div>

<p>
<button type="submit" class="btn btn-primary">Редактировать</button>
<a href='<c:url value="blog" />'role="button"class="btn btn-secondary">Отменить/Возврат</a>


</p>
</form>

</div>
</article>



<jsp:include page="/WEB-INF/jspf/footer.jsp" />
</section>
</div>
</body>
</html>

