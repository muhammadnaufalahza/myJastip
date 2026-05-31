package myjastip.payment;

public class Escrow extends Payment {
    private String transactionId;
    private double amount;
    private String status;

    public void holdFunds(){
        this.status = "Tahan";
        System.out.println("Sistem Escrow: Dana sebesar " + this.amount + " ditahan untuk transaksi ID: " + this.transactionId);
    }

    public void releaseFunds(){
        this.status = "Lepas";
        System.out.println("Sistem Escrow: Dana sebesar " + this.amount + " di lepas untuk transaksi ID: " + this.transactionId);
    }
    public void refundFunds(){
        this.status = "Refund";
        System.out.println("Sistem Escrow: Transaksi batal. Dana dikembalikan ke Customer.");
    }
}
