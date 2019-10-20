<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
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
            <h2><fmt:message key="errorPage.request.fail" bundle="${rb}"/> ${pageContext.errorData.requestURI}</h2>
            <br/>
            <h3><fmt:message key="errorPage.exception" bundle="${rb}"/> ${pageContext.exception}</h3>
            <br/>
            <h3><fmt:message key="errorPage.exception.message" bundle="${rb}"/>${pageContext.exception.message}</h3>
        </c:when>
        <c:when test="${not empty pageContext.errorData.requestURI}">
            <h2><fmt:message key="errorPage.page.existed" bundle="${rb}"/> ${pageContext.errorData.requestURI}</h2>
        </c:when>
        <c:otherwise>
            ${errorMessage}
        </c:otherwise>
    </c:choose>
    ${pageContext.session.removeAttribute("errorMessage")}
    <c:url value="/index.html" var="mainUrl"/>
    <a href="${mainUrl}"><fmt:message key="errorPage.redirect" bundle="${rb}"/></a>
</body>
</html>
