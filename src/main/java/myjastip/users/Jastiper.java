package myjastip.users;

public class Jastiper {
    private double rating;
    private double isVerified;
    private double isAvailable;

    public Jastiper(double rating, double isVerified, double isAvailable) {
        this.rating = rating;
        this.isVerified = isVerified;
        this.isAvailable = isAvailable;
    }

    public void acceptOrder(String orderId) {
    }

    public void rejectOrder(String orderId) {

    }
    public void requestVerify() {

    }
}

