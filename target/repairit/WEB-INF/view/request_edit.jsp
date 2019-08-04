<%--
  Created by IntelliJ IDEA.
  User: helvetica
  Date: 02.08.19
  Time: 15:01
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
            <h2 class="page-header" th:text="#{req.edit}">Request Edit</h2>

            <form th:action="@{/request-display/edit/{id}(id=${requestId})}" method="post">

                <div class="form-group">
                    <label id="masterLabel" for="masterElement" th:text="#{req.edit.master}">Master</label>
                    <input type="text"
                           class="form-control"
                           id="masterElement"
                           name="masterId"
                           th:placeholder="#{req.edit.set.master}">
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
