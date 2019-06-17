<%@ page contentType="text/html;
    charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>ServiceAgency</title>
    <link href="styles/main.css" rel="stylesheet">
</head>
<body>
<div id="wrap">
    <jsp:include page="navbar.jsp"></jsp:include>
    <div class="wrap">
        <h2>${user.name} Wellcome to our agency!</h2>
        <a href="/jdbc">jdbc</a>

    </div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
