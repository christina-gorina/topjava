<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }

        .col {
            float: left;
            width: 50%;
        }

        .line {
            float: left;
            width: 100%;
            margin-bottom: 20px;
        }

        .line {
            float: left;
            width: 100%;
            margin-bottom: 20px;
        }

        label {
            display: block;
            font-weight: bold;
        }

        table {
            margin-bottom: 10px;
        }

    </style>
</head>
<body>
    <section>
        <h3><a href="index.html">Home</a></h3>
        <hr/>
        <h2>Meals</h2>
        <div class="line">
            <form id="filter" method="get" action="meals">
                <input type="hidden" name="action" value="filter">
                <div class="line">
                    <div class="col">
                        <div class="col">
                            <label for="startDate">От даты (включая)</label>
                            <input type="date" name="startDate" id="startDate" value="${request.getParameter("startDate")}">
                        </div>
                        <div class="col">
                            <label for="endDate">До даты (включая)</label>
                            <input type="date" name="endDate" id="endDate" value="${request.getParameter("endDate")}">
                        </div>
                    </div>
                    <div class="col">
                        <div class="col">
                            <label for="startTime">От времени (включая)</label>
                            <input type="time" name="startTime" id="startTime" value="${request.getParameter("startTime")}">
                        </div>
                        <div class="col">
                            <label for="endTime">До времени (исключая)</label>
                            <input type="time" name="endTime" id="endTime" value="${request.getParameter("endTime")}">
                        </div>
                    </div>
                </div>
                <div class="line">
                    <input type="submit" value="Отфильтровать">
                </div>
            </form>
        </div>
        <hr/>
        <div class="line">
            <table border="1" cellpadding="8" cellspacing="0">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Description</th>
                    <th>Calories</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <c:forEach items="${meals}" var="meal">
                    <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
                    <tr class="${meal.excess ? 'excess' : 'normal'}">
                        <td>
                                <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                                <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                                <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                                ${fn:formatDateTime(meal.dateTime)}
                        </td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
                        <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                        <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
                    </tr>
                </c:forEach>
            </table>
            <a href="meals?action=create">Add Meal</a>
        </div>
    </section>
</body>
</html>