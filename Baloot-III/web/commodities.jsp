<%@ page import="Baloot.Api.Responses.GetCommoditiesListResponse" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="Baloot.Api.Requests.AddCommentRequest" %>
<%@ page import="Baloot.Servlets.CommoditiesServlet" %>
<%@ page import="com.google.gson.reflect.TypeToken" %>
<%@ page import="Baloot.Utils.Session" %><%--
<%@ page import="com.google.gson.Gson"%>
  Created by IntelliJ IDEA.
  User: shayan
  Date: 4/7/23
  Time: 6:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String commoditiesListString = (String) request.getAttribute("commoditiesList");
  List<GetCommoditiesListResponse.Commodity> commoditiesList = new Gson().fromJson(commoditiesListString, new TypeToken<List<GetCommoditiesListResponse.Commodity>>(){}.getType());
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Commodities</title>
  <style>
    table{
      width: 100%;
      text-align: center;
    }
  </style>
</head>
<body>
<a href="/">Home</a>
<p id="username">username: <%=Session.getInstance().getLoggedInUsername()%>></p>
<br><br>
<form action="commodities" method="POST">
  <label>Search:</label>
  <input type="text" name="search" value="">
  <button type="submit" name="searchName" value="search_by_name">Search By Name</button>
  <button type="submit" name="searchCat" value="search_by_category">Search By Category</button>
</form>
<br><br>
<form action="commodities" method="GET">
  <label>Clear Search:</label>
  <button type="submit" name="clearSearch" value="clear">Clear Search</button>
</form>
<form action="commodities" method="POST">
  <label>Sort By:</label>
  <input type="text" name="sort" value="">
  <button type="submit" name="sortBy" value="sort_by">Sort</button>
</form>
<br><br>
<table>
  <tr>
    <th>Id</th>
    <th>Name</th>
    <th>Provider Name</th>
    <th>Price</th>
    <th>Categories</th>
    <th>Rating</th>
    <th>In Stock</th>
    <th>Links</th>
  </tr>
  <% for(int i = 0; i < commoditiesList.size(); i+=1) { %>
  <tr>
    <td><%=commoditiesList.get(i).id%></td>
    <td><%=commoditiesList.get(i).name%></td>
    <td><%=commoditiesList.get(i).providerId%></td>
    <td><%=commoditiesList.get(i).price%></td>
    <td><%=commoditiesList.get(i).categories%></td>
    <td><%=commoditiesList.get(i).rating%></td>
    <td><%=commoditiesList.get(i).inStock%></td>
    <td><a href="commodities/<%=commoditiesList.get(i).id%>">Link</a></td>
    <% } %>
  </tr>
</table>
</body>
</html>
