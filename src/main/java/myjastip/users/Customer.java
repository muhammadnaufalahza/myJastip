package myjastip.users;

import myjastip.location.Location;
import myjastip.payment.Order;
import myjastip.payment.Payable;
import myjastip.payment.Payment;
import myjastip.storage.Cart;
import myjastip.storage.Item;

import java.util.ArrayList;

public class Customer extends User implements Payable {
	private String address;
	private Cart cart = new Cart();
	private Location orderLocation = new Location();
	private ArrayList<Payment> paymentHistory = new ArrayList<>();

	public Customer() {
		super();
	}

	public Customer(String userId, String name, String email, String password, String phoneNumber, String address) {
		super(userId, name, email, password, phoneNumber);
		this.address = address;
		this.cart = new Cart();
		this.orderLocation = new Location();
		this.paymentHistory = new ArrayList<>();
	}

	@Override
	public void payment(double amount) {
            Payment payment = new Payment();
            payment.processPayment(amount, "Sukses");
            payment.getReceipt();
            paymentHistory.add(payment);
	}

	@Override
	public void refund(long orderId) {
            System.out.println("Refund Order : " + orderId);
	}

	@Override
	public ArrayList<Payment> getPaymentHistory() {
		return paymentHistory;
	}

	public void addToCart(Item item, int qty) {
		cart.addItem(item, qty);
                System.out.println("Nama Barang = " + item.getItemName());
                System.out.println("Jumlah barang = " + qty);
                System.out.println("Berhasil dimasukan ke Keranjang Belanja");
	}

	public void cancelOrder(String orderId) {

	}

//	public Order createOrder() {
//		return new Order();
//	}

	public void rate(Jastiper service, int value) {

	}

	public String getAddress() {
		return address;
	}

	public Cart getCart() {
		return cart;
	}

	public void setAddress(String address) {
            this.address = address;
	}

	public void confirmReceipt(String orderId) {

	}

	public void pay(double amount) {
            Payment payment = new Payment();
            payment.processPayment(amount, "Sukses");
            paymentHistory.add(payment);
	}

	public void refund(String orderld) {
            System.out.println("Refund order : " + orderld);
	}


	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Location getOrderLocation() {
		return orderLocation;
	}

	public void setOrderLocation(Location orderLocation) {
		this.orderLocation = orderLocation;
	}

	public void setPaymentHistory(ArrayList<Payment> paymentHistory) {
		this.paymentHistory = paymentHistory;
	}
}
