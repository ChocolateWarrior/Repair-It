<%--
  Created by IntelliJ IDEA.
  User: helvetica
  Date: 02.08.19
  Time: 14:59
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
    <title>Request Form</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<%--    <link rel="stylesheet" href="@{/css/style.css}" >--%>
    <link href="https://fonts.googleapis.com/css?family=Oswald&display=swap" rel="stylesheet">
</head>
<body>
<div class="container" style="margin-top: 60px">

    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <h2 class="page-header"><fmt:message key="req.header">Reg Form</fmt:message></h2>

<%--            <h5 class="text-success" th:text="${message}"></h5>--%>
            <form style="margin-bottom: 30px" name="form" autocomplete="off" action="${pageContext.request.contextPath}/app/request" method="post">
                <div class="form-group">
                    <label id="TypeLabel" for="TypeElement"><fmt:message key="req.select.type">select type</fmt:message></label>
                    <select class="form-control"
                            id="TypeElement"
                            name="specification">
                        <option value="clothes"><fmt:message key="spec.clothes">clothes</fmt:message></option>
                        <option value="electronics"><fmt:message key="spec.electronics">electronics</fmt:message></option>
                        <option value="furniture"><fmt:message key="spec.furniture">furniture</fmt:message></option>
                        <option value="sanitary_engineering"><fmt:message key="spec.sanitary">sanitary engineering</fmt:message></option>
                        <option value="mechanics"><fmt:message key="spec.mechanics">mechanics</fmt:message></option>
                    </select>
                </div>

                <div class="form-group">
                    <label id="DescriptionLabel" for="DescriptionElement"><fmt:message key="req.desc">desc</fmt:message></label>
                    <input type="text"
                           class="form-control"
                           id="DescriptionElement"
                           name="description"
                           placeholder=<fmt:message key="req.enter.desc"/>>
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
                <a href="${pageContext.request.contextPath}/app/index"><fmt:message key="main.return">main</fmt:message></a>
                <%--                <span> OR </span>--%>
                <%--                <a href="${pageContext.request.jsp.contextPath}/master-registration"><fmt:message key="reg.option.master">register as master</fmt:message></a>--%>
            </div>

        </div>
    </div>

</div>

</body>
</html>
