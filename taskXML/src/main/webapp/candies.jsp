<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <style>
    table, th, td {
        border: 1px solid black;
        border-collapse: collapse;
    }
    </style>
    <meta charset="utf-8">
    <title>PRINT CANDIES</title>
</head>
<body>
    <table>
        <tr>
            <th text-align: center><c:out value="№ п/п" /></th>
            <th text-align: center><c:out value="Наименование" /></th>
            <th text-align: center><c:out value="Штрихкод" /></th>
            <th text-align: center><c:out value="Тип и свойства конфеты" /></th>
            <th text-align: center><c:out value="Состав" /></th>
            <th text-align: center><c:out value="Пищевая ценность" /></th>
            <th text-align: center><c:out value="Дата изготовления" /></th>
            <th text-align: center><c:out value="Срок годности" /></th>
            <th text-align: center><c:out value="Изготовитель" /></th>
            <th text-align: center colspan="6"><c:out value="Адрес изготовителя" /></th>
        </tr>
        <tr>
            <th colspan="9"></th>
            <th text-align: center><c:out value="Страна" />
            <th text-align: center><c:out value="Индекс" />
            <th text-align: center><c:out value="Область и/или район" />
            <th text-align: center><c:out value="Населенный пункт" />
            <th text-align: center><c:out value="Улица" />
            <th text-align: center><c:out value="Дом" />
        </tr>
        <c:forEach var="elem" items="${candies}" varStatus="status">
            <tr>
                <td><c:out value="${ status.count }" /></td>
                <td><c:out value="${elem.name}" /></td>
                <td><c:out value="${elem.barcode}" /></td>
                <td><c:out value="${elem.candieType}" /></td>
                <td><c:out value="${elem.composition}" /></td>
                <td><c:out value="${elem.nutritionValue}" /></td>
                <td><c:out value="${elem.date}" /></td>
                <td>
                    <c:out value="${elem.shelfLife}" />
                    <c:out value="${elem.expirationMeasure.measure}" />
                </td>
                <td><c:out value="${elem.producer.name}" /></td>
                <td><c:out value="${elem.producer.address.country}" /></td>
                <td><c:out value="${elem.producer.address.postcode}" /></td>
                <td>
                    <c:out value="${elem.producer.address.region} " />
                    <c:out value="${elem.producer.address.district}" />
                </td>
                <td><c:out value="${elem.producer.address.locality}" /></td>
                <td><c:out value="${elem.producer.address.street}" /></td>
                <td>
                    <c:out value="${elem.producer.address.building}" />
                    <c:out value="${elem.producer.address.corps}" />
                </td>
            </tr>
        </c:forEach>
    </table>   
</body>
</html>