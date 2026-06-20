package myjastip.app.admin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import myjastip.db.DatabaseUtil;
import myjastip.users.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class EditUsersLayout {
    List<User> users;
    public EditUsersLayout() {
        this.users = new ArrayList<>();
    }

    public VBox userMenu() {
        VBox orderBox = new VBox(32);
        DatabaseUtil.insertUsers(users);
        for (User user : users) {
//            if (user.getUserType() == UserTypes.ADMIN) continue;
            HBox orderMenu = new HBox(12);

            Label idLabel = new Label("ID: " + user.getUserId());

            Label userNameLabel = new Label("Nama: " + user.getName());
            userNameLabel.setStyle(
                "-fx-font-family: 'Inter';" +
                "-fx-font-size: 20px;" +
                "-fx-font-weight: 700;" +
                "-fx-text-fill: #B5C48E;"
            );


            Label emailLabel = new Label("Email: " + user.getEmail());
            emailLabel.setStyle(
                    "-fx-font-family: 'Inter';" +
                    "-fx-font-size: 14px;" +
                    "-fx-text-fill: #6b8570;"
            );

            Label passwordLabel = new Label("Password: " + user.getPassword());
            passwordLabel.setStyle(
                    "-fx-font-family: 'Inter';" +
                    "-fx-font-size: 14px;" +
                    "-fx-text-fill: #6b8570;"
            );

            Label phoneNumberLabel = new Label("Nomor HP: " + user.getPhoneNumber());
            phoneNumberLabel.setStyle(
                    "-fx-font-family: 'Inter';" +
                    "-fx-font-size: 14px;" +
                    "-fx-text-fill: #6b8570;"
            );

            Label balanceLabel = new Label("Saldo: Rp " + new BigDecimal(String.valueOf(user.getBalance())).toPlainString());
            balanceLabel.setStyle(
                    "-fx-font-family: 'Inter';" +
                    "-fx-font-size: 14px;" +
                    "-fx-text-fill: #6b8570;"
            );


            Button deleteUserButton = new Button("Hapus User");
            deleteUserButton.setMinWidth(200);
            if (user.getUserType() == UserTypes.ADMIN) {
                deleteUserButton.setDisable(true);
            }

            deleteUserButton.setOnAction(e -> {
                DatabaseUtil.removeUser(user.getUserId());
                orderBox.getChildren().remove(orderMenu);
            });

            deleteUserButton.setStyle(
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


            Button editUserButton = new Button("Edit User");
            editUserButton.setMinWidth(200);

            editUserButton.setOnAction(e -> {

                Dialog<Pair<String, User>> dialog = new Dialog<>();
                dialog.setTitle("myJastip Editor");
                dialog.setHeaderText("Edit User");

                Image image = new Image("https://cdn-app.sealsubscriptions.com/shopify/public/img/promo/no-image-placeholder.png");
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                imageView.setPreserveRatio(true);
                dialog.setGraphic(imageView);

                ButtonType editButtonType = new ButtonType("Selesai", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                TextField usernameInput = new TextField();
                usernameInput.setPromptText("Username:");
                usernameInput.setText(user.getName());
                TextField passwordInput = new TextField();
                passwordInput.setPromptText("Password:");
                passwordInput.setText(user.getPassword());
                TextField emailInput = new TextField();
                emailInput.setPromptText("Email:");
                emailInput.setText(user.getEmail());

                UnaryOperator<TextFormatter.Change> integerFilter = change -> {
                    String newText = change.getControlNewText();
                    if (newText.matches("\\d*")) {
                        return change; // Accept the change
                    }
                    return null; // Reject the change
                };

                TextField phoneNumberInput = new TextField();
                phoneNumberInput.setPromptText("Nomor Telepon:");
                phoneNumberInput.setTextFormatter(new TextFormatter<>(integerFilter));
                phoneNumberInput.setText(user.getPhoneNumber());


                UnaryOperator<TextFormatter.Change> decimalFilter = change -> {
                    String newText = change.getControlNewText();
                    // Allows empty input, digits, and a optional single decimal dot
                    if (newText.matches("\\d*\\.?\\d*")) {
                        return change;
                    }
                    return null;
                };

                TextField balanceInput = new TextField();
                balanceInput.setPromptText("Saldo:");
                balanceInput.setTextFormatter(new TextFormatter<>(decimalFilter));
                balanceInput.setText(new BigDecimal(String.valueOf(user.getBalance())).toPlainString());


                grid.add(new Label("Username:"), 0, 0);
                grid.add(usernameInput, 1, 0);
                grid.add(new Label("Email:"), 0, 1);
                grid.add(emailInput, 1, 1);
                grid.add(new Label("Password:"), 0, 2);
                grid.add(passwordInput, 1, 2);
                grid.add(new Label("No. Telepon:"), 0, 3);
                grid.add(phoneNumberInput, 1, 3);
                grid.add(new Label("Saldo:"), 0, 4);
                grid.add(balanceInput, 1, 4);

                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == editButtonType) {
                        DatabaseUtil.changeUser(user.getUserId(), usernameInput.getText(), emailInput.getText(), passwordInput.getText(), phoneNumberInput.getText(), Double.parseDouble(balanceInput.getText()));
                        if (user.getUserType() == UserTypes.CUSTOMER) {
                            return new Pair<>(user.getUserId(), new Customer(user.getUserId(), usernameInput.getText(), emailInput.getText(), passwordInput.getText(), phoneNumberInput.getText(), Double.parseDouble(balanceInput.getText())));
                        } else if (user.getUserType() == UserTypes.JASTIPER) {
                            return new Pair<>(user.getUserId(), new Jastiper(user.getUserId(), usernameInput.getText(), emailInput.getText(), passwordInput.getText(), phoneNumberInput.getText(), Double.parseDouble(balanceInput.getText())));
                        } else {
                            return new Pair<>(user.getUserId(), new Admin(user.getUserId(), usernameInput.getText(), emailInput.getText(), passwordInput.getText(), phoneNumberInput.getText(), Double.parseDouble(balanceInput.getText())));
                        }
                    }
                    return null;
                });


                dialog.getDialogPane().setContent(grid);

                Optional<Pair<String, User>> result = dialog.showAndWait();

                result.ifPresent(pair -> {
                    userNameLabel.setText("Nama: " + pair.getValue().getName());
                    emailLabel.setText("Email: " + pair.getValue().getEmail());
                    passwordLabel.setText("Password: " + pair.getValue().getPassword());
                    phoneNumberLabel.setText("Nomor HP: " + pair.getValue().getPhoneNumber());
                    balanceLabel.setText("Saldo: Rp " + new BigDecimal(String.valueOf(pair.getValue().getBalance())).toPlainString());
                });

            });

            editUserButton.setStyle(
                "-fx-font-family: 'Inter';" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: 600;" +
                "-fx-text-fill: white;" +
                "-fx-background-color: linear-gradient(to right, #22c55e, #10b981);" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 8 16 8 16;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(34,197,94,0.3), 14, 0, 0, 4);"
            );



            idLabel.setStyle(
                "-fx-font-family: 'Courier New';" +
                "-fx-font-size: 12px;" +
                "-fx-text-fill: #6b8570;"
            );

            VBox orderSpec = new VBox(16);
            HBox.setHgrow(orderSpec, Priority.ALWAYS);

            HBox controls = new HBox(12);

            VBox infoBox = new VBox(12);
            infoBox.getChildren().addAll(emailLabel, passwordLabel, phoneNumberLabel, balanceLabel);

            HBox rightControl = new HBox(12);
            rightControl.setAlignment(Pos.BOTTOM_RIGHT);
            HBox.setHgrow(rightControl, Priority.ALWAYS);
            rightControl.setMaxWidth(Double.MAX_VALUE);

            rightControl.getChildren().addAll(deleteUserButton, editUserButton);

            controls.getChildren().addAll(infoBox, rightControl);


//            locationLabel.setAlignment(Pos.CENTER_LEFT);
            orderSpec.getChildren().addAll(idLabel, userNameLabel, controls);
            orderMenu.getChildren().add(orderSpec);
            orderMenu.setStyle(
                "-fx-background-color: rgba(30, 50, 45, 0.7);" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: rgba(255, 255, 255, 0.08);" +
                "-fx-border-radius: 12;" +
                "-fx-padding: 24;"
//                "-fx-alignment: center-left;"
            );
            orderBox.getChildren().add(orderMenu);
        }
        return orderBox;
    }

    public VBox createEditUserLayout() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Edit User");
        titleLabel.setStyle(
            "-fx-font-family: 'Inter';" +
            "-fx-font-size: 32px;" +
            "-fx-font-weight: 800;" +
            "-fx-text-fill: #8aad7a;"
        );

        ScrollPane userScrollPane = new ScrollPane();
        userScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        userScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        userScrollPane.setFitToWidth(true);
        userScrollPane.setContent(userMenu());
        userScrollPane.setStyle(
            "-fx-overflow: hidden;" +
            "-fx-background: transparent; -fx-background-color: transparent;"
        );
        layout.getChildren().addAll(titleLabel, userScrollPane);
        return layout;
    }


}
