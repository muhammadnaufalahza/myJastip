package myjastip.users;

import myjastip.db.DatabaseUtil;
import myjastip.location.InvalidCoordinateException;
import myjastip.location.Location;
import myjastip.payment.*;
import myjastip.storage.Cart;
import myjastip.storage.CartItem;
import myjastip.storage.Item;

import java.util.List;
import java.util.UUID;

import java.util.ArrayList;

public class Customer extends User implements Payable {

	private Cart cart;
	private Location orderLocation;
	private List<EscrowPayment> paymentHistory;
	private final List<Order> orders;

	public Customer(String userId, String name, String email, String password, String phoneNumber, double balance) {
		super(userId, name, email, password, phoneNumber, balance);
		this.cart = new Cart();
		this.paymentHistory = new ArrayList<>();
		this.orders = new ArrayList<>();
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
	public List<EscrowPayment> getPaymentHistory() {
		paymentHistory.clear();
		ArrayList<EscrowPayment> tempPayments = new ArrayList<>();
		DatabaseUtil.insertPaymentArray(tempPayments);
		tempPayments.stream()
				.filter(payment -> DatabaseUtil.getOrder(payment.getOrderId()).getReceiverId().equals(userId))
				.forEach(payment -> paymentHistory.add(payment));

		return paymentHistory;
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

	public Cart getCart() {
		return cart;
	}

	public void setOrderLocation(String location, double latitude, double longitude) {
		if ((latitude < -90 || latitude > 90) && (longitude < -180 || longitude > 180)) {
			throw new InvalidCoordinateException("Koordinat tidak valid!");
		}
		if (latitude < -90 || latitude > 90) {
			throw new InvalidCoordinateException("Latitude tidak valid!");
		}
		if (longitude < -180 || longitude > 180) {
			throw new InvalidCoordinateException("Longitude tidak valid!");
		}
		this.orderLocation = new Location(location, latitude, longitude);
	}

	public List<Order> getOrders() {
		DatabaseUtil.insertOrdersByReceiverId(orders, userId);
		return orders;
	}



}
