package myjastip.app.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Pair;
import myjastip.db.DatabaseUtil;
import myjastip.storage.Item;
import myjastip.users.*;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.UnaryOperator;

public class EditItemLayout {
    private TilePane tilePane;
    private List<Item> items = new ArrayList<>();
    private final String defaultImageURL = "https://cdn-app.sealsubscriptions.com/shopify/public/img/promo/no-image-placeholder.png";
    private Admin admin;

    public EditItemLayout(Admin admin) {
        this.admin = admin;
        DatabaseUtil.insertItems(items);
    }

    public VBox itemPane(Item item) {
        VBox itemBox = new VBox(8);
        VBox contentBox = new VBox(8);
        itemBox.setMaxWidth(225);

        Image image;

        try {
            image = new Image(item.getImageUrl());
        } catch ( IllegalArgumentException e) {
            image = new Image(defaultImageURL);
        }

        ImageView imageView = new ImageView(image);


        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);
        StackPane sp = new StackPane(imageView);

        Label itemName = new Label(item.getItemName());
        itemName.setWrapText(true);
        itemName.setStyle(
            "-fx-font-family: 'Inter';" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: 600;" +
            "-fx-text-fill: #f1f5f0;" +
            "-fx-wrap-text: true;"
        );

        Label itemDescription = new Label(item.getDescription());
        itemDescription.setWrapText(true);
        itemDescription.setStyle(
            "-fx-font-family: 'Inter';" +
            "-fx-font-size: 12px;" +
            "-fx-text-fill: #6b8570;"
        );

        Label itemPrice = new Label("Rp" + item.getBasePrice());
        itemPrice.setWrapText(true);
        itemPrice.setStyle(
            "-fx-font-family: 'Inter';" +
            "-fx-font-size: 18px;" +
            "-fx-font-weight: 700;" +
            "-fx-text-fill: #B5C48E;"
        );
        Label itemStoreLocation = new Label(item.getStoreLocationName());
        itemStoreLocation.setWrapText(true);
        itemStoreLocation.setStyle(
            "-fx-font-family: 'Inter';" +
            "-fx-font-size: 12px;" +
            "-fx-text-fill: #6b8570;"
        );

        FlowPane itemCategories = new FlowPane();
        itemCategories.setHgap(4);
        itemCategories.setVgap(4);
        for (String category : item.getCategories()) {
            Label catoegyLabel = new Label(category);
            catoegyLabel.setStyle(
                "-fx-font-family: 'Inter';" +
                "-fx-font-size: 11px;" +
                "-fx-text-fill: #B5C48E;" +
                "-fx-background-color: rgba(107, 158, 126, 0.1);" +
                "-fx-background-radius: 100;" +
                "-fx-padding: 2 8 2 8;"
            );
            itemCategories.getChildren().add(catoegyLabel);
        }


        Button editButton = new Button("Edit");

        editButton.setOnAction(e -> {
            Dialog<Pair<String, Item>> dialog = new Dialog<>();
            dialog.setTitle("myJastip Editor");
            dialog.setHeaderText("Edit Item");

            Image dialogImage = new Image(defaultImageURL);
            ImageView dialogImageView = new ImageView(dialogImage);
            dialogImageView.setFitWidth(50);
            dialogImageView.setFitHeight(50);
            dialogImageView.setPreserveRatio(true);
            dialog.setGraphic(dialogImageView);

            ButtonType editButtonType = new ButtonType("Selesai", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField nameInput = new TextField();
            nameInput.setPromptText("Nama");
            nameInput.setText(item.getItemName());

            TextField descriptionInput = new TextField();
            descriptionInput.setPromptText("Deskripsi");
            descriptionInput.setText(item.getDescription());

            UnaryOperator<TextFormatter.Change> decimalFilter = change -> {
                String newText = change.getControlNewText();
                // Allows empty input, digits, and a optional single decimal dot
                if (newText.matches("\\d*\\.?\\d*")) {
                    return change;
                }
                return null;
            };
            TextField priceInput = new TextField();
            priceInput.setPromptText("Harga");
            priceInput.setTextFormatter(new TextFormatter<>(decimalFilter));
            priceInput.setText(new BigDecimal(String.valueOf(item.getBasePrice())).toPlainString());


            TextField storeLocationInput = new TextField();
            storeLocationInput.setPromptText("Alamat Toko");
            storeLocationInput.setText(item.getStoreLocationName());

            TextField categoryInput = new TextField();
            categoryInput.setPromptText("Kategori");
            categoryInput.setText(item.getCategoriesAsString());

            TextField imageURLInput = new TextField();
            imageURLInput.setPromptText("URL Gambar");
            imageURLInput.setText(item.getImageUrl());


            grid.add(new Label("Nama:"), 0, 0);
            grid.add(nameInput, 1, 0);
            grid.add(new Label("Deskripsi:"), 0, 1);
            grid.add(descriptionInput, 1, 1);
            grid.add(new Label("Harga:"), 0, 2);
            grid.add(priceInput, 1, 2);
            grid.add(new Label("Alamat:"), 0, 3);
            grid.add(storeLocationInput, 1, 3);
            grid.add(new Label("Kategori:"), 0, 4);
            grid.add(categoryInput, 1, 4);
            grid.add(new Label("URL Gambar:"), 0, 5);
            grid.add(imageURLInput, 1, 5);

            Node editButtonNode = dialog.getDialogPane().lookupButton(editButtonType);

            editButtonNode.disableProperty().bind(
                nameInput.textProperty().isEmpty()
                .or(priceInput.textProperty().isEmpty())
                .or(storeLocationInput.textProperty().isEmpty())
            );

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == editButtonType) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<String>>(){}.getType();
                    List<String> categories = gson.fromJson(categoryInput.getText(), listType);

                    double itemPriceParsed = priceInput.getText().isEmpty() ? 0 : Double.parseDouble(priceInput.getText());

                    if (categories == null) {
                        categories = new ArrayList<>();
                    }

                    return new Pair<>(item.getItemId(), new Item(item.getItemId(), nameInput.getText(), descriptionInput.getText(), itemPriceParsed, storeLocationInput.getText(), categories, imageURLInput.getText()));
                }
                return null;
            });


            dialog.getDialogPane().setContent(grid);

            Optional<Pair<String, Item>> result = dialog.showAndWait();

            result.ifPresent(pair -> {
                try {
                    imageView.setImage(new Image(pair.getValue().getImageUrl()));
                } catch (IllegalArgumentException ex) {
                    System.out.println("Error: " + ex.getMessage());
                } finally {
                    admin.editItem(pair.getValue());
                    itemName.setText(pair.getValue().getItemName());
                    itemPrice.setText("Rp" + pair.getValue().getBasePrice());
                    itemStoreLocation.setText(pair.getValue().getStoreLocationName());
                    itemCategories.getChildren().clear();
                    for (String category : pair.getValue().getCategories()) {
                        Label catoegyLabel = new Label(category);
                        catoegyLabel.setStyle(
                                "-fx-font-family: 'Inter';" +
                                        "-fx-font-size: 11px;" +
                                        "-fx-text-fill: #B5C48E;" +
                                        "-fx-background-color: rgba(107, 158, 126, 0.1);" +
                                        "-fx-background-radius: 100;" +
                                        "-fx-padding: 2 8 2 8;"
                        );
                        itemCategories.getChildren().add(catoegyLabel);
                    }

                }

            });
        });

        editButton.setStyle(
            "-fx-font-family: 'Inter';" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: 600;" +
            "-fx-text-fill: white;" +
            "-fx-background-color: linear-gradient(to right, #6B9E7E, #2D5F52);" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 8 16 8 16;" +
            "-fx-cursor: hand;"
        );

        Button deleteButton = new Button("Hapus");

        deleteButton.setOnAction(e -> {
            DatabaseUtil.removeItem(item.getItemId());
            tilePane.getChildren().remove(itemBox);
        });

        deleteButton.setStyle(
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

        VBox orderButtonBox = new VBox(12);
        HBox.setHgrow(editButton, Priority.ALWAYS);
        editButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(deleteButton, Priority.ALWAYS);
        deleteButton.setMaxWidth(Double.MAX_VALUE);

        VBox.setVgrow(orderButtonBox, Priority.ALWAYS);
        orderButtonBox.setMaxHeight(Double.MAX_VALUE);

        orderButtonBox.getChildren().addAll(editButton, deleteButton);

        contentBox.getChildren().addAll(sp, itemName, itemPrice, itemDescription, itemStoreLocation, itemCategories);
        HBox.setHgrow(contentBox, Priority.ALWAYS);
        contentBox.setMaxHeight(Double.MAX_VALUE);
        itemBox.getChildren().addAll(contentBox, orderButtonBox);
        orderButtonBox.setAlignment(Pos.BOTTOM_CENTER);

        itemBox.setStyle(
            "-fx-background-color: rgba(30, 50, 45, 0.7);" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: rgba(255, 255, 255, 0.08);" +
            "-fx-border-radius: 12;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 6, 0, 0, 2);" +
            "-fx-padding: 16 16 16 16;"
        );
        return itemBox;
    }

    public TilePane itemGridPane() {
        tilePane = new TilePane();

        tilePane.setHgap(10);
        tilePane.setVgap(10);
        tilePane.setPadding(new Insets(15));
//        tilePane.setPrefColumns(4);
//        tilePane.setPrefWidth(850);
        tilePane.setTileAlignment(Pos.CENTER);

        for (Item i : items) {
            tilePane.getChildren().addAll(itemPane(i));
        }
//        tilePane.setStyle(
//            "-fx-background-color: rgba(30, 50, 45, 1.0);" +
//            "-fx-overflow: hidden;"
//        );

        return tilePane;
    }

    public VBox createStoreLayout() {

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Pilihan Pesanan");
        titleLabel.setStyle(
            "-fx-font-family: 'Inter';" +
            "-fx-font-size: 32px;" +
            "-fx-font-weight: 800;" +
            "-fx-text-fill: #8aad7a;"
        );

        Button addButton = new Button("Tambah Item");
        addButton.setOnAction(e -> {
            Dialog<Pair<String, Item>> dialog = new Dialog<>();
            dialog.setTitle("myJastip Editor");
            dialog.setHeaderText("Tambah Item");

            Image dialogImage = new Image("https://cdn-app.sealsubscriptions.com/shopify/public/img/promo/no-image-placeholder.png");
            ImageView dialogImageView = new ImageView(dialogImage);
            dialogImageView.setFitWidth(50);
            dialogImageView.setFitHeight(50);
            dialogImageView.setPreserveRatio(true);
            dialog.setGraphic(dialogImageView);

            ButtonType editButtonType = new ButtonType("Selesai", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField nameInput = new TextField();
            nameInput.setPromptText("Nama");

            TextField descriptionInput = new TextField();
            descriptionInput.setPromptText("Deskripsi");

            UnaryOperator<TextFormatter.Change> decimalFilter = change -> {
                String newText = change.getControlNewText();
                // Allows empty input, digits, and a optional single decimal dot
                if (newText.matches("\\d*\\.?\\d*")) {
                    return change;
                }
                return null;
            };
            TextField priceInput = new TextField();
            priceInput.setPromptText("Harga");
            priceInput.setTextFormatter(new TextFormatter<>(decimalFilter));


            TextField storeLocationInput = new TextField();
            storeLocationInput.setPromptText("Alamat Toko");

            TextField categoryInput = new TextField();
            categoryInput.setPromptText("Kategori");

            TextField imageURLInput = new TextField();
            imageURLInput.setPromptText("URL Gambar");
            imageURLInput.setText(defaultImageURL);


            grid.add(new Label("Nama:"), 0, 0);
            grid.add(nameInput, 1, 0);
            grid.add(new Label("Deskripsi:"), 0, 1);
            grid.add(descriptionInput, 1, 1);
            grid.add(new Label("Harga:"), 0, 2);
            grid.add(priceInput, 1, 2);
            grid.add(new Label("Alamat:"), 0, 3);
            grid.add(storeLocationInput, 1, 3);
            grid.add(new Label("Kategori:"), 0, 4);
            grid.add(categoryInput, 1, 4);
            grid.add(new Label("URL Gambar:"), 0, 5);
            grid.add(imageURLInput, 1, 5);

            Node addButtonNode = dialog.getDialogPane().lookupButton(editButtonType);

            addButtonNode.disableProperty().bind(
                nameInput.textProperty().isEmpty()
                .or(priceInput.textProperty().isEmpty())
                .or(storeLocationInput.textProperty().isEmpty())
            );

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == editButtonType) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<String>>(){}.getType();
                    List<String> categories = gson.fromJson(categoryInput.getText(), listType);

                    double itemPrice = priceInput.getText().isEmpty() ? 0 : Double.parseDouble(priceInput.getText());

                    if (categories == null) {
                        categories = new ArrayList<>();
                    }

                    UUID uuid = UUID.randomUUID();
                    String itemId = uuid.toString();
                    return new Pair<>(itemId, new Item(itemId, nameInput.getText(), descriptionInput.getText(), itemPrice, storeLocationInput.getText(), categories, imageURLInput.getText()));
                }
                return null;
            });



            dialog.getDialogPane().setContent(grid);

            Optional<Pair<String, Item>> result = dialog.showAndWait();

            result.ifPresent(pair -> {
                admin.addItem(pair.getValue());
                tilePane.getChildren().addAll(itemPane(pair.getValue()));
            });
        });
        addButton.setStyle(
                "-fx-font-family: 'Inter';" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: 600;" +
                "-fx-text-fill: white;" +
                "-fx-background-color: linear-gradient(to right, #6B9E7E, #2D5F52);" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 8 16 8 16;" +
                "-fx-cursor: hand;"
        );

        ScrollPane scrollPane = new ScrollPane();
        TilePane igp = itemGridPane();
        igp.setBackground(Background.EMPTY);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(igp);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scrollPane.setStyle(
            "-fx-overflow: hidden;" +
            "-fx-background: transparent; -fx-background-color: transparent;"
        );

        layout.getChildren().addAll(titleLabel, addButton, scrollPane);

        return layout;
    }

}
