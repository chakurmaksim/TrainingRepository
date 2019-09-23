<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/main.css">
    <title>Title</title>
</head>
<body>
<jsp:include page="header.jsp"/>
    <div class="container col-lg-6 col-md-8 col-sm-12">
        <div class="alert-primary reg-form">
            <c:url value="/login.html" var="loginUrl"/>
            <form role="form" action="${loginUrl}" method="post">
                <div class="form-group">
                    <label for="login" class="control-label col-md">Login</label>
                    <div class="col-md">
                        <input type="text" class="form-control" id="login" name="login"
                               aria-describedby="loginHelp" placeholder="Enter your login">
                        <small id="emailHelp" class="form-text text-muted">It should be unique</small>
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="control-label col-md">Password</label>
                    <div class="col-md">
                        <input type="password" class="form-control" id="password" name="password"
                               aria-describedby="passwordHelp" placeholder="enter your password">
                        <small id="passwordHelp" class="form-text text-muted">We'll never share your password with anyone else.</small>
                    </div>
                </div>
                <div class="form-group form-check">
                    <input type="checkbox" class="form-check-input" id="checkMeOut">
                    <label class="form-check-label" for="checkMeOut">Remember me</label>
                </div>
                <button type="submit" class="btn btn-primary">Sign in</button>
            </form>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous">
    </script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous">
    </script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>
