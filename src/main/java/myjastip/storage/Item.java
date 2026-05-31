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

	public Item(String itemId, String itemName, String description, double basePrice, String storeLocationName, ArrayList<String> categories) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.description = description;
		this.basePrice = basePrice;
		this.storeLocationName = storeLocationName;
		this.categories = categories;
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


}
