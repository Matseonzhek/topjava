<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>

<jsp:useBean id="meal" class="ru.javawebinar.topjava.model.Meal" scope="request"/>

<form method="post" action='MealServlet' name="frmUPDMealRecord">
    idMeal <input type="number" readonly="readonly" name="idMeal" value="<c:out value="${meal.id}"/> "/></br>
    DateTime<input type="datetime-local" name="dateTime" value="<c:out value ="${meal.dateTime}" />"/> </br>
    Description: <input type="text" name="description" value="<c:out value="${meal.description}"/>"/> </br>
    Calories: <input type="number" name="calories" value="<c:out value="${meal.calories}"/> "/> </br>

    <button type="submit"> Save</button>
    <button type="reset"> Cancel</button>

</form>
</body>
</html>
