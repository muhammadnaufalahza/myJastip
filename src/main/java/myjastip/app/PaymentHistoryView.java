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
import myjastip.payment.EscrowPayment;
import myjastip.payment.PaymentStatus;
import myjastip.users.Customer;

import java.math.BigDecimal;

public class PaymentHistoryView {
    private final MyJastipWindow appWindow;
    private Scene paymentHistoryScene;
    private Customer customer;

    public PaymentHistoryView(MyJastipWindow appWindow) {
        this.appWindow = appWindow;
    }

    public VBox paymentHistoryMenu() {
        VBox paymentBox = new VBox(12);

        for (EscrowPayment payment : customer.getPaymentHistory()) {
            HBox orderMenu = new HBox(12);

            Label paymentIdLabel = new Label("Id pesanan: " + payment.getPaymentId());
            Label paymentAmtLabel = new Label("Rp. " +  new BigDecimal(String.valueOf(payment.getAmount())).toPlainString());

            HBox rightControl = new HBox(12);
            HBox.setHgrow(rightControl, Priority.ALWAYS);
            rightControl.setAlignment(Pos.CENTER_RIGHT);

            Button payButton = new Button("Bayar");
            if (payment.getStatus() != PaymentStatus.UNFINISHED) {
                payButton.setDisable(true);
            }
            payButton.setOnAction(e -> {
                appWindow.showPaymentScene(customer, payment);
            });

            rightControl.getChildren().addAll(payButton);

            VBox orderSpec = new VBox();


            orderSpec.getChildren().addAll(paymentIdLabel, paymentAmtLabel);
            orderMenu.getChildren().addAll(orderSpec, rightControl);
            paymentBox.getChildren().add(orderMenu);
        }
        return paymentBox;
    }

    public void createPaymentHistoryScene() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Pesanan Kamu");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ScrollPane paymentHistoryScrollPane = new ScrollPane();
        paymentHistoryScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        paymentHistoryScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        paymentHistoryScrollPane.setFitToWidth(true);
        paymentHistoryScrollPane.setContent(paymentHistoryMenu());

        Button backButton = new Button("Kembali ke Dashboard");
        backButton.setStyle("-fx-background-color: #4067e4; -fx-text-fill: white;  -fx-background-radius: 20px; -fx-border-radius: 20px;");

        backButton.setOnAction(e -> appWindow.showDashboardScene(customer));

        layout.getChildren().addAll(welcomeLabel, paymentHistoryScrollPane, backButton);
        paymentHistoryScene = new Scene(layout, 1200, 800);
    }

    public Scene getPaymentHistoryScene() {
        createPaymentHistoryScene();
        return paymentHistoryScene;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


}
