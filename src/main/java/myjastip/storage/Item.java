package myjastip.storage;

import java.sql.Array;
import java.util.ArrayList;

public class Item {
	private String itemId;
	private String itemName;
	private String description;
	private double basePrice;
	private String storeLocationName;
	private ArrayList<String> categories;
	private String imageUrl;

	public Item(String itemId, String itemName, String description, double basePrice, String storeLocationName, ArrayList<String> categories, String imageUrl) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.description = description;
		this.basePrice = basePrice;
		this.storeLocationName = storeLocationName;
		this.categories = categories;
		this.imageUrl = imageUrl;
	}

	public String getItemDetails() {
		return String.format(
			"Id Item     : %s\n" +
			"Nama Item   : %s\n" +
			"Deskripsi   : %s\n" +
			"Harga       : %s\n" +
			"Lokasi Toko : %s\n" +
			"Kategori    : %s\n",

			itemId,
			itemName,
			description,
		 	basePrice,
		 	storeLocationName,
			categories
		);

	}

	public double getBasePrice() {
		return basePrice;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}

	public String getStoreLocationName() {
		return storeLocationName;
	}

	public void setStoreLocationName(String storeLocationName) {
		this.storeLocationName = storeLocationName;
	}

	public ArrayList<String> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<String> categories) {
		this.categories = categories;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
