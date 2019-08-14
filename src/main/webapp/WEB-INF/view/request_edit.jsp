
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property/messages"/>

<html lang = "en">

<head>
    <meta charset="UTF-8">
    <title>Request Edit</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Oswald&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/style.css" >
</head>

<body>
<div class="container" style="margin-top: 60px">

    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <h2 class="page-header"><fmt:message key="req.edit">Request Edit</fmt:message></h2>

            <form action="${pageContext.request.contextPath}/app/display-request/edit?id=${request.id}" method="post">
                <c:if test="${!requestScope.all_masters.isEmpty()}">
                <div class="form-group">
                    <label for="masterSelect">
                        <fmt:message key="req.edit.set.master">Master</fmt:message>
                    </label>
                    <select multiple class="form-control" id="masterSelect" name="masters">
                        <c:forEach items="${requestScope.all_masters}" var="master">
                            <option value=<c:out value="${master.username}"/>>
                                <c:out value="${master.username}"/>
                            </option>
                        </c:forEach>
                    </select>

                    <div class="row">
                        <div class="col-sm-6">
                            <label>
                                <input name="master_request_id" value="${request.id}" hidden/>
                            </label>
                            <span class="form-heading">
                                <fmt:message key="req.display.price">
                                    price
                                </fmt:message>
                            </span>
                            <div class="form-group">
                                <label id="priceLabel" for="priceElement"></label>
                                <input type="number"
                                       min="0"
                                       max="1000000"
                                       class="form-control"
                                       id="priceElement"
                                       name="master_request_price"
                                       value="${requestScope.master_request_price}"
                                       placeholder=<fmt:message key="req.display.price"/>>
                                <c:if test="${requestScope.price_message_er != null}">
                                    <p class="text-danger"><c:out value="${requestScope.price_message_er}"/></p>
                                </c:if>
                            </div>

                        </div>

                    </div>

                </div>

                    <button type="submit" class="btn btn-success" style="margin-top:30px">
                        <fmt:message key="req.send">send</fmt:message>
                    </button>

                </c:if>

                <c:if test="${requestScope.all_masters.isEmpty()}">
                    <span class="text-danger">
                        <fmt:message key="req.edit.no_master_available"/>
                    </span>
                </c:if>

            </form>

            <div class="aboutLanguage">
                <span><fmt:message key="lang.change">change language</fmt:message></span>:
                <a href="?lang=en"><fmt:message key="lang.en">english</fmt:message></a>
                /
                <a href="?lang=uk" ><fmt:message key="lang.uk">ukrainian</fmt:message></a>
            </div>
            <div class="footer_bar">
                <a href="${pageContext.request.contextPath}/app/display-request">
                    <fmt:message key="display.return">main</fmt:message>
                </a>
                <span style="color: aliceblue;">/</span>
                <a href="${pageContext.request.contextPath}/app/index">
                    <fmt:message key="main.return">main</fmt:message>
                </a>

            </div>

        </div>
    </div>

</div>

</body>
</html>
