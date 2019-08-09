<%--
  Created by IntelliJ IDEA.
  User: helvetica
  Date: 09.08.19
  Time: 13:51
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
    <title>Balance</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>
<div class="container" style="margin-top: 60px">

    <div class = "balance_replenish" style="margin-top: 30px">
        <h3><fmt:message key ="index.replenish">Balance replenish</fmt:message></h3>
        <h4><fmt:message key="balance"/></h4>
        <div class="balance">

<%--            <c:choose>--%>
<%--                <c:when test="${balance != null}">--%>
<%--                    <span>${balance.doubleValue}</span>--%>
<%--                    <br />--%>
<%--                </c:when>--%>
<%--                <c:otherwise>--%>
<%--                    <span>--%>
<%--                        <fmt:message key="balance.empty"/>--%>
<%--                    </span>--%>
<%--                    <br />--%>
<%--                </c:otherwise>--%>
<%--            </c:choose>--%>

<%--            <span>--%>
<%--                <c:if test="${balance != null ? balance.doubleValue() : 'balance is empty'}"></c:if>--%>
<%--            </span>--%>
        </div>

        <form action="${pageContext.request.contextPath}/app/balance?id=${user.id}" method="post" style="margin-top: 30px">
            <div class="form-group">
                <label id="balanceLabel" for="balanceElement">
                    <fmt:message key="balance.replenishment">
                        Balance
                    </fmt:message>
                </label>
                <input type="number"
                       min="10"
                       class="form-control"
                       id="balanceElement"
                       name="sum"
                       placeholder=<fmt:message key="balance.replenish"/>>
            </div>

            <button type="submit" class="btn btn-success">
                <fmt:message key="balance.replenish">
                    Submit
                </fmt:message>
            </button>
        </form>
    </div>

    <div class="aboutLanguage">
        <span><fmt:message key="lang.change">change language</fmt:message></span>:
        <a href="?lang=en"><fmt:message key="lang.en">english</fmt:message></a>
        /
        <a href="?lang=uk" ><fmt:message key="lang.uk">ukrainian</fmt:message></a>
    </div>

</div>

</body>
</html>
