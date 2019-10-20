<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
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
            <h2><fmt:message key="successPage.operation" bundle="${rb}"/></h2>
            <p> ${message} </p>
        </c:when>
        <c:otherwise>
            <h2><fmt:message key="successPage.authorization" bundle="${rb}"/></h2>
            <p>
                <fmt:message key="successPage.hello" bundle="${rb}"/>
                    ${authorizedUser.surname} ${authorizedUser.name} ${authorizedUser.patronymic}
            </p>
        </c:otherwise>
    </c:choose>
    ${pageContext.session.removeAttribute("message")}
</div>
<ctg:info-footer/>
<script src="js/bootstrap.min.js"></script>
</body>
</html>
