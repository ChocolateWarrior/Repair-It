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

<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="property/messages"/>
<html lang = "${param.lang}">
<head>
    <meta charset="UTF-8">
    <title>Registration Form</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

<%--    <link rel="stylesheet" th:href="@{/css/style.css}" >--%>
    <link href="https://fonts.googleapis.com/css?family=Oswald&display=swap" rel="stylesheet">

</head>

<body>
<div class = "container" style="margin-top: 60px">
    <div class = "row">
        <div class = "col-md-12">
            <div class = "panel panel-default">
                <div class = "panel-heading"><fmt:message key="req.display.heading">requests</fmt:message></div>
                <div class = "panel-body">
                    <form action="#" th:action="@{/request-display}" method="get">

                        <table class = "table table-bordered table-hover table-responsive" th:field="${all_requests}" style="text-align: center">
                            <thead class ="thead-dark">
                            <tr>
                                <th>
                                    <fmt:message key="req.display.id">
                                        id
                                    </fmt:message>
                                </th>
                                <th>
                                    <fmt:message key="req.display.type">
                                        type
                                    </fmt:message>
                                </th>
                                <th>
                                    <fmt:message key="req.display.description">
                                        desc
                                    </fmt:message>
                                </th>
                                <th>
                                    <fmt:message key="req.display.req_time">
                                        time
                                    </fmt:message>
                                </th>
                                <th>
                                    <fmt:message key="req.display.user_id">
                                        user_id
                                    </fmt:message>
                                </th>
                                <th>
                                    <fmt:message key="req.display.master_id">
                                        master_id
                                    </fmt:message>
                                </th>
                                <th>
                                    <fmt:message key="req.display.finish_time">
                                        finish
                                    </fmt:message>
                                </th>
                                <th>
                                    <fmt:message key="req.display.price">
                                        price
                                    </fmt:message>
                                </th>
                                <th>
                                    <fmt:message key="req.display.status">
                                        status
                                    </fmt:message>
                                </th>
                                <th>
                                    <fmt:message key="display.rejection">
                                        rejection
                                    </fmt:message>
                                </th>
                                <th>
                                    <fmt:message key="req.display.add_master">
                                        add_master
                                    </fmt:message>
                                </th>

                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach items="${requestScope.requests}" var="request">
                                <tr>
                                    <td><c:out value="${request.id}" /></td>
                                    <td><c:out value="${request.specification}" /></td>
                                    <td><c:out value="${request.description}" /></td>
                                    <td><c:out value="${request.requestTime}" /></td>
                                    <td><c:out value="${request.user.id}" /></td>
                                    <td>
                                        <c:forEach items="${request.masters}" var="master">
                                            <p><c:out value="${master.username}" /></p>
                                            <br>
                                        </c:forEach>
                                    </td>
                                    <td><c:out value="${request.finishTime}" /></td>
                                    <td><c:out value="${request.price}" /></td>
                                    <td><c:out value="${request.state}" /></td>
                                    <td>
                                        <form th:action="@{/request-display/reject/{id}(id=${r.id})}" method="post">
                                            <!--                                    <label for="rejectionMessageElement"></label>-->
                                            <input type="text" id="rejectionMessageElement"
                                                   name="rejectionMessage"
                                                   style="margin-bottom: 10px"
                                                   th:placeholder="#{display.rejection_type}">
                                            <button style="margin-top: 10px" type="submit"
                                                    class="btn btn-sm btn-secondary"
                                                    th:text="#{display.reject}">Reject</button>
                                        </form>
                                    </td>
                                    <td>
                                        <form th:action="@{/request-display/edit/{id}(id=${r.id})}" method="get">
                                            <button type="submit" class="btn btn-sm btn-info">
                                                <fmt:message key="display.edit">
                                                    edit
                                                </fmt:message>
                                            </button>
                                        </form>
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

</body>
</html>

