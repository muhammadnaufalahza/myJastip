package myjastip.app.jastiper;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import myjastip.db.DatabaseUtil;
import myjastip.payment.Order;
import myjastip.payment.OrderStatus;
import myjastip.storage.CartItem;
import myjastip.users.Jastiper;

import java.util.ArrayList;
import java.util.List;

public class JastiperOrderLayout {

    private Jastiper jastiper;

    public JastiperOrderLayout(Jastiper jastiper) {
        this.jastiper = jastiper;
    }

    public VBox orderMenu() {
        VBox orderBox = new VBox(12);

        for (Order order : jastiper.getAcceptedOrders()) {
            HBox orderMenu = new HBox(16);

            Label idLabel = new Label("ID: " + order.getOrderId());
            idLabel.setStyle(
                "-fx-font-family: 'Courier New';" +
                "-fx-font-size: 12px;" +
                "-fx-text-fill: #6b8570;"
            );
            List<String> arrItems = new ArrayList<>();
            for (CartItem cartItem : order.getOrderedCart().getCartItems()) {
                arrItems.add(String.format("- %d %s\n", cartItem.getQuantity(), cartItem.getItem().getItemName()));
            }
            Label itemsLabel = new Label(String.join("", arrItems));
            itemsLabel.setWrapText(true);

            VBox statusBox = new VBox(4);
            VBox locationBox = new VBox(4);

            Label receiverLabel = new Label("Penerima: " + DatabaseUtil.getUser(order.getReceiverId()).getName());
            Label statusLabel = new Label(order.getOrderStatus().toString());
            Label locationLabel = new Label(order.getLocation().toString());

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

            receiverLabel.setStyle(
                    "-fx-font-weight: 600;" +
                    "-fx-font-size: 15;" +
                    "-fx-text-fill: #f1f5f0;"
            );
            itemsLabel.setStyle(
                "-fx-font-family: 'Inter';" +
                "-fx-font-size: 20px;" +
                "-fx-font-weight: 700;" +
                "-fx-text-fill: #B5C48E;"
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
            locationLabel.setStyle(
                    "-fx-font-family: 'Inter';" +
                            "-fx-font-size: 14px;" +
                            "-fx-text-fill: #6b8570;"
            );

            HBox rightControl = new HBox(12);
            HBox.setHgrow(rightControl, Priority.ALWAYS);
            rightControl.setAlignment(Pos.BOTTOM_RIGHT);

            Button finishDeliveryButton = new Button("Selesaikan Pengiriman");

            if (order.getOrderStatus() == OrderStatus.DELIVERED || order.getOrderStatus() == OrderStatus.COMPLETED) {
                finishDeliveryButton.setDisable(true);
            }

            finishDeliveryButton.setOnAction(e -> {
                jastiper.finishDelivery(order);
                finishDeliveryButton.setDisable(true);
//                orderBox.getChildren().remove(orderMenu);
                statusLabel.setText(order.getOrderStatus().toString());
                statusLabel.setStyle(
                    "-fx-font-family: 'Inter';" +
                    "-fx-font-size: 12px;" +
                    "-fx-font-weight: 600;" +
                    "-fx-text-fill: #4ade80;" +
                    "-fx-background-color: rgba(34, 197, 94, 0.15);" +
                    "-fx-background-radius: 100;" +
                    "-fx-padding: 4 12 4 12;"
                );
            });

            rightControl.getChildren().add(finishDeliveryButton);

            finishDeliveryButton.setStyle(
                "-fx-font-family: 'Inter';" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: 600;" +
                "-fx-text-fill: white;" +
                "-fx-background-color: linear-gradient(to right, #22c55e, #10b981);" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 8 16 8 16;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(107, 158, 126, 0.35), 14, 0, 0, 4);"
            );

            VBox orderSpec = new VBox(16);
            HBox.setHgrow(orderSpec, Priority.ALWAYS);

            HBox controls = new HBox(12);

            HBox statusLocation = new HBox(16);
            statusLocation.getChildren().addAll(statusBox, locationBox);

            controls.getChildren().addAll(statusLocation, rightControl);

            orderSpec.getChildren().addAll(idLabel, receiverLabel, itemsLabel, controls);
            orderMenu.getChildren().addAll(orderSpec);
            orderMenu.setStyle(
                "-fx-padding: 16 20 16 20;" +
                "-fx-border-color: transparent transparent rgba(255,255,255,0.08) transparent;" +
                "-fx-border-width: 0 0 1 0;" +
                "-fx-alignment: center-left;" +
                "-fx-background-color: linear-gradient(to bottom right, rgba(107,158,126,0.08), rgba(45,95,82,0.04));" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: rgba(255, 255, 255, 0.08);" +
                "-fx-border-radius: 12;" +
                "-fx-padding: 24;"
            );
            orderBox.getChildren().add(orderMenu);
        }


        orderBox.setStyle(
            "-fx-background-color: rgba(30, 50, 45, 0.7);" +
            "-fx-border-color: rgba(255, 255, 255, 0.08);" +
            "-fx-border-radius: 12;" +
            "-fx-background-radius: 12;"
        );
        return orderBox;
    }


    public VBox createJastiperOrderLayout() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Pesanan Diterima");
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


    public void setJastiper(Jastiper jastiper) {
        this.jastiper = jastiper;
    }
}
