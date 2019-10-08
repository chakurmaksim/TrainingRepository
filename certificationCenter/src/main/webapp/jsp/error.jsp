<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Exception page</title>
</head>
<body>
    <c:choose>
        <c:when test="${not empty error}">
            <h2>${error}</h2>
        </c:when>
        <c:when test="${pageContext.errorData.statusCode eq '500'}">
            <h2>Запрос на страницу ${pageContext.errorData.requestURI} не выполнен</h2>
            <br/>
            <h3>Возникшее исключение: ${pageContext.exception}</h3>
            <br/>
            <h3>Сообщение возникшего исключения: ${pageContext.exception.message}</h3>
        </c:when>
        <c:when test="${not empty pageContext.errorData.requestURI}">
            <h2>Запрошенная страница ${pageContext.errorData.requestURI} не найдена на сервере</h2>
        </c:when>
        <c:otherwise>Непредвиденная ошибка приложения</c:otherwise>
    </c:choose>
    <c:url value="/index.html" var="mainUrl"/>
    <a href="${mainUrl}">На главную</a>
</body>
</html>
