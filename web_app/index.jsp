<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<title>Book Store</title>

<style>
body { margin: 0; font-family: Arial; }

.top-bar {
    display: flex;
    justify-content: space-between;
    padding: 15px 30px;
    background: #f5f5f5;
}

.right-buttons button {
    margin-left: 10px;
}

.grid-container {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 20px;
    padding: 20px;
}

.card {
    border: 1px solid #ccc;
    border-radius: 10px;
    padding: 10px;
    text-align: center;
    box-shadow: 2px 2px 5px gray;
}

.card img {
    width: 100%;
    height: 150px;
    object-fit: cover;
}

.search-box {
    text-align: center;
    margin-top: 10px;
}
</style>
</head>

<body>

<div class="top-bar">
    <div><h2>Book Store</h2></div>

    <div class="right-buttons">
        <form action="check_out.jsp" method="get" style="display:inline;">
            <button type="submit">Checkout</button>
        </form>

        <form action="status.jsp" method="get" style="display:inline;">
            <button type="submit">Check Status</button>
        </form>
    </div>
</div>

<div class="search-box">
    <form method="get">
        <input type="text" name="search" placeholder="Search by id, name, author, type">
        <button type="submit">Search</button>
    </form>
</div>

<div class="grid-container">

<%
String search = request.getParameter("search");

// session cart
ArrayList<String[]> cart = (ArrayList<String[]>) session.getAttribute("cart");
if(cart == null){
    cart = new ArrayList<>();
    session.setAttribute("cart", cart);
}

// add to cart logic
String add = request.getParameter("add");
if(add != null){
    String name = request.getParameter("book_name");
    String author = request.getParameter("author");
    String price = request.getParameter("price");

    cart.add(new String[]{name, author, price});
}

try {
    Class.forName("com.mysql.cj.jdbc.Driver");

    Connection conn = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/java_pbl", "root", "12345");

    String query;

    if(search != null && !search.trim().equals("")){
        query = "SELECT * FROM book_details WHERE s_no LIKE ? OR book_name LIKE ? OR author_name LIKE ? OR book_type LIKE ?";
    } else {
        query = "SELECT * FROM book_details";
    }

    PreparedStatement ps = conn.prepareStatement(query);

    if(search != null && !search.trim().equals("")){
        for(int i=1;i<=4;i++){
            ps.setString(i, "%" + search + "%");
        }
    }

    ResultSet rs = ps.executeQuery();

    while(rs.next()){
%>

<form method="get">
    <div class="card">
        <img src="book.png">
        <h3><%= rs.getString("book_name") %></h3>
        <p>Author: <%= rs.getString("author_name") %></p>
        <p>Price: <%= rs.getString("price") %></p>
        <p>Type: <%= rs.getString("book_type") %></p>

        <input type="hidden" name="book_name" value="<%= rs.getString("book_name") %>">
        <input type="hidden" name="author" value="<%= rs.getString("author_name") %>">
        <input type="hidden" name="price" value="<%= rs.getString("price") %>">
        <input type="hidden" name="add" value="1">

        <button type="submit">Add to Cart</button>
    </div>
</form>

<%
    }

    conn.close();

} catch(Exception e){
    out.println(e);
}
%>

</div>

</body>
</html>