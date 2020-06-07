<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Meals</title>
    </head>
    <body>
        <h3><a href="index.html">Home</a></h3>
        <hr>
        <h2>Meals</h2>
        <table style="width: 100%; border-collapse: collapse;">
            <thead>
                <tr style="background-color: darkcyan;">
                    <th>Date/Time</th>
                    <th>Description</th>
                    <th>Calories</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach var="meal" items="${mealsTo}">
                <tr style="color: ${meal.isExcess() ? 'red' : 'green'}">
                    <td>${meal.getDateTimeFormatted()}</td>
                    <td>${meal.getDescription()}</td>
                    <td>${meal.getCalories()}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </body>
</html>
