package myjastip.storage;

public class CartItem {
	private Item item;
	private int quantity;
	private double subTotalPrice;

	public CartItem(Item item, int quantity) {
		this.item = item;
		this.quantity = quantity;
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
}
