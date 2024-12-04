
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>评论</title>
</head>
<body>
<h1>输入评论内容</h1>
<form action="${pageContext.request.contextPath}/comment" method="post">
    <textarea name="message" cols="30" rows="10"></textarea>
    <input type="submit" value="提交">
</form>
<p >${requestScope.get("name")}<span style="color: red">${requestScope.get("comment")}</span></p>
</body>
</html>
