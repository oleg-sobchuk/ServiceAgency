<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav>
    <h1 class="inline1" id="main_header">Service Agency</h1>
    <span>
        <c:choose>
            <c:when test="${empty user}">
                <form class="inline1" action="${pageContext.request.contextPath}/login.jsp">
                    <input type="submit" value="login">
                </form>
            </c:when>
            <c:otherwise>
                <span id="user-name">${user.name}</span>
            </c:otherwise>
        </c:choose>
    </span>
    <span>
            <form class="inline1" action="${pageContext.request.contextPath}/login" method="post">
                <input type="hidden" name="action" id="action" value="logout"/>
                <input style="background-color: darkred" type="submit" value="logout">
            </form>
    </span>
</nav>