package myjastip;


import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import myjastip.storage.Item;
import myjastip.users.Customer;
import myjastip.users.User;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

/*
VM Arguments untuk JavaFX:
--module-path "D:\Libs\openjfx-26.0.1_windows-x64_bin-sdk\javafx-sdk-26.0.1\lib" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics
*/


public class MyJastip extends Application {
	private Parent createContent() {
		return new StackPane(new Text("Sistem Jastip"));
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setScene(new Scene(createContent(), 300, 300));
		stage.show();
	}




	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.print("Masukkan Password Supabase: ");

		final String PASSWORD = sc.nextLine();

		Connection connection = DatabaseUtil.getConnection(PASSWORD);

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

		users.get(0).login();

		System.exit(0);

//		Application.launch(MyJastip.class, args); // Untuk membuka JavaFx

        }


}
