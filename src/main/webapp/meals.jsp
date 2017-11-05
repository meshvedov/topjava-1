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

        .botonLine {
            border-bottom: 2px solid #a09986;
        }

        .red {
            color: red;
        }

        .green {
            color: green;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>

<table width="50%">
    <thead>
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${meal}">
        <tr <c:if test="${item.exceed == false}"> class="red" </c:if>
            <c:if test="${item.exceed == true}"> class="green" </c:if> >
            <td>${f:formatLocalDateTime(item.dateTime, 'dd.MM.yyyy')}</td>
            <td>${item.description}</td>
            <td>${item.calories}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
