package myjastip.users;

import myjastip.db.DatabaseUtil;
import myjastip.location.Location;
import myjastip.payment.Order;
import myjastip.payment.OrderStatus;
import myjastip.payment.Payable;
import myjastip.payment.Payment;
import myjastip.storage.Cart;
import myjastip.storage.CartItem;
import myjastip.storage.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Customer extends User implements Payable {
	private String address;

	private Cart cart;
	private Location orderLocation;
	private ArrayList<Payment> paymentHistory;
	private ArrayList<Order> orders;

	public Customer() {
		super();
	}


	public Customer(String userId, String name, String email, String password, String phoneNumber, String address, Cart cart, Location orderLocation, ArrayList<Payment> paymentHistory, ArrayList<Order> orders) {
		super(userId, name, email, password, phoneNumber);
		this.address = address;
		this.cart = cart;
		this.orderLocation = orderLocation;
		this.paymentHistory = paymentHistory;
		this.orders = orders;
	}

	@Override
	public void payment(double amount) {
            try {

                if (amount <= 0) {
                    throw new IllegalArgumentException("Nominal pembayaran harus lebih dari 0");
                }

                Payment payment = new Payment();

                payment.processPayment(amount, "SUCCESS");

                paymentHistory.add(payment);

                System.out.println("Pembayaran berhasil.");

            } catch (IllegalArgumentException e) {

                System.out.println("Error : " + e.getMessage());
            }
	}

	@Override
	public void refund(long orderId) {

	}

	@Override
	public ArrayList<Payment> getPaymentHistory() {
		return paymentHistory;
	}

	public ArrayList<Item> searchItem(String keyword) {
            ArrayList<Item> hasilCari = new ArrayList<>();
            for(CartItem cartItem : cart.getCartItems()){
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

	public void createOrder() {
		// jangan ubah ini
		if (!cart.isCartEmpty()) {
			DatabaseUtil.insertOrder(
					OrderStatus.PENDING,
					orderLocation.getLocationName(),
					orderLocation.getLatitude(), orderLocation.getLongitude(),
					cart.calculateTotalPrice(), cart.calculateTotalPrice() * 0.1, 10_000.0,
					userId,
					cart
			);
			cart.emptyCart();
			System.out.println("Pesanan telah dibuat");
		} else {
			System.out.println("Pesanan Kosong");
		}

	}

	public void cancelOrder(Order order) {
		// jangan ubah ini
		DatabaseUtil.changeOrderStatus(order.getOrderId(), OrderStatus.CANCELLED);
		order.setOrderStatus(OrderStatus.CANCELLED);
//			DatabaseUtil.insertOrdersByReceiverId(orders, this.getUserId());
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
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
}
