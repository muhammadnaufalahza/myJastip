package myjastip;

import javafx.application.Application;
import myjastip.app.MyJastipWindow;
import myjastip.db.DatabaseUtil;
import myjastip.payment.Order;
import myjastip.storage.Item;
import myjastip.users.User;

import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

/*

VM Arguments untuk JavaFX:
--module-path "D:\Libs\openjfx-26.0.1_windows-x64_bin-sdk\javafx-sdk-26.0.1\lib" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics

*/

public class MyJastip {
	public static void main(String[] args) throws Exception {

		Scanner sc = new Scanner(System.in);
		System.out.print("Apakah anda ingin membuka aplikasi JavaFX? pastikan sudah setup JavaFX! [Y] ");
		String in = sc.nextLine();

		if (in.equals("y") || in.equals("Y")) {
			Application.launch(MyJastipWindow.class, args); // Untuk membuka JavaFx
		}


		System.exit(0);


        }


}
