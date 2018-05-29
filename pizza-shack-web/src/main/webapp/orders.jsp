<!DOCTYPE html>
<%@page import="com.pizzashack.client.dto.Order"%>
<%@page import="com.pizzashack.client.web.OrderManager"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%
	String token = (String) session.getAttribute("access.token");
	if (token == null) {
		response.sendRedirect("login.jsp");
		return;
	}
	session.setAttribute("cancel.order", "true");
%>
<html lang="en">
<head>
    <jsp:include page="include_head.jsp"/>
</head>

<body>


<div class="container">
    <jsp:include page="include_head_row.jsp" />


    <div class="row">
        <div class="navbar">
            <div class="navbar-inner">
                <div class="container">

                    <div class="nav-collapse">
                        <ul class="nav">
                            <li><a href="index.jsp">Pizza</a></li>
                            <li class="active"><a href="orders.jsp">My Orders</a></li>
                            <li><a href="logout.jsp">Log Out</a></li>
                        </ul>
                    </div>
                    <!-- /.nav-collapse -->
                </div>
            </div>
            <!-- /navbar-inner -->
        </div>
    </div>
    <div class="clearfix"></div>
    <div class="row well">
        <div class="span12">
            <h1>My Orders</h1>
        </div>
        <div class="span10">

            <form class="form-horizontal" action="orders.jsp" method="POST">
                <fieldset>
                    <div class="control-group">
                        <label class="control-label" for="quantity">Order Number</label>

                        <div class="controls">
                            <input type="text" class="input-xlarge" id="quantity" name="orderId"/>

                            <p class="help-block">Enter the order number provided during the order submission process.</p>
                        </div>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">Find order</button>
                        <button type="button" class="btn" onclick="javascript:location.href='index.jsp'">Cancel</button>
                    </div>
                </fieldset>
            </form>
            <%
            	String orderId = request.getParameter("orderId");
            	if (orderId != null) {
                        List<Order> orders = new ArrayList<Order>();
                        OrderManager manager = new OrderManager();
                        if (orderId.equals("*")) {
                            orders = manager.getAllOrders(token);
                        } else {
                            Order order = manager.getOrder(orderId, token);
                            if (order != null) {
                                orders.add(order);
                            }
                        }


                        if (orders != null) {
                            for (Order order : orders) {
            %>
            	<table class="table">

            			<tr>
            				<th>Order Id</th>
                                        <td><b><%=order.getOrderId()%></b></td>
            			</tr>
            			<tr>
            				<th>Pizza Type</th>
            				<td><%=order.getPizzaType()%></td>
            			</tr>
            			<tr>
            				<th>Quantity</th>
            				<td><%=order.getQuantity()%></td>
            			</tr>
            			<tr>
            				<th>Status</th>
            				<td><%=order.isDelivered() ? "Delivered" : "Pending"%></td>
            			</tr>
            		
            	</table>
            <%
                    }
            		} else {
            %>
            <p>Invalid order ID</p>
            <%			
            		}
            	}
            %>
        </div>
    </div>

</div>
<!-- /container -->


</body>
</html>
