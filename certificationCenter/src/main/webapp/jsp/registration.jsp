<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<head>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/main.css">
    <title>Registration page</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="container" style="text-align: center;">
    <div id="alert" class="alert alert-danger" style="display: none">
    </div>
    <div class="alert-warning reg-form">
        <p style="color:red">${errorMessage}</p>
        <form action="registration.html" method="post" onsubmit="checkRegistrationData(this);return false;">
            <div class="form-group">
                <label for="unp">*<fmt:message key="formForRegistration.unpLabel" bundle="${rb}"/>:</label>
                <fmt:message var="unpPlaceholder" key="formForRegistration.unpPlaceholder" bundle="${rb}"/>
                <input id="unp" name="unp" type="number" pattern="\[0-9]{9}" class="form-control" placeholder="${unpPlaceholder}">
            </div>
            <div class="form-group">
                <label for="applicant">*<fmt:message key="formForRegistration.applicantLabel" bundle="${rb}"/>:</label>
                <input id="applicant" name="organisationName" class="form-control">
            </div>
            <div class="form-group">
                <label for="address">*<fmt:message key="formForRegistration.applicantAddress" bundle="${rb}"/>:</label>
                <input id="address" name="organisationAddress" class="form-control">
            </div>
            <div class="form-group">
                <label for="applicantPhone">*<fmt:message key="formForRegistration.applicantPhone" bundle="${rb}"/>:</label>
                <input id="applicantPhone" name="organisationPhone" type="tel" pattern="\+[0-9]{11,12}" class="form-control" placeholder="+375171234567">
            </div>
            <div class="form-group">
                <label for="applicantEmail">*<fmt:message key="formForRegistration.applicantEmail" bundle="${rb}"/>:</label>
                <input id="applicantEmail" name="organisationEmail" type="email" class="form-control" placeholder="name@example.com">
            </div>
            <hr/>
            <br/>
            <hr/>
            <div class="form-group">
                <label for="login" >*<fmt:message key="formForRegistration.login" bundle="${rb}"/>:</label>
                <input id="login" name="login" class="form-control">
            </div>
            <div class="form-group">
                <label for="firstname" >*<fmt:message key="formForRegistration.executorName" bundle="${rb}"/>:</label>
                <input id="firstname" name="name" class="form-control">
            </div>
            <div class="form-group">
                <label for="lastname">*<fmt:message key="formForRegistration.executorSurname" bundle="${rb}"/>:</label>
                <input id="lastname" name="surname" class="form-control">
            </div>
            <div class="form-group">
                <label for="patronymic"><fmt:message key="formForRegistration.executorPatronymic" bundle="${rb}"/>:</label>
                <input id="patronymic" name="patronymic" class="form-control">
            </div>
            <div class="form-group">
                <label for="executorEmail">*<fmt:message key="formForRegistration.executorEmail" bundle="${rb}"/>:</label>
                <input id="executorEmail" name="executorEmail" type="email" class="form-control" placeholder="name@example.com">
            </div>
            <div class="form-group">
                <label for="executorPhone">*<fmt:message key="formForRegistration.executorPhone" bundle="${rb}"/>:</label>
                <input id="executorPhone" name="executorPhone" type="tel" pattern="\+[0-9]{12}" class="form-control" placeholder="+375291234567">
            </div>
            <div class="form-group">
                <label for="password">*<fmt:message key="formForRegistration.password" bundle="${rb}"/>:</label>
                <input id="password" name="password" type="password" class="form-control">
            </div>
            <div class="form-group">
                <label for="repeatPassword">*<fmt:message key="formForRegistration.passwordAgain" bundle="${rb}"/>:</label>
                <input id="repeatPassword" name="passwordAgain" type="password" class="form-control">
            </div>
            <fmt:message var="registration" key="formForRegistration.submit" bundle="${rb}"/>
            <input type="submit" class="btn btn-secondary btn-lg btn-block" value="${registration}">
        </form>
    </div>
</div>

<script src="js/bootstrap.min.js"></script>
<script src="js/main.js"></script>
</body>
</html>
