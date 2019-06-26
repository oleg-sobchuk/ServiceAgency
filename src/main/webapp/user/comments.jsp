<%@ page contentType="text/html;
    charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>ServiceAgency</title>
    <link href="${pageContext.request.contextPath}/styles/main.css" rel="stylesheet">
</head>
<body>
<div id="wrap">
    <jsp:include page="${pageContext.request.contextPath}/navbar.jsp"></jsp:include>

    <p>Comments to order with id : ${order_id}</p>
    <c:choose>
        <c:when test="${empty comments}">
            <p>There is no comments</p>
        </c:when>
        <c:otherwise>
            <table style="width: 50%">
                <c:forEach items="${comments}" var="comment">
                    <tr>
                        <td>
                            <div style="background-color: aliceblue">
                                <div class="comment-time">${comment.createDate}</div>
                            </div>

                            <div class="comment-content">
                                <p>${comment.text}</p>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>

    <form action="${pageContext.request.contextPath}/user/comments?order_id=${order_id}" method="post">
        <p><b>Enter your comment here:</b></p>
        <c:if test="${not empty message}">
            <p class="error-msg">${message}</p>
        </c:if>
        <p><textarea rows="10" cols="45" name="text" required></textarea></p>
        <p><input type="submit" value="Send"></p>
    </form>
</div>
<jsp:include page="${pageContext.request.contextPath}/footer.jsp"></jsp:include>
</body>
</html>
