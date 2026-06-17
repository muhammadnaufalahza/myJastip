package myjastip.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import myjastip.db.DatabaseUtil;
import myjastip.users.Customer;
import myjastip.users.Jastiper;
import myjastip.users.User;

import java.util.UUID;
import java.util.function.UnaryOperator;

public class AuthView {

    private final MyJastipWindow appWindow;
    private Scene loginScene;
    private Scene registerScene;

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


    public AuthView(MyJastipWindow appWindow) {
        this.appWindow = appWindow;
        createLoginScene();
        createRegisterScene();
    }

    private void createLoginScene() {
        StackPane mainLayout = new StackPane();
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);
        layout.setMaxSize(400,600);

        Label titleLabel = new Label("Login");
        titleLabel.setStyle(
            "-fx-font-family: 'Inter';" +
            "-fx-font-size: 28px;" +
            "-fx-font-weight: 800;" +
            "-fx-text-fill: #8aad7a;"
        );

        Label subtitleLabel = new Label("Masuk ke akun kamu");
        subtitleLabel.setStyle(
            "-fx-font-family: 'Inter';" +
            "-fx-font-size: 14px;" +
            "-fx-text-fill: #6b8570;"
        );


        TextField usernameInput = new TextField();
        usernameInput.setPromptText("Username");
        usernameInput.setStyle(inputFieldStyle);

        usernameInput.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                usernameInput.setStyle(inputFieldFocusStyle);
            } else {
                usernameInput.setStyle(inputFieldStyle);
            }
        });

        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Password");
        passwordInput.setStyle(inputFieldStyle);

        passwordInput.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                passwordInput.setStyle(inputFieldFocusStyle);
            } else {
                passwordInput.setStyle(inputFieldStyle);
            }
        });


        Button loginButton = new Button("Masuk");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setStyle(
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


        Hyperlink registerLink = new Hyperlink("Belum punya akun? Daftar");
        registerLink.setStyle(
            "-fx-font-family: 'Inter';" +
            "-fx-font-size: 14px;" +
            "-fx-text-fill: #8aad7a;" +
            "-fx-border-color: transparent;"
        );
        registerLink.setOnAction(e -> appWindow.showRegisterScene());

        loginButton.setOnAction(e -> {
            String name = usernameInput.getText();
            String pass = passwordInput.getText();

            try {
                if (!(name.isEmpty() || pass.isEmpty())) {
                    String userId = DatabaseUtil.getUserId(name, pass);
                    User user = DatabaseUtil.getUser(userId);
                    if (user != null) {
//                        appWindow.showDashboardScene(user);
                        if (user instanceof Customer) {
                            appWindow.showCustomerDashboardScene((Customer) user);
                        } else if (user instanceof Jastiper) {
                            appWindow.showJastiperDashboardScene((Jastiper) user);
                        }
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

        layout.getChildren().addAll(titleLabel, subtitleLabel, usernameInput, passwordInput, loginButton, registerLink);

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

        loginScene = new Scene(mainLayout, 1600, 1000);
    }

    private void createRegisterScene() {
        StackPane mainLayout = new StackPane();
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);
        layout.setMaxSize(400,600);

        Label titleLabel = new Label("Register");
        titleLabel.setStyle(
            "-fx-font-family: 'Inter';" +
            "-fx-font-size: 28px;" +
            "-fx-font-weight: 800;" +
            "-fx-text-fill: #8aad7a;"
        );

        Label subtitleLabel = new Label("Bergabung dengan myJastip sekarang");
        subtitleLabel.setStyle(
            "-fx-font-family: 'Inter';" +
            "-fx-font-size: 14px;" +
            "-fx-text-fill: #6b8570;"
        );

        TextField usernameInput = new TextField();
        usernameInput.setPromptText("Username");
        usernameInput.setStyle(inputFieldStyle);
        usernameInput.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                usernameInput.setStyle(inputFieldFocusStyle);
            } else {
                usernameInput.setStyle(inputFieldStyle);
            }
        });

        TextField emailInput = new TextField();
        emailInput.setPromptText("Email");
        emailInput.setStyle(inputFieldStyle);
        emailInput.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                emailInput.setStyle(inputFieldFocusStyle);
            } else {
                emailInput.setStyle(inputFieldStyle);
            }
        });

        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Password");
        passwordInput.setStyle(inputFieldStyle);
        passwordInput.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                passwordInput.setStyle(inputFieldFocusStyle);
            } else {
                passwordInput.setStyle(inputFieldStyle);
            }
        });

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
        phoneNumberInput.setStyle(inputFieldStyle);
        phoneNumberInput.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                passwordInput.setStyle(inputFieldFocusStyle);
            } else {
                passwordInput.setStyle(inputFieldStyle);
            }
        });

        ComboBox<String> registerAsInput = new ComboBox<>();
        registerAsInput.getItems().addAll("Customer","Jastiper");
        registerAsInput.setPromptText("Pilih Jenis Akun");
        registerAsInput.setStyle(
            "-fx-font-family: 'Inter';" +
            "-fx-font-size: 15px;" +
            "-fx-text-fill: #f1f5f0;" +
            "-fx-background-color: rgba(255, 255, 255, 0.06);" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: rgba(255, 255, 255, 0.12);" +
            "-fx-border-radius: 8;" +
            "-fx-padding: 10 16 10 16;"
        );

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
                        appWindow.showJastiperDashboardScene(jastiper);
                    } else {
                        Customer customer = new Customer(uuid.toString(), username, email, password, phoneNumber, Double.parseDouble(balance));
                        DatabaseUtil.insertUser(customer);
//                        appWindow.showDashboardScene(customer);
                        appWindow.showCustomerDashboardScene(customer);
                    }

                }
            } catch (InvalidAuthException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(titleLabel, usernameInput, emailInput, passwordInput, phoneNumberInput, registerAsInput, balanceInput, registerButton, loginLink);
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

        registerScene = new Scene(mainLayout, 1600, 1000);
    }

    public Scene getLoginScene() {
        return loginScene;
    }

    public Scene getRegisterScene() {
        return registerScene;
    }
}
