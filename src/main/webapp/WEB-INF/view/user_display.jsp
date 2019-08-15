
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property/messages"/>

<html lang = "en">

<head>
    <meta charset="UTF-8">
    <title>Users</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Oswald&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/style.css" >
</head>

<body>

<div class="container" style="margin-top: 60px">
    <div class = "row">
        <div class = "col-md-12">
            <div class = "panel panel-default">
                <div class = "panel-heading" style="color: aliceblue">
                    <fmt:message key="display.users">Users</fmt:message>
                </div>
                <div class = "panel-body">
                    <ul class="list-group">
                        <c:forEach items="${requestScope.users}" var="user">

                            <li class="list-group-item ">

                                <span ><fmt:message key="display.id">id</fmt:message></span>
                                <span><c:out value="${user.id}" /></span>
                                <br>

                                <span><fmt:message key="display.first_name">first name</fmt:message></span>
                                <span><c:out value="${user.firstName}" /></span>
                                <br>

                                <span ><fmt:message key="display.last_name">last name</fmt:message></span>
                                <span><c:out value="${user.lastName}" /></span>
                                <br>

                                <span ><fmt:message key="display.login">username</fmt:message></span>
                                <span><c:out value="${user.username}" /></span>
                                <br>

                                <span ><fmt:message key="display.authorities">authorities</fmt:message></span>
                                <span>
                                    <c:forEach items="${user.authorities}" var="auth">
                                        <span>[<c:out value="${auth}" />]</span>
                                    </c:forEach>
                                </span>
                                <br>

                                <span ><fmt:message key="display.specifications">specifications</fmt:message></span>
                                <span>
                                    <c:forEach items="${user.specifications}" var="spec">
                                        <span>[<c:out value="${spec}" />]</span>
                                    </c:forEach>
                                </span>
                                <br>

                                <span ><fmt:message key="display.user_requests">user requests</fmt:message></span>
                                <span>
                                    <c:forEach items="${user.userRequests}" var="req">
                                        <span>[<c:out value="${req.id}" />]</span>
                                    </c:forEach>
                                </span>
                                <br>

                                <span ><fmt:message key="display.master_requests">master requests</fmt:message></span>
                                <span>
                                    <c:forEach items="${user.masterRequests}" var="req">
                                        <span>[<c:out value="${req.id}" />]</span>
                                    </c:forEach>
                                </span>
                                <br>

                                <span>
                                    <a href="${pageContext.request.contextPath}/app/display/edit?id=${user.id}"
                                       class="btn btn-primary">
                                        <span><fmt:message key="display.edit"/></span>
                                    </a>
                                </span>
                                <hr>
                            </li>
                        </c:forEach>
                    </ul>

                </div>
            </div>
        </div>
    </div>

    <div class = "footer" style="margin-top: 20px">
        <span><fmt:message key="lang.change">change</fmt:message></span>:
        <a href="?lang=en" ><fmt:message key="lang.en">english</fmt:message></a>
        /
        <a href="?lang=uk"><fmt:message key="lang.uk">ukrainian</fmt:message></a>
    </div>

    <div class="footer_bar">
        <a href="${pageContext.request.contextPath}/app/index">
            <fmt:message key="main.return">main</fmt:message>
        </a>
    </div>

</div>
</body>
</html>
