package myjastip.app.admin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import myjastip.db.DatabaseUtil;
import myjastip.payment.EscrowPayment;
import myjastip.payment.Order;
import myjastip.storage.CartItem;
import myjastip.users.Admin;

import java.util.ArrayList;
import java.util.List;

public class EditOrdersLayout {
    List<Order> orders;
    private Admin admin;
    public EditOrdersLayout(Admin admin) {
        this.admin = admin;
        this.orders = new ArrayList<>();
    }


    public VBox orderMenu() {
        VBox orderBox = new VBox(32);
        DatabaseUtil.insertOrders(orders);
        for (Order order : orders) {
            HBox orderMenu = new HBox(12);

            Label idLabel = new Label("ID: " + order.getOrderId());
            List<String> arrItems = new ArrayList<>();
            for (CartItem cartItem : order.getOrderedCart().getCartItems()) {
                arrItems.add(String.format("- %d %s\n", cartItem.getQuantity(), cartItem.getItem().getItemName()));
            }
            Label itemsLabel = new Label(String.join("", arrItems));
            itemsLabel.setWrapText(true);
            itemsLabel.setStyle(
                "-fx-font-family: 'Inter';" +
                "-fx-font-size: 20px;" +
                "-fx-font-weight: 700;" +
                "-fx-text-fill: #B5C48E;"
            );


            Label statusLabel = new Label(order.getOrderStatus().toString());

            Label locationLabel = new Label(order.getLocation().toString());
            locationLabel.setStyle(
                "-fx-font-family: 'Inter';" +
                "-fx-font-size: 14px;" +
                "-fx-text-fill: #6b8570;"
            );

            VBox statusBox = new VBox(4);
            VBox locationBox = new VBox(4);

            Label statusTitleLabel = new Label("Status");
            statusBox.getChildren().addAll(statusTitleLabel, statusLabel);
            statusTitleLabel.setStyle(
                "-fx-text-fill: #6b8570;" +
                "-fx-font-size: 12;"
            );

            Label locationTitleLabel = new Label("Tujuan");
            locationBox.getChildren().addAll(locationTitleLabel, locationLabel);
            locationTitleLabel.setStyle(
                "-fx-text-fill: #6b8570;" +
                "-fx-font-size: 12;"
            );


            switch (order.getOrderStatus()) {
                case CANCELLED:
                    statusLabel.setStyle(
                        "-fx-font-family: 'Inter';" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: 600;" +
                        "-fx-text-fill: #f87171;" +
                        "-fx-background-color: rgba(239, 68, 68, 0.15);" +
                        "-fx-background-radius: 100;" +
                        "-fx-padding: 4 12 4 12;"
                    );
                    break;
                case PENDING, OUT_FOR_DELIVERY:
                    statusLabel.setStyle(
                        "-fx-font-family: 'Inter';" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: 600;" +
                        "-fx-text-fill: #facc15;" +
                        "-fx-background-color: rgba(234, 179, 8, 0.15);" +
                        "-fx-background-radius: 100;" +
                        "-fx-padding: 4 12 4 12;"
                    );
                    break;
                case COMPLETED, DELIVERED:
                    statusLabel.setStyle(
                        "-fx-font-family: 'Inter';" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: 600;" +
                        "-fx-text-fill: #4ade80;" +
                        "-fx-background-color: rgba(34, 197, 94, 0.15);" +
                        "-fx-background-radius: 100;" +
                        "-fx-padding: 4 12 4 12;"
                    );
                    break;
            }


            Button deleteOrderButton = new Button("Hapus Pesanan");
            deleteOrderButton.setMinWidth(200);

            deleteOrderButton.setOnAction(e -> {
                DatabaseUtil.removeOrder(order.getOrderId());
                DatabaseUtil.removePayment(DatabaseUtil.getPaymentByOrderId(order.getOrderId()).getPaymentId());
                orderBox.getChildren().remove(orderMenu);
            });
            deleteOrderButton.setStyle(
                "-fx-font-family: 'Inter';" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: 600;" +
                "-fx-text-fill: white;" +
                "-fx-background-color: linear-gradient(to right, #ef4444, #e11d48);" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 8 16 8 16;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(239,68,68,0.3), 14, 0, 0, 4);"
            );

            idLabel.setStyle(
                    "-fx-font-family: 'Courier New';" +
                            "-fx-font-size: 12px;" +
                            "-fx-text-fill: #6b8570;"
            );

            VBox orderSpec = new VBox(16);
            HBox.setHgrow(orderSpec, Priority.ALWAYS);

            HBox controls = new HBox(12);

            HBox statusLocation = new HBox(16);
            statusLocation.getChildren().addAll(statusBox, locationBox);

            HBox rightControl = new HBox(12);
            rightControl.setAlignment(Pos.BOTTOM_RIGHT);
            HBox.setHgrow(rightControl, Priority.ALWAYS);
            rightControl.setMaxWidth(Double.MAX_VALUE);

            rightControl.getChildren().addAll(deleteOrderButton);

            controls.getChildren().addAll(statusLocation, rightControl);


//            locationLabel.setAlignment(Pos.CENTER_LEFT);
            orderSpec.getChildren().addAll(idLabel, itemsLabel, controls);
            orderMenu.getChildren().add(orderSpec);
            orderMenu.setStyle(
                "-fx-background-color: rgba(30, 50, 45, 0.7);" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: rgba(255, 255, 255, 0.08);" +
                "-fx-border-radius: 12;" +
                "-fx-padding: 24;"
//                "-fx-alignment: center-left;"
            );
            orderBox.getChildren().add(orderMenu);
        }
        return orderBox;
    }

    public VBox createEditOrdersLayout() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Edit Pesanan");
        titleLabel.setStyle(
            "-fx-font-family: 'Inter';" +
            "-fx-font-size: 32px;" +
            "-fx-font-weight: 800;" +
            "-fx-text-fill: #8aad7a;"
        );

        ScrollPane orderHistoryScrollPane = new ScrollPane();
        orderHistoryScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        orderHistoryScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        orderHistoryScrollPane.setFitToWidth(true);
        orderHistoryScrollPane.setContent(orderMenu());
        orderHistoryScrollPane.setStyle(
            "-fx-overflow: hidden;" +
            "-fx-background: transparent; -fx-background-color: transparent;"
        );
        layout.getChildren().addAll(titleLabel, orderHistoryScrollPane);
        return layout;
    }


}
