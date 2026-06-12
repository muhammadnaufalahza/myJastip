package myjastip.users;
import myjastip.users.InvalidRatingException;

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
        if(!isAvailable){
            System.out.println("Jastiper sedang tidak tersedia");
            return;
        }
        
        System.out.println("Pesanan " + orderId + " berhasil diterima");  
    }

    public void rejectOrder(String orderId) {
        System.out.println("Pesanan " + orderId + " berhasil ditolak");
    }
    public void requestVerify() {
        System.out.println("Permintaan aktivasi telah dikirim ke admin");
    }

    public void setRating(double rating) throws InvalidRatingException {
        if ( rating < 1 || rating > 5){
            throw new InvalidRatingException("Rating harus antara 1 sampai 5");
        }
        this.rating = rating;
    }
}

