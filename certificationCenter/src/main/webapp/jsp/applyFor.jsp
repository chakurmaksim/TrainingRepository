<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/form.css">
    <title>Apply for</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div id="container">
    <div id="content">
        <div class="content">
            <form method="post" action="applyFor.html" enctype="multipart/form-data">
                <p style="color:red">${errorMessage}</p>
                <fieldset>
                    <legend>Реквизиты заявки</legend>
                    <ul>
                        <li>
                            <label class="required" for="applicant_name">Наименование заявителя:</label>
                            <input readonly="readonly" name="applicant_name" type="text" value="${authorizedUser.organisation.name}" id="applicant_name" />
                        </li>
                        <li>
                            <label class="required" for="applicant_address">Место нахождения:</label>
                            <input readonly="readonly" name="applicant_address" type="text" value="${authorizedUser.organisation.address}" id="applicant_address" />
                        </li>
                        <li>
                            <label class="required" for="applicant_unp">УНП</label>
                            <input readonly="readonly" name="applicant_unp" type="text" value="${authorizedUser.organisation.unp}" id="applicant_unp" />
                        </li>
                        <li>
                            <label class="required" for="applicant_phone">Телефон:</label>
                            <input readonly="readonly" type="text" name="applicant_phone" value="+${authorizedUser.organisation.phoneNumber}" id="applicant_phone" />
                        </li>
                        <li>
                            <label for="applicant_email">Адрес эл. почты:</label>
                            <input readonly="readonly" type="text" name="applicant_email" value="${authorizedUser.organisation.email}" id="applicant_email" />
                        </li>
                        <li>
                            <label class="required" for="application_product_name">Наименование и обозначение продукции:</label>
                            <textarea rows="4" cols="30" name="product_name" id="application_product_name"></textarea>
                            <br>
                            <div class="help">наименование и обозначение продукции, на которую
                                распространяется декларация о соответствии, сведения о продукции,
                                обеспечивающие ее идентификацию (тип, марка, артикул продукции)</div>
                        </li>
                        <li>
                            <label for="application_product_code">Код ТН ВЭД ЕАЭС:</label>
                            <input pattern="[0-9]{4,10}" name="product_code" id="application_product_code" />
                        </li>
                        <li>
                            <label class="required" for="producer_name">Наименование изготовителя:</label>
                            <input type="text" name="producer_name" id="producer_name" />
                        </li>
                        <li>
                            <label class="required" for="producer_address">Адрес места осуществления деятельности:</label>
                            <input type="text" name="producer_address" id="producer_address" />
                        </li>
                        <li>
                            <label class="required" for="application_quantity_attr">Количественный признак выпускаемой продукции</label>
                            <select name="product_quantity_attribute" id="application_quantity_attr">
                                <option value="1" selected>Серийный выпуск</option>
                                <option value="2">Партия</option>
                                <option value="3">Единичное изделие</option>
                            </select>
                        </li>
                        <li>
                            <label class="required" for="tnpa_confirm">Соответствует требованиям</label>
                            <textarea rows="4" cols="30" name="requirements" id="tnpa_confirm"></textarea>
                        </li>
                        <li>
                            <label class="required" for="application_executor">Представитель заказчика</label>
                            <input readonly="readonly" type="text" name="executor" value="${authorizedUser.surname} ${authorizedUser.name}" id="application_executor" />
                        </li>
                        <li>
                            <label class="required" for="executor_phone">Телефон представителя</label>
                            <input readonly="readonly" type="text" name="executor_phone" value="+${authorizedUser.phone}" id="executor_phone" />
                        </li>
                        <li>
                            <label class="required" for="executor_email">Адрес эл. почты представителя</label>
                            <input readonly="readonly" type="text" name="executor_email" value="${authorizedUser.mail}" id="executor_email" />
                        </li>
                    </ul>
                </fieldset>
                <fieldset>
                    <legend>Документация</legend>
                    <ul>
                        <li>
                            <label for="application_attachment">Приложение</label>
                            <input type="file" name="attachment" multiple="true" id="application_attachment" />
                        </li>
                    </ul>
                </fieldset>
                <fieldset>
                    <legend>Дополнительные сведения</legend>
                    <ul>
                        <li>
                            <label for="application_arrival_date">Дата подачи документов</label>
                            <input type="date" name="date_add" id="application_arrival_date" />
                        </li>
                    </ul>
                </fieldset>
                <fieldset class="submit">
                    <input type="submit" value="Отправить" />
                </fieldset>
            </form>
        </div>
    </div>
</div>

<script src="js/bootstrap.min.js"></script>
</body>
</html>
