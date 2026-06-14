package myjastip.users;

import myjastip.db.DatabaseUtil;
import myjastip.location.Location;
import myjastip.payment.*;
import myjastip.storage.Cart;
import myjastip.storage.CartItem;
import myjastip.storage.Item;

import java.util.UUID;

import java.util.ArrayList;

public class Customer extends User implements Payable {

	private Cart cart;
	private Location orderLocation;
	private ArrayList<EscrowPayment> paymentHistory;
	private ArrayList<Order> orders;

	public Customer(String userId, String name, String email, String password, String phoneNumber, double balance) {
		super(userId, name, email, password, phoneNumber, balance);
		this.cart = new Cart();
		this.orderLocation = new Location();
		this.paymentHistory = new ArrayList<>();
		this.orders = new ArrayList<>();
	}

	public Customer(String userId, String name, String email, String password, String phoneNumber, double balance, Cart cart, Location orderLocation, ArrayList<EscrowPayment> paymentHistory, ArrayList<Order> orders) {
		super(userId, name, email, password, phoneNumber, balance);
		this.cart = cart;
		this.orderLocation = orderLocation;
		this.paymentHistory = paymentHistory;
		this.orders = orders;
	}

	@Override
	public void pay(EscrowPayment payment) throws InsufficientBalanceException {
		if (balance < payment.getAmount()) {
			throw new InsufficientBalanceException("Saldo anda belum cukup untuk membayar pembayaran ini!");
		} else {
			payment.processPayment(payment.getAmount());
			System.out.println("Pembayaran berhasil.");
		}
	}

	@Override
	public ArrayList<EscrowPayment> getPaymentHistory() {
		ArrayList<EscrowPayment> tempPayments = new ArrayList<>();
		DatabaseUtil.insertPaymentArray(paymentHistory);
		paymentHistory.stream()
				.filter(payment -> DatabaseUtil.getOrder(payment.getOrderId()).getReceiverId().equals(userId));
		return paymentHistory;
	}

	public ArrayList<Item> searchItem(String keyword) {
		ArrayList<Item> hasilCari = new ArrayList<>();
		for (CartItem cartItem : cart.getCartItems()){
			if (cartItem.getItem().getItemName().toLowerCase().contains(keyword.toLowerCase())) {

				hasilCari.add(cartItem.getItem());
			}
		}
		return hasilCari;
	}

	public void addToCart(Item item, int qty) {
		try {
			if (qty <= 0) {
				throw new IllegalArgumentException("Jumlah barang harus lebih dari 0");
			}
			cart.addItem(item, qty);

		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public Order createOrder() throws EmptyOrderException{

		if (!cart.isCartEmpty()) {
			UUID uuid = UUID.randomUUID();
			Order order = new Order(
					uuid.toString(),
					OrderStatus.PENDING,
					orderLocation,
					cart.calculateTotalPrice(),
					calculateTransporationFee(), calculateServiceFee(),
					userId,
					cart
			);
			DatabaseUtil.insertOrder(order);
			cart.emptyCart();
			System.out.println("Pesanan telah dibuat");
			return order;
		} else {
			throw new EmptyOrderException("Pesanan Kosong");
		}

	}

	public double calculateTransporationFee() {
		return cart.calculateTotalPrice() * 0.1;
	}

	public double calculateServiceFee() {
		return cart.calculateTotalPrice() * 0.05;
	}

	public double calculateFinalPrice() {
		return cart.calculateTotalPrice() + calculateTransporationFee() + calculateServiceFee();
	}


	public void cancelOrder(Order order) {
		DatabaseUtil.changeOrderStatus(order.getOrderId(), OrderStatus.CANCELLED);
		order.setOrderStatus(OrderStatus.CANCELLED);
		EscrowPayment payment = DatabaseUtil.getPaymentByOrderId(order.getOrderId());

		if (payment.getStatus() != PaymentStatus.UNFINISHED) {
			payment.refundFunds();
		} else {
			DatabaseUtil.changePaymentStatus(payment.getPaymentId(), PaymentStatus.CANCELLED);
		}
	}

	public void completeOrder(Order order) {
		DatabaseUtil.changeOrderStatus(order.getOrderId(), OrderStatus.COMPLETED);
		order.setOrderStatus(OrderStatus.COMPLETED);

		EscrowPayment payment = DatabaseUtil.getPaymentByOrderId(order.getOrderId());
		payment.releaseFunds();
	}

	public void rate(Jastiper service, int value) {
		try {
			if (value < 1 || value > 5) {
				throw new IllegalArgumentException("Rating harus 1 - 5");
			}
			service.addRating(value);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}


	public Cart getCart() {
		return cart;
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

	public void setPaymentHistory(ArrayList<EscrowPayment> paymentHistory) {
		this.paymentHistory = paymentHistory;
	}

	public ArrayList<Order> getOrders() {
		DatabaseUtil.insertOrdersByReceiverId(orders, userId);
		return orders;
	}

}
