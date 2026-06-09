package myjastip;

import javafx.application.Application;
import myjastip.app.MyJastipWindow;
import myjastip.db.DatabaseUtil;
import myjastip.storage.Item;
import myjastip.users.User;

import java.sql.Connection;
import java.util.ArrayList;

/*

VM Arguments untuk JavaFX:
--module-path "D:\Libs\openjfx-26.0.1_windows-x64_bin-sdk\javafx-sdk-26.0.1\lib" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics

*/

public class MyJastip {
	public static void main(String[] args) throws Exception {

 		try {
			Connection connection = DatabaseUtil.getConnection();

			ArrayList<Item> items = new ArrayList<>();
			ArrayList<User> users = new ArrayList<>();

			DatabaseUtil.insertItems(items, connection);

			for (Item i : items) {
				System.out.println(i.getItemDetails());
			}

			DatabaseUtil.insertUsers(users, connection);

			for (User u : users) {
				System.out.println(u.toString());
			}
		} catch (Exception e) {
			System.out.println("Gagal menghubungkan Database");
		}

		Application.launch(MyJastipWindow.class, args); // Untuk membuka JavaFx


		System.exit(0);


        }


}
