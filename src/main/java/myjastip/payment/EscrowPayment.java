package myjastip.payment;

import myjastip.db.DatabaseUtil;
import myjastip.users.User;

public class EscrowPayment {
    protected String paymentId;
    protected String orderId;
    protected double amount;
    protected PaymentStatus status;

    public EscrowPayment(String paymentId, String orderId, double amount) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.amount = amount;
        this.status = PaymentStatus.UNFINISHED;

    }
    public EscrowPayment(String paymentId, String orderId, double amount, PaymentStatus status) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
    }

    public void processPayment(double amount) {
        Order order = DatabaseUtil.getOrder(orderId);
        User user = DatabaseUtil.getUser(order.getReceiverId());
        DatabaseUtil.changeUserBalance(user.getUserId(), user.getBalance() - amount);
        DatabaseUtil.changePaymentStatus(paymentId, PaymentStatus.HELD);
        user.setBalance(user.getBalance() - amount);
        status = PaymentStatus.HELD;
        System.out.println("Pembayaran sebesar " + amount + " berhasil diproses.");
    }

    public String getReceipt() {
        return "ID Pembayaran: " + paymentId + "  Total: " + amount + "  Status: " + status;
    }

    public void releaseFunds() {
        Order order = DatabaseUtil.getOrder(orderId);
        User user = DatabaseUtil.getUser(order.getJastiperId());
        DatabaseUtil.changeUserBalance(user.getUserId(), user.getBalance() + amount);
        DatabaseUtil.changePaymentStatus(paymentId, PaymentStatus.RELEASED);
        user.setBalance(user.getBalance() + amount);
        status = PaymentStatus.RELEASED;
        System.out.println("Sistem Escrow: Dana sebesar " + this.amount + " di lepas");
    }

    public void refundFunds() {
        Order order = DatabaseUtil.getOrder(orderId);
        User user = DatabaseUtil.getUser(order.getReceiverId());
        DatabaseUtil.changeUserBalance(user.getUserId(), user.getBalance() + amount);
        DatabaseUtil.changePaymentStatus(paymentId, PaymentStatus.REFUNDED);
        user.setBalance(user.getBalance() + amount);
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

}
