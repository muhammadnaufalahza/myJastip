package myjastip.app.customer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import myjastip.db.DatabaseUtil;
import myjastip.payment.EscrowPayment;
import myjastip.users.Customer;

import java.math.BigDecimal;

public class PaymentHistoryLayout {

    private Customer customer;

    public PaymentHistoryLayout(Customer customer) {
        this.customer = customer;
    }

    public VBox paymentHistoryMenu() {
        VBox paymentBox = new VBox(12);

        for (EscrowPayment payment : ((Customer) DatabaseUtil.getUser(customer.getUserId())).getPaymentHistory()) {
            HBox orderMenu = new HBox(12);

            Label paymentIdLabel = new Label("ID Pembayaran: " + payment.getPaymentId());
            Label orderIdLabel = new Label("ID Pesanan: " + payment.getOrderId());
            Label paymentAmtLabel = new Label("Rp. " +  new BigDecimal(String.valueOf(payment.getAmount())).toPlainString());

            paymentAmtLabel.setStyle(
                    "-fx-font-family: 'Inter';" +
                            "-fx-font-size: 20px;" +
                            "-fx-font-weight: 700;" +
                            "-fx-text-fill: #B5C48E;"
            );

            Label statusTitleLabel = new Label("Status");

            VBox statusBox = new VBox(4);
            Label statusLabel = new Label(payment.getStatus().toString());
            statusBox.getChildren().addAll(statusTitleLabel, statusLabel);

            statusTitleLabel.setStyle(
                    "-fx-text-fill: #6b8570;" +
                            "-fx-font-size: 12;"
            );


            switch (payment.getStatus()) {
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
                case UNFINISHED:
                    statusLabel.setStyle(
                            "-fx-font-family: 'Inter';" +
                                    "-fx-font-size: 12px;" +
                                    "-fx-font-weight: 600;" +
                                    "-fx-text-fill: #ffffff;" +
                                    "-fx-background-color: rgba(192, 192, 192, 0.15);" +
                                    "-fx-background-radius: 100;" +
                                    "-fx-padding: 4 12 4 12;"
                    );
                    break;
                case HELD, REFUNDED:
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
                case RELEASED:
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

            HBox rightControl = new HBox(16);
            rightControl.setMinWidth(200);
            HBox.setHgrow(rightControl, Priority.ALWAYS);
            rightControl.setAlignment(Pos.CENTER_RIGHT);


            rightControl.getChildren().addAll(paymentAmtLabel);

            VBox orderSpec = new VBox(16);
            HBox.setHgrow(orderSpec, Priority.ALWAYS);

            paymentIdLabel.setStyle(
                "-fx-font-family: 'Courier New';" +
                "-fx-font-size: 12px;" +
                "-fx-text-fill: #6b8570;"
            );
            orderIdLabel.setStyle(
                "-fx-font-family: 'Courier New';" +
                "-fx-font-size: 12px;" +
                "-fx-text-fill: #6b8570;"
            );

            orderSpec.getChildren().addAll(paymentIdLabel, orderIdLabel, statusBox);
            orderMenu.getChildren().addAll(orderSpec, rightControl);
            orderMenu.setStyle(
                "-fx-padding: 16 20 16 20;" +
                "-fx-border-color: transparent transparent rgba(255,255,255,0.08) transparent;" +
                "-fx-border-width: 0 0 1 0;" +
                "-fx-alignment: center-left;" +
                "-fx-background-color: linear-gradient(to bottom right, rgba(107,158,126,0.08), rgba(45,95,82,0.04));" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: rgba(255, 255, 255, 0.08);" +
                "-fx-border-radius: 12;" +
                "-fx-padding: 24;");
            paymentBox.getChildren().add(orderMenu);
        }
        paymentBox.setStyle(
                "-fx-background-color: rgba(30, 50, 45, 0.7);" +
                "-fx-border-color: rgba(255, 255, 255, 0.08);" +
                "-fx-border-radius: 12;" +
                "-fx-background-radius: 12;"
        );
        return paymentBox;
    }

    public VBox createPaymentHistoryLayout() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Histori Pembayaran");
        titleLabel.setStyle(
            "-fx-font-family: 'Inter';" +
            "-fx-font-size: 32px;" +
            "-fx-font-weight: 800;" +
            "-fx-text-fill: #8aad7a;"
        );

        ScrollPane paymentHistoryScrollPane = new ScrollPane();
        paymentHistoryScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        paymentHistoryScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        paymentHistoryScrollPane.setFitToWidth(true);
        paymentHistoryScrollPane.setContent(paymentHistoryMenu());
        paymentHistoryScrollPane.setStyle(
            "-fx-overflow: hidden;" +
            "-fx-background: transparent; -fx-background-color: transparent;"
        );

        layout.getChildren().addAll(titleLabel, paymentHistoryScrollPane);
        return layout;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
