package myjastip.payment;

import myjastip.location.Location;
import myjastip.storage.Cart;

public class Order {
	private String orderId;
	private OrderStatus orderStatus;
	private Location location;
	private double totalItemPrice;
	private double transportationFee;
	private double serviceFee;
	private String receiverId;
	private String jastiperId;

	private double totalBill;
	private Cart orderedCart;


	public Order(String orderId, OrderStatus orderStatus, Location location, double totalItemPrice, double transportationFee, double serviceFee, String receiverId, Cart orderedCart) {
		this.orderId = orderId;
		this.orderStatus = orderStatus;
		this.location = location;
		this.totalItemPrice = totalItemPrice;
		this.transportationFee = transportationFee;
		this.serviceFee = serviceFee;
		this.receiverId = receiverId;
		this.orderedCart = orderedCart;
		this.totalBill = this.totalItemPrice + this.transportationFee + this.serviceFee;
	}

	public Order(String orderId, OrderStatus orderStatus, Location location, double totalItemPrice, double transportationFee, double serviceFee, String receiverId, Cart orderedCart, String jastiperId) {
		this.orderId = orderId;
		this.orderStatus = orderStatus;
		this.location = location;
		this.totalItemPrice = totalItemPrice;
		this.transportationFee = transportationFee;
		this.serviceFee = serviceFee;
		this.receiverId = receiverId;
		this.orderedCart = orderedCart;
		this.jastiperId = jastiperId;
		this.totalBill = this.totalItemPrice + this.transportationFee + this.serviceFee;

	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
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
		this.totalBill = this.totalItemPrice + this.transportationFee + this.serviceFee;
		return totalBill;
	}

	public Cart getOrderedCart() {
		return orderedCart;
	}

	public void setOrderedCart(Cart orderedCart) {
		this.orderedCart = orderedCart;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getJastiperId() {
		return jastiperId;
	}

	public void setJastiperId(String jastiperId) {
		this.jastiperId = jastiperId;
	}
}
