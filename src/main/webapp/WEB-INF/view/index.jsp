<%--
  Created by IntelliJ IDEA.
  User: helvetica
  Date: 21.07.19
  Time: 18:02
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
    <title>Repair It</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>
<div class="container" style="margin-top: 50px">

    <div class = "navigation">
        <h2 class="h2">Repair It</h2>
        <h3><fmt:message key="index.greeting">greeting</fmt:message></h3>
        <ul class="nav nav-tabs nav-fill bg-light">
            <li class="nav-item">
                <a class="nav-link disabled" href="${pageContext.request.contextPath}/app/index"><fmt:message key="index.nav_bar.main">main</fmt:message></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/app/registration"><fmt:message key="index.nav_bar.registration">sign up</fmt:message></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/app/login" ><fmt:message key="index.nav_bar.log_in">sign in</fmt:message></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/app/logout"><fmt:message key="index.nav_bar.log_out">sign out</fmt:message></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/app/request"><fmt:message key="index.nav_bar.request">leave request.jsp</fmt:message></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/app/display"><fmt:message key="index.nav_bar.display">display</fmt:message></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/app/display-request"><fmt:message key="index.nav_bar.request_display">display requests</fmt:message></a>
            </li>
        </ul>

    </div>

    <div class="jumbotron">
<%--        <h3><fmt:message key="index.my_requests"/></h3>--%>

        <div class="user_requests">
            <h3>
                <fmt:message key="index.my_requests">
                    My requests
                </fmt:message>
            </h3>

            <form action="${pageContext.request.contextPath}/app/index" method="get">
            <div class="UserRequestsInfo">

                <c:forEach items="${requestScope.user_requests}" var="request">
                    <div>
                        <span><fmt:message key="index.type"/><c:out value="${request.specification}" /></span>
                        <br>
                        <span><fmt:message key="index.description"/><c:out value="${request.description}" /></span>
                        <br>
                        <span><fmt:message key="index.price"/><c:out value="${request.price}" /></span>
                        <br>
                        <span><fmt:message key="index.status"/><c:out value="${request.state}" /></span>
                        <br>
                        <span>
                            <fmt:message key="index.masters"/>
                            <c:forEach items="${request.masters}" var="master">
                                <p><c:out value="${master.username}" /></p>
                                <br>
                            </c:forEach>
                        </span>

                            <%--                    <form action="@{/main/payment}" method="post" th:field="${paid}" >--%>
                            <%--                        <label>--%>
                            <%--                            <input name="requestPrice" th:value="${request.jsp.price}" hidden/>--%>
                            <%--                            <input name="requestId" th:value="${request.jsp.id}" hidden/>--%>
                            <%--                        </label>--%>
                            <%--                        <div th:if="${balance &gt; request.jsp.price}" th:unless="${request.jsp.state == paid || request.jsp.state == completed}">--%>
                            <%--                            <button type="submit" class="btn btn-primary" th:text="#{global.pay}">--%>
                            <%--                                Pay--%>
                            <%--                            </button>--%>
                            <%--                        </div>--%>
                            <%--                    </form>--%>

                            <%--                    <form action="#" th:action="@{/main/comment}" method="post" th:field="${completed}">--%>
                            <%--                        <div th:if="${request.jsp.state == completed}" style="margin-top: 30px">--%>
                            <%--                            <h5 th:text="#{index.comment}"></h5>--%>
                            <%--                            <label>--%>
                            <%--                                <input name="comment"--%>
                            <%--                                       th:placeholder="#{index.leave.comment}"/>--%>
                            <%--                                <input name="requestId" th:value="${request.jsp.id}" hidden/>--%>
                            <%--                            </label>--%>
                            <%--                            <button type="submit" class="btn btn-primary" th:text="#{global.submit}">--%>
                            <%--                                leave--%>
                            <%--                            </button>--%>
                            <%--                        </div>--%>
                            <%--                    </form>--%>

                    </div>

                </c:forEach>


                <%--            <div th:if="${ #lists.isEmpty(user_requests)}">--%>
                <%--                <span th:text="#{index.no_requests}">No requests yet. Leave request.jsp</span>--%>
                <%--                <a href="/request" th:text="#{index.here}">here.</a>--%>
                <%--            </div>--%>
            </div>
            </form>

                <div class = "MasterRequestInfo">
                    <div class = "master_request"
                         style="margin-top: 30px">
                        <h3><fmt:message key="index.my_requests.master">My requests (Master)</fmt:message></h3>
                        <div style="margin-top: 20px">
                            <div class="master_request_heading">
                                <ul class="list-group">
                                    <c:forEach items="${requestScope.master_requests}" var="master_request">
                                    <li class="list-group-item ">
                                        <span>
                                            <fmt:message key="index.description"/>
                                            <c:out value="${master_request.description}"/>
                                        </span>
                                        <form action="${pageContext.request.contextPath}/app/index/edit" method="post"
                                              style="margin-top: 30px">
                                            <div class="row">
                                                <div class="col-sm-6">
                                                    <label>
                                                        <input name="master_request_id" value="${master_request.id}" hidden/>
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
                                                               class="form-control"
                                                               id="priceElement"
                                                               name="master_request_price"
                                                               placeholder=<fmt:message key="req.display.price"/>>
                                                    </div>
                                                </div>

                                                <div class="col-sm-6">
                                                    <span class="form-heading" >
                                                        <fmt:message key="index.select_state">
                                                            Select state
                                                        </fmt:message>
                                                    </span>
                                                    <div class="form-group">
                                                        <label id="SecondTypeLabel" for="state" class="col-form-label"></label>
                                                        <select class="form-control" id="state" name="master_request_state" required>
                                                            <option value="ACCEPTED">
                                                                <fmt:message key="state.accepted">
                                                                    Accepted
                                                                </fmt:message>
                                                            </option>
                                                            <option value="COMPLETED">
                                                                <fmt:message key="state.completed">
                                                                    Completed
                                                                </fmt:message>
                                                            </option>
                                                            <option value="REJECTED" >
                                                                <fmt:message key="state.rejected">
                                                                    Rejected
                                                                </fmt:message>
                                                            </option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="col-sm-6">
                                                    <button type="submit" class="btn btn-success">
                                                        <fmt:message key="global.submit">
                                                            Submit
                                                        </fmt:message>
                                                    </button>
                                                </div>
                                            </div>
                                            <hr>
                                        </form>
                                    </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
<%--            </form>--%>

        </div>
    </div>

    <div class = "footer" style="margin-top: 20px">
        <span><fmt:message key="lang.change">change</fmt:message></span>:
        <a href="?lang=en" ><fmt:message key="lang.en">english</fmt:message></a>
        /
        <a href="?lang=uk"><fmt:message key="lang.uk">ukrainian</fmt:message></a>
    </div>

<%--    <div class="user_balance" th:field="${balance}">--%>
<%--        <span th:text="#{balance.user}">BALANCE: </span>--%>
<%--        <span th:text="${balance != null} ? ${balance} : '0'">some balance</span>--%>
<%--        <br>--%>
<%--        <span th:text="#{index.replenish}">Replenish</span>--%>
<%--        <a href="/balance" th:text="#{index.here}">here.</a>--%>
<%--    </div>--%>





</div>


</body>
</html>
