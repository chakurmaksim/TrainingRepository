<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<c:set var="role" value="${authorizedUser.role.roleName}"/>
<header class="alert-dark">
    <div class="logo-form">
        <img src="img/logo.png" width="50px" height="50px">
    </div>
    <nav class="navbar navbar-expand-md navbar-light">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="index.html"><fmt:message key="menu.home" bundle="${rb}"/></a>
                </li>
                <c:if test="${not empty role and role eq 'Administrator'}">
                    <li class="nav-item">
                        <a id="users" class="nav-link" href="users.html"><fmt:message key="menu.users" bundle="${rb}"/></a>
                    </li>
                </c:if>
                <c:if test="${not empty role and role eq 'Client'}">
                    <li class="nav-item">
                        <a id="apply" class="nav-link" href="applyFor.html"><fmt:message key="menu.applyFor" bundle="${rb}"/></a>
                    </li>
                </c:if>
                <c:if test="${not empty role and role eq 'Client' or role eq 'Expert'}">
                    <li class="nav-item">
                        <a id="apps" class="nav-link" href="applications.html?page=1"><fmt:message key="menu.applications" bundle="${rb}"/></a>
                    </li>
                </c:if>
                <c:if test="${empty role}">
                    <li class="nav-item">
                        <a id="login" class="nav-link" href="login.html"><fmt:message key="menu.login" bundle="${rb}"/></a>
                    </li>
                    <li class="nav-item">
                        <a id="reg" class="nav-link" href="registration.html"><fmt:message key="menu.registration" bundle="${rb}"/></a>
                    </li>
                </c:if>
                <c:if test="${not empty role}">
                    <li class="nav-item">
                        <a id="logout" class="nav-link" href="logout.html"><fmt:message key="menu.logout" bundle="${rb}"/></a>
                    </li>
                </c:if>
                <li class="nav-item">
                    <a class="nav-link" href="home.html" style="color: red">${authorizedUser.login}</a>
                </li>
            </ul>
            <form class="form-inline my-2 my-lg-0" action="chooseLanguage.html">
                <div class="input-group">
                    <select name="locale" class="custom-select" id="languageSelector">
                        <option value="ru_RU"><fmt:message key="language.russian" bundle="${rb}"/></option>
                        <option value="en_EN"><fmt:message key="language.english" bundle="${rb}"/></option>
                        <option value="de_DE"><fmt:message key="language.german" bundle="${rb}"/></option>
                    </select>
                    <div class="input-group-append">
                        <input type="hidden" name="servletPath" value="${pageContext.request.servletPath}">
                        <c:if test="${not empty application}">
                            <c:set var="application" value="${application}" scope="request"/>
                        </c:if>
                        <button class="btn btn-outline-dark" type="submit"><fmt:message key="language.choose" bundle="${rb}"/></button>
                    </div>
                </div>
            </form>
        </div>
    </nav>
</header>