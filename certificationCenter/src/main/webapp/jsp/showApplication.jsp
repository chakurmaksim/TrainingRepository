<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/show.css">
    <title>Title</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<c:set var="app" value="${application}" scope="page"/>
<c:set var="role" value="${authorizedUser.role.roleName}"/>
<c:if test="${not empty role and role eq 'Client'}">
    <div class="container">
        <ul class="list-group list-group-horizontal">
            <li class="list-group-item active">Show application</li>
            <li class="list-group-item">
                <a href="edit.html?id=${app.id}">Edit application</a>
            </li>
            <li class="list-group-item">
                <a href="delete.html?id=${app.id}">Delete application</a>
            </li>
        </ul>
    </div>
</c:if>
<div id='content'>
    <div class='content'>
        <div id='show'>
            <p class='right header'>Общество с ограниченной ответственностью</p>
            <p class='right header'>«Центр сертификации продукции»</p>
            <p class='right header'>220017, г.Минск, ул. Казимировская, 15</p>
            <p class='right header'>тел. +375 17 123-45-67</p>
            <p class='right header'>адрес эл. почты: info@сertification.by</p>
            <p class="center">
                <c:choose>
                    <c:when test="${app.reg_num > 0}">
                        <c:set var="regNum" value="${app.reg_num}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="regNum" value=""/>
                    </c:otherwise>
                </c:choose>
                <c:set var="datePattern" value="MM/dd/yyyy"/>
                <c:set var="date" value="${app.date_add}"/>
                <fmt:parseDate value="${date}" pattern="yyyy-MM-dd" var="parsedDate"/>
                <fmt:formatDate value="${parsedDate}" pattern="${datePattern}" var="formattedDate"/>
                <b>ЗАЯВКА №<c:out value="${regNum}"/> от <c:out value="${formattedDate}"/></b>
            </p>
            <p class="center">на проведение регистрации декларации о соответствии</p>
            <table>
                <tr>
                    <td class="title"></td>
                    <td class="data"><c:out value="${app.org.name}"/></td>
                </tr>
                <tr>
                    <td></td>
                    <td class="help">наименование заявителя</td>
                </tr>
            </table>
            <table>
                <tr>
                    <td class="title">место нахождения</td>
                    <td class="data"> </td>
                </tr>
            </table>
            <table>
                <tr><td class="data"><c:out value="${app.org.address}"/> </td></tr>
            </table>
            <table>
                <tr>
                    <td class="title">УНП</td><td class="data"><c:out value="${app.org.unp}"/></td>
                    <td class="title">тел.</td><td class="data"><c:out value="+${app.org.phoneNumber}"/></td>
                    <td class="title">email</td><td class="data"><c:out value="${app.org.email}"/></td>
                </tr>
            </table>
            <p>прошу провести регистрацию декларации о соответствии продукции</p>
            <table>
                <tr>
                    <td class="data">
                        <c:forEach var="product" items="${app.products}">
                            <c:out value="${product.name}, "/>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td class="help">наименование и обозначение продукции, на которую распространяется декларация о
                        соответствии, сведения о продукции, обеспечивающие ее идентификацию (тип, марка, артикул продукции)
                    </td>
                </tr>
            </table>
            <table>
                <tr>
                    <td class="title">код ТН ВЭД ЕАЭС:</td>
                    <td class="data">
                        <c:forEach var="product" items="${app.products}">
                            <c:out value="${product.code}, "/>
                        </c:forEach>
                    </td>
                </tr>
            </table>
            <table>
                <tr>
                    <td class="title">изготовитель:</td>
                    <td class="data"><c:out value="${app.products[0].producer}"/></td>
                </tr>
                <tr>
                    <td></td>
                    <td class="help">полное наименование изготовителя
                    </td>
                </tr>
            </table>
            <table>
                <tr>
                    <td class="title">место нахождения:</td>
                    <td class="data"><c:out value="${app.products[0].address}"/></td>
                </tr>
            </table>
            <table>
                <tr>
                    <td class="title"></td>
                    <td class="data"><c:out value="${app.products[0].attr.attribute}"/> </td>
                </tr>
                <tr>
                    <td></td>
                    <td class="help">сведения о серийном выпуске, партии или единичном изделии
                    </td>
                </tr>
            </table>
            <table>
                <tr>
                    <td class="title">на соответствие требованиям:</td>
                    <td class="data"></td>
                </tr>
            </table>
            <table>
                <tr>
                    <td class="data"><c:out value="${app.requirements}"/></td>
                </tr>
                <tr>
                    <td class="help">
                        наименование и обозначение документов, устанавливающих технические требования (с указанием пунктов)
                    </td>
                </tr>
            </table>

            <p>Приложения</p>
            <p>1. Документы, подтверждающие соответствие продукции установленным требованиям:</p>

            <table>
                <tr>
                    <td class="title" style="width: 250px">Руководитель организации</td>
                    <td></td>
                    <td class="data" style="width: 100px"></td>
                    <td style="width: 50px"></td>
                    <td class="data"></td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td class="help">подпись</td>
                    <td></td>
                    <td class="help">инициалы, фамилия</td>
                </tr>
            </table>
            <table>
                <tr>
                    <td class="title" style="width: 250px">Главный бухгалтер</td>
                    <td></td>
                    <td class="data" style="width: 100px"></td>
                    <td style="width: 50px"></td>
                    <td class="data"></td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td class="help">подпись</td>
                    <td></td>
                    <td class="help">инициалы, фамилия</td>
                </tr>
            </table>
            <p>М.П.</p>
            <table>
                <tbody>
                <tr>
                    <td class="title">Ответственный исполнитель</td>
                    <td class="data">
                        <c:out value="${app.executor.surname}"/>
                        <c:out value=" ${app.executor.name}"/>
                        <c:choose>
                            <c:when test="${not empty app.executor.patronymic}">
                                <c:out value=" ${app.executor.patronymic}"/>
                            </c:when>
                        </c:choose>
                    </td>
                    <td class="title">Телефон</td>
                    <td class="data"><c:out value="${app.executor.phone}"/></td>
                    <td class="title">Адрес эл. почты</td>
                    <td class="data"><c:out value="${app.executor.email}"/></td>
                </tr>
                <tr>
                    <td></td>
                    <td class="help">фамилия, имя, отчество</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                </tbody>
            </table>
        </div>
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
