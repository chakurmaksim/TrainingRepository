<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="ctg" uri="customtags" %>
<html>
<head>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/main.css">
    <title>Authorized</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div id="content">
    <c:choose>
        <c:when test="${not empty message}">
            <h2>Транзакция</h2>
            <p> ${message} </p>
        </c:when>
        <c:otherwise>
            <h2>Авторизация</h2>
            <p>
                Здравствуйте, ${authorizedUser.surname} ${authorizedUser.name} ${authorizedUser.patronymic}
            </p>
        </c:otherwise>
    </c:choose>
    ${pageContext.session.removeAttribute("message")}
</div>
<ctg:info-footer/>
<script src="js/bootstrap.min.js"></script>
</body>
</html>
