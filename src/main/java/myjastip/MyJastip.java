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
        return new StackPane(new Text("Sistem Jastip"));
    }
	
	public static void main(String[] args) {	
		System.out.println("Hello World!!!");

                
//		DatabaseUtil.testDB();
//		Test PostgreSQL Database
		
		Application.launch(MyJastip.class, args);
                

//              VM Arguments:
//		--module-path "D:\Libs\openjfx-26.0.1_windows-x64_bin-sdk\javafx-sdk-26.0.1\lib" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics

        }

}
