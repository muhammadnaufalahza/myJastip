package myjastip.users;

public class Jastiper extends User {
    private double rating;
    private boolean isVerified;
    private boolean isAvailable;

    public Jastiper() {
        super();
    }

    public Jastiper(String userId, String name, String email, String password, String phoneNumber, double rating, boolean isVerified, boolean isAvailable) {
        super(userId, name, email, password, phoneNumber);
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

