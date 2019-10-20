<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="ctg" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/main.css">
    <title>Start page</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<main class="mt-5">
    <div id="intro" class="view">
    </div>
    <div class="container">
        <section id="best-features" class="text-center">
            <h2 class="mb-5">О компании</h2>
            <div class="row d-flex justify-content-center mb-4">
                <div class="col-md-8">
                    <p>
                        Общество с ограниченной ответственностью «Центр сертификации продукции».
                        Компания аккредитована в качестве органа по сертификации.
                        Основное направление деятельности нашей организации — подтверждение соответствия продукции требованиям
                        технических регламентов Таможенного (Евразийского экономического) Союза.
                        Заявку на декларирование соответствия можно подать в электронном виде. Для этого достаточно зарегистрироваться у нас на сайте.
                    </p>
                </div>
            </div>
            <h2 class="mb-5">Виды продукции, с которыми мы работаем</h2>
            <div class="row">
                <div class="col-md-3 mb-1">
                    <h4 class="my-4">Технические средства</h4>
                    <p>
                        Приемно-контрольные приборы и управления, электрические приборы,
                        оборудование и аппараты: медицинские, промышленные и бытовые, ПЭВМ, музыкальные инструменты
                    </p>
                </div>
                <div class="col-md-3 mb-1">
                    <h4 class="my-4">Машины и оборудование</h4>
                    <p>
                        Технологическое оборудование, механизмы, машины, станки, электроинструмент,
                        воздухонагреватели, кондиционеры.
                    </p>
                </div>
                <div class="col-md-3 mb-1">
                    <i class="fas fa-bicycle"></i>
                    <h4 class="my-4">Товары для детей и подростков</h4>
                    <p>
                        Пустышки, подгузники, трусики и пеленки, одежда детская, ватные палочки,
                        зубные щетки, школьные принадлежности, книги и журналы, велосипеды и коляски детские
                    </p>
                </div>
                <div class="col-md-3 mb-1">
                    <i class="fas fa-bicycle"></i>
                    <h4 class="my-4">Игрушки</h4>
                    <p>
                        Развивающие, музыкальные, электрические и мягкие игрушки для всех возрастных категорий,
                        куклы, машинки, гирлянды, настольные и комнатные игры, товары для праздников
                    </p>
                </div>
            </div>
        </section>
    </div>
</main>
<ctg:info-footer/>

<script src="js/bootstrap.min.js"></script>
</body>
</html>
