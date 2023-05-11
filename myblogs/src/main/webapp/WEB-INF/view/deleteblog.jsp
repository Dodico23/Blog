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
                           value="${blogDelete[0].getId()}" />
                </div>
            </div>

            <div class="row mb-3">
                <label for="computerName" class="col-sm-2 col-form-label">Заголовок</label>
                <div class="col-sm-10">
                    <input type="text" name="title" class="form-control" id="staticTitle"
                     value="${blogDelete[0].getTitle()}" />
                </div>
            </div>
            
            <div class="row mb-3">
                <label for="computerName" class="col-sm-2 col-form-label">Описание</label>
                <div class="col-sm-10">
                    <input type="text" name="content" class="form-control" id="staticContent"
                     value="${blogDelete[0].getContent()}" />
                </div>
            </div>
            
             <div class="row mb-3">
                <label for="computerName" class="col-sm-2 col-form-label">Дата</label>
                <div class="col-sm-10">
                    <input type="text" name="date" class="form-control" id="staticDate"
                     value="${blogDelete[0].getDate()}" />
                </div>
            </div>
            

<div class="row mb-3">
                <label for="computerStatus" class="col-sm-2 col-form-label">Автор</label>
                <div class="col-sm-10">
                    <select name="computerStatus" class="form-control" >
                        <option>Выберите автора</option>
                        <c:forEach var="author" items="${authors}" >
                            <option value="${author}" >
                                <c:out value="${author.getFirstName()}"></c:out>
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>


<div class="col text-center">
                <button type="submit" class="btn btn-primary" id="btn-1">Удалить</button>
                <a href='<c:url value="/blog" />' role="button" id="btn-2" class="btn btn-secondary">Отменить/Возврат</a>
            </div>
</form>

</div>
</article>



<jsp:include page="/WEB-INF/jspf/footer.jsp" />
</section>
</div>
</body>
</html>

