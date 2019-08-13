
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property/messages"/>

<html lang = "en">

<head>
    <meta charset="UTF-8">
    <title>Repair It</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Oswald&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/style.css" >
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
                <a class="nav-link" href="${pageContext.request.contextPath}/app/logout"><fmt:message key="index.nav_bar.log_out">sign out</fmt:message></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/app/request"><fmt:message key="index.nav_bar.request">leave request</fmt:message></a>
            </li>
            <c:if test="${requestScope.user.hasAuthority('ADMIN')}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/app/display"><fmt:message key="index.nav_bar.display">display</fmt:message></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/app/display-request"><fmt:message key="index.nav_bar.request_display">display requests</fmt:message></a>
                </li>
            </c:if>
        </ul>
    </div>

    <div class="jumbotron">

        <div class="user_balance">

            <span><fmt:message key="balance.user">BALANCE: </fmt:message></span>
            <span>${user.balance}</span>
            <br />

            <br>
            <span>
                <fmt:message key="index.replenish">
                    Replenish
                </fmt:message>
            </span>
            <a href="${pageContext.request.contextPath}/app/balance?id=${user.id}">
                <fmt:message key="index.here">here.</fmt:message>
            </a>
        </div>

            <div class="user_requests">
                <h3>
                    <fmt:message key="index.my_requests">
                        My requests
                    </fmt:message>
                </h3>

                <c:if test="${requestScope.user.userRequests.isEmpty()}">
                    <span class="text-warning">
                        <fmt:message key ="index.no_requests"/>
                    </span>
                    <a href="${pageContext.request.contextPath}/app/request">
                        <fmt:message key="index.here"/>
                    </a>
                </c:if>


                <ul class="list-group">

                    <c:forEach items="${requestScope.user_requests}" var="request">

                        <li class="list-group-item ">

                            <form action="${pageContext.request.contextPath}/app/index" method="get">
                                <div class="UserRequestsInfo">
                                    <div>
                                        <span><fmt:message key="index.type"/><c:out value="${request.specification}" /></span>
                                        <br>
                                        <span><fmt:message key="index.description"/><c:out value="${request.description}" /></span>
                                        <br>
                                        <c:if test="${request.price != null}">
                                            <span><fmt:message key="index.price"/><c:out value="${request.price}" /></span>
                                            <br>
                                        </c:if>
                                        <c:if test="${request.state != null}">
                                            <span><fmt:message key="index.status"/><c:out value="${request.state}" /></span>
                                            <br>
                                        </c:if>
                                        <c:if test="${!request.masters.isEmpty()}">
                                            <span>
                                                <fmt:message key="index.masters"/>
                                                <c:forEach items="${request.masters}" var="master">
                                                    <span>[<c:out value="${master.username}" />]</span>
                                                </c:forEach>
                                            </span>
                                        </c:if>
                                    </div>
                                </div>

                            </form>
                            <c:if test="${request.state == requestScope.completed && request.comment == null}">
                                <form action="${pageContext.request.contextPath}/app/index/comment" method="post">

                                    <div style="margin-top: 30px">
                                        <h5>
                                            <fmt:message key="index.comment">
                                                comment
                                            </fmt:message>
                                        </h5>
                                        <label>
                                            <input name="comment" placeholder=<fmt:message key="index.leave.comment"/>>
                                            <input name="request_comment_id" value="${request.id}" hidden/>
                                        </label>
                                        <c:if test="${requestScope.comment_message_er!=null}">
                                            <p class="text-danger"><c:out value="${requestScope.comment_message_er}"/></p>
                                        </c:if>
                                        <button type="submit" class="btn btn-primary">
                                            <fmt:message key="global.submit">
                                                leave
                                            </fmt:message>
                                        </button>
                                    </div>
                                </form>
                            </c:if>

                            <c:if test="${requestScope.user.balance > request.price && request.state == requestScope.accepted}">
                                <form action="${pageContext.request.contextPath}/app/index/payment" method="post">
                                    <label>
                                        <input name="request_payment_price" value="${request.price}" hidden/>
                                        <input name="request_payment_id" value="${request.id}" hidden/>
                                    </label>
                                    <div>
                                        <button type="submit" class="btn btn-primary">
                                            <fmt:message key="global.pay">
                                                Pay
                                            </fmt:message>
                                        </button>
                                    </div>
                                </form>
                            </c:if>
                        </li>
                    </c:forEach>

                </ul>

                <c:if test="${requestScope.user.hasAuthority('MASTER')}">
                    <div class = "MasterRequestInfo">
                        <div class = "master_request"
                             style="margin-top: 30px">
                            <div style="margin-top: 20px">
                                <div class="master_request_heading">
                                    <h3><fmt:message key="index.my_requests.master">My requests (Master)</fmt:message></h3>
                                </div>
                                <ul class="list-group">
                                    <c:forEach items="${requestScope.master_requests}" var="master_request">
                                        <li class="list-group-item ">

                                            <div class="master-options">
                                                <span>
                                                    <fmt:message key="index.description"/>
                                                    <c:out value="${master_request.description}"/>
                                                </span>
                                                <br>
                                                <span>
                                                    <fmt:message key="index.status"/>
                                                    <c:out value="${master_request.state}"/>
                                                </span>


                                                <c:if test="${master_request.state == requestScope.paid}">
                                                    <form action="${pageContext.request.contextPath}/app/index/complete" method="post">
                                                        <label>
                                                            <input name="master_request_id" value="${master_request.id}" hidden/>
                                                        </label>
                                                        <button type="submit" class="btn btn-success">
                                                            <fmt:message key="index.complete">
                                                                Complete
                                                            </fmt:message>
                                                        </button>
                                                    </form>
                                                </c:if>
                                            </div>

                                        </li>
                                    </c:forEach>
                                </ul>

                            </div>
                        </div>
                    </div>
                </c:if>
            </div>

    </div>

    <div class = "footer" style="margin-top: 20px">
        <span><fmt:message key="lang.change">change</fmt:message></span>:
        <a href="?lang=en" ><fmt:message key="lang.en">english</fmt:message></a>
        /
        <a href="?lang=uk"><fmt:message key="lang.uk">ukrainian</fmt:message></a>
    </div>

</div>
</body>
</html>
