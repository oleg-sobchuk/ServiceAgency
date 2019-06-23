<%@ page contentType="text/html;
    charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Service Agency Registration</title>
    <link href="${pageContext.request.contextPath}/styles/main.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <form action="${pageContext.request.contextPath}/register" method="post">
        <h2>Registration</h2>
        <p class="error-msg">${message}</p>
        <h3>Name: <input type="text" name="userName" value=""/></h3>
        <h3>Password: <input type="password" name="userPassword" value=""/></h3>
        <input type="submit" value="register"/>
    </form>
</div>
<jsp:include page="${pageContext.request.contextPath}/footer.jsp"></jsp:include>
</body>
</html>
