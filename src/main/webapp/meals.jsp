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
        <h2>Meals</h2>
        <table class="meals_table" border="1" >
            <thead>
                <tr>
                    <th>Date/Time</th>
                    <th>Description</th>
                    <th>Calories</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach var="meal" items="${mealsTo}">
                <tr class="${meal.excess ? 'red' : 'green'}">
                    <td>${meal.dateTime.format(formatter)}</td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </body>
</html>
