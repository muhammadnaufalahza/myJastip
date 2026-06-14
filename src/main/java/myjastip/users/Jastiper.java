package myjastip.users;

import myjastip.db.DatabaseUtil;
import myjastip.payment.Order;
import myjastip.payment.OrderStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Jastiper extends User {

    private double rating = -1;
    private boolean isVerified;
    private ArrayList<Order> acceptedOrders;

    public Jastiper(String userId, String name, String email, String password, String phoneNumber, double balance) {
        super(userId, name, email, password, phoneNumber, balance);
        this.isVerified = false;
        this.acceptedOrders = new ArrayList<>();
    }

    public Jastiper(String userId, String name, String email, String password, String phoneNumber, double balance, ArrayList<Order> acceptedOrders) {
        super(userId, name, email, password, phoneNumber, balance);
        this.isVerified = false;
        this.acceptedOrders = acceptedOrders;
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

    public void requestVerify() {
        if (isVerified) {
            System.out.println("Akun sudah terverifikasi");
        } else {
            System.out.println("Permintaan verifikasi dikirim");
        }
    }

    public void setRating(double rating) {
        try {
            if (rating < 1 || rating > 5) {
                throw new IllegalArgumentException("Rating harus antara 1 - 5");
            }
            this.rating = rating;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addRating(double newRating) {
        setRating(newRating);
    }

    public void showAcceptedOrders() {
        for (Order order : acceptedOrders) {
            System.out.println(order);
        }
    }

    public double getRating() {
        return rating;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public ArrayList<Order> getAcceptedOrders() {
        DatabaseUtil.insertOrdersByJastiperId(acceptedOrders, userId);
        return acceptedOrders;
    }

    public void setAcceptedOrders(ArrayList<Order> acceptedOrders) {
        this.acceptedOrders = acceptedOrders;
    }
}