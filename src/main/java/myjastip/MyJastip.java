package myjastip;


import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import myjastip.storage.Item;
import myjastip.users.User;

import java.util.ArrayList;

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

	public static void main(String[] args) {	
		ArrayList<Item> items = new ArrayList<>();
		ArrayList<User> users = new ArrayList<>();

		DatabaseUtil.insertItems(items);

		for (Item i : items) {
			System.out.println(i.getItemDetails());
		}

//		Application.launch(MyJastip.class, args); // Untuk membuka JavaFx

        }

}
