<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ page import="domain.Author"%>

<!DOCTYPE html>
<html>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" href="css/bootstrap.min.css">

<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title>Авторы</title>
<head>
<meta charset="UTF-8">
<title>Authors</title>
</head>
<body>
<jsp:include page="/WEB-INF/jspf/header.jsp" />
<div id="main">
<section>
<aside class="leftAside">
<h3>Список авторов</h3>
<table>
<thead>
<tr>
<th>Код</th>
<th>Имя Автора</th>
<th>Фамилия Автора</th>
<th>Почта</th>
<th>Телефон</th>
<th>Дата Регистрации</th>
<th>Редактировать</th>
<th>Удалить</th>
</tr>
</thead>
<tbody>
<c:forEach var="authors" items="${author}">
<tr>
<td>${authors.getId()}</td>
<td>${authors.getFirstName()}</td>
<td>${authors.getLastName()}</td>
<td>${authors.getEmail()}</td>
<td>${authors.getPhone()}</td>
<td>${authors.getDataRegistration()}</td>
<td width="5"><a href='<c:url value="/editauthor?id=${authors.getId()}" />'
role="button" class="btn btn-outline-primary">
<img alt="Редактировать" src="images/редактировать.png"></a>
</td> <td width="5"><a href='<c:url value="/deleteauthor?id=${authors.getId()}"/>'
role="button" class="btn btn-outline-primary"> <img
alt="Удалить" src="images/удалить.png"></a></td>

</tr>
</c:forEach>
</tbody>
</table>
</aside>
</section>
<section>
<article>
<h3>Добавить автора</h3>
<div class="text-article">
<form method="POST" action="">
<p>
<label for="firstname">Имя</label> <input type="text" name="firstname" />
</p>
<p>
<label for="lastname">Фамилия</label> <input type="text" name="lastname" />
</p>
<p>
<label for="email">Почта</label> <input type="text" name="email" />
</p>

<p>
<label for="dataregistration">Дата Регистрации</label> <input type="date" name="dataregistration" />
</p>
<p>
<label for="phone">Телефон</label> <input type="text" name="phone" />
</p>
<p>
<button type="submit" >Добавить</button>
</p>
</form>

</div>
</article>
</section>
</div>


<jsp:include page="/WEB-INF/jspf/footer.jsp" />
</body>
</html>

