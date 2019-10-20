<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<head>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/main.css">
    <title>Applications</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container">
    <p style="color:red">${errorMessage}</p>
    <fmt:message key="formForApps.applications" bundle="${rb}" var="appsFiled"/>
    <h3>${fn:toUpperCase(appsFiled)}</h3>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th><c:out value="№"/></th>
            <fmt:message key="formForApps.date.added" bundle="${rb}" var="appDate"/>
            <th><c:out value="${appDate}"/></th>
            <fmt:message key="formForApps.regNumber" bundle="${rb}" var="regNumber"/>
            <th><c:out value="${regNumber}"/></th>
            <fmt:message key="formForApps.date.finished" bundle="${rb}" var="completionDate"/>
            <th><c:out value="${completionDate}"/></th>
            <fmt:message key="formForApps.stage" bundle="${rb}" var="stage"/>
            <th><c:out value="${stage}"/></th>
        </tr>
        </thead>
        <tbody
        <c:forEach var="entity" items="${apps}" varStatus="status">
            <tr>
                <td>
                    <a href="showApplication.html?application_id=<c:out value='${entity.id}' />">
                        <c:out value="${entity.id}"/></a>
                </td>
                <c:set var="datePattern" value="dd/MM/yyyy"/>
                <td>
                    <c:set var="dateAdd" value="${entity.date_add}"/>
                    <fmt:parseDate value="${dateAdd}" pattern="yyyy-MM-dd"
                                   var="parsedDateAdd"/>
                    <fmt:formatDate value="${parsedDateAdd}"
                                    pattern="${datePattern}"
                                    var="formattedDateAdd"/>
                    <c:out value="${formattedDateAdd}"/>
                </td>
                <td>
                    <c:set var="number" value="${entity.reg_num}"
                           scope="request"/>
                    <c:if test="${number > 0}">
                        <c:out value="${number}"/>
                    </c:if>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${not empty entity.date_resolve}">
                            <c:set var="dateResolve"
                                   value="${entity.date_add}"/>
                            <fmt:parseDate value="${dateResolve}"
                                           pattern="yyyy-MM-dd"
                                           var="parsedDateResolve"/>
                            <fmt:formatDate value="${parsedDateResolve}"
                                            pattern="${datePattern}"
                                            var="formattedDateResolve"/>
                            <c:out value="${formattedDateResolve}"/>
                        </c:when>
                    </c:choose>
                </td>
                <td>
                    <c:catch var="e">
                        <c:out value="${entity.status.status}"/>
                    </c:catch>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<jsp:include page="pagination.jsp"/>
<script src="js/bootstrap.min.js"></script>
</body>
</html>
