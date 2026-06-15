package myjastip.users;

import myjastip.db.DatabaseUtil;
import myjastip.payment.Order;
import myjastip.payment.OrderStatus;

import java.util.ArrayList;

public class Jastiper extends User {
    private final ArrayList<Order> acceptedOrders;

    public Jastiper(String userId, String name, String email, String password, String phoneNumber, double balance) {
        super(userId, name, email, password, phoneNumber, balance);
        this.acceptedOrders = new ArrayList<>();
    }

    public void acceptOrder(Order order) {
        try {
            updateOrderStatus(order, OrderStatus.OUT_FOR_DELIVERY);
            DatabaseUtil.addJastiperId(order.getOrderId(), userId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void finishDelivery(Order order) {
        try {
            acceptedOrders.remove(order);
            updateOrderStatus(order, OrderStatus.DELIVERED);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateOrderStatus(Order order, OrderStatus status) {
        order.setOrderStatus(status);
        DatabaseUtil.changeOrderStatus(order.getOrderId(), status);
    }

    public ArrayList<Order> getAcceptedOrders() {
        DatabaseUtil.insertOrdersByJastiperId(acceptedOrders, userId);
        return acceptedOrders;
    }

}