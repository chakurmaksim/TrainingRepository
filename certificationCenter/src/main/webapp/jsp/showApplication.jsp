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
    <link rel="stylesheet" href="css/show.css">
    <title>Title</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<c:set var="app" value="${application}" scope="page"/>
<c:set var="role" value="${authorizedUser.role.roleName}"/>
<c:if test="${not empty role and role eq 'Client'}">
    <ul class="nav">
        <li class="nav-item">
            <a class="nav-link" href="showApplication.html?application_id=${app.id}">
                <fmt:message key="submenu.showApplication" bundle="${rb}"/>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="editApplication.html?application_id=${app.id}">
                <fmt:message key="submenu.editApplication" bundle="${rb}"/>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="deleteApplication.html?application_id=${app.id}"
               onclick="return confirm('Вы уверены?')">
                <fmt:message key="submenu.deleteApplication" bundle="${rb}"/>
            </a>
        </li>
    </ul>
</c:if>
<div id="show">
    <p class='right header'>Общество с ограниченной ответственностью</p>
    <p class='right header'>«Центр сертификации продукции»</p>
    <p class='right header'>220017, г.Минск, ул. Казимировская, 15</p>
    <p class='right header'>тел. +375 17 123-45-67</p>
    <p class='right header'>адрес эл. почты: <a href='mailto: info@сertification.by'>info@сertification.by</a></p>
    <p class="center">
        <c:choose>
            <c:when test="${app.reg_num > 0}">
                <c:set var="regNum" value="${app.reg_num}"/>
            </c:when>
            <c:otherwise>
                <c:set var="regNum" value=""/>
            </c:otherwise>
        </c:choose>
        <c:set var="datePattern" value="dd/MM/yyyy"/>
        <c:set var="date" value="${app.date_add}"/>
        <fmt:parseDate value="${date}" pattern="yyyy-MM-dd" var="parsedDate"/>
        <fmt:formatDate value="${parsedDate}" pattern="${datePattern}" var="formattedDate"/>
        <b>ЗАЯВКА №<c:out value="${regNum}"/> от <c:out value="${formattedDate}"/></b>
    </p>
    <p class="center">на проведение регистрации декларации о соответствии</p>
    <table>
        <tr>
            <td class="title"></td>
            <td class="data"><c:out value="${app.organisation.name}"/></td>
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
        <tr><td class="data"><c:out value="${app.organisation.address}"/> </td></tr>
    </table>
    <table>
        <tr>
            <td class="title">УНП</td><td class="data"><c:out value="${app.organisation.unp}"/></td>
            <td class="title">тел.</td><td class="data"><c:out value="+${app.organisation.phoneNumber}"/></td>
            <td class="title">email</td><td class="data"><a href='mailto:${app.organisation.email}'>${app.organisation.email}</a></td>
        </tr>>
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
            <td class="data"><c:out value="${app.products[0].attr.attributeName}"/> </td>
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
                <c:if test="${not empty app.executor.patronymic}">
                    <c:out value=" ${app.executor.patronymic}"/>
                </c:if>
            </td>
            <td class="title">Телефон</td>
            <td class="data"><c:out value="+${app.executor.phone}"/></td>
        </tr>
        <tr>
            <td></td>
            <td class="help">фамилия, имя, отчество</td>
            <td></td>
            <td></td>
        </tr>
        </tbody>
    </table>
    <table>
        <tr>
            <td class="title">Адрес эл. почты исполнителя</td>
            <td class="data"><a href='mailto:${app.executor.mail}'>${app.executor.mail}</a></td>
        </tr>
    </table>
    <p>Приложения</p>
    <p>1. Документы, подтверждающие соответствие продукции установленным требованиям:</p>
    <c:forEach var="document" items="${app.documents}">
        <p>
            <a href="downloadDocument.html?uploadFilePath=${document.uploadFilePath}&fileName=${document.fileName}">
                    ${document.fileName}
            </a>
        </p>
    </c:forEach>
</div>

<script src="js/bootstrap.min.js"></script>
</body>
</html>
