package myjastip.payment;

import myjastip.location.Location;
import myjastip.storage.Cart;

import java.util.HashMap;

public class Order {
	private String orderId;
	private String orderStatus;
	private Location location;
	private double totalItemPrice;
	private double transportationFee;
	private double serviceFee;
	private String recieverId;

	private double totalBill;
	private Cart orderedCart;

	public Order() {

	}

	public Order(String orderId, String orderStatus, Location location, double totalItemPrice, double transportationFee, double serviceFee, Cart orderedCart) {
		this.orderId = orderId;
		this.orderStatus = orderStatus;
		this.location = location;
		this.totalItemPrice = totalItemPrice;
		this.transportationFee = transportationFee;
		this.serviceFee = serviceFee;
		this.orderedCart = orderedCart;
	}

	public double calculateTotalBill() {
                totalBill = totalItemPrice + transportationFee + serviceFee;
		return totalBill;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public double getTotalItemPrice() {
		return totalItemPrice;
	}

	public void setTotalItemPrice(double totalItemPrice) {
		this.totalItemPrice = totalItemPrice;
	}

	public double getTransportationFee() {
		return transportationFee;
	}

	public void setTransportationFee(double transportationFee) {
		this.transportationFee = transportationFee;
	}

	public double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(double serviceFee) {
		this.serviceFee = serviceFee;
	}

	public double getTotalBill() {
		return totalBill;
	}

	public void setTotalBill(double totalBill) {
		this.totalBill = totalBill;
	}

	public Cart getOrderedCart() {
		return orderedCart;
	}

	public void setOrderedCart(Cart orderedCart) {
		this.orderedCart = orderedCart;
	}

	public String getRecieverId() {
		return recieverId;
	}

	public void setRecieverId(String recieverId) {
		this.recieverId = recieverId;
	}
}
