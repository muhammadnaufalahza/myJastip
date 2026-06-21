package myjastip.users;

import myjastip.db.DatabaseUtil;
import myjastip.storage.Item;

public class Admin extends User {
    public Admin(String userId, String name, String email, String password, String phoneNumber, double balance) {
        super(userId, name, email, password, phoneNumber, balance);
    }

    public void editItem(Item item) {
        DatabaseUtil.changeItem(item.getItemId(), item.getItemName(), item.getDescription(), item.getBasePrice(), item.getStoreLocationName(), item.getCategoriesAsString(), item.getImageUrl());

    }
    public void addItem(Item item) {
        DatabaseUtil.insertItem(item);
    }

    public void editUser(Item item) {

    }
    public void addUser(Item item) {

    }

    public void removeOrderAndPayment() {

    }
}
