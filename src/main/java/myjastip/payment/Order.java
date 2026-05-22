package myjastip.payment;

public class Order {
	private String orderId;
	private String orderStatus;
//	private Location location;
	private double totalItemPrice;
	private double transporationFee;
	private double serviceFree;
	private double totalBill;
	
	public double calculateTotalBill() {
		return 0.0;
	}
	
}
