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
	private String address;

	private Cart cart;
	private Location orderLocation;
	private ArrayList<Payment> paymentHistory;
	private ArrayList<Order> orders;

	public Customer() {
		super();
	}

	public Customer(String userId, String name, String email, String password, String phoneNumber, double balance, String address, Cart cart, Location orderLocation, ArrayList<Payment> paymentHistory, ArrayList<Order> orders) {
		super(userId, name, email, password, phoneNumber, balance);
		this.address = address;
		this.cart = cart;
		this.orderLocation = orderLocation;
		this.paymentHistory = paymentHistory;
		this.orders = orders;
	}

	@Override
	public void pay(Payment payment) throws InsufficientBalanceException {
		if (balance < payment.getAmount()) {
			throw new InsufficientBalanceException("Saldo anda belum cukup untuk membayar pembayaran ini!");
		}


		payment.processPayment(payment.getAmount());
		paymentHistory.add(payment);
		System.out.println("Pembayaran berhasil.");

	}

	@Override
	public void refund(Order order) {

	}

	@Override
	public ArrayList<Payment> getPaymentHistory() {
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
					new Location(orderLocation.getLocationName(), orderLocation.getLatitude(), orderLocation.getLongitude()),
					cart.calculateTotalPrice(),
					cart.calculateTotalPrice() * 0.1, 20_000.0,
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



	public void cancelOrder(Order order) {
		DatabaseUtil.changeOrderStatus(order.getOrderId(), OrderStatus.CANCELLED);
		order.setOrderStatus(OrderStatus.CANCELLED);
	}

	public void completeOrder(Order order) {
		DatabaseUtil.changeOrderStatus(order.getOrderId(), OrderStatus.COMPLETED);
		order.setOrderStatus(OrderStatus.COMPLETED);
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

	public String getAddress() {
		return address;
	}

	public Cart getCart() {
		return cart;
	}

	public void setAddress(String address) {
            this.address = address;
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

	public ArrayList<Order> getOrders() {
		DatabaseUtil.insertOrdersByReceiverId(orders, userId);
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
}
