<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <h2>ПОДАННЫЕ ЗАЯВКИ</h2>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th><c:out value="№" /></th>
                <th><c:out value="Дата подачи" /></th>
                <th><c:out value="Регистрационный номер" /></th>
                <th><c:out value="Дата окончания работ" /></th>
                <th><c:out value="Этап работ" /></th>
            </tr>
            </thead>
            <tbody>
                <c:forEach var="entity" items="${apps}" varStatus="status">
                    <tr>
                        <td>
                            <a href="showApplication.html?application_id=<c:out value='${entity.id}' />">
                                <c:out value="${entity.id}" /></a>
                        </td>
                        <td><c:out value="${entity.date_add}" /></td>
                        <td>
                            <c:set var="number" value="${entity.reg_num}" scope="request" />
                            <c:if test="${number == 0}">
                                <c:out value="Не зарегистрирована" />
                            </c:if>
                            <c:if test="${number > 0}">
                                <c:out value="${number}" />
                            </c:if>
                        </td>
                        <td>
                            <c:catch var="e">
                                <c:out value="${entity.date_resolve}" />
                            </c:catch>
                            <c:if test="${e != null}">
                                <c:out value="${entity.date_resolve}" />
                            </c:if>
                        </td>
                        <td>
                            <c:catch var="e">
                                <c:out value="${entity.status.status}" />
                            </c:catch>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="container">
        <ul class="pagination">
            <li class="page-item"><a class="page-link" href="#">Previous</a></li>
            <li class="page-item active"><a class="page-link" href="applications.html?page=1">1</a></li>
            <li class="page-item"><a class="page-link" href="applications.html?page=2">2</a></li>
            <li class="page-item"><a class="page-link" href="applications.html?page=3">3</a></li>
            <li class="page-item"><a class="page-link" href="#">Next</a></li>
        </ul>
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
