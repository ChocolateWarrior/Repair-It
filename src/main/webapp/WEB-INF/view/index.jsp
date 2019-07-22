<%--
  Created by IntelliJ IDEA.
  User: helvetica
  Date: 21.07.19
  Time: 18:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="property/messages"/>

<html lang = "${param.lang}">
<head>
    <meta charset="UTF-8">
    <title>Repair It</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>
<div class="container" style="margin-top: 50px">

    <div class = "navigation">
        <h2 class="h2">Repair It</h2>
        <h3><fmt:message key="index.greeting">greeting</fmt:message></h3>
        <ul class="nav nav-tabs nav-fill bg-light">
            <li class="nav-item">
                <a class="nav-link disabled" href="/main"><fmt:message key="index.nav_bar.main">main</fmt:message></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/registration"><fmt:message key="index.nav_bar.registration">sign up</fmt:message></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/login" ><fmt:message key="index.nav_bar.log_in">sign in</fmt:message></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/logout"><fmt:message key="index.nav_bar.log_out">sign out</fmt:message></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/request"><fmt:message key="index.nav_bar.request">leave request</fmt:message></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/user-display"><fmt:message key="index.nav_bar.display">display</fmt:message></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/request-display"><fmt:message key="index.nav_bar.request_display">display requests</fmt:message></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/master-display" ><fmt:message key="index.nav_bar.master_display">display masters</fmt:message></a>
            </li>
        </ul>

    </div>

    <div class="jumbotron">
        <h3><fmt:message key="index.my_requests"/></h3>
<%--        <ul class="list-group" th:field="${user_requests}">--%>
<%--            <li class="list-group-item "--%>
<%--                th:each="request : ${user_requests} "--%>
<%--                th:text="${request}"--%>
<%--                th:value="${request}"></li>--%>

<%--        </ul>--%>

    </div>

    <div class = "userInfo">

        Logged user: <span>Bob</span>
        Roles: <span>[ROLE_USER, ROLE_ADMIN]</span>
    </div>

    <div class = "footer" style="margin-top: 20px">
        <span><fmt:message key="lang.change">change</fmt:message></span>:
        <a href="?lang=en" ><fmt:message key="lang.en">english</fmt:message></a>
        /
        <a href="?lang=uk"><fmt:message key="lang.uk">ukrainian</fmt:message></a>
    </div>
</div>


</body>
</html>
