<%--
  Created by IntelliJ IDEA.
  User: helvetica
  Date: 02.08.19
  Time: 15:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<div class="container" style="margin-top: 60px">

    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <h2 class="page-header" th:text="#{user.edit}">User Edit</h2>

            <form th:action="@{/user-display/edit/{id}(id=${userId})}" method="post">
                <div class="form-group">
                    <label id="firstNameLabel" for="firstNameElement" th:text="#{reg.first_name}">First Name</label>
                    <input type="text"
                           class="form-control"
                           id="firstNameElement"
                           name="firstName"
                           th:placeholder="#{reg.enter.first_name}">
                </div>

                <div class="form-group">
                    <label id="lastNameLabel" for="lastNameElement" th:text="#{reg.last_name}">Last Name</label>
                    <input type="text"
                           class="form-control"
                           id="lastNameElement"
                           name="lastName"
                           th:placeholder="#{reg.enter.last_name}">
                </div>

                <div class="form-group">
                    <label id="passwordLabel" for="passwordElement" th:text="#{reg.password}">Password</label>
                    <input type="text"
                           class="form-control"
                           id="passwordElement"
                           name="password"
                           th:placeholder="#{reg.enter.password}">
                </div>

                <div class="form-group">
                    <label id="loginLabel" for="loginElement" th:text="#{reg.login}">Username</label>
                    <input type="text"
                           class="form-control"
                           id="loginElement"
                           name="login"
                           th:placeholder="#{reg.enter.login}">
                </div>

                <button type="submit" class="btn btn-success" th:text="#{global.submit}">
                    Submit
                </button>
            </form>

            <div class="aboutLanguage">
                <span th:text="#{lang.change}"></span>:
                <a href="?lang=en" th:text="#{lang.en}">english</a>
                /
                <a href="?lang=uk" th:text="#{lang.uk}">ukrainian</a>
            </div>
            <div class="footer_bar">
                <a href="/main" th:text="#{main.return}">main</a>
            </div>

        </div>
    </div>

</div>

</body>
</html>
