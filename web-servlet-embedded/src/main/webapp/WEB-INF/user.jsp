<%--
  Created by IntelliJ IDEA.
  User: Sarkura
  Date: 2024/11/22
  Time: 21:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.User" %>
<%
  User user = (User) request.getAttribute("user");
%>
<html>
<head>
    <title>Hello World - JSP</title>
</head>
<body>
    <h1>Hello <%= user.name %> !</h1>
    <p>School name:
      <span style="color:red">
      <%= user.school.name%>
      </span>
    </p>
    <p>
      School Address:
      <span style="color:red">
        <%= user.school.address %>
      </span>
    </p>
</body>
</html>
