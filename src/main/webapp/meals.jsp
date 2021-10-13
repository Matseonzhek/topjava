<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<p><a href="${pageContext.request.contextPath}/editMeal.jsp?action=insert"> Add Meal </a></p>
<table border="1" cellspacing="0" cellpadding="2">
    <tr>
        <th style="align-items: center"> Date</th>
        <th style="align-items: self-start"> Description</th>
        <th style="align-items: self-start"> Calories</th>
        <th></th>
        <th></th>
    </tr>
    <jsp:useBean id="mealDao" class="ru.javawebinar.topjava.dao.MealDao" scope="request"/>
    <c:forEach items="${mealDao.mealToList}" var="mealTo">
        <tr style="color: ${mealTo.excess ? '#DC143C' :  '#7FFF00'}">
            <td> ${mealTo.date} ${mealTo.time} </td>
            <td> ${mealTo.description} </td>
            <td> ${mealTo.calories} </td>
            <td><a href="${pageContext.request.contextPath}/meals.jsp?action=edit&mealId=<c:out value="${mealTo.id}"/>">Update</a>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/meals.jsp?action=delete&mealId=<c:out value="${mealTo.id}"/>">Delete </a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
