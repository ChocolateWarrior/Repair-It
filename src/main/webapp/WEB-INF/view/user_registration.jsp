<%--
  Created by IntelliJ IDEA.
  User: helvetica
  Date: 21.07.19
  Time: 20:37
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
    <title>Registration Form</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>


<div class="container" style="margin-top: 60px">


    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <h2 class="page-header" ><fmt:message key="reg.header">Registration Form</fmt:message></h2>

            <form style="margin-bottom: 30px" name="form" autocomplete="off">
                <div class="form-group">
                    <label id="firsNameLabel" for="firstName"><fmt:message key="reg.first_name">first_name</fmt:message></label>
                    <input type="text"
                           name="firstName"
                           class="form-control"
                           id="firstName"
                           placeholder="<fmt:message key="reg.enter.first_name">login</fmt:message>"
                           required>
                </div>
                <div class="form-group">
                    <label id="lastNameLabel" for="lastName" ><fmt:message key="reg.last_name">last_name</fmt:message></label>
                    <input type="text"
                           name="lastName"
                           class="form-control"
                           id="lastName"
                           placeholder="<fmt:message key="reg.enter.last_name">last name</fmt:message>"
                           required>
                </div>
                <div class="form-group">
                    <label id="usernameLabel" for="username"><fmt:message key="reg.login">login</fmt:message></label>
                    <input type="text"
                           name="username"
                           class="form-control"
                           id="username"
                           placeholder="<fmt:message key="reg.enter.login">login</fmt:message>"
                           required>
                </div>
                <div class="form-group">
                    <label id="passwordLabel" for="password"><fmt:message key="reg.password">password</fmt:message></label>
                    <input type="text"
                           name="password"
                           class="form-control"
                           id="password"
                           placeholder="<fmt:message key="reg.enter.password">password</fmt:message>"
                           required>
                </div>
                <button type="submit" class="btn btn-success" style="margin-top:30px">
                    <fmt:message key="reg.register">register</fmt:message>
                </button>

            </form>
            <div class="aboutLanguage">
                <span><fmt:message key="lang.change">change language</fmt:message></span>:
                <a href="?lang=en"><fmt:message key="lang.en">english</fmt:message></a>
                /
                <a href="?lang=uk" ><fmt:message key="lang.uk">ukrainian</fmt:message></a>
            </div>
            <div class="footer_bar">
                <a href="${pageContext.request.contextPath}/index"><fmt:message key="main.return">main</fmt:message></a>
<%--                <span> OR </span>--%>
<%--                <a href="${pageContext.request.jsp.contextPath}/master-registration"><fmt:message key="reg.option.master">register as master</fmt:message></a>--%>
            </div>

        </div>
    </div>

</div>

</body>
</html>
