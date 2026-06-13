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
import myjastip.users.Jastiper;

public class JastiperOrderView {
    private final MyJastipWindow appWindow;
    private Scene jastiperOrderScene;
    private Jastiper jastiper;

    public JastiperOrderView(MyJastipWindow appWindow) {
        this.appWindow = appWindow;
    }

    public void createJastiperOrderScene() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Pesanan Kamu");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

//        ScrollPane orderHistoryScrollPane = new ScrollPane();
//        orderHistoryScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//        orderHistoryScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
//        orderHistoryScrollPane.setFitToWidth(true);
//        orderHistoryScrollPane.setContent(orderHistoryMenu());

        Button logoutButton = new Button("Kembali ke Dashboard");
        logoutButton.setStyle("-fx-background-color: #4067e4; -fx-text-fill: white;  -fx-background-radius: 20px; -fx-border-radius: 20px;");

        logoutButton.setOnAction(e -> appWindow.showDashboardScene(jastiper));

        layout.getChildren().addAll(welcomeLabel, logoutButton);
//        layout.getChildren().addAll(welcomeLabel, orderHistoryScrollPane, logoutButton);
        jastiperOrderScene = new Scene(layout, 1200, 800);
    }

    public Scene getCustomerOrdersScene() {
        createJastiperOrderScene();
        return jastiperOrderScene;
    }

    public void setCustomer(Jastiper jastiper) {
        this.jastiper = jastiper;
    }

}
