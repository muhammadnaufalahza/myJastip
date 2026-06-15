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
//		Scanner sc = new Scanner(System.in);
//		startMenu();

		try {
			Application.launch(MyJastipWindow.class, args); // Untuk membuka aplikasi JavaFX
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

//		int in = -1;
//		do {
//			try {
//				System.out.print("Masukkan input: ");
//				in = sc.nextInt();
//				switch (in) {
//					case 1:
//						try {
//							Application.launch(MyJastipWindow.class, args); // Untuk membuka aplikasi JavaFX
//						} catch (Exception e) {
//							System.out.println("Error: " + e.getMessage());
//						}
//						break;
//					case 2:
//						break;
//					case 0:
//						System.out.println("Keluar dari program...");
//						System.exit(0);
//					default:
//						throw new InputMismatchException();
//				}
//			} catch (InputMismatchException e) {
//				System.out.println("Input tidak valid!");
//				in = -1;
//				sc.next();
//			}
//		} while (in == -1);
	}

	private static void startMenu() {
		System.out.println("-- Sistem Jasa Titip --");
		System.out.println("Pilihan menu:");
		System.out.println("1. Buka aplikasi JavaFX (Khusus Customer/Jastiper). Pastikan sudah setup JavaFX!");
		System.out.println("2. Buka menu ResolutionCenter di terminal");
		System.out.println("0. keluar dari program");
	}
}
