<%@ page contentType="text/html;
    charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="styles/main.css" rel="stylesheet">
</head>
<body>
<div id="parent">
<h1>Login</h1>
<div class="container">
    <form class="form-login" method="post" action="/login">
        <h2>Please sign in</h2>
        <p style="color: darkred">${message}</p>
        <label for="inputName">User name</label>
        <input name="name" type="text" id="inputName"
               placeholder="User name" required autofocus>
        <input type="hidden" name="action" id="action" value="login"/>
        <br>
        <label for="inputPassword">Password</label>
        <input name="password" type="password" id="inputPassword"
                placeholder="Password" required>

        <button type="submit">Login</button>
    </form>
    <a href="register.jsp">Register</a>
</div>


<jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
</html>
