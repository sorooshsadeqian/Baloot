<%--
  Created by IntelliJ IDEA.
  User: shayan
  Date: 4/7/23
  Time: 7:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>400 Error</title>
</head>
<body>
<h1>400<br><%= session.getAttribute("error") %></h1>
</body>
</html>
