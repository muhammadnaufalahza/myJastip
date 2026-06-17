package myjastip.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import myjastip.db.DatabaseUtil;
import myjastip.storage.Item;
import myjastip.users.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StoreLayout {

    private Customer customer;
    private List<Item> items = new ArrayList<>();

    public StoreLayout(Customer customer) {
        this.customer = customer;
        DatabaseUtil.insertItems(items);
    }

    public VBox itemPane(Item item) {
        VBox itemBox = new VBox(8);
        VBox contentBox = new VBox(8);
        itemBox.setMaxWidth(225);

        Image image = new Image(item.getImageUrl());
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


        Button orderButton = new Button("Pesan");

        orderButton.setOnAction(e -> {
                customer.addToCart(item, 1);
                orderButton.setDisable(true);
            }
        );

        if (customer.getCart().isItemInCart(item)) {
            orderButton.setDisable(true);
        }
        orderButton.setStyle(
            "-fx-font-family: 'Inter';" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: 600;" +
            "-fx-text-fill: white;" +
            "-fx-background-color: linear-gradient(to right, #6B9E7E, #2D5F52);" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 8 16 8 16;" +
            "-fx-cursor: hand;"
        );

        HBox orderButtonBox = new HBox();
        HBox.setHgrow(orderButton, Priority.ALWAYS);
        orderButton.setMaxWidth(Double.MAX_VALUE);

        VBox.setVgrow(orderButtonBox, Priority.ALWAYS);
        orderButtonBox.setMaxHeight(Double.MAX_VALUE);

        orderButtonBox.getChildren().add(orderButton);

        contentBox.getChildren().addAll(sp, itemName, itemPrice, itemStoreLocation, itemCategories);
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
        TilePane tilePane = new TilePane();

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

        layout.getChildren().addAll(titleLabel, scrollPane);

        return layout;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
