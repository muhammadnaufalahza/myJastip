package myjastip.app.admin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import myjastip.app.*;
import myjastip.app.customer.CustomerOrdersLayout;
import myjastip.app.customer.PaymentHistoryLayout;
import myjastip.app.customer.StoreLayout;
import myjastip.db.DatabaseUtil;
import myjastip.location.InvalidCoordinateException;
import myjastip.payment.EmptyOrderException;
import myjastip.payment.EscrowPayment;
import myjastip.payment.Order;
import myjastip.storage.CartItem;
import myjastip.users.Customer;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class AdminDashboardView {

    private final MyJastipWindow appWindow;
    private Scene dashboardScene;
    private Customer customer;
    private BorderPane mainLayout;
    private StoreLayout storeLayout;
    private PaymentHistoryLayout paymentHistoryLayout;
    private CustomerOrdersLayout customerOrdersLayout;


    String inputFieldStyle =
        "-fx-font-family: 'Inter';" +
        "-fx-font-size: 15px;" +
        "-fx-text-fill: #f1f5f0;" +
        "-fx-prompt-text-fill: #6b8570;" +
        "-fx-background-color: rgba(255, 255, 255, 0.06);" +
        "-fx-background-radius: 8;" +
        "-fx-border-color: rgba(255, 255, 255, 0.12);" +
        "-fx-border-radius: 8;" +
        "-fx-padding: 12 16 12 16;";
    String inputFieldFocusStyle =
        "-fx-font-family: 'Inter';" +
        "-fx-font-size: 15px;" +
        "-fx-text-fill: #f1f5f0;" +
        "-fx-prompt-text-fill: #6b8570;" +
        "-fx-background-color: rgba(255, 255, 255, 0.1);" +
        "-fx-background-radius: 8;" +
        "-fx-border-color: #8aad7a;" +
        "-fx-border-radius: 8;" +
        "-fx-padding: 12 16 12 16;" +
        "-fx-effect: dropshadow(gaussian, rgba(107,158,126,0.15), 6, 0, 0, 0);";

    public AdminDashboardView(MyJastipWindow appWindow) {
        this.appWindow = appWindow;
    }

    public VBox cartsMenu() {
        VBox orderBox = new VBox(12);
        VBox cartBox = new VBox(12);
        VBox totalPriceBox = new VBox(12);

        Label transporationFeeLabel = new Label(String.format("%.2f", customer.calculateTransporationFee()));
        Label serviceFeeLabel = new Label(String.format("%.2f", customer.calculateServiceFee()));
        Label totalPriceLabel = new Label(String.format("%.2f", customer.getCart().calculateTotalPrice()));
        Label finalPriceLabel = new Label(String.format("%.2f", customer.calculateFinalPrice()));

        int n = customer.getCart().getCartItems().size();


        for (int i = 0; i < n; i++) {
            CartItem cartItem = customer.getCart().getCartItems().get(i);

            HBox cartItemBox = new HBox(16);

            Label itemNameLabel = new Label(cartItem.getItem().getItemName());
            Label itemQtyLabel = new Label(String.format("%d", cartItem.getQuantity()));
            itemQtyLabel.setAlignment(Pos.CENTER);
            itemQtyLabel.setStyle(
                "-fx-font-family: 'Cascadia Mono';" +
                "-fx-font-size: 16px;" +
                "-fx-text-fill: #f1f5f0;" +
                "-fx-background-color: rgba(107, 158, 126, 0.0);" +
                "-fx-background-radius: 0;" +
                "-fx-min-width: 32;" +
                "-fx-min-height: 32;" +
                "-fx-cursor: hand;"
            );
            Label itemSubTotalLabel = new Label(String.format("%.2f",  cartItem.getSubTotal()));
            Label itemPriceLabel = new Label(String.format("%.2f",  cartItem.getItem().getBasePrice()));

            HBox addSubBox = new HBox(8);
            Button itemQtyAdd = new Button("+");
            itemQtyAdd.setStyle(
                "-fx-font-family: 'Cascadia Mono';" +
                "-fx-font-size: 16px;" +
                "-fx-text-fill: #8aad7a;" +
                "-fx-background-color: rgba(107, 158, 126, 0.08);" +
                "-fx-background-radius: 0;" +
                "-fx-min-width: 32;" +
                "-fx-min-height: 32;" +
                "-fx-cursor: hand;"
            );

            Button itemQtySub = new Button("-");
            itemQtySub.setStyle(
                "-fx-font-family: 'Cascadia Mono';" +
                "-fx-font-size: 16px;" +
                "-fx-text-fill: #8aad7a;" +
                "-fx-background-color: rgba(107, 158, 126, 0.08);" +
                "-fx-background-radius: 0;" +
                "-fx-min-width: 32;" +
                "-fx-min-height: 32;" +
                "-fx-cursor: hand;"
            );
            itemQtyAdd.setOnAction(e -> {
                cartItem.addQuantity(1);
                itemQtyLabel.setText(String.format("%d", cartItem.getQuantity()));
                itemSubTotalLabel.setText(String.format("%.2f",  cartItem.getSubTotal()));
                totalPriceLabel.setText(String.format("%.2f", customer.getCart().calculateTotalPrice()));
                transporationFeeLabel.setText(String.format("%.2f", customer.calculateTransporationFee()));
                serviceFeeLabel.setText(String.format("%.2f", customer.calculateServiceFee()));
                finalPriceLabel.setText(String.format("%.2f", customer.calculateFinalPrice()));

            });
            itemQtySub.setOnAction(e -> {
                if (cartItem.getQuantity() > 1) {
                    cartItem.subtractQuantity(1);
                    itemQtyLabel.setText(String.format("%d", cartItem.getQuantity()));
                    itemSubTotalLabel.setText(String.format("%.2f",  cartItem.getSubTotal()));
                } else {
                    customer.getCart().removeItem(cartItem);
//                    cartBox.getChildren().removeAll(itemNameLabel, addSubBox, itemSubTotalLabel);
                    cartBox.getChildren().remove(cartItemBox);
                }
                totalPriceLabel.setText(String.format("%.2f", customer.getCart().calculateTotalPrice()));
                transporationFeeLabel.setText(String.format("%.2f", customer.calculateTransporationFee()));
                serviceFeeLabel.setText(String.format("%.2f", customer.calculateServiceFee()));
                finalPriceLabel.setText(String.format("%.2f", customer.calculateFinalPrice()));
            });

            HBox.setHgrow(itemNameLabel, Priority.ALWAYS);
            itemNameLabel.setMaxWidth(Double.MAX_VALUE);
            itemNameLabel.setWrapText(true);
            itemNameLabel.setStyle(
                "-fx-font-weight: 600;" +
                "-fx-font-size: 15;" +
                "-fx-text-fill: #f1f5f0;"
            );

            itemSubTotalLabel.setMinWidth(150);
            itemSubTotalLabel.setAlignment(Pos.CENTER_RIGHT);
            itemSubTotalLabel.setStyle(
                "-fx-font-family: 'Cascadia Mono';" +
                "-fx-font-weight: 600;" +
                "-fx-font-size: 15;" +
                "-fx-text-fill: #B5C48E;"

            );

            itemPriceLabel.setMinWidth(150);
            itemPriceLabel.setAlignment(Pos.CENTER_RIGHT);
            itemPriceLabel.setStyle(
                "-fx-font-family: 'Cascadia Mono';" +
                "-fx-font-weight: 600;" +
                "-fx-font-size: 15;" +
                "-fx-color: #B5C48E;"
            );

            addSubBox.getChildren().addAll(itemQtyAdd, itemQtyLabel, itemQtySub);


            cartItemBox.getChildren().addAll(itemNameLabel, addSubBox, itemSubTotalLabel);

            cartItemBox.setStyle(
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

            cartBox.getChildren().add(cartItemBox);

        }


        ScrollPane storeScrollPane = new ScrollPane();
        storeScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        storeScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        storeScrollPane.setFitToWidth(true);
        cartBox.setStyle(
            "-fx-background-color: rgba(30, 50, 45, 1.0);" +
            "-fx-overflow: hidden;"
        );
        storeScrollPane.setContent(cartBox);

        storeScrollPane.setStyle(
            "-fx-background-color: rgba(30, 50, 45, 0.7);" +
            "-fx-background-radius: 16;" +
            "-fx-border-color: rgba(255, 255, 255, 0.08);" +
            "-fx-border-radius: 16;" +
            "-fx-padding: 32;"
        );

        Label totalItemNameLabel = new Label("Total Harga Barang");
        HBox.setHgrow(totalItemNameLabel, Priority.ALWAYS);
        totalItemNameLabel.setMaxWidth(Double.MAX_VALUE);
        Label transportNameLabel = new Label("Biaya Transportasi (10%)");
        HBox.setHgrow(transportNameLabel, Priority.ALWAYS);
        transportNameLabel.setMaxWidth(Double.MAX_VALUE);
        Label serviceNameLabel = new Label("Biaya Layanan (5%)");
        HBox.setHgrow(serviceNameLabel, Priority.ALWAYS);
        serviceNameLabel.setMaxWidth(Double.MAX_VALUE);
        Label totalNameLabel = new Label("Total Pembayaran");
        HBox.setHgrow(totalNameLabel, Priority.ALWAYS);
        totalNameLabel.setMaxWidth(Double.MAX_VALUE);


        HBox totalItemBox = new HBox();
        totalItemBox.getChildren().addAll(totalItemNameLabel,totalPriceLabel);
        HBox transportBox = new HBox();
        transportBox.getChildren().addAll(transportNameLabel,transporationFeeLabel);
        HBox serviceBox = new HBox();
        serviceBox.getChildren().addAll(serviceNameLabel,serviceFeeLabel);
        HBox totalBox = new HBox();
        totalBox.getChildren().addAll(totalNameLabel,finalPriceLabel);

        totalPriceLabel.setAlignment(Pos.CENTER_RIGHT);
        transporationFeeLabel.setAlignment(Pos.CENTER_RIGHT);
        serviceFeeLabel.setAlignment(Pos.CENTER_RIGHT);
        finalPriceLabel.setAlignment(Pos.CENTER_RIGHT);

        String nameStyle =
            "-fx-font-size: 14px;" +
            "-fx-text-fill: #a3b8a0;";

        String numStyle =
            "-fx-font-family: 'Cascadia Mono';" + nameStyle;

        totalItemNameLabel.setStyle(nameStyle);
        transportNameLabel.setStyle(nameStyle);
        serviceNameLabel.setStyle(nameStyle);
        totalNameLabel.setStyle(nameStyle);

        totalPriceLabel.setStyle(numStyle);
        transporationFeeLabel.setStyle(numStyle);
        serviceFeeLabel.setStyle(numStyle);
        finalPriceLabel.setStyle(numStyle);



        totalPriceBox.getChildren().addAll(totalItemBox, transportBox, serviceBox, totalBox);
        totalPriceBox.setStyle(
            "-fx-background-color: linear-gradient(to bottom right, rgba(107,158,126,0.08), rgba(45,95,82,0.04));" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: rgba(255, 255, 255, 0.08);" +
            "-fx-border-radius: 12;" +
            "-fx-padding: 24;"
        );

        orderBox.getChildren().addAll(storeScrollPane, totalPriceBox);

        return orderBox;
    }

    public VBox createCustomerDashboard() {

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);


        HBox userBox = new HBox();

        String username = (customer != null) ? customer.getName() : "null";
        Label welcomeLabel = new Label("Selamat Datang, " + username + "!");
        welcomeLabel.setStyle(
            "-fx-font-family: 'Inter';" +
            "-fx-font-size: 32px;" +
            "-fx-font-weight: 800;" +
            "-fx-text-fill: #8aad7a;"
        );


        Button logoutButton = new Button("Logout");
        logoutButton.setStyle(
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
        logoutButton.setOnAction(e -> appWindow.showLoginScene());

        HBox logoutBox = new HBox();
        logoutBox.getChildren().add(logoutButton);
        HBox.setHgrow(logoutBox, Priority.ALWAYS);
        logoutBox.setMaxWidth(Double.MAX_VALUE);
        logoutBox.setAlignment(Pos.CENTER_RIGHT);

        userBox.getChildren().addAll(welcomeLabel, logoutBox);




        Label balanceTitleLabel = new Label("Saldo:");
        balanceTitleLabel.setStyle(
            "-fx-font-family: 'Inter';" +
            "-fx-font-size: 13px;" +
            "-fx-text-fill: #6b8570;" +
            "-fx-padding: 4 0 0 0;"
        );
        Label balanceLabel = new Label("Rp. " + new BigDecimal(String.valueOf(DatabaseUtil.getUser(customer.getUserId()).getBalance())).toPlainString());
        balanceLabel.setStyle(
            "-fx-font-family: 'Cascadia Code';" +
            "-fx-font-size: 28px;" +
            "-fx-font-weight: 800;" +
            "-fx-text-fill: #8aad7a;"
        );

        VBox saldoCard = new VBox(8);
        saldoCard.getChildren().addAll(balanceTitleLabel, balanceLabel);
        saldoCard.setStyle(
            "-fx-background-color: linear-gradient(to bottom right, rgba(107,158,126,0.08), rgba(45,95,82,0.04));" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: rgba(255, 255, 255, 0.08);" +
            "-fx-border-radius: 12;" +
            "-fx-padding: 24;"
        );

//        Button paymentHistoryButton = new Button("Histori Pembayaran");
//        paymentHistoryButton.setStyle("-fx-background-color: #88FF74; -fx-text-fill: black; -fx-background-radius: 20px; -fx-border-radius: 20px;");
//        paymentHistoryButton.setOnAction(e -> appWindow.showPaymentHistoryScene(customer));


        VBox addressSelectionMenu = new VBox(8);
        HBox addressSelectionInput = new HBox(8);
        Label addrSelectionLabel = new Label("Alamat Tujuan Pesanan ");
        Label addrSelectionSubLabel = new Label("Isi alamat pengiriman untuk pesanan kamu");
        addrSelectionLabel.setStyle(
            "-fx-font-size: 18px;" +
            "-fx-font-weight: 800;" +
            "-fx-text-fill: #f1f5f0;");
        addrSelectionSubLabel.setStyle(
            "-fx-font-size: 13px;" +
            "-fx-text-fill: #6b8570;");

//        GridPane gp = new GridPane();

//        Label setAddressLabel = new Label("Alamat ");
//        Label setLatitudeLabel = new Label("Garis lintang ");
//        Label setLongitudeLabel = new Label("Garis bujur ");

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

//        gp.add(setAddressLabel,  0, 1); gp.add(inputAddress,  1, 1);
//        gp.add(setLatitudeLabel, 0, 2); gp.add(inputLatitude, 1, 2);
//        gp.add(setLongitudeLabel,0, 3); gp.add(inputLongitude,1, 3);

//        addressSelectionMenu.getChildren().addAll(addrSelectionLabel, gp);

        inputAddress.setPromptText("Nama Alamat");
        HBox.setHgrow(inputAddress, Priority.ALWAYS);
        inputAddress.setStyle(inputFieldStyle);
        inputAddress.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                inputAddress.setStyle(inputFieldFocusStyle);
            } else {
                inputAddress.setStyle(inputFieldStyle);
            }
        });


        inputLatitude.setPromptText("Garis Lintang");
        inputLatitude.setStyle(inputFieldStyle);
        inputLatitude.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                inputLatitude.setStyle(inputFieldFocusStyle);
            } else {
                inputLatitude.setStyle(inputFieldStyle);
            }
        });


        inputLongitude.setPromptText("Garis Bujur");
        inputLongitude.setStyle(inputFieldStyle);
        inputLongitude.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                inputLongitude.setStyle(inputFieldFocusStyle);
            } else {
                inputLongitude.setStyle(inputFieldStyle);
            }
        });

        addressSelectionInput.getChildren().addAll(inputAddress, inputLatitude, inputLongitude);

        addressSelectionMenu.getChildren().addAll(addrSelectionLabel, addressSelectionInput);

        addressSelectionMenu.setStyle(
            "-fx-background-color: rgba(30, 50, 45, 0.7);" +
            "-fx-background-radius: 16;" +
            "-fx-border-color: rgba(255, 255, 255, 0.08);" +
            "-fx-border-radius: 16;" +
            "-fx-padding: 32;"
        );

//        Button storeButton = new Button("Toko");
//        storeButton.setStyle("-fx-background-color: #88FF74; -fx-text-fill: black; -fx-background-radius: 20px; -fx-border-radius: 20px;");
//        storeButton.setOnAction(e -> appWindow.showStoreScene(customer));

        double totalPrice = customer.calculateFinalPrice();

        Button orderButton = new Button("Buat Pesanan");

        orderButton.setOnAction(e -> {
            try {
                if (!(inputAddress.getText().isEmpty() || inputLatitude.getText().isEmpty() || inputLongitude.getText().isEmpty())) {
                    customer.setOrderLocation(inputAddress.getText(), Double.parseDouble(inputLatitude.getText()), Double.parseDouble(inputLatitude.getText()));
                } else {
                    throw new InvalidAddressException("Isi Alamat dengan Lengkap!");
                }
                Order order = customer.createOrder();
//                ((GridPane) storeScrollPane.getContent()).getChildren().clear();

                UUID uuid = UUID.randomUUID();
                EscrowPayment payment = new EscrowPayment(uuid.toString(), order.getOrderId(), order.getTotalBill());
                DatabaseUtil.insertPayment(payment);
                appWindow.showPaymentScene(customer, payment);
            } catch (EmptyOrderException | InvalidAddressException | InvalidCoordinateException ex) {
                System.out.println("Error: " + ex.getMessage());
            }

        });
        orderButton.setStyle(
            "-fx-font-family: 'Inter';" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: 600;" +
            "-fx-text-fill: white;" +
            "-fx-background-color: linear-gradient(to right, #6B9E7E, #2D5F52);" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 14 32 14 32;" +
            "-fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, rgba(107,158,126,0.35), 14, 0, 0, 4);"
        );

//        Button orderViewButton = new Button("Lihat Pesanan");
//        orderViewButton.setStyle("-fx-background-color: #80BEFF; -fx-text-fill: black; -fx-background-radius: 20px; -fx-border-radius: 20px;");
//        orderViewButton.setOnAction(e -> {
//            appWindow.showCustomerOrdersScene(customer);
//        });

        layout.getChildren().addAll(userBox, saldoCard, addressSelectionMenu, cartsMenu(), orderButton);


        return layout;
    }


    public void createDashboardScene() {
        mainLayout = new BorderPane();
        VBox sidebar = createSidebar();
        mainLayout.setLeft(sidebar);
        mainLayout.setStyle(
            "-fx-background-color: linear-gradient(to bottom right, #1a2a2e, #263842, #1e3530);"
        );
        showView(createCustomerDashboard());
        dashboardScene = new Scene(mainLayout, 1600, 1000);
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(10); // Vertical layout with 10px spacing
        sidebar.setPadding(new Insets(15));
        sidebar.setPrefWidth(200);

        // Hardcoded basic styling (best practice: migrate to an external CSS stylesheet)
        sidebar.setStyle(
            "-fx-background-color: rgba(22, 36, 38, 0.95);" +
            "-fx-padding: 24;" +
            "-fx-pref-width: 260;" +
            "-fx-min-height: 100%;" +
            "-fx-border-color: transparent rgba(255,255,255,0.08) transparent transparent;" +
            "-fx-border-width: 0 1 0 0;"
        );


        String defaultNavStyle =
                "-fx-font-family: 'Inter';" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: 500;" +
                "-fx-text-fill: #a3b8a0;" +
                "-fx-background-color: transparent;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 10 14 10 14;" +
                "-fx-cursor: hand;" +
                "-fx-alignment: center-left;";

        String activeNavStyle =
                "-fx-font-family: 'Inter';" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: 500;" +
                "-fx-text-fill: #8aad7a;" +
                "-fx-background-color: rgba(107, 158, 126, 0.12);" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 10 14 10 14;" +
                "-fx-cursor: hand;" +
                "-fx-alignment: center-left;";

        // Generate individual interactive navigation nodes
        Button btnDashboard = createNavButton("Dashboard");
        btnDashboard.setStyle(activeNavStyle);
        Button btnStore = createNavButton("Pilihan Pesanan");
        btnStore.setStyle(defaultNavStyle);
        Button btnOrders = createNavButton("Pesanan Saya");
        btnOrders.setStyle(defaultNavStyle);
        Button btnPaymentHistory = createNavButton("Histori Pembayaran");
        btnPaymentHistory.setStyle(defaultNavStyle);

//        // Attach specialized view-swapping actions to button click contexts
        btnDashboard.setOnAction(e -> {
            setCustomer(customer);
            showView(createCustomerDashboard());
            sidebar.getChildren().forEach(node -> node.setStyle(defaultNavStyle));
            btnDashboard.setStyle(activeNavStyle);
        });
        btnStore.setOnAction(e -> {
            storeLayout = new StoreLayout(customer);
            showView(storeLayout.createStoreLayout());
            sidebar.getChildren().forEach(node -> node.setStyle(defaultNavStyle));
            btnStore.setStyle(activeNavStyle);
        });
        btnOrders.setOnAction(e -> {
            customerOrdersLayout = new CustomerOrdersLayout(customer, appWindow);
            showView(customerOrdersLayout.createCustomerOrdersLayout());
            sidebar.getChildren().forEach(node -> node.setStyle(defaultNavStyle));
            btnOrders.setStyle(activeNavStyle);
        });
        btnPaymentHistory.setOnAction(e -> {
            paymentHistoryLayout = new PaymentHistoryLayout(customer);
            showView(paymentHistoryLayout.createPaymentHistoryLayout());
            sidebar.getChildren().forEach(node -> node.setStyle(defaultNavStyle));
            btnPaymentHistory.setStyle(activeNavStyle);
        });

        sidebar.getChildren().addAll(btnDashboard, btnStore, btnOrders, btnPaymentHistory);
        return sidebar;
    }

    private Button createNavButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE); // Force button to stretch to full VBox width
        return btn;
    }

    private void showView(Node node) {
        StackPane viewContainer = new StackPane();
        viewContainer.getChildren().add(node);
        mainLayout.setCenter(viewContainer);
    }

    public Scene getDashboardScene() {
        createDashboardScene();
        return dashboardScene;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}