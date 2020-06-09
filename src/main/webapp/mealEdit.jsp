<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="<c:url value="style.css"/>" rel="stylesheet" type="text/css"/>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meal</h2>
<form method="POST" action='meals' name="meals">
    <label>dateTime <input type="datetime-local" name="dateTime" maxlength="30" value="${meal.dateTime}"
                           required/></label>
    <label>description <input type="text" name="description" maxlength="200" value="${meal.description}"
                              required/></label>
    <label>calories <input type="number" name="calories" min="1" max="2000000000" step="1" value="${meal.calories}"
                           required/></label>
    <input type="hidden" name="id" value="${meal.id}"/>
    <input type="submit" value="Submit"/>
</form>

</body>
</html>
