package myjastip.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Jastiper extends User {

    private double rating;
    private boolean isVerified;
    private boolean isAvailable;
    private ArrayList<String> acceptedOrders;
    private HashMap<String, String> orderStatus;

    public Jastiper() {
        super();
        this.acceptedOrders = new ArrayList<>();
        this.orderStatus = new HashMap<>();
    }

    public Jastiper(String userId, String name, String email,String password, String phoneNumber,double rating, boolean isVerified,boolean isAvailable) {

        super(userId, name, email, password, phoneNumber);

        this.rating = rating;
        this.isVerified = isVerified;
        this.isAvailable = isAvailable;

        this.acceptedOrders = new ArrayList<>();
        this.orderStatus = new HashMap<>();
    }

    public void acceptOrder(String orderId) {

        try {

            if (!isVerified) {
                throw new Exception("Jastiper belum terverifikasi");
            }

            if (!isAvailable) {
                throw new Exception("Jastiper sedang tidak tersedia");
            }

            acceptedOrders.add(orderId);

            orderStatus.put(orderId, "DITERIMA");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    public void rejectOrder(String orderId) {

        orderStatus.put(orderId, "DITOLAK");
    }

    public void updateOrderStatus(String orderId,
                                  String status) {

        if (orderStatus.containsKey(orderId)) {

            orderStatus.put(orderId, status);

        } else {

            System.out.println("Order tidak ditemukan");
        }
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

        for (String orderId : acceptedOrders) {

            System.out.println(orderId);
        }
    }

    public void showOrderStatus() {

        for (Map.Entry<String, String> order :orderStatus.entrySet()) {

            System.out.println("Order ID : " + order.getKey() + " | Status : " + order.getValue());
        }
    }

    public double getRating() {
        return rating;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}