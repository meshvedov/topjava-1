<%--
  Created by IntelliJ IDEA.
  User: mic
  Date: 04.11.17
  Time: 23:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>

<html>
<head>
    <title>MEALS</title>
    <style type="text/css">
        table {
            font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
            border-collapse: collapse; /* Отображать двойные линии как одинарные */
        }

        th {
            background: #ccc; /* Цвет фона */
            text-align: left; /* Выравнивание по левому краю */
            border-bottom: 3px solid #B9B29F;
        }

        td, th {
            /*border: 1px solid #800; /* Параметры границы */
            padding: 4px; /* Поля в ячейках */
        }

        #botonLine {
            border-bottom: 2px solid #a09986;
        }

        .red {
            background-color: red;
        }

        .green {
            background-color: green;
        }
    </style>
</head>
<body>
<%--<h3><a href="index.html">Home</a></h3>--%>

<table width="50%">
    <thead>
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th colspan="2">Действие</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${meal}">
        <tr class="${item.exceed == false ? 'green' : 'red'}" id="botonLine">
            <td>${f:formatLocalDateTime(item.dateTime, 'dd.MM.yyyy HH:mm')}</td>
            <td>${item.description}</td>
            <td>${item.calories}</td>
            <td><a href="meals?action=edit&mealID=<c:out value="${item.id}"/>">Edit</a> </td>
            <td><a href="meals?action=delete&mealID=<c:out value="${item.id}"/>">Delete</a> </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<p><a href="meals?action=insert">Добавить</a> </p>

</body>
</html>