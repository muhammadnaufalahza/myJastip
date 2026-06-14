package myjastip.app;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Pair;
import myjastip.db.DatabaseUtil;
import myjastip.payment.Order;
import myjastip.storage.Cart;
import myjastip.users.Customer;
import myjastip.users.Jastiper;
import myjastip.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.UnaryOperator;

public class AuthView {

    private final MyJastipWindow appWindow;
    private Scene loginScene;
    private Scene registerScene;

    public AuthView(MyJastipWindow appWindow) {
        this.appWindow = appWindow;
        createLoginScene();
        createRegisterScene();
    }

    private void createLoginScene() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-background-radius: 20px; -fx-border-radius: 20px;");

        TextField usernameInput = new TextField();
        usernameInput.setPromptText("Username");

        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Password");

        Button loginButton = new Button("Masuk");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 20px; -fx-border-radius: 20px;");

        Hyperlink registerLink = new Hyperlink("Belum punya akun? Daftar");

        registerLink.setOnAction(e -> appWindow.showRegisterScene());

        loginButton.setOnAction(e -> {
            String name = usernameInput.getText();
            String pass = passwordInput.getText();

            try {
                if (!(name.isEmpty() || pass.isEmpty())) {
                    String userId = DatabaseUtil.getUserId(name, pass);
                    User user = DatabaseUtil.getUser(userId);
                    if (user != null) {
                        appWindow.showDashboardScene(user);
                    } else {
                        throw new UserNotFoundException("User atau Password Salah!");
                    }
                } else {
                    throw new InvalidAuthException("Isi Username dan Password!");
                }
            } catch (InvalidAuthException | UserNotFoundException ex) {
                System.out.println("Error: " + ex.getMessage());
            }

        });

        layout.getChildren().addAll(titleLabel, usernameInput, passwordInput, loginButton, registerLink);
        loginScene = new Scene(layout, 600, 400);
    }

    private void createRegisterScene() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Register");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField usernameInput = new TextField();
        usernameInput.setPromptText("Username");

        TextField emailInput = new TextField();
        emailInput.setPromptText("Email");

        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Password");

        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change; // Accept the change
            }
            return null; // Reject the change
        };

        TextField phoneNumberInput = new TextField();
        phoneNumberInput.setPromptText("Nomor Telepon");
        phoneNumberInput.setTextFormatter(new TextFormatter<>(integerFilter));

        ComboBox<String> registerAsInput = new ComboBox<>();
        registerAsInput.getItems().addAll("Customer","Jastiper");
        registerAsInput.setPromptText("Pilih Jenis Akun");

        UnaryOperator<TextFormatter.Change> decimalFilter = change -> {
            String newText = change.getControlNewText();
            // Allows empty input, digits, and a optional single decimal dot
            if (newText.matches("\\d*\\.?\\d*")) {
                return change;
            }
            return null;
        };

        TextField balanceInput = new TextField();
        balanceInput.setPromptText("Saldo Awal");
        balanceInput.setTextFormatter(new TextFormatter<>(decimalFilter));

        Button registerButton = new Button("Daftar Akun");
        registerButton.setMaxWidth(Double.MAX_VALUE);
        registerButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");

        Hyperlink loginLink = new Hyperlink("Sudah punya akun? Login");

        loginLink.setOnAction(e -> appWindow.showLoginScene());
        registerButton.setOnAction(e -> {
            try {
                String username = usernameInput.getText();
                String email = emailInput.getText();
                String password = passwordInput.getText();
                String phoneNumber = phoneNumberInput.getText();
                String account = registerAsInput.getValue();
                String balance = balanceInput.getText();
                if (
                    usernameInput.getText().isEmpty() ||
                    email.isEmpty() ||
                    password.isEmpty() ||
                    phoneNumber.isEmpty() ||
                    account == null ||
                    balance.isEmpty()
                ) {
                    throw new InvalidAuthException("Isi semua field!");
                } else {
                    UUID uuid = UUID.randomUUID();
                    if (registerAsInput.getValue().equals("Jastiper")) {
                        Jastiper jastiper = new Jastiper(uuid.toString(), username, email, password, phoneNumber, Double.parseDouble(balance));
                        DatabaseUtil.insertUser(jastiper);
                        appWindow.showDashboardScene(jastiper);
                    } else {
                        Customer customer = new Customer(uuid.toString(), username, email, password, phoneNumber, Double.parseDouble(balance));
                        DatabaseUtil.insertUser(customer);
                        appWindow.showDashboardScene(customer);
                    }

                }
            } catch (InvalidAuthException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(titleLabel, usernameInput, emailInput, passwordInput, phoneNumberInput, registerAsInput, balanceInput, registerButton, loginLink);
        registerScene = new Scene(layout, 600, 400);
    }



    public Scene getLoginScene() {
        return loginScene;
    }

    public Scene getRegisterScene() {
        return registerScene;
    }
}
