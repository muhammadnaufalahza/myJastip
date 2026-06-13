package myjastip.storage;

import java.util.ArrayList;

public class Cart {
	private ArrayList<CartItem> cartItems = new ArrayList<>();

	public void addItem(Item item, int qty) {
		cartItems.add(new CartItem(item, qty));
	}
	
	public void removeItem(Item item) {
		cartItems.removeIf(i -> i.getItem().equals(item));
	}
	public void removeItem(CartItem item) {
		cartItems.removeIf(i -> i.equals(item));
	}

	public double calculateTotalPrice() {
		double total = 0.0;
		for (CartItem c : cartItems) {
			total += c.getSubTotal();
		}
		return total;
	}
	
	public void emptyCart() {
		cartItems.clear();
	}

	public boolean isCartEmpty() {
		return cartItems.isEmpty();
	}

	public ArrayList<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(ArrayList<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
}
