<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <a class="nav-link" href="index.html">Home page</a>
                </li>
                <c:if test="${not empty role and role eq 'Administrator'}">
                    <li class="nav-item">
                        <a id="users" class="nav-link" href="users.html">Users</a>
                    </li>
                </c:if>
                <c:if test="${not empty role and role eq 'Client'}">
                    <li class="nav-item">
                        <a id="apply" class="nav-link" href="applyFor.html">Apply for</a>
                    </li>
                </c:if>
                <c:if test="${not empty role and role eq 'Client' or role eq 'Expert'}">
                    <li class="nav-item">
                        <a id="apps" class="nav-link" href="applications.html?page=1">Applications</a>
                    </li>
                </c:if>
                <c:if test="${empty role}">
                    <li class="nav-item">
                        <a id="login" class="nav-link" href="login.html">Login</a>
                    </li>
                    <li class="nav-item">
                        <a id="reg" class="nav-link" href="registration.html">Registration</a>
                    </li>
                </c:if>
                <c:if test="${not empty role}">
                    <li class="nav-item">
                        <a id="logout" class="nav-link" href="logout.html">Logout</a>
                    </li>
                </c:if>
                <li class="nav-item">
                    <a class="nav-link" href="home.html" style="color: red">${authorizedUser.login}</a>
                </li>
            </ul>
        </div>
    </nav>
</header>