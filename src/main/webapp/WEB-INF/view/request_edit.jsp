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
    <title>Title</title>
</head>
<body>
<div class="container" style="margin-top: 60px">

    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <h2 class="page-header"><fmt:message key="req.edit">Request Edit</fmt:message></h2>

            <form action="${pageContext.request.contextPath}/display-request/edit?id=${request.id}" method="post">

                <div class="form-group">
                    <label id="masterLabel" for="masterElement" >
                        <fmt:message key="req.edit.master">Master</fmt:message>
                    </label>
                    <input type="text"
                           class="form-control"
                           id="masterElement"
                           name="masterId"
                           placeholder=<fmt:message key="req.edit.set.master"/>>
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
                <a href="${pageContext.request.contextPath}/index">
                    <fmt:message key="main.return">main</fmt:message>
                </a>
            </div>

        </div>
    </div>

</div>

</body>
</html>
