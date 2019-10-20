<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<div class="container">
    <ul class="pagination">
        <fmt:message key="paginationPage.previous" bundle="${rb}" var="prev"/>
        <fmt:message key="paginationPage.next" bundle="${rb}" var="next"/>
        <c:set var="active" value="${currentPageNumber % 3}"/>
        <c:set var="remain" value="${lastPageNumber - currentPageNumber}"/>
        <c:choose>
            <c:when test="${active == 1}">
                <c:if test="${currentPageNumber == 1}">
                    <li class="page-item active">
                        <a class="page-link" href="applications.html?page=1">1</a>
                    </li>
                </c:if>
                <c:if test="${currentPageNumber > 1}">
                    <li class="page-item">
                        <a class="page-link"
                           href="applications.html?page=${currentPageNumber - 1}">${prev}</a>
                    </li>
                    <li class="page-item active">
                        <a class="page-link" href="applications.html?page=${currentPageNumber}">${currentPageNumber}</a>
                    </li>
                </c:if>
                <c:if test="${remain > 0}">
                    <li class="page-item">
                        <a class="page-link"
                           href="applications.html?page=${currentPageNumber + 1}">${currentPageNumber + 1}</a>
                    </li>
                    <c:if test="${remain > 1}">
                        <li class="page-item">
                            <a class="page-link"
                               href="applications.html?page=${currentPageNumber + 2}">${currentPageNumber + 2}</a>
                        </li>
                    </c:if>
                    <li class="page-item">
                        <a class="page-link"
                           href="applications.html?page=${currentPageNumber + 1}">${next}</a>
                    </li>
                </c:if>
            </c:when>
            <c:when test="${active == 2}">
                <li class="page-item">
                    <a class="page-link"
                       href="applications.html?page=${currentPageNumber - 1}">${prev}</a>
                </li>
                <li class="page-item">
                    <a class="page-link" href="applications.html?page=${currentPageNumber - 1}">${currentPageNumber - 1}</a>
                </li>
                <li class="page-item active">
                    <a class="page-link" href="applications.html?page=${currentPageNumber}">${currentPageNumber}</a>
                </li>
                <c:if test="${remain > 0}">
                    <li class="page-item">
                        <a class="page-link" href="applications.html?page=${currentPageNumber + 1}">${currentPageNumber + 1}</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link"
                           href="applications.html?page=${currentPageNumber + 1}">${next}</a>
                    </li>
                </c:if>
            </c:when>
            <c:when test="${active == 0}">
                <li class="page-item">
                    <a class="page-link"
                       href="applications.html?page=${currentPageNumber - 1}">${prev}</a>
                </li>
                <li class="page-item">
                    <a class="page-link" href="applications.html?page=${currentPageNumber - 2}">${currentPageNumber - 2}</a>
                </li>
                <li class="page-item">
                    <a class="page-link" href="applications.html?page=${currentPageNumber - 1}">${currentPageNumber - 1}</a>
                </li>
                <li class="page-item active">
                    <a class="page-link" href="applications.html?page=${currentPageNumber}">${currentPageNumber}</a>
                </li>
                <c:if test="${remain > 0}">
                    <li class="page-item">
                        <a class="page-link"
                           href="applications.html?page=${currentPageNumber + 1}">${next}</a>
                    </li>
                </c:if>
            </c:when>
        </c:choose>
    </ul>
</div>
