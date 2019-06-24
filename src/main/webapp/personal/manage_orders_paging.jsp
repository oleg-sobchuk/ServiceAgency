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

    <p>Orders manage page:</p>
    <p>Founded ${order_list_size} orders.</p>
    <div class="error-msg">${error_message}</div>
    <c:choose>
        <c:when test="${empty orders}">
            <p>You haven't orders for now</p>
        </c:when>
        <c:otherwise>
            <form method="post">
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
                    <th></th>
                </tr>
                <c:forEach items="${orders}" var="order">
                    <tr>
                        <td>${order.addDate}</td>
                        <td>${order.deviceDescription}</td>
                        <td>${order.malfunctionDescription}</td>
                        <td>${order.orderStatus}</td>
                        <td>${order.updateDate}</td>
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
                        <td><a href="${pageContext.request.contextPath}/user/comments?order_id=${order.id}">view</a></td>
                        <td>
                            <c:choose>
                                <c:when test="${order.orderStatus == 'NEW'}">
                                    <!--<a href="${pageContext.request.contextPath}/personal/process_order?order_id=${order.id}">accept/reject</a>-->
                                    <button type="submit" formaction="${pageContext.request.contextPath}/personal/process_order?order_id=${order.id}&action=accept">
                                        accept
                                    </button>
                                    <button type="submit" formaction="${pageContext.request.contextPath}/personal/process_order?order_id=${order.id}&action=reject">
                                        reject
                                    </button>
                                </c:when>
                                <c:when test="${order.orderStatus == 'ACCEPTED'}">
                                    <!--<form action="${pageContext.request.contextPath}/personal/process_order?order_id=${order.id}&action=repair">
                                        <input type="submit" value="make repair">
                                    </form>-->
                                    <button type="submit" formaction="${pageContext.request.contextPath}/personal/process_order?order_id=${order.id}&action=repair">
                                        make repair
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <p style="color: grey">closed</p>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            </form>

                <span>
                    <a href="${pageContext.request.contextPath}/personal/process_order?page_action=prev">prev</a>
                </span>
                <span>
                    <${order_page_number}>
                </span>
                <span>
                    <a href="${pageContext.request.contextPath}/personal/process_order?page_action=next">next</a>
                </span>

        </c:otherwise>
    </c:choose>
    
</div>
<jsp:include page="${pageContext.request.contextPath}/footer.jsp"></jsp:include>
</body>
</html>
