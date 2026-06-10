package myjastip.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import myjastip.users.Customer;
import myjastip.users.User;

public class DashboardView {

    private final MyJastipWindow appWindow;
    private Scene dashboardScene;
    private User user;

    public DashboardView(MyJastipWindow appWindow) {
        this.appWindow = appWindow;
        createDashboardScene();
    }

    public void createDashboardScene() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);

        String username = (user != null) ? user.getName() : "null";
        Label welcomeLabel = new Label("Selamat Datang, " + username + "!");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label userTypeLabel = new Label("Akun: " + (user instanceof Customer ? "Customer" : "Jastiper"));

        Label infoLabel = new Label("Ini adalah halaman Dashboard Utama.");

        Button logoutButton = new Button("Keluar / Logout");
        logoutButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        logoutButton.setOnAction(e -> appWindow.showLoginScene());

        layout.getChildren().addAll(welcomeLabel, userTypeLabel, infoLabel, logoutButton);
        dashboardScene = new Scene(layout, 600, 400);
    }

    public Scene getDashboardScene() {
        createDashboardScene();
        return dashboardScene;
    }

    public void setUser(User user) {
        this.user = user;
    }
}