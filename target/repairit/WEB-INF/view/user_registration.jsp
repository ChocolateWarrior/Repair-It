
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property/messages"/>

<html lang = "en">

<head>
    <meta charset="UTF-8">
    <title>Registration</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Oswald&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/style.css" >
</head>
<body>


<div class="container" style="margin-top: 60px">


    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <h2 class="page-header" ><fmt:message key="reg.header">Registration Form</fmt:message></h2>

            <c:if test="${requestScope.error_message != null}">
                <p class="text-danger"><c:out value="${requestScope.error_message}"/></p>
            </c:if>

            <form style="margin-bottom: 30px" name="form" autocomplete="off">
                <div class="form-group">
                    <label id="firsNameLabel" for="firstName">
                        <fmt:message key="reg.first_name">
                            first_name
                        </fmt:message>
                    </label>
                    <input type="text"
                           name="firstName"
                           class="form-control"
                           id="firstName"
                           value="${requestScope.first_name}"
                           placeholder="<fmt:message key="reg.enter.first_name">login</fmt:message>"
                           required>
                </div>
                <c:if test="${requestScope.first_name_error != null}">
                    <p class="text-danger"><c:out value="${requestScope.first_name_error}"/></p>
                </c:if>

                <div class="form-group">
                    <label id="lastNameLabel" for="lastName" >
                        <fmt:message key="reg.last_name">
                            last_name
                        </fmt:message>
                    </label>
                    <input type="text"
                           name="lastName"
                           class="form-control"
                           id="lastName"
                           value="${requestScope.last_name}"
                           placeholder="<fmt:message key="reg.enter.last_name">last name</fmt:message>"
                           required>
                </div>

                <c:if test="${requestScope.last_name_error != null}">
                    <p class="text-danger"><c:out value="${requestScope.last_name_error}"/></p>
                </c:if>

                <div class="form-group">
                    <label id="usernameLabel" for="username">
                        <fmt:message key="reg.login">
                            login
                        </fmt:message>
                    </label>
                    <input type="text"
                           name="username"
                           class="form-control"
                           id="username"
                           value="${requestScope.username}"
                           placeholder="<fmt:message key="reg.enter.login">login</fmt:message>"
                           required>
                </div>

                <c:if test="${requestScope.username_error != null}">
                    <p class="text-danger"><c:out value="${requestScope.username_error}"/></p>
                </c:if>

                <div class="form-group">
                    <label id="passwordLabel" for="password">
                        <fmt:message key="reg.password">
                            password
                        </fmt:message>
                    </label>
                    <input type="text"
                           name="password"
                           class="form-control"
                           id="password"
                           value="${requestScope.password}"
                           placeholder="<fmt:message key="reg.enter.password">password</fmt:message>"
                           required>
                </div>

                <c:if test="${requestScope.password_error != null}">
                    <p class="text-danger"><c:out value="${requestScope.password_error}"/></p>
                </c:if>

                <div class="form-group">

                    <label id="SecondTypeLabel" for="specification"
                           class="col-form-label">
                        <fmt:message key="reg.select.specification">
                            Select specification
                        </fmt:message>
                    </label>
                    <select multiple class="form-control" id="specification" name="specifications">
                        <c:forEach items="${requestScope.all_specifications}" var="specification">
                            <option value=<c:out value="${specification}"/>>
                                <c:out value="${specification}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <button type="submit" class="btn btn-success" style="margin-top:30px">
                    <fmt:message key="reg.register">register</fmt:message>
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
                    <fmt:message key="main.return">
                        main
                    </fmt:message>
                </a>
            </div>

        </div>
    </div>

</div>

</body>
</html>
