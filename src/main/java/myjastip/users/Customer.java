package myjastip.users;

import myjastip.location.Location;
import myjastip.payment.Order;
import myjastip.payment.Payable;
import myjastip.payment.Payment;
import myjastip.storage.Cart;
import myjastip.storage.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Customer extends User implements Payable {
	private String address;

	private Cart cart = new Cart();
	private Location orderLocation = new Location();
	private ArrayList<Payment> paymentHistory;
	private ArrayList<Item> listItem;
        private HashMap<String, String> orderStatus;
        

	public Customer() {
            super();
            this.cart = new Cart();
            this.orderLocation = new Location();
            this.paymentHistory = new ArrayList<>();
            this.listItem = new ArrayList<>();
            this.orderStatus = new HashMap<>();
        }

	public Customer(String userId, String name, String email, String password, String phoneNumber, String address) {
		super(userId, name, email, password, phoneNumber);
		this.address = address;
		this.cart = new Cart();
		this.orderLocation = new Location();
                this.paymentHistory = new ArrayList<>();
                this.listItem = new ArrayList<>();
                this.orderStatus = new HashMap<>();
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
            if (orderStatus.containsKey(String.valueOf(orderId))) {

                orderStatus.put(String.valueOf(orderId),"REFUND");

                System.out.println("Refund berhasil.");

            } else {

                System.out.println("Order tidak ditemukan.");
            }
	}

	@Override
	public ArrayList<Payment> getPaymentHistory() {
		return paymentHistory;
	}

	public ArrayList<Item> searchItem(String keyword) {
            ArrayList<Item> hasilCari = new ArrayList<>();
            for(Item item : listItem ){
                if (item.getItemName().toLowerCase().contains(keyword.toLowerCase())) {

                    hasilCari.add(item);
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

	public void createOrder(String orderId) {

        if (cart == null) {

            System.out.println("Keranjang kosong.");

            return;
        }

            orderStatus.put(orderId, "DIBUAT");
        }

        public void cancelOrder(String orderId) {

            if (orderStatus.containsKey(orderId)) {

                orderStatus.put(orderId, "DIBATALKAN");

        } else {

                System.out.println("Order tidak ditemukan.");
            }
        }
        
        public void showOrderHistory() {

            for (Map.Entry<String, String> order :orderStatus.entrySet()) {

                System.out.println("Order ID : " + order.getKey() + " | Status : " + order.getValue());
            }
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
}
