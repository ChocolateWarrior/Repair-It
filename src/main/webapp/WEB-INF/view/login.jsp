<%--
  Created by IntelliJ IDEA.
  User: helvetica
  Date: 21.07.19
  Time: 18:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property/messages"/>
<html lang = "en">

<head>
    <meta charset="UTF-8">
    <title>Log In</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Oswald&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/style.css" >
</head>

<body>
<div class="container" style="margin-top: 60px">
    <div class="card">
        <div class="card-header"><fmt:message key="login.logging_in">login</fmt:message></div>
        <div class="card-body">

            <form action="${pageContext.request.contextPath}/app/login" method="post">

                <div class="form-group">
                    <label id="usernameLabel" for="username" ><fmt:message key="login.login">login</fmt:message></label>
                    <input type="text"
                           id="username"
                           name="username"
                           placeholder=<fmt:message key="login.enter.login"/>
                           required
                           class="form-control">
                </div>
                <div class="form-group">
                    <label id="passwordLabel" for="password" ><fmt:message key="login.password">password</fmt:message></label>
                    <input type="password"
                           id="password"
                           name="password"
                           placeholder=<fmt:message key="login.enter.password"/>
                           required
                           class="form-control">
                </div>
                <button type="submit" class="btn btn-primary" ><fmt:message key="login.sign_in">sign in</fmt:message></button>
            </form>
            <div class="aboutLanguage">
                <span><fmt:message key="lang.change">change language</fmt:message></span>:
                <a href="?lang=en"><fmt:message key="lang.en">english</fmt:message></a>
                /
                <a href="?lang=uk" ><fmt:message key="lang.uk">ukrainian</fmt:message></a>
            </div>

        </div>
    </div>
    <div class = "navigation">
        <ul class="nav nav-tabs nav-fill bg-light">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/app/registration"><fmt:message key="login.reg_here">register here</fmt:message></a>
            </li>
        </ul>
    </div>
</div>

</body>
</html>
