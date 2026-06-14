package myjastip.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import myjastip.db.DatabaseUtil;
import myjastip.location.Location;
import myjastip.payment.EmptyOrderException;
import myjastip.payment.Order;
import myjastip.payment.OrderStatus;
import myjastip.payment.EscrowPayment;
import myjastip.storage.CartItem;
import myjastip.users.Customer;
import myjastip.users.Jastiper;
import myjastip.users.User;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class DashboardView {

    private final MyJastipWindow appWindow;
    private Scene dashboardScene;
    private User user;

    public DashboardView(MyJastipWindow appWindow) {
        this.appWindow = appWindow;
//        createDashboardScene();
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
            if (order.getOrderStatus() != OrderStatus.PENDING) continue;
            HBox orderMenu = new HBox(12);

            Label destinationLabel = new Label("Tujuan: " + order.getLocation().getLocationName());
            Label recieverLabel = new Label("Penerima: " + DatabaseUtil.getUser(order.getReceiverId()).getName());
            Label locationLabel = new Label("Lokasi: " + order.getLocation());

            HBox rightControl = new HBox();
            HBox.setHgrow(rightControl, Priority.ALWAYS);
            rightControl.setAlignment(Pos.CENTER_RIGHT);

            Button acceptButton = new Button("Terima Pesanan");
            acceptButton.setOnAction(e -> {
                Jastiper jastiper = (Jastiper) user;
                orderBox.getChildren().remove(orderMenu);
                jastiper.acceptOrder(order);
            });
            rightControl.getChildren().add(acceptButton);

            VBox orderSpec = new VBox();
            orderSpec.getChildren().addAll(destinationLabel, recieverLabel, locationLabel);
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

//        Label infoLabel = new Label("Ini adalah halaman Dashboard Utama.");

        Label balanceLabel = new Label("Saldo: Rp." + user.getBalance());

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-background-radius: 20px; -fx-border-radius: 20px;");

        logoutButton.setOnAction(e -> appWindow.showLoginScene());


        layout.getChildren().addAll(welcomeLabel, userTypeLabel, balanceLabel);

        if (user instanceof Customer) {

            VBox addressSelectionMenu = new VBox();
            Label addrSelectionLabel = new Label("isi alamat tujuan pesanan: ");
//        addressSelectionMenu.setPrefWidth(500);
            GridPane gp = new GridPane();

            Label setAddressLabel = new Label("Alamat ");
            Label setLatitudeLabel = new Label("Garis lintang ");
            Label setLongitudeLabel = new Label("Garis bujur ");

            TextField inputAddress = new TextField();
            TextField inputLatitude = new TextField();
            TextField inputLongitude = new TextField();

            // Regex matches optional leading minus sign, optional digits, optional decimal point, and optional fractional digits
            Pattern validEditingState = Pattern.compile("-?(\\d*\\.?\\d*)?");

            UnaryOperator<TextFormatter.Change> filter = change -> {
                String newText = change.getControlNewText();
                if (validEditingState.matcher(newText).matches()) {
                    return change; // Accept change
                }
                return null; // Reject change
            };

            TextFormatter<String> textFormatterLatitude = new TextFormatter<>(filter);
            TextFormatter<String> textFormatterLongitude = new TextFormatter<>(filter);

            inputLatitude.setTextFormatter(textFormatterLatitude);
            inputLongitude.setTextFormatter(textFormatterLongitude);

            // Retrieve the float value later
            inputLatitude.setOnAction(e -> {
                try {
                    float value = Float.parseFloat(inputLatitude.getText());
                    System.out.println("User entered: " + value);
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid float or empty string");
                }
            });

            inputLongitude.setOnAction(e -> {
                try {
                    float value = Float.parseFloat(inputLatitude.getText());
                    System.out.println("User entered: " + value);
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid float or empty string");
                }
            });

            gp.add(setAddressLabel,  0, 1); gp.add(inputAddress,  1, 1);
            gp.add(setLatitudeLabel, 0, 2); gp.add(inputLatitude, 1, 2);
            gp.add(setLongitudeLabel,0, 3); gp.add(inputLongitude,1, 3);

            addressSelectionMenu.getChildren().addAll(addrSelectionLabel, gp);



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
                try {
                    if (!(inputAddress.getText().isEmpty() || inputLatitude.getText().isEmpty() || inputLongitude.getText().isEmpty())) {
                        customer.setOrderLocation(new Location(inputAddress.getText(), Double.parseDouble(inputLatitude.getText()), Double.parseDouble(inputLatitude.getText())));
                    } else if (!inputAddress.getText().isEmpty()) {
                        customer.setOrderLocation(new Location(inputAddress.getText()));
                    } else {
                        customer.setOrderLocation(new Location());
                    }
                    Order order = customer.createOrder();
                    ((VBox) storeScrollPane.getContent()).getChildren().clear();

                    UUID uuid = UUID.randomUUID();
                    EscrowPayment payment = new EscrowPayment(uuid.toString(), order.getOrderId(), order.getTotalBill());
                    DatabaseUtil.insertPayment(payment);
                    appWindow.showPaymentScene(customer, payment);
                } catch (EmptyOrderException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }

            });

            Button orderViewButton = new Button("Lihat Pesanan");
            orderViewButton.setStyle("-fx-background-color: #80BEFF; -fx-text-fill: black; -fx-background-radius: 20px; -fx-border-radius: 20px;");
            orderViewButton.setOnAction(e -> {
                appWindow.showCustomerOrdersScene((Customer) user);

            });


            layout.getChildren().addAll(addressSelectionMenu, storeButton, storeScrollPane, orderButton, orderViewButton, logoutButton);
        }
        else {
            ScrollPane orderScrollPane = new ScrollPane();
            orderScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            orderScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            orderScrollPane.setFitToWidth(true);
            orderScrollPane.setContent(orderMenu());

            Button acceptedOrdersButton = new Button("Lihat Pesanan yang diterima");
            acceptedOrdersButton.setStyle("-fx-background-color: #80BEFF; -fx-text-fill: black; -fx-background-radius: 20px; -fx-border-radius: 20px;");
            acceptedOrdersButton.setOnAction(e -> {
                appWindow.showJastiperOrderScene((Jastiper) user);
            });

            layout.getChildren().addAll(acceptedOrdersButton, orderScrollPane, logoutButton);
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