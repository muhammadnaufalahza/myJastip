package myjastip;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MyJastip extends Application {

	@Override
	public void start(Stage stage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(createContent(),400,400);
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private Parent createContent() {
        return new StackPane(new Text("Sistem Jastip dari NetBeans!!!"));
    }
	
	public static void main(String[] args) throws Exception {				
//		DatabaseUtil.testDB();
//		Perlu PostgreSQL
		
		Application.launch(MyJastip.class, args);
		
		
	}

}
