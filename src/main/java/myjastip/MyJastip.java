package myjastip;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MyJastip extends Application {

	@Override
	public void start(Stage stage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {				
		System.out.println("hello world");
		
		Application.launch(MyJastip.class, args);
		
	}

}
