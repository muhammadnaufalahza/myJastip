package myjastip.users;

import myjastip.payment.Order;
import myjastip.payment.OrderStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Jastiper extends User {

    private double rating = -1;
    private boolean isVerified;
    private ArrayList<Order> acceptedOrders;

    public Jastiper() {
        super();
    }

    public Jastiper(String userId, String name, String email, String password, String phoneNumber, ArrayList<Order> acceptedOrders) {
        super(userId, name, email, password, phoneNumber);
        this.isVerified = false;
        this.acceptedOrders = acceptedOrders;
    }

    public void acceptOrder(Order order) {
        try {
//            if (!isVerified) {
//                throw new Exception("Jastiper belum terverifikasi");
//            }
            acceptedOrders.add(order);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void rejectOrder(Order order) {

    }

    public void updateOrderStatus(Order order, OrderStatus status) {
        order.setOrderStatus(status);
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

    public void showOrderStatus() {
//        for (Map.Entry<String, String> order :orderStatus.entrySet()) {
//            System.out.println("Order ID : " + order.getKey() + " | Status : " + order.getValue());
//        }
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

}