<%@ page contentType="text/html;
    charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="${pageContext.request.contextPath}/styles/main.css" rel="stylesheet">
</head>
<body>
<div id="parent">
<h1>Login</h1>
<div class="container">
    <form class="form-login" method="post" action="${pageContext.request.contextPath}/login">
        <p class="error-msg">${message}</p>
        <label for="inputName">User name</label>
        <input name="name" type="text" id="inputName"
               placeholder="User name" required autofocus>
        <input type="hidden" name="action" id="action" value="login"/>
        <br>
        <label for="inputPassword">Password</label>
        <input name="password" type="password" id="inputPassword"
                placeholder="Password" required>

        <input type="submit" value="Login">
    </form>
    <a href="${pageContext.request.contextPath}/register.jsp">Register</a>
</div>


<jsp:include page="${pageContext.request.contextPath}/footer.jsp"></jsp:include>
</div>
</body>
</html>
