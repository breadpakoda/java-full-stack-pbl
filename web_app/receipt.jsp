<!DOCTYPE html>
<html>
<head>
<title>Receipt</title>
</head>
<body>

<%
String total = request.getParameter("total");
%>

<h2>Payment Successful</h2>

<h3>Receipt</h3>

<p>Total Paid: <%= total %></p>

<hr>

<h3>Status</h3>
<p>Out for Delivery</p>

</body>
</html>