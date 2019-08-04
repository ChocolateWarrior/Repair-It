<%--
  Created by IntelliJ IDEA.
  User: helvetica
  Date: 22.07.19
  Time: 15:28
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
    <div class = "row">
        <div class = "col-md-12">
            <div class = "panel panel-default">
                <div class = "panel-heading"><fmt:message key="display.users">Users</fmt:message></div>
                <div class = "panel-body">
                    <form action="#" method="get">

                        <table class = "table table-striped" >
                            <thead class ="thead-dark">
                            <tr>
                                <th ><fmt:message key="display.id">id</fmt:message></th>
                                <th><fmt:message key="display.first_name">first name</fmt:message></th>
                                <th ><fmt:message key="display.last_name">last name</fmt:message></th>
                                <th ><fmt:message key="display.login">username</fmt:message></th>
                                <th ><fmt:message key="display.password">password</fmt:message></th>
                                <th ><fmt:message key="display.edit">edit</fmt:message></th>
                                <th ><fmt:message key="display.remove">remove</fmt:message></th>

                            </tr>
                            </thead>
                            <tbody >

                                    <c:forEach items="${requestScope.users}" var="user">
                                        <tr>
                                            <td><c:out value="${user.id}" /></td>
                                            <td><c:out value="${user.firstName}" /></td>
                                            <td><c:out value="${user.lastName}" /></td>
                                            <td><c:out value="${user.username}" /></td>
                                            <td><c:out value="${user.password}" /></td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/display/edit?id=${user.id}" class="btn btn-primary">
                                                    <span><fmt:message key="display.edit"/></span>
                                                </a>
                                            </td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/display/delete?id=${user.id}" class="btn btn-primary">
                                                    <span><fmt:message key="display.remove"/></span>
                                                </a>
                                            </td>

                                        </tr>
                                    </c:forEach>

                            </tbody>

                        </table>

                    </form>


                </div>

            </div>
        </div>
    </div>
</div>
</body>
</html>
