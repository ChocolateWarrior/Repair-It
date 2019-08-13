
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property/messages"/>

<html lang = "en">
<head>
    <meta charset="UTF-8">
    <title>Balance</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Oswald&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/style.css" >
</head>
<body>
<div class="container" style="margin-top: 60px">

    <div class = "balance_replenish" style="margin-top: 30px">
        <h3><fmt:message key ="index.replenish">Balance replenish</fmt:message></h3>
        <h4><fmt:message key="balance"/></h4>
        <div class="balance">
            <c:out value="${requestScope.balance}"/>
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
                       value="${requestScope.sum}"
                       placeholder=<fmt:message key="balance.replenish"/>>
            </div>

            <c:if test="${requestScope.replenish_message_er != null}">
                <p class="text-danger"><c:out value="${requestScope.replenish_message_er}"/></p>
            </c:if>

            <c:if test="${requestScope.replenish_message_sc != null}">
                <p class="text-success"><c:out value="${requestScope.replenish_message_sc}"/></p>
            </c:if>

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
    <div class="footer_bar">
        <a href="${pageContext.request.contextPath}/app/index">
            <fmt:message key="main.return">main</fmt:message>
        </a>
    </div>

</div>

</body>
</html>
