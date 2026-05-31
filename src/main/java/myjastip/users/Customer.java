package myjastip.users;

import myjastip.payment.Order;
import myjastip.payment.Payable;
import myjastip.payment.Payment;
import myjastip.storage.Cart;
import myjastip.storage.Item;

import java.util.ArrayList;

public class Customer extends User implements Payable {
	private String address;
	private Cart cart;


	@Override
	public void payment(double amount) {

	}

	@Override
	public void refund(long orderId) {

	}

	@Override
	public ArrayList<Payment> getPaymentHistory() {
		return null;
	}

	public ArrayList<Item> searchItem(String keyword) {
		return new ArrayList<>();
	}

	public void addToCart(Item item, int qty) {

	}

	public void cancelOrder(String orderld) {

	}

	public Order createOrder() {
		return new Order();
	}

	public void rate(Jastiper service, int value) {

	}

	public void getAddress(String address) {

	}

	public void setAddress(String address) {

	}

	public void confirmReceipt(String orderld) {

	}

	public void pay(double amount) {

	}

	public void refund(String orderld) {

	}
}
