<%@ page import="Baloot.Api.Responses.GetUserResponse" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.reflect.TypeToken" %>
<%@ page import="Baloot.Api.Responses.GetBuyListResponse" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String userJson = (String) request.getAttribute("user");
    GetUserResponse user = new Gson().fromJson(userJson, new TypeToken<GetUserResponse>(){}.getType());

    String buyListJson = (String) request.getAttribute("buylist");
    GetBuyListResponse buyList = new Gson().fromJson(buyListJson, new TypeToken<GetBuyListResponse>(){}.getType());

%>
<html lang="en"><head>
    <meta charset="UTF-8">
    <title>User</title>
    <style>
        li {
            padding: 5px
        }
        table{
            width: 100%;
            text-align: center;
        }
    </style>
</head>
<body>
<a href="/">Home</a>
<ul>
    <li id="username">Username: <%=user.username%></li>
    <li id="email">Email: <%=user.email%></li>
    <li id="birthDate">Birth Date: 2000/07/21</li>
    <li id="address">Tehran, North Karegar, No 10</li>
    <li id="credit">Credit: <%=user.credit%></li>
    <li>Current Buy List Price: <%=buyList.totalPrice%></li>
    <li>Price With Discount: <%=buyList.discountPrice%></li>
    <li>
        <a href="/credit">Add Credit</a>
    </li>
    <li>
        <form action="" method="post">
            <label for="discount_code">Discount Code</label>
            <input id="discount_code" type="text" name="code" />
            <button type="submit" name="discountButton" value="discount">Apply Discount</button>
        </form>
    </li>
    <li>
        <form action="" method="post">
            <label>Submit & Pay</label>
            <input id="form_payment" type="hidden" name="username" value="<%=user.username%>">
            <button type="submit" name="payButton" value="pay">Payment</button>
        </form>
    </li>
</ul>
<table>
    <caption>
        <h2>Buy List</h2>
    </caption>
    <tbody><tr>
        <th>Id</th>
        <th>Name</th>
        <th>Provider Name</th>
        <th>Price</th>
        <th>Categories</th>
        <th>Rating</th>
        <th>In Stock</th>
        <th></th>
        <th></th>
    </tr>
    <% for(int i = 0; i < buyList.commoditiesList.size(); i+=1) { %>
    <tr>
        <td> <%= buyList.commoditiesList.get(i).id %></td>
        <td> <%= buyList.commoditiesList.get(i).name %></td>
        <td><%= buyList.commoditiesList.get(i).providerId %></td>
        <td><%= buyList.commoditiesList.get(i).price %></td>
        <td><%= buyList.commoditiesList.get(i).categories %></td>
        <td><%= buyList.commoditiesList.get(i).rating %></td>
        <td><%= buyList.commoditiesList.get(i).inStock %></td>
        <td><a href="/commodities/<%= buyList.commoditiesList.get(i).id %>">Link</a></td>
        <td>
            <form action="" method="POST">
                <input type="hidden" name="commodityId" value="<%= buyList.commoditiesList.get(i).id %>">
                <button type="submit" name="removeButton" value="remove">Remove</button>
            </form>
        </td>
    </tr>
    <% } %>
    </tbody></table>
</body></html>