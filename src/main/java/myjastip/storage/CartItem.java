package myjastip.storage;

public class CartItem {
	private Item item;
	private int quantity;

	public CartItem(Item item, int quantity) {
		this.item = item;
		this.quantity = quantity;;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getSubTotal() {
		return item.getBasePrice() * quantity;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void addQuantity(int qty) {
		this.quantity += qty;
	}

	public void subtractQuantity(int qty) {
		this.quantity -= qty;
	}

}