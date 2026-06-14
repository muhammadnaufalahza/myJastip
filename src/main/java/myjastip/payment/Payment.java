package myjastip.payment;

public class Payment {
    protected String paymentId;
    protected double amount;
    protected String status;
    protected String createdAt;
    private String transactionId;

    public void processPayment(double amount,String status,String transactionId){
        this.transactionId = transactionId;
        this.amount = amount;
        this.status = status;
        System.out.println("Pembayaran sebesar " + amount + " berhasil diproses.");
    }

    public String getReceipt() {
        return "ID Pembayaran: " + paymentId + "  Total: " + amount + "  Status: " + status;
    }

   public void cancelPayment() {
    this.status = "CANCEL";
    System.out.println("Pembayaran dengan ID : " + paymentId + " telah dibatalkan.");
}
}
