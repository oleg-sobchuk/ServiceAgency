<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav>
    <h1 class="inline1" id="main_header">Service Agency</h1>

    <span id="header-links">
        <c:if test="${not empty user}">
            <span>
                <a href="${pageContext.request.contextPath}/">Home</a>
            </span>
            <span>
            <c:choose>
                <c:when test="${userOnly}">
                    <a href="${pageContext.request.contextPath}/user/orders">My orders</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/personal/process_order">Manage orders</a>
                </c:otherwise>
            </c:choose>
            </span>
        </c:if>
    </span>
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