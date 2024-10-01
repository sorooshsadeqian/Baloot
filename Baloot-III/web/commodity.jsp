<%@ page import="com.google.gson.Gson" %>
<%@ page import="Baloot.Api.Responses.GetCommodityByIdResponse" %>
<%@ page import="com.google.gson.reflect.TypeToken" %>
<%@ page import="Baloot.Utils.Session" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String commodityJson = (String) request.getAttribute("commodity");
  GetCommodityByIdResponse commodity = new Gson().fromJson(commodityJson, new TypeToken<GetCommodityByIdResponse>(){}.getType());
%>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Commodity</title>
  <style>
    li {
      padding: 5px;
    }
    table {
      width: 100%;
      text-align: center;
    }
  </style>
</head>
<body>
<a href="/">Home</a>
<span>username: <%=Session.getInstance().getLoggedInUsername()%></span>
<br>
<ul>
  <li id="id">Id: <%= commodity.id %></li>
  <li id="name">Name:  <%= commodity.name %></li>
  <li id="providerName">Provider Name:  <%= commodity.providerId %></li>
  <li id="price">Price:  <%= commodity.price %></li>
  <li id="categories">Categories:  <%= commodity.categories %></li>
  <li id="rating">Rating:  <%= commodity.rating %></li>
  <li id="inStock">In Stock:  <%= commodity.inStock %></li>
</ul>

<label>Add Your Comment:</label>
<form action="" method="post">
  <input type="text" name="comment" value="" required/>
  <button type="submit" name="commentButton" value="comment">submit</button>
</form>
<br>
<form action="" method="post">
  <label>Rate(between 1 and 10):</label>
  <input type="number" id="rate" name="rate" min="1" max="10" required>
  <button type="submit" name="rateButton" value="rate">Rate</button>
</form>
<br>
<form action="" method="post">
  <button type="submit" name="buyListButton" value="buyList">Add to BuyList</button>
</form>
<br />
<table>
  <caption><h2>Comments</h2></caption>
  <tr>
    <th>username</th>
    <th>comment</th>
    <th>date</th>
    <th>likes</th>
    <th>dislikes</th>
  </tr>
  <% for(int i = 0; i < commodity.comments.size(); i+=1) { %>
  <tr>
    <td><%=commodity.comments.get(i).username%></td>
    <td><%=commodity.comments.get(i).text%></td>
    <td><%=commodity.comments.get(i).date%></td>
    <td>
      <form action="" method="POST">
        <label><%=commodity.comments.get(i).likes%></label>
        <input
                id="form_comment_like_email"
                type="hidden"
                name="comment_email"
                value="<%=commodity.comments.get(i).email%>"
        />
        <button type="submit" name="voteButton" value="like">like</button>
      </form>
    </td>
    <td>
      <form action="" method="POST">
        <label><%=commodity.comments.get(i).dislikes%></label>
        <input
                id="form_comment_dislike_email"
                type="hidden"
                name="comment_email"
                value="<%=commodity.comments.get(i).email%>"
        />
        <button type="submit" name="voteButton" value="dislike">dislike</button>
      </form>
    </td>
  </tr>
      <% } %>
</table>
<br><br>
<table>
  <caption><h2>Suggested Commodities</h2></caption>
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
  <% for(int i = 0; i < commodity.suggestedCommodities.size(); i+=1) { %>
  <tr>
    <td> <%= commodity.suggestedCommodities.get(i).id %></td>
    <td> <%= commodity.suggestedCommodities.get(i).name %></td>
    <td><%= commodity.suggestedCommodities.get(i).providerId %></td>
    <td><%= commodity.suggestedCommodities.get(i).price %></td>
    <td><%= commodity.suggestedCommodities.get(i).categories %></td>
    <td><%= commodity.suggestedCommodities.get(i).rating %></td>
    <td><%= commodity.suggestedCommodities.get(i).inStock %></td>
    <td><a href="/commodities/<%= commodity.suggestedCommodities.get(i).id %>">Link</a></td>
  </tr>
  <% } %>
</table>
</body>
