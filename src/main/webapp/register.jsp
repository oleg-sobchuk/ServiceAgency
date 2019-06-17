<%@ page contentType="text/html;
    charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Service Agency Registration</title>
    <link href="styles/main.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <form action="Register" method="post">
        <input type="hidden" name="action" value="register">
        <h1>Registration</h1>
        <h2>Name: <input type="text" name="userName" value=""/></h2>
        <h2>Password: <input type="password" name="userPassword" value=""/></h2>
        <input type="submit" value="Register"/>
    </form>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
