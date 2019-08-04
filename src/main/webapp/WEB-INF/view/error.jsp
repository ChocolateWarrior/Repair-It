<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" isErrorPage="true"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*" %>



<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Error Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<%--    <link rel="stylesheet" href="@{/css/style.css}" >--%>
    <link href="https://fonts.googleapis.com/css?family=Oswald&display=swap" rel="stylesheet">
</head>
    <body>
        <div class="container" style="margin-top: 50px;">
            <div class="error-body">
                <h1>Something went wrong! </h1>
                <h2>
                    Error Page<br/>
                    <i>Error <%= exception %></i>
                </h2>
                <br>
                <a href="${pageContext.request.contextPath}/WEB-INF/view/index.jsp">Index</a>
                <h2>Our Engineers are working on it</h2>
                <a href="/main" style="margin-top: 20px">Home</a>
            </div>
        </div>




    </body>
</html>