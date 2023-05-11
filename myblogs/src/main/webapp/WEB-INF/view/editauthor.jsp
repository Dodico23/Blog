<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ page import="domain.Author"%>

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


</tr>
</c:forEach>
</tbody>
</table>
</aside>
</section>
<section>
<article>
<h3>Редактирование автора</h3>
<div class="text-article">
<form method="POST" action="">
<p>


<div class="mb-3 row">
<label for="idrole" class="col-sm-4 col-form-label">
Код автора</label>
<div class="col-sm-6">
<input type="text" class="form-control" readonly
value="${authorsEdit[0].getId()}" />
</div>
</div>
<div class="mb-3 row">
<label for="namerole"
class="col-sm-4 col-form-label">Имя Автора</label>
<div class="col-sm-6">
<input type="text" class="form-control" name="firstname"
value="${authorsEdit[0].getFirstName()}" />
</div>
</div>

<div class="mb-3 row">
<label for="namerole"
class="col-sm-4 col-form-label">Фамилия автора</label>
<div class="col-sm-6">
<input type="text" class="form-control" name="lastname"
value="${authorsEdit[0].getLastName()}" />
</div>
</div>

<div class="mb-3 row">
<label for="namerole"
class="col-sm-4 col-form-label">Почта</label>
<div class="col-sm-6">
<input type="text" class="form-control" name="email"
value="${authorsEdit[0].getEmail()}" />
</div>
</div>

<div class="mb-3 row">
<label for="namerole"
class="col-sm-4 col-form-label">Телефон</label>
<div class="col-sm-6">
<input type="text" class="form-control" name="phone"
value="${authorsEdit[0].getPhone()}" />
</div>
</div>

<div class="mb-3 row">
<label for="namerole"
class="col-sm-4 col-form-label">Дата Регистрации</label>
<div class="col-sm-6">
<input type="date" class="form-control" name="dataregistration"
value="${authorsEdit[0].getDataRegistration()}" />
</div>
</div>
<p>
<button type="submit"
class="btn btn-primary">Редактировать</button>
<a href='<c:url value="/author" />'
role="button"
class="btn btn-secondary">Отменить/Возврат</a>


</p>
</form>

</div>
</article>



<jsp:include page="/WEB-INF/jspf/footer.jsp" />
</section>
</div>
</body>
</html>

