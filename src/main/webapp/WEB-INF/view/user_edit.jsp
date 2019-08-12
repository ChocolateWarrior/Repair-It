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

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property/messages"/>

<html lang = "en">

<head>
    <meta charset="UTF-8">
    <title>User Edit</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Oswald&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/style.css" >
</head>
<body>

<div class="container" style="margin-top: 60px">



    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <h2 class="page-header"><fmt:message key="user.edit">User Edit</fmt:message></h2>

            <form action="${pageContext.request.contextPath}/app/display/edit?id=${user.id}" method="post">

                <c:if test="${requestScope.message_sc != null}">
                    <p style="color: lightgreen"><c:out value="${requestScope.message_sc}"/></p>
                </c:if>

                <div class="form-group">
                    <label id="firstNameLabel" for="firstNameElement">
                        <fmt:message key="reg.first_name">
                            First Name
                        </fmt:message>
                    </label>
                    <input type="text"
                           class="form-control"
                           id="firstNameElement"
                           name="first_name"
                           value="${requestScope.first_name}"
                           placeholder=<fmt:message key="reg.enter.first_name"/>>
                </div>
                <c:if test="${requestScope.first_name_error != null}">
                    <p class="text-danger"><c:out value="${requestScope.first_name_error}"/></p>
                </c:if>

                <div class="form-group">
                    <label id="lastNameLabel" for="lastNameElement">
                        <fmt:message key="reg.last_name">
                            Last Name
                        </fmt:message>
                    </label>
                    <input type="text"
                           class="form-control"
                           id="lastNameElement"
                           name="last_name"
                           value="${requestScope.last_name}"
                           placeholder=<fmt:message key="reg.enter.last_name"/>>
                </div>
                <c:if test="${requestScope.last_name_error != null}">
                    <p class="text-danger"><c:out value="${requestScope.last_name_error}"/></p>
                </c:if>

                <div class="form-group">
                    <label id="loginLabel" for="loginElement">
                        <fmt:message key="reg.login">
                            Username
                        </fmt:message>
                    </label>
                    <input type="text"
                           class="form-control"
                           id="loginElement"
                           name="username"
                           value="${requestScope.username}"
                           placeholder=<fmt:message key="reg.enter.login"/>>
                </div>
                <c:if test="${requestScope.username_error != null}">
                    <p class="text-danger"><c:out value="${requestScope.username_error}"/></p>
                </c:if>

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
