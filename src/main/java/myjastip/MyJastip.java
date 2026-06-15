package myjastip;

import javafx.application.Application;
import myjastip.app.MyJastipWindow;

import java.awt.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/*

VM Arguments untuk JavaFX:
--module-path "D:\Libs\openjfx-26.0.1_windows-x64_bin-sdk\javafx-sdk-26.0.1\lib" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics

*/

public class MyJastip {
	public static void main(String[] args) {
		try {
			Application.launch(MyJastipWindow.class, args); // Untuk membuka aplikasi JavaFX
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
