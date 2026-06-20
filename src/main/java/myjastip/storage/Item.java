package myjastip.storage;

import java.util.ArrayList;
import java.util.List;

public class Item {
	private String itemId;
	private String itemName;
	private String description;
	private double basePrice;
	private String storeLocationName;
	private List<String> categories;
	private String imageUrl;

	public Item(String itemId, String itemName, String description, double basePrice, String storeLocationName, List<String> categories, String imageUrl) {
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

	public List<String> getCategories() {
		return categories;
	}

	public String getCategoriesAsString() {
		List<String> arrCategories = new ArrayList<>();
		for (String category : categories) {
			arrCategories.add(String.format("'%s'", category));
		}
		String categoryString = String.join(",", arrCategories);
		return "[" + categoryString + "]";
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
