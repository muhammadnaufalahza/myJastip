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
import myjastip.users.Jastiper;

public class JastiperOrderView {
    private final MyJastipWindow appWindow;
    private Scene jastiperOrderScene;
    private Jastiper jastiper;

    public JastiperOrderView(MyJastipWindow appWindow) {
        this.appWindow = appWindow;
    }

    public VBox orderMenu() {
        VBox orderBox = new VBox(12);

        for (Order order : jastiper.getAcceptedOrders()) {
            HBox orderMenu = new HBox(12);

            Label destinationLabel = new Label("Penerima: " + DatabaseUtil.getUser(order.getReceiverId()));
            Label statusLabel = new Label("Status: " + order.getOrderStatus());
            Label locationLabel = new Label("Lokasi: " + order.getLocation());

            HBox rightControl = new HBox(12);
            HBox.setHgrow(rightControl, Priority.ALWAYS);
            rightControl.setAlignment(Pos.CENTER_RIGHT);

            Button finishDeliveryButton = new Button("Selesaikan Pengiriman");

            if (order.getOrderStatus() == OrderStatus.DELIVERED || order.getOrderStatus() == OrderStatus.COMPLETED) {
                finishDeliveryButton.setDisable(true);
            }

            finishDeliveryButton.setOnAction(e -> {
                jastiper.finishDelivery(order);
                finishDeliveryButton.setDisable(true);
//                orderBox.getChildren().remove(orderMenu);
                statusLabel.setText("Status: " + OrderStatus.DELIVERED);
            });

            rightControl.getChildren().add(finishDeliveryButton);

            VBox orderSpec = new VBox();


            orderSpec.getChildren().addAll(destinationLabel, statusLabel, locationLabel);
            orderMenu.getChildren().addAll(orderSpec, rightControl);
            orderBox.getChildren().add(orderMenu);
        }
        return orderBox;
    }


    public void createJastiperOrderScene() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Pesanan Kamu");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ScrollPane orderHistoryScrollPane = new ScrollPane();
        orderHistoryScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        orderHistoryScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        orderHistoryScrollPane.setFitToWidth(true);
        orderHistoryScrollPane.setContent(orderMenu());

        Button logoutButton = new Button("Kembali ke Dashboard");
        logoutButton.setStyle("-fx-background-color: #4067e4; -fx-text-fill: white;  -fx-background-radius: 20px; -fx-border-radius: 20px;");

        logoutButton.setOnAction(e -> appWindow.showDashboardScene(jastiper));

        layout.getChildren().addAll(welcomeLabel, orderHistoryScrollPane, logoutButton);
        jastiperOrderScene = new Scene(layout, 1200, 800);
    }

    public Scene getJastiperOrderScene() {
        createJastiperOrderScene();
        return jastiperOrderScene;
    }

    public void setJastiper(Jastiper jastiper) {
        this.jastiper = jastiper;
    }
}
