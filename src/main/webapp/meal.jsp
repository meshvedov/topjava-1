<%--
  Created by IntelliJ IDEA.
  User: mic
  Date: 07.11.17
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-16" language="java" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://example.com/functions" prefix="f" %>
<html>
<head>
    <title>Add new meal</title>
</head>
<body>
<form method="post" action="/meals" name="frmAddMeal">
    <%--Meal ID: <input type="text" readonly="readonly" name="mealID" value=" <c:out value="${meal.id}"/> "/> <br/>--%>
    Meal ID: <input type="text" readonly="readonly" name="mealID" value="${meal.id}"/> <br/>
    Дата/время: <input type="text"
                       name="dateTime"
                       value="${f:formatLocalDateTime(meal.dateTime, 'dd.MM.yyyy HH:mm')}"
            <%--value="${meal.dateTime}"--%>
                /> <br/>
    Описание: <input type="text" name="description" value="${meal.description}"/> <br/>
    Калории:  <input type="text"
                    name="calories"
                    value="${meal.calories}"/> <br>
    <input type="submit" value="Submit"/>

</form>
</body>
</html>
