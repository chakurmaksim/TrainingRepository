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
<c:set var="role" value="${authorizedUser.role.roleName}"/>
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
            <c:if test="${not empty role and role eq 'Expert'}">
                <fmt:message key="formForRegistration.applicationRegister" bundle="${rb}" var="appReg"/>
                <th><c:out value="${appReg}"/></th>
            </c:if>
        </tr>
        </thead>
        <tbody
        <c:forEach var="entity" items="${apps}" varStatus="status">
            <form action="registerApplication.html" method="post">
                <c:set var="datePattern" value="dd/MM/yyyy"/>
                <tr>
                    <td>
                        <a href="showApplication.html?application_id=<c:out value='${entity.id}' />">
                            <c:out value="${entity.id}"/></a>
                    </td>
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
                        <c:choose>
                            <c:when test="${not empty role and role eq 'Client'}">
                                <c:if test="${number > 0}">
                                    <c:out value="${number}"/>
                                </c:if>
                            </c:when>
                            <c:when test="${not empty role and role eq 'Expert'}">
                                <c:if test="${number > 0}">
                                    <input type="number" name="registration_number" value="${number}" placeholder="[0-9]{1,9}">
                                </c:if>
                                <c:if test="${number == 0}">
                                    <input type="number" name="registration_number" placeholder="[0-9]{1,9}">
                                </c:if>
                            </c:when>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty entity.date_resolve}">
                                <c:set var="dateResolve"
                                       value="${entity.date_resolve}"/>
                                <fmt:parseDate value="${dateResolve}"
                                               pattern="yyyy-MM-dd"
                                               var="parsedDateResolve"/>
                                <fmt:formatDate value="${parsedDateResolve}"
                                                pattern="${datePattern}"
                                                var="formattedDateResolve"/>
                                <c:if test="${not empty role and role eq 'Client'}">
                                    <c:out value="${formattedDateResolve}"/>
                                </c:if>
                                <c:if test="${not empty role and role eq 'Expert'}">
                                    <input type="text" name="date_resolve" value="${formattedDateResolve}" placeholder="dd/MM/yyyy">
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${not empty role and role eq 'Expert'}">
                                    <input type="text" name="date_resolve" placeholder="dd/MM/yyyy">
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:if test="${not empty role and role eq 'Client'}">
                            <c:catch var="e">
                                <c:out value="${entity.status.status}"/>
                            </c:catch>
                        </c:if>
                        <c:if test="${not empty role and role eq 'Expert'}">
                            <c:catch var="e">
                                <select name="application_status_index">
                                    <option value="0" <c:if test="${entity.status.index == 0}">selected</c:if>>
                                        На регистрации
                                    </option>
                                    <option value="1" <c:if test="${entity.status.index == 1}">selected</c:if>>
                                        Рассмотрение и принятие решения
                                    </option>
                                    <option value="2" <c:if test="${entity.status.index == 2}">selected</c:if>>
                                        Отклонена
                                    </option>
                                    <option value="3" <c:if test="${entity.status.index == 3}">selected</c:if>>
                                        Работы завершены
                                    </option>
                                </select>
                            </c:catch>
                        </c:if>
                    </td>
                    <c:if test="${not empty role and role eq 'Expert'}">
                        <fmt:message key="formForRegistration.submit" bundle="${rb}" var="register" />
                        <td>
                            <input type="hidden" name="servletPath" value="${pageContext.request.servletPath}">
                            <input type="hidden" name="application_id" value="<c:out value='${entity.id}'/>">
                            <input type="submit" value="${register}" onsubmit="putApplicationListToSession(this);return false;">
                        </td>
                    </c:if>
                </tr>
            </form>
        </c:forEach>
        </tbody>
    </table>
</div>
<script>
    function putApplicationListToSession(form) {
        ${pageContext.session.setAttribute("applicationList", apps)}
        form.submit();
    }
</script>
<jsp:include page="pagination.jsp"/>
<script src="js/bootstrap.min.js"></script>
</body>
</html>
