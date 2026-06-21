package myjastip.app.admin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import myjastip.app.*;
import myjastip.app.customer.PaymentHistoryLayout;
import myjastip.users.Admin;

public class AdminDashboardView {

    private final MyJastipWindow appWindow;
    private Scene dashboardScene;
    private Admin admin;
    private BorderPane mainLayout;
    private EditItemLayout editItemLayout;
    private EditUsersLayout editUsersLayout;
    private EditOrdersLayout editOrdersLayout;


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

    public VBox createAdminDashboard() {

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);


        HBox userBox = new HBox();

        Label welcomeLabel = new Label("Akun admin: " + admin.getName());
        welcomeLabel.setStyle(
            "-fx-font-family: 'Google Sans Code';" +
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

        layout.getChildren().addAll(userBox);

        return layout;
    }


    public void createDashboardScene() {
        mainLayout = new BorderPane();
        VBox sidebar = createSidebar();
        mainLayout.setLeft(sidebar);
        mainLayout.setStyle(
            "-fx-background-color: linear-gradient(to bottom right, #1a2a2e, #263842, #1e3530);"
        );
        showView(createAdminDashboard());
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
        Button btnItemEdit = createNavButton("Edit Barang");
        btnItemEdit.setStyle(defaultNavStyle);
        Button btnUserEdit = createNavButton("Edit User");
        btnUserEdit.setStyle(defaultNavStyle);
        Button btnOrderEdit = createNavButton("Edit Pesanan");
        btnOrderEdit.setStyle(defaultNavStyle);

//        // Attach specialized view-swapping actions to button click contexts
        btnDashboard.setOnAction(e -> {
            showView(createAdminDashboard());
            sidebar.getChildren().forEach(node -> node.setStyle(defaultNavStyle));
            btnDashboard.setStyle(activeNavStyle);
        });
        btnItemEdit.setOnAction(e -> {
            editItemLayout = new EditItemLayout(admin);
            showView(editItemLayout.createStoreLayout());
            sidebar.getChildren().forEach(node -> node.setStyle(defaultNavStyle));
            btnItemEdit.setStyle(activeNavStyle);
        });
        btnUserEdit.setOnAction(e -> {
            editUsersLayout = new EditUsersLayout(admin);
            showView(editUsersLayout.createEditUserLayout());
            sidebar.getChildren().forEach(node -> node.setStyle(defaultNavStyle));
            btnUserEdit.setStyle(activeNavStyle);
        });
        btnOrderEdit.setOnAction(e -> {
            editOrdersLayout = new EditOrdersLayout(admin);
            showView(editOrdersLayout.createEditOrdersLayout());
            sidebar.getChildren().forEach(node -> node.setStyle(defaultNavStyle));
            btnOrderEdit.setStyle(activeNavStyle);
        });

        sidebar.getChildren().addAll(btnDashboard, btnItemEdit, btnUserEdit, btnOrderEdit);
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

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}