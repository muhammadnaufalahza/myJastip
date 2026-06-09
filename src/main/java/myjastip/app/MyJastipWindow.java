package myjastip.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import myjastip.users.User;

public class MyJastipWindow extends Application {

    private Stage primaryStage;
    private AuthView authView;
    private DashboardView dashboardView;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setMinWidth(600);
        this.primaryStage.setMinHeight(400);
        this.primaryStage.setTitle("myJastip");

        this.authView = new AuthView(this);
        this.dashboardView = new DashboardView(this);

        showLoginScene();
        this.primaryStage.show();
    }

    public void showLoginScene() {
        primaryStage.setScene(authView.getLoginScene());
    }

    public void showRegisterScene() {
        primaryStage.setScene(authView.getRegisterScene());
    }

    public void showDashboardScene(User user) {
        dashboardView.setUser(user);
        primaryStage.setScene(dashboardView.getDashboardScene());
    }
}
