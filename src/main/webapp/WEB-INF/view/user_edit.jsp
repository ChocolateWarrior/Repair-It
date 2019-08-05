<%--
  Created by IntelliJ IDEA.
  User: helvetica
  Date: 02.08.19
  Time: 15:02
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
    <title>User Editor</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>

<div class="container" style="margin-top: 60px">

    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <h2 class="page-header"><fmt:message key="user.edit">User Edit</fmt:message></h2>

            <form action="${pageContext.request.contextPath}/display/edit?id=${user.id}" method="post">
                <div class="form-group">
                    <label id="firstNameLabel" for="firstNameElement">
                        <fmt:message key="reg.first_name">
                            First Name
                        </fmt:message>
                    </label>
                    <input type="text"
                           class="form-control"
                           id="firstNameElement"
                           name="firstNameEdit"
                           placeholder=<fmt:message key="reg.enter.first_name"/>>
                </div>

                <div class="form-group">
                    <label id="lastNameLabel" for="lastNameElement">
                        <fmt:message key="reg.last_name">
                            Last Name
                        </fmt:message>
                    </label>
                    <input type="text"
                           class="form-control"
                           id="lastNameElement"
                           name="lastNameEdit"
                           placeholder=<fmt:message key="reg.enter.last_name"/>>
                </div>

                <div class="form-group">
                    <label id="passwordLabel" for="passwordElement">
                        <fmt:message key="reg.password">
                            Password
                        </fmt:message>
                    </label>
                    <input type="text"
                           class="form-control"
                           id="passwordElement"
                           name="passwordEdit"
                           placeholder=<fmt:message key="reg.enter.password"/>>
                </div>

                <div class="form-group">
                    <label id="loginLabel" for="loginElement">
                        <fmt:message key="reg.login">
                            Username
                        </fmt:message>
                    </label>
                    <input type="text"
                           class="form-control"
                           id="loginElement"
                           name="loginEdit"
                           placeholder=<fmt:message key="reg.enter.login"/>>
                </div>

                <button type="submit" class="btn btn-success">
                    <fmt:message key="global.submit">
                        Submit
                    </fmt:message>
                </button>

            </form>

            <div class = "footer" style="margin-top: 20px">
                <span><fmt:message key="lang.change">change</fmt:message></span>:
                <a href="?lang=en" ><fmt:message key="lang.en">english</fmt:message></a>
                /
                <a href="?lang=uk"><fmt:message key="lang.uk">ukrainian</fmt:message></a>
            </div>

        </div>
    </div>

</div>

</body>
</html>
