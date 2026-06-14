package myjastip.payment;

import myjastip.db.DatabaseUtil;
import myjastip.users.User;

import java.time.Instant;

public class EscrowPayment {
    protected String paymentId;
    protected String orderId;
    protected double amount;
    protected PaymentStatus status;
    protected String updatedAt;

    public EscrowPayment(String paymentId, String orderId, double amount) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.amount = amount;
        this.status = PaymentStatus.UNFINISHED;
        this.updatedAt = Instant.now().toString();
    }
    public EscrowPayment(String paymentId, String orderId, double amount, PaymentStatus status, String updatedAt) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
        this.updatedAt = updatedAt;
    }

    public void processPayment(double amount) {
        Order order = DatabaseUtil.getOrder(orderId);
        User user = DatabaseUtil.getUser(order.getReceiverId());
        DatabaseUtil.changeUserBalance(user.getUserId(), user.getBalance() - amount);
        DatabaseUtil.changePaymentStatus(paymentId, PaymentStatus.HELD);
        user.setBalance(user.getBalance() - amount);
        System.out.println("Pembayaran sebesar " + amount + " berhasil diproses.");
    }

    public String getReceipt() {
        return "ID Pembayaran: " + paymentId + "  Total: " + amount + "  Status: " + status;
    }

//    public void cancelPayment() {
//    //        this.status = "CANCEL";
//        System.out.println("Pembayaran dengan ID " + paymentId + " telah dibatalkan.");
//    }

    public void holdFunds(){
        status = PaymentStatus.HELD;
        System.out.println("Sistem Escrow: Dana sebesar " + this.amount + " ditahan");
    }

    public void releaseFunds(){
        status = PaymentStatus.RELEASED;
        System.out.println("Sistem Escrow: Dana sebesar " + this.amount + " di lepas");
    }
    public void refundFunds(){
        status = PaymentStatus.REFUNDED;
        System.out.println("Sistem Escrow: Transaksi batal. Dana dikembalikan ke Customer.");
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
