package myjastip.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import myjastip.db.DatabaseUtil;
import myjastip.payment.Order;
import myjastip.payment.OrderStatus;
import myjastip.users.Customer;

import java.util.ArrayList;

public class CustomerOrdersView {
    private final MyJastipWindow appWindow;
    private Scene customerOrdersScene;
    private Customer customer;

    public CustomerOrdersView(MyJastipWindow appWindow) {
        this.appWindow = appWindow;
    }
    public VBox orderHistoryMenu() {
        VBox orderBox = new VBox(12);

        for (int i = customer.getOrders().size() - 1; i >= 0; i--) {
            Order order = customer.getOrders().get(i);
            HBox orderMenu = new HBox(12);

            Label destinationLabel = new Label("Tujuan: " + order.getLocation().getLocationName());
            Label statusLabel = new Label("Status: " + order.getOrderStatus());
            Label locationLabel = new Label("Lokasi: " + order.getLocation());

            HBox rightControl = new HBox(12);
            HBox.setHgrow(rightControl, Priority.ALWAYS);
            rightControl.setAlignment(Pos.CENTER_RIGHT);

            Button cancelOrderButton = new Button("Batalkan Pesanan");
            if (order.getOrderStatus() == OrderStatus.DELIVERED || order.getOrderStatus() == OrderStatus.CONFIRMED || order.getOrderStatus() == OrderStatus.CANCELLED) {
                cancelOrderButton.setDisable(true);
            }
            cancelOrderButton.setOnAction(e -> {
                customer.cancelOrder(order);
                cancelOrderButton.setDisable(true);
                statusLabel.setText("Status: " + OrderStatus.CANCELLED);
            });


            Button finishOrderButton = new Button("Selesaikan Pesanan");
            if (order.getOrderStatus() != OrderStatus.DELIVERED) {
                finishOrderButton.setDisable(true);
            }
            finishOrderButton.setOnAction(e -> {
                DatabaseUtil.changeOrderStatus(order.getOrderId(), OrderStatus.CONFIRMED);
                order.setOrderStatus(OrderStatus.CONFIRMED);
                cancelOrderButton.setDisable(true);
                DatabaseUtil.insertOrdersByReceiverId(customer.getOrders(), customer.getUserId());
                statusLabel.setText("Status: " + OrderStatus.CONFIRMED);
            });


            rightControl.getChildren().addAll(cancelOrderButton, finishOrderButton);

            VBox orderSpec = new VBox();


            orderSpec.getChildren().addAll(destinationLabel, statusLabel, locationLabel);
            orderMenu.getChildren().addAll(orderSpec, rightControl);
            orderBox.getChildren().add(orderMenu);
        }

        return orderBox;
    }

    public void createCustomerOrdersScene() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Pesanan Kamu");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ScrollPane orderHistoryScrollPane = new ScrollPane();
        orderHistoryScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        orderHistoryScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        orderHistoryScrollPane.setFitToWidth(true);
        orderHistoryScrollPane.setContent(orderHistoryMenu());

        Button logoutButton = new Button("Kembali ke Dashboard");
        logoutButton.setStyle("-fx-background-color: #4067e4; -fx-text-fill: white;  -fx-background-radius: 20px; -fx-border-radius: 20px;");

        logoutButton.setOnAction(e -> appWindow.showDashboardScene(customer));

        layout.getChildren().addAll(welcomeLabel, orderHistoryScrollPane, logoutButton);
        customerOrdersScene = new Scene(layout, 1200, 800);
    }

    public Scene getCustomerOrdersScene() {
        createCustomerOrdersScene();
        return customerOrdersScene;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        DatabaseUtil.insertOrdersByReceiverId(customer.getOrders(), customer.getUserId());
    }

}
