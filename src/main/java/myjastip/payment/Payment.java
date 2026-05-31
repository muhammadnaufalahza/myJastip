package myjastip.payment;

public class Payment {
    protected String paymentId;
    protected double amount;
    protected String status;
    protected String createdAt;

    public void processPayment(double amount,String status){
        this.amount = amount;
        this.status = "Sukses";
        System.out.println("Pembayaran sebesar " + amount + " berhasil diproses.");
}

    public String getReceipt() {
        return "ID Pembayaran: " + paymentId + "  Total: " + amount + "  Status: " + status;
    }

    public void cancelPayment() {
        this.status = "CANCEL";
        System.out.println("Pembayaran dengan ID " + paymentId + " telah dibatalkan.");
    }
}
