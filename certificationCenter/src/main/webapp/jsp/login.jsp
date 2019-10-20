<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<head>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/main.css">
    <title>Login page</title>
</head>
<body>
<jsp:include page="header.jsp"/>
    <div class="container col-lg-6 col-md-8 col-sm-12">
        <div class="alert-primary reg-form">
            <p style="color:red">${errorMessage}</p>
            <c:url value="login.html" var="loginUrl"/>
            <form role="form" action="${loginUrl}" method="post">
                <div class="form-group">
                    <label for="login" class="control-label col-md"><fmt:message key="formForLogin.loginLabel" bundle="${rb}"/></label>
                    <div class="col-md">
                        <fmt:message var="loginPlaceholder" key="formForLogin.loginPlaceholder" bundle="${rb}"/>
                        <input type="text" class="form-control" id="login" name="login"
                               aria-describedby="loginHelp" placeholder="${loginPlaceholder}" value="${savedLogin}">
                        <small id="loginHelp" class="form-text text-muted"><fmt:message key="formForLogin.loginHelp" bundle="${rb}"/></small>
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="control-label col-md"><fmt:message key="formForLogin.passwordLabel" bundle="${rb}"/></label>
                    <div class="col-md">
                        <fmt:message var="passwordPlaceholder" key="formForLogin.passwordPlaceholder" bundle="${rb}"/>
                        <input type="password" class="form-control" id="password" name="password"
                               aria-describedby="passwordHelp" placeholder="${passwordPlaceholder}" value="${savedPassword}">
                        <small id="passwordHelp" class="form-text text-muted"><fmt:message key="formForLogin.passwordHelp" bundle="${rb}"/></small>
                    </div>
                </div>
                <div class="form-group form-check">
                    <input type="checkbox" class="form-check-input" id="checkMeOut" name="rememberMe">
                    <label class="form-check-label" for="checkMeOut"><fmt:message key="formForLogin.checkboxLabel" bundle="${rb}"/></label>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message key="formForLogin.button" bundle="${rb}"/></button>
            </form>
        </div>
    </div>

    <script src="js/bootstrap.min.js"></script>
</body>
</html>
