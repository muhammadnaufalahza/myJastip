package myjastip.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Bayar");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        VBox paymentBox = new VBox();

        Label paymentIdLabel = new Label("ID Pembayaran: " + payment.getPaymentId());
        Label amountLabel = new Label("Total: Rp. " + payment.getAmount());

        paymentBox.getChildren().addAll(paymentIdLabel, amountLabel);

        Button payButton = new Button("Bayar");
        payButton.setOnAction(e -> {
            try {
                customer.pay(payment);
            } catch (InsufficientBalanceException ex) {
                System.out.println("Error : " + ex.getMessage());
            } finally {
                appWindow.showDashboardScene(DatabaseUtil.getUser(customer.getUserId()));
            }
        });

        layout.getChildren().addAll(titleLabel, paymentBox, payButton);
        paymentScene = new Scene(layout, 600, 400);
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
