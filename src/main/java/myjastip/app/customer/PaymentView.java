package myjastip.app.customer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import myjastip.app.MyJastipWindow;
import myjastip.db.DatabaseUtil;
import myjastip.payment.InsufficientBalanceException;
import myjastip.payment.EscrowPayment;
import myjastip.users.Customer;

public class PaymentView {
    private final MyJastipWindow appWindow;
    private Scene paymentScene;
    private Customer customer;
    private EscrowPayment payment;

    public PaymentView(MyJastipWindow appWindow) {
        this.appWindow = appWindow;
    }


    public void createPaymentScene() {
        StackPane mainLayout = new StackPane();
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);
        layout.setMaxSize(800,600);

        Label titleLabel = new Label("Konfirmasi Pembayaran");
        titleLabel.setStyle(
                "-fx-font-family: 'Inter';" +
                        "-fx-font-size: 28px;" +
                        "-fx-font-weight: 800;" +
                        "-fx-text-fill: #8aad7a;"
        );

        Label subtitleLabel = new Label("Pastikan data pembayaran sudah benar");
        subtitleLabel.setStyle(
                "-fx-font-family: 'Inter';" +
                        "-fx-font-size: 14px;" +
                        "-fx-text-fill: #6b8570;"
        );

        VBox paymentBox = new VBox(24);
        HBox.setHgrow(paymentBox, Priority.ALWAYS);


        HBox paymentIdBox = new HBox();
        Label paymentIdTitleLabel = new Label("ID Pembayaran");
        paymentIdTitleLabel.setAlignment(Pos.CENTER_LEFT);
        paymentIdTitleLabel.setStyle(
                "-fx-text-fill: #6b8570;" +
                        "-fx-font-size: 12;"
        );
        Label paymentIdLabel = new Label(payment.getPaymentId());
        paymentIdLabel.setStyle(
                "-fx-font-family: 'Courier New';" +
                        "-fx-font-size: 12px;" +
                        "-fx-text-fill: #6b8570;"
        );
        HBox paymentIdLabelBox = new HBox();
        paymentIdLabelBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(paymentIdLabelBox, Priority.ALWAYS);
        paymentIdLabelBox.setMaxWidth(Double.MAX_VALUE);

        paymentIdLabelBox.getChildren().add(paymentIdLabel);
        paymentIdBox.getChildren().addAll(paymentIdTitleLabel, paymentIdLabelBox);



        HBox amountBox = new HBox(24);
        Label amountTitleLabel = new Label("Total Bayar");
        amountTitleLabel.setAlignment(Pos.CENTER_LEFT);
        amountTitleLabel.setStyle(
                "-fx-text-fill: #6b8570;" +
                "-fx-font-size: 12;"
        );
        Label amountLabel = new Label(String.format("Rp %.2f", payment.getAmount()) );
        amountLabel.setStyle(
                "-fx-font-size: 20px;" +
                "-fx-font-weight: 800;" +
                "-fx-text-fill: #B5C48E;"
        );
        HBox amountLabelBox = new HBox();
        amountLabelBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(amountLabelBox, Priority.ALWAYS);
        amountLabelBox.setMaxWidth(Double.MAX_VALUE);

        amountLabelBox.getChildren().add(amountLabel);
        amountBox.getChildren().addAll(amountTitleLabel, amountLabelBox);


        HBox balanceBox = new HBox();
        Label balanceTitleLabel = new Label("Saldo");
        balanceTitleLabel.setAlignment(Pos.CENTER_LEFT);

        balanceTitleLabel.setStyle(
                "-fx-text-fill: #6b8570;" +
                        "-fx-font-weight: 600;" +
                        "-fx-font-size: 12;"
        );
        Label balanceLabel = new Label(String.format("%.2f", payment.getAmount()) );
        balanceLabel.setStyle(
                "-fx-font-size: 15px;" +
                        "-fx-text-fill: #f1f5f0;"
        );
        HBox balanceLabelBox = new HBox();
        balanceLabelBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(balanceLabelBox, Priority.ALWAYS);
        balanceLabelBox.setMaxWidth(Double.MAX_VALUE);

        balanceLabelBox.getChildren().add(balanceLabel);
        balanceBox.getChildren().addAll(balanceTitleLabel, balanceLabelBox);

        paymentBox.getChildren().addAll(paymentIdBox, amountBox, balanceBox);

        Button payButton = new Button("Bayar");
        payButton.setMaxWidth(Double.MAX_VALUE);
        payButton.setStyle(
                "-fx-font-family: 'Inter';" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: 600;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-color: linear-gradient(to right, #6B9E7E, #2D5F52);" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 14 24 14 24;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(107,158,126,0.35), 14, 0, 0, 4);"
        );
        payButton.setOnAction(e -> {
            try {
                customer.pay(payment);
            } catch (InsufficientBalanceException ex) {
                System.out.println("Error : " + ex.getMessage());
            } finally {
                appWindow.showCustomerDashboardScene((Customer) DatabaseUtil.getUser(customer.getUserId()));
            }
        });


        layout.getChildren().addAll(titleLabel, subtitleLabel, paymentBox, payButton);

        layout.setStyle(
                "-fx-alignment: center;" +
                        "-fx-padding: 48 36 48 36;" +
                        "-fx-background-color: rgba(30, 50, 45, 0.7);" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-color: rgba(255, 255, 255, 0.08);" +
                        "-fx-border-radius: 16;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 32, 0, 0, 8);"
        );
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #1a2a2e, #263842, #1e3530);"
        );
        mainLayout.getChildren().add(layout);




//        VBox layout = new VBox(20);
//        layout.setPadding(new Insets(30));
//        layout.setAlignment(Pos.CENTER);
//        layout.setMaxSize(600,800);
//
//        Label titleLabel = new Label("Konfirmasi Pembayaran");
//        titleLabel.setStyle(
//            "-fx-font-family: 'Inter';" +
//            "-fx-font-size: 28px;" +
//            "-fx-font-weight: 800;" +
//            "-fx-text-fill: #8aad7a;"
//        );
//
//        VBox paymentBox = new VBox();
//        HBox.setHgrow(paymentBox, Priority.ALWAYS);
//
//        HBox paymentIdBox = new HBox();
//        Label paymentIdTitleLabel = new Label("ID Pembayaran");
//        paymentIdTitleLabel.setStyle(
//                "-fx-text-fill: #6b8570;" +
//                        "-fx-font-size: 12;"
//        );
//        Label paymentIdLabel = new Label(payment.getPaymentId());
//        paymentIdLabel.setStyle(
//                "-fx-font-family: 'Courier New';" +
//                        "-fx-font-size: 12px;" +
//                        "-fx-text-fill: #6b8570;"
//        );
//        HBox paymentIdLabelBox = new HBox(paymentIdLabel);
//        HBox.setHgrow(paymentIdLabelBox, Priority.ALWAYS);
//        paymentIdLabel.setAlignment(Pos.CENTER_RIGHT);
//
//        paymentIdBox.getChildren().addAll(paymentIdTitleLabel, paymentIdLabel);
//
//        Label amountLabel = new Label("Total: Rp. " + payment.getAmount());
//
//        paymentBox.getChildren().addAll(paymentIdBox, amountLabel);
//
//        Button payButton = new Button("Bayar");
//        payButton.setOnAction(e -> {
//            try {
//                customer.pay(payment);
//            } catch (InsufficientBalanceException ex) {
//                System.out.println("Error : " + ex.getMessage());
//            } finally {
//                appWindow.showCustomerDashboardScene((Customer) DatabaseUtil.getUser(customer.getUserId()));
//            }
//        });
//
//        layout.getChildren().addAll(titleLabel, paymentBox, payButton);
        paymentScene = new Scene(mainLayout, 1600, 1000);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setPayment(EscrowPayment payment) {
        this.payment = payment;
    }

    public Scene getPaymentScene() {
        createPaymentScene();
        return paymentScene;
    }
}
