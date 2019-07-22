<%--
  Created by IntelliJ IDEA.
  User: helvetica
  Date: 21.07.19
  Time: 18:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang = "${param.lang}">

<head>
    <title>LogIn</title>
</head>
<body>

    <div class="form">

        <h2>Log in</h2>

        <form method="post" action="">

            <input type="text" required placeholder="login" name="login">
            <br>
            <input type="password" required placeholder="password" name="password">
            <br>
            <input class="button" type="submit" value="Sign in">
        </form>

    </div>

</body>
</html>
