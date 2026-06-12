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
import javafx.scene.text.Font;
import myjastip.db.DatabaseUtil;
import myjastip.payment.Order;
import myjastip.storage.CartItem;
import myjastip.users.Customer;
import myjastip.users.User;

import java.util.ArrayList;

public class DashboardView {

    private final MyJastipWindow appWindow;
    private Scene dashboardScene;
    private User user;

    public DashboardView(MyJastipWindow appWindow) {
        this.appWindow = appWindow;
        createDashboardScene();
    }

    public VBox cartsMenu() {
        VBox cartBox = new VBox(12);
        for (CartItem cartItem : ((Customer) user).getCart().getCartItems()) {
            HBox itemBox = new HBox(12);

            Label itemLabel = new Label(cartItem.getItem().getItemName() + " x" + cartItem.getQuantity());
            Button itemQtyAdd = new Button("+");
            itemQtyAdd.setFont(new Font("Consolas", 12));
            itemQtyAdd.setStyle("-fx-background-color: white; -fx-border-color: green; -fx-text-fill: black; -fx-background-radius: 20px; -fx-border-radius: 20px;");

            Button itemQtyMin = new Button("-");
            itemQtyMin.setFont(new Font("Consolas", 12));
            itemQtyMin.setStyle("-fx-background-color: white; -fx-border-color: red; -fx-text-fill: black; -fx-background-radius: 20px; -fx-border-radius: 20px;");

            itemLabel.setMinWidth(100);
            itemLabel.setMaxWidth(800);
            itemQtyAdd.setOnAction(e -> {
                cartItem.addQuanitity(1);
                itemLabel.setText(cartItem.getItem().getItemName() + " x" + cartItem.getQuantity());
            });
            itemQtyMin.setOnAction(e -> {
                if (cartItem.getQuantity() > 1) {
                    cartItem.subtractQuanitity(1);
                    itemLabel.setText(cartItem.getItem().getItemName() + " x" + cartItem.getQuantity());
                } else {
                    ((Customer) user).getCart().removeItem(cartItem);
                    cartBox.getChildren().removeIf(i -> i.equals(itemBox));
                }
            });

            itemBox.getChildren().addAll(itemLabel, itemQtyAdd, itemQtyMin);

            cartBox.getChildren().add(itemBox);
        }

        return cartBox;
    }

    public VBox orderMenu() {
        VBox orderBox = new VBox(12);
        ArrayList<Order> orders = new ArrayList<>();
        DatabaseUtil.insertOrders(orders);

        for (Order order : orders) {
            HBox orderMenu = new HBox(12);

            Label destinationLabel = new Label("Tujuan: " + order.getLocation().getLocationName());
            Label recieverLabel = new Label("Penerima: " + DatabaseUtil.getUser(order.getRecieverId()));
            Label itemLabel = new Label("Lokasi: " + order.getLocation());

            HBox rightControl = new HBox();
            HBox.setHgrow(rightControl, Priority.ALWAYS);
            rightControl.setAlignment(Pos.CENTER_RIGHT);
            Button acceptButton = new Button("Terima Pesanan");
            rightControl.getChildren().add(acceptButton);

            VBox orderSpec = new VBox();
            orderSpec.getChildren().addAll(destinationLabel, recieverLabel, itemLabel);

            orderMenu.getChildren().addAll(orderSpec, rightControl);

            orderBox.getChildren().add(orderMenu);

        }

        return orderBox;
    }


    public void createDashboardScene() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);

        String username = (user != null) ? user.getName() : "null";
        Label welcomeLabel = new Label("Selamat Datang, " + username + "!");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label userTypeLabel = new Label("Akun: " + (user instanceof Customer ? "Customer" : "Jastiper"));

        Label infoLabel = new Label("Ini adalah halaman Dashboard Utama.");

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-background-radius: 20px; -fx-border-radius: 20px;");

        logoutButton.setOnAction(e -> appWindow.showLoginScene());

        if (user instanceof Customer) {
            Button storeButton = new Button("Toko");
            storeButton.setStyle("-fx-background-color: #88FF74; -fx-text-fill: black; -fx-background-radius: 20px; -fx-border-radius: 20px;");
            storeButton.setOnAction(e -> appWindow.showStoreScene((Customer) user));

            ScrollPane storeScrollPane = new ScrollPane();
            storeScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            storeScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            storeScrollPane.setFitToWidth(true);
            storeScrollPane.setContent(cartsMenu());

            Button orderButton = new Button("Buat Pesanan");
            orderButton.setStyle("-fx-background-color: #00FF00; -fx-text-fill: black; -fx-background-radius: 20px; -fx-border-radius: 20px;");

            orderButton.setOnAction(e -> {
                Customer customer = (Customer) user;
                if (!(customer.getCart().isCartEmpty())) {
                    DatabaseUtil.insertOrder(
                            "Sending",
                            customer.getOrderLocation().getLocationName(),
                            customer.getOrderLocation().getLatitude(), customer.getOrderLocation().getLongitude(),
                            customer.getCart().calculateTotalPrice(), customer.getCart().calculateTotalPrice() * 0.1, 10_000.0,
                            customer.getUserId(),
                            customer.getCart()
                    );
                    customer.getCart().emptyCart();
                    storeScrollPane.setContent(cartsMenu());
                    System.out.println("Pesanan telah dibuat");
                } else {
                    System.out.println("Pesanan Kosong");
                }
            });

            Button orderViewButton = new Button("Lihat Pesanan");
            orderViewButton.setStyle("-fx-background-color: #80BEFF; -fx-text-fill: black; -fx-background-radius: 20px; -fx-border-radius: 20px;");
            orderViewButton.setOnAction(e -> appWindow.showCustomerOrdersScene((Customer) user));

            layout.getChildren().addAll(welcomeLabel, userTypeLabel, infoLabel, storeButton, storeScrollPane, orderButton, orderViewButton, logoutButton);
        }
        else {
            ScrollPane orderScrollPane = new ScrollPane();
            orderScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            orderScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            orderScrollPane.setFitToWidth(true);
            orderScrollPane.setContent(orderMenu());

            layout.getChildren().addAll(welcomeLabel, userTypeLabel, infoLabel, orderScrollPane, logoutButton);
        }
        dashboardScene = new Scene(layout, 1200, 800);
    }

    public Scene getDashboardScene() {
        createDashboardScene();
        return dashboardScene;
    }

    public void setUser(User user) {
        this.user = user;
    }
}