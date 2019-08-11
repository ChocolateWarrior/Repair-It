<%--
  Created by IntelliJ IDEA.
  User: helvetica
  Date: 04.08.19
  Time: 17:39
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
    <title>Requests</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Oswald&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/style.css" >

</head>

<body>
    <div class = "container" style="margin-top: 60px">
        <div class = "row">
            <div class = "col-md-12">
                <div class = "panel panel-default">
                    <div class = "panel-heading"><fmt:message key="req.display.heading">requests</fmt:message></div>
                    <div class = "panel-body">
                        <ul class="list-group">
                            <c:forEach items="${requestScope.requests}" var="request">

                                <li class="list-group-item ">
                                    <form action="${pageContext.request.contextPath}/app/display-request" method="get">

                                        <span>
                                            <fmt:message key="req.display.id">
                                                id
                                            </fmt:message>
                                        </span>
                                        <span><c:out value="${request.id}" /></span>
                                        <br>

                                        <span>
                                            <fmt:message key="req.display.type">
                                                type
                                            </fmt:message>
                                        </span>
                                        <span><c:out value="${request.specification}" /></span>
                                        <br>

                                        <span>
                                            <fmt:message key="req.display.description">
                                                desc
                                            </fmt:message>
                                        </span>
                                        <span><c:out value="${request.description}" /></span>
                                        <br>

                                        <span>
                                            <fmt:message key="req.display.req_time">
                                                time
                                            </fmt:message>
                                        </span>
                                        <span><c:out value="${request.requestTime}" /></span>
                                        <br>

                                        <span>
                                            <fmt:message key="req.display.user_id">
                                                user_id
                                            </fmt:message>
                                        </span>
                                        <span><c:out value="${request.user.id}" /></span>
                                        <br>

                                        <span>
                                            <fmt:message key="req.display.status">
                                                status
                                            </fmt:message>
                                        </span>
                                        <span><c:out value="${request.state}" /></span>
                                        <br>

                                        <span>
                                            <fmt:message key="req.display.master_id">
                                                master_id
                                            </fmt:message>
                                        </span>
                                        <span>
                                            <c:forEach items="${request.masters}" var="master">
                                                <p><c:out value="${master.username}" /></p>
                                                <br>
                                            </c:forEach>
                                        </span>
                                        <br>

                                        <span>
                                            <fmt:message key="req.display.finish_time">
                                                finish
                                            </fmt:message>
                                        </span>
                                        <span><c:out value="${request.finishTime}" /></span>
                                        <br>

                                        <span>
                                            <fmt:message key="req.display.price">
                                                price
                                            </fmt:message>
                                        </span>
                                        <span><c:out value="${request.price}" /></span>
                                        <br>

                                        <span>
                                            <fmt:message key="req.display.add_master">
                                                add_master
                                            </fmt:message>
                                        </span>
                                        <span>
                                            <a href="${pageContext.request.contextPath}/app/display-request/edit?id=${request.id}"
                                               class="btn btn-primary">
                                                <span><fmt:message key="req.display.add_master"/></span>
                                            </a>
                                        </span>
                                        <br>

                                    </form>

                                    <form action="${pageContext.request.contextPath}/app/display-request/reject?rejection_id=${request.id}"
                                          method="post">

                                        <span>
                                            <fmt:message key="display.rejection">
                                                rejection
                                            </fmt:message>
                                        </span>

                                        <input type="text"
                                               id="rejectionMessageElement"
                                               name="rejection_message"
                                               style="margin-bottom: 10px"
                                               placeholder=<fmt:message key="display.rejection_type"/>>

                                        <button class ="btn btn-danger">
                                            <span>
                                                <fmt:message key="display.reject"/>
                                            </span>
                                        </button>
                                    </form>

<%--                                    <form action="${pageContext.request.contextPath}/app/display-request/accept?accept_id=${request.id}"--%>
<%--                                          method="post">--%>

<%--                                        <span>--%>
<%--                                            <fmt:message key="display.acceptance">--%>
<%--                                                acceptance--%>
<%--                                            </fmt:message>--%>
<%--                                        </span>--%>

<%--                                        <input type="text"--%>
<%--                                               id="acceptanceMethod"--%>
<%--                                               name="rejection_message"--%>
<%--                                               style="margin-bottom: 10px"--%>
<%--                                               placeholder=<fmt:message key="display.rejection_type"/>>--%>

<%--                                        <button class ="btn btn-danger">--%>
<%--                                            <span>--%>
<%--                                                <fmt:message key="display.reject"/>--%>
<%--                                            </span>--%>
<%--                                        </button>--%>
<%--                                    </form>--%>

                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
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

