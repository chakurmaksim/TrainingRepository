<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
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
            <c:set var="action" value="applyFor.html"/>
            <c:if test="${not empty application}">
                <c:set var="action" value="editApplication.html"/>
            </c:if>
            <form method="post" action="${action}" enctype="multipart/form-data">
                <p style="color:red">${errorMessage}</p>
                <fieldset>
                    <legend><fmt:message key="formForApply.legend" bundle="${rb}"/></legend>
                    <ul>
                        <li>
                            <label class="required" for="applicant_name"><fmt:message key="formForApply.organisation.name" bundle="${rb}"/></label>
                            <input readonly="readonly" name="applicant_name" type="text" value="${authorizedUser.organisation.name}" id="applicant_name" />
                        </li>
                        <li>
                            <label class="required" for="applicant_address"><fmt:message key="formForApply.organisation.location" bundle="${rb}"/></label>
                            <input readonly="readonly" name="applicant_address" type="text" value="${authorizedUser.organisation.address}" id="applicant_address" />
                        </li>
                        <li>
                            <label class="required" for="applicant_unp"><fmt:message key="formForApply.unp" bundle="${rb}"/></label>
                            <input readonly="readonly" name="applicant_unp" type="text" value="${authorizedUser.organisation.unp}" id="applicant_unp" />
                        </li>
                        <li>
                            <label class="required" for="applicant_phone"><fmt:message key="formForApply.phone" bundle="${rb}"/></label>
                            <input readonly="readonly" type="text" name="applicant_phone" value="+${authorizedUser.organisation.phoneNumber}" id="applicant_phone" />
                        </li>
                        <li>
                            <label for="applicant_email"><fmt:message key="formForApply.email" bundle="${rb}"/></label>
                            <input readonly="readonly" type="text" name="applicant_email" value="${authorizedUser.organisation.email}" id="applicant_email" />
                        </li>
                        <li>
                            <label class="required" for="application_product_name"><fmt:message key="formForApply.product.name" bundle="${rb}"/></label>
                            <textarea rows="4" cols="30" name="product_name" id="application_product_name">${application.products.get(0).name}</textarea>
                            <br>
                            <div class="help"><fmt:message key="formForApply.product.help" bundle="${rb}"/></div>
                        </li>
                        <li>
                            <label for="application_product_code"><fmt:message key="formForApply.product.code" bundle="${rb}"/></label>
                            <input pattern="[0-9]{4,10}" name="product_code" id="application_product_code" value="${application.products.get(0).code}"/>
                        </li>
                        <li>
                            <label class="required" for="producer_name"><fmt:message key="formForApply.producer.name" bundle="${rb}"/></label>
                            <input type="text" name="producer_name" id="producer_name" value="${application.products.get(0).producer}" />
                        </li>
                        <li>
                            <label class="required" for="producer_address"><fmt:message key="formForApply.producer.location" bundle="${rb}"/></label>
                            <input type="text" name="producer_address" id="producer_address" value="${application.products.get(0).address}"/>
                        </li>
                        <li>
                            <label class="required" for="application_quantity_attr"><fmt:message key="formForApply.quantity.attribute" bundle="${rb}"/></label>
                            <select name="product_quantity_attribute" id="application_quantity_attr">
                                <option value="1" selected><fmt:message key="formForApply.serial" bundle="${rb}"/></option>
                                <option value="2"><fmt:message key="formForApply.batch" bundle="${rb}"/></option>
                                <option value="3"><fmt:message key="formForApply.single.item" bundle="${rb}"/></option>
                            </select>
                        </li>
                        <li>
                            <label class="required" for="tnpa_confirm"><fmt:message key="formForApply.requirements" bundle="${rb}"/></label>
                            <textarea rows="4" cols="30" name="requirements" id="tnpa_confirm">${application.requirements}</textarea>
                        </li>
                        <li>
                            <label class="required" for="application_executor"><fmt:message key="formForApply.executor" bundle="${rb}"/></label>
                            <input readonly="readonly" type="text" name="executor" value="${authorizedUser.surname} ${authorizedUser.name}" id="application_executor" />
                        </li>
                        <li>
                            <label class="required" for="executor_phone"><fmt:message key="formForApply.executor.phone" bundle="${rb}"/></label>
                            <input readonly="readonly" type="text" name="executor_phone" value="+${authorizedUser.phone}" id="executor_phone" />
                        </li>
                        <li>
                            <label class="required" for="executor_email"><fmt:message key="formForApply.executor.email" bundle="${rb}"/></label>
                            <input readonly="readonly" type="text" name="executor_email" value="${authorizedUser.mail}" id="executor_email" />
                        </li>
                    </ul>
                </fieldset>
                <c:if test="${empty application}">
                    <fieldset>
                        <legend><fmt:message key="formForApply.documentation" bundle="${rb}"/></legend>
                        <ul>
                            <li>
                                <label for="application_attachment"><fmt:message key="formForApply.appendix" bundle="${rb}"/></label>
                                <input type="file" name="attachment" multiple="true" id="application_attachment" />
                            </li>
                        </ul>
                    </fieldset>
                    <fieldset>
                        <legend><fmt:message key="formForApply.additional" bundle="${rb}"/></legend>
                        <ul>
                            <li>
                                <label for="application_arrival_date"><fmt:message key="formForApply.date" bundle="${rb}"/></label>
                                <input type="date" name="date_add" id="application_arrival_date" value="20.10.2019"/>
                            </li>
                        </ul>
                    </fieldset>
                </c:if>
                <fieldset class="submit">
                    <c:if test="${empty application}">
                        <fmt:message key="formForApply.submit" bundle="${rb}" var="send"/>
                        <input type="submit" value="${send}" />
                    </c:if>
                    <c:if test="${not empty application}">
                        <fmt:message key="formForApply.edit" bundle="${rb}" var="edit"/>
                        <input type="submit" value="${edit}" onsubmit="putApplicationToSession(this);return false"/>
                        <script>
                            function putApplicationToSession(form) {
                                ${pageContext.session.setAttribute("currentApplication", application)}
                                form.submit();
                            }
                        </script>
                    </c:if>
                </fieldset>
            </form>
        </div>
    </div>
</div>

<script src="js/bootstrap.min.js"></script>
</body>
</html>
