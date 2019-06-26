<%@ page contentType="text/html;
    charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

        <p>Orders manage page:</p>
        <c:choose>
            <c:when test="${empty orders}">
                <p>You haven't orders for now</p>
            </c:when>
            <c:otherwise>
                <table class="order-table">
                    <tr>
                        <th>Date</th>
                        <th>Device</th>
                        <th>Malfunction</th>
                        <th>Status</th>
                        <th>Last update date</th>
                        <th>Note</th>
                        <th>Price</th>
                        <th>Comments</th>
                    </tr>
                    <c:forEach items="${orders}" var="order">
                        <tr>
                            <td>${order.addDate}</td>
                            <td>${order.deviceDescription}</td>
                            <td>${order.malfunctionDescription}</td>
                            <td>${order.orderStatus}</td>
                            <td>${order.updateDate}</td>
                            <td>${order.note}</td>
                            <td>${order.price}</td>
                            <td><a href="${pageContext.request.contextPath}/user/comments?order_id=${order.id}">view</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    <br>
    <p>Fill up form to add new order for repair</p>
    <p class="error-msg">${message}</p>
    <form action="${pageContext.request.contextPath}/user/orders" method="post">
        <p><b>Describe the device (brand, type, model, etc):</b></p>
        <p><textarea rows="10" cols="45" name="device_desc" required></textarea></p>
        <p><b>Describe the malfuction of device (errors, indications, cause, etc):</b></p>
        <p><textarea rows="10" cols="45" name="malfunc_desc" required></textarea></p>
        <p><input type="submit" value="Send"></p>
    </form>

</div>
<jsp:include page="${pageContext.request.contextPath}/footer.jsp"></jsp:include>
</body>
</html>