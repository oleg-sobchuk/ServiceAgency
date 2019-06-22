<%@ page contentType="text/html;
    charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>ServiceAgency</title>
    <link href="${pageContext.request.contextPath}/styles/main.css" rel="stylesheet">
</head>
<body>
<div id="wrap">
    <jsp:include page="${pageContext.request.contextPath}/navbar.jsp"></jsp:include>
    <div class="wrap">
        <c:choose>
            <c:when test="${empty user}">
                <h2>Welcome to our agency!</h2>
                <h3>Please login to continue</h3>
            </c:when>
            <c:otherwise>
                <h2>${user.name} welcome to our agency!</h2>
                <a href="${pageContext.request.contextPath}/user/orders">go to orders</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<jsp:include page="${pageContext.request.contextPath}/footer.jsp"></jsp:include>
</body>
</html>
