package myjastip.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import myjastip.users.Customer;

public class CustomerOrdersView {
    private final MyJastipWindow appWindow;
    private Scene customerOrdersScene;
    private Customer customer;

    public CustomerOrdersView(MyJastipWindow appWindow) {
        this.appWindow = appWindow;
        createCustomerOrdersScene();
    }

    public void createCustomerOrdersScene() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Pesanan Kamu");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button logoutButton = new Button("Kembali ke Dashboard");
        logoutButton.setStyle("-fx-background-color: #4067e4; -fx-text-fill: white;  -fx-background-radius: 20px; -fx-border-radius: 20px;");

        logoutButton.setOnAction(e -> appWindow.showDashboardScene(customer));

        layout.getChildren().addAll(welcomeLabel, logoutButton);
        customerOrdersScene = new Scene(layout, 1200, 800);
    }

    public Scene getCustomerOrdersScene() {
        createCustomerOrdersScene();
        return customerOrdersScene;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
