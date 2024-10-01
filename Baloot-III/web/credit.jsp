
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>Credit</title>
</head>

<body>
<a href="/">Home</a>
<p>Current Credit: <%= request.getAttribute("credit") %></p>
<form method="post" action="credit">
  <label>Credits:</label>
  <input name="credit" type="text" />
  <br>
  <button type="submit">Add credits</button>
</form>
</body>
</html>