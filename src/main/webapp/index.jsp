<%--
  Created by IntelliJ IDEA.
  User: helvetica
  Date: 21.07.19
  Time: 18:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang = "en"
      xmlns:th="http://www.thymeleaf.org"
<head>
    <meta charset="UTF-8">
    <title>Easy Repairs</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>
<div class="container" style="margin-top: 50px">

    <div class = "navigation">
        <h2 class="h2">Easy Repairs</h2>
        <h3 th:text="#{index.greeting}"></h3>
        <ul class="nav nav-tabs nav-fill bg-light">
            <li class="nav-item">
                <a class="nav-link disabled" href="/main" th:text = "#{index.nav_bar.main}">main</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/registration" th:text = "#{index.nav_bar.registration}">sign up</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/login" th:text = "#{index.nav_bar.log_in}">sign in</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/logout" th:text = "#{index.nav_bar.log_out}">sign out</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/request" th:text = "#{index.nav_bar.request}">leave request</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/user-display" th:text = "#{index.nav_bar.display}">display</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/request-display" th:text = "#{index.nav_bar.request_display}">display requests</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/master-display" th:text = "#{index.nav_bar.master_display}">display masters</a>
            </li>
        </ul>

    </div>

    <div class="jumbotron">
        <h3>My requests</h3>
<%--        <ul class="list-group" th:field="${user_requests}">--%>
<%--            <li class="list-group-item "--%>
<%--                th:each="request : ${user_requests} "--%>
<%--                th:text="${request}"--%>
<%--                th:value="${request}"></li>--%>

<%--        </ul>--%>

    </div>

    <div class = "userInfo" sec:authorize="hasAuthority('USER')">

        Logged user: <span>Bob</span>
        Roles: <span>[ROLE_USER, ROLE_ADMIN]</span>
    </div>

    <div class = "footer" style="margin-top: 20px">
        <span th:text="#{lang.change}"></span>:
        <a href="?lang=en" th:text="#{lang.en}">english</a>
        /
        <a href="?lang=uk" th:text="#{lang.uk}">ukrainian</a>
    </div>
</div>


</body>
</html>
