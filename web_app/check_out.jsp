<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<title>Checkout</title>
</head>
<body>

<h2>Checkout</h2>

<%
ArrayList<String[]> cart = (ArrayList<String[]>) session.getAttribute("cart");

int total = 0;

if(cart != null && cart.size() > 0){
    for(String[] item : cart){
%>
        <p>
            Book: <%= item[0] %> |
            Author: <%= item[1] %> |
            Price: <%= item[2] %>
        </p>
<%
        try {
            total += Integer.parseInt(item[2]);
        } catch(Exception e){}
    }
} else {
%>
    <p>Cart is empty</p>
<%
}
%>

<h3>Total: <%= total %></h3>

<form action="receipt.jsp" method="post">
    <input type="hidden" name="total" value="<%= total %>">
    <button type="submit">Pay</button>
</form>

</body>
</html>