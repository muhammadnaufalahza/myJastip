package myjastip.payment;

import java.time.Instant;

public class Escrow extends Payment {
    private String transactionId;

    public Escrow(String paymentId, String orderId, double amount, String transactionId) {
        super(paymentId, orderId, amount);
        this.transactionId = transactionId;
    }

    public Escrow(String paymentId, String orderId, double amount, PaymentStatus status, String updatedAt, String transactionId) {
        super(paymentId, orderId, amount, status, updatedAt);
        this.transactionId = transactionId;
    }

    public void holdFunds(){
        status = PaymentStatus.HELD;
        System.out.println("Sistem Escrow: Dana sebesar " + this.amount + " ditahan untuk transaksi ID: " + this.transactionId);
    }

    public void releaseFunds(){
        status = PaymentStatus.RELEASED;
        System.out.println("Sistem Escrow: Dana sebesar " + this.amount + " di lepas untuk transaksi ID: " + this.transactionId);
    }
    public void refundFunds(){
        status = PaymentStatus.REFUNDED;
        System.out.println("Sistem Escrow: Transaksi batal. Dana dikembalikan ke Customer.");
    }
}
