<%@ page contentType="text/html;
    charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
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

    <p>Order manage page:</p>


    <div class="error-msg">${error_message}</div>
    <div class="success-msg">${success_message}</div>

    <form method="post">
        <table class="order-table">
            <tr>
                <td>Owner</td>
                <td>${owner_user}</td>
            </tr>
            <tr>
                <td>Date</td>
                <td>${order.addDate}</td>
            </tr>
            <tr>
                <td>Device</td>
                <td>${order.deviceDescription}</td>
            </tr>
            <tr>
                <td>Malfunction</td>
                <td>${order.malfunctionDescription}</td>
            </tr>
            <tr>
                <td>Status</td>
                <td>${order.orderStatus}</td>
            </tr>
            <tr>
                <td>Last update date</td>
                <td>${order.updateDate}</td>
            </tr>
            <tr>
                <td>Note</td>
                <td>
                    <c:choose>
                        <c:when test="${order.orderStatus == 'NEW'}">
                            <input type="text" name="note" placeholder="add note to reject">
                        </c:when>
                        <c:otherwise>
                            ${order.note}
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td>Price</td>
                <td>
                    <c:choose>
                        <c:when test="${order.orderStatus == 'NEW'}">
                            <input type="text" name="price" placeholder="set price to accept">
                        </c:when>
                        <c:when test="${order.orderStatus == 'ACCEPTED'}">
                            ${order.price}
                        </c:when>
                        <c:when test="${order.orderStatus == 'REJECTED'}">
                            <p> - </p>
                        </c:when>
                        <c:otherwise>
                            ${order.price}
                        </c:otherwise>
                    </c:choose>

                </td>
            </tr>
            <tr>
                <td>Comments</td>
                <td><a href="${pageContext.request.contextPath}/user/comments?order_id=${order.id}">view</a></td>
            </tr>
            <tr>
                <td>
                    <c:choose>
                        <c:when test="${order.orderStatus == 'NEW'}">
                            <button type="submit" formaction="${pageContext.request.contextPath}/personal/process_order?order_id=${order.id}&action=accept">accept</button>
                            <button type="submit" formaction="${pageContext.request.contextPath}/personal/process_order?order_id=${order.id}&action=reject">reject</button>
                        </c:when>
                        <c:when test="${order.orderStatus == 'ACCEPTED'}">
                            <button type="submit" formaction="${pageContext.request.contextPath}/personal/process_order?order_id=${order.id}&action=repair">make repair</button>
                        </c:when>
                        <c:otherwise>
                            <p style="color: grey">closed</p>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </table>
    </form>

    <a href="/personal/process_order">Back to orders</a>

</div>
<jsp:include page="${pageContext.request.contextPath}/footer.jsp"></jsp:include>
</body>
</html>
