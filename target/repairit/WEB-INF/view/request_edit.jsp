<%--
  Created by IntelliJ IDEA.
  User: helvetica
  Date: 02.08.19
  Time: 15:01
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
    <title>Request Edit</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <%--    <link rel="stylesheet" th:href="@{/css/style.css}" >--%>
    <link href="https://fonts.googleapis.com/css?family=Oswald&display=swap" rel="stylesheet">
</head>
<body>
<div class="container" style="margin-top: 60px">

    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <h2 class="page-header"><fmt:message key="req.edit">Request Edit</fmt:message></h2>

            <form action="${pageContext.request.contextPath}/app/display-request/edit?id=${request.id}" method="post">

                <div class="form-group">
                    <label for="masterSelect">
                        <fmt:message key="req.edit.master">Master</fmt:message>
                    </label>
                    <span>placeholder=<fmt:message key="req.edit.set.master"/></span>
                    <select multiple class="form-control" id="masterSelect" name="masters">
                        <c:forEach items="${requestScope.all_masters}" var="master">
                            <option value=<c:out value="${master.username}"/>>
                                <c:out value="${master.username}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <button type="submit" class="btn btn-success" style="margin-top:30px">
                    <fmt:message key="req.send">send</fmt:message>
                </button>
            </form>


            <div class="aboutLanguage">
                <span><fmt:message key="lang.change">change language</fmt:message></span>:
                <a href="?lang=en"><fmt:message key="lang.en">english</fmt:message></a>
                /
                <a href="?lang=uk" ><fmt:message key="lang.uk">ukrainian</fmt:message></a>
            </div>
            <div class="footer_bar">
                <a href="${pageContext.request.contextPath}/app/index">
                    <fmt:message key="main.return">main</fmt:message>
                </a>
            </div>

        </div>
    </div>

</div>

</body>
</html>
