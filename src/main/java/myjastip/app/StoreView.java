package myjastip.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import myjastip.db.DatabaseUtil;
import myjastip.storage.Item;
import myjastip.users.Customer;
import myjastip.users.User;

import java.util.ArrayList;

public class StoreView {

    private final MyJastipWindow appWindow;
    private Scene storeScene;
    private Customer customer;
    private final ArrayList<Item> items = new ArrayList<>();

    public StoreView(MyJastipWindow appWindow) {
        this.appWindow = appWindow;
        DatabaseUtil.insertItems(items);
//        createStoreScene();
    }
    public VBox itemPane(Item item) {
        VBox itemBox = new VBox();
//        itemPane.setStyle("-fx-background-color: black;");
        itemBox.setMaxWidth(200);
//        itemBox.setPrefSize(100,100);
//        Circle circle = new Circle(50,Color.BLUE);
//        circle.relocate(20, 20);
//        Rectangle rectangle = new Rectangle(100,100, Color.RED);
//        rectangle.relocate(70,70);

//        Image image = new Image("https://mit-press-new-us.imgix.net/covers/9780262046305.jpg?auto=format&w=145");

        Image image = new Image(item.getImageUrl());
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);
        StackPane sp = new StackPane(imageView);

        Label itemName = new Label(item.getItemName());
        itemName.setWrapText(true);
        itemName.setStyle("-fx-font-weight: bold;");
        Label itemPrice = new Label("Rp" + item.getBasePrice());
        itemPrice.setWrapText(true);
        Label itemStoreLocation = new Label(item.getStoreLocationName());
        itemStoreLocation.setWrapText(true);
        Label itemCategories = new Label("Kategori: " + String.join(", ", item.getCategories()));
        itemCategories.setPrefWidth(200);
        itemCategories.setWrapText(true);
        Button orderButton = new Button("Pesan");
        orderButton.setOnAction(e -> {
                customer.addToCart(item, 1);
                orderButton.setDisable(true);
            }
        );

//        itemPane.getChildren().addAll(sp);
        itemBox.getChildren().addAll(sp, itemName, itemPrice, itemStoreLocation, itemCategories, orderButton);

        return itemBox;
    }

    public TilePane itemGridPane() {
        TilePane tilePane = new TilePane();

        tilePane.setHgap(10);
        tilePane.setVgap(10);
        tilePane.setPadding(new Insets(15));
        tilePane.setPrefColumns(4);
        tilePane.setPrefWidth(850);
        tilePane.setTileAlignment(Pos.CENTER);

        for (Item i : items) {
            tilePane.getChildren().addAll(itemPane(i));
        }
        return tilePane;
    }

    public void createStoreScene() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Toko");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TilePane itemGrids = itemGridPane();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(itemGrids);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);

        Button logoutButton = new Button("Kembali ke Dashboard");
        logoutButton.setStyle("-fx-background-color: #4067e4; -fx-text-fill: white;  -fx-background-radius: 20px; -fx-border-radius: 20px;");

        logoutButton.setOnAction(e -> appWindow.showDashboardScene(customer));

        layout.getChildren().addAll(welcomeLabel, scrollPane, logoutButton);
        storeScene = new Scene(layout, 1200, 800);
    }

    public Scene getStoreScene() {
        createStoreScene();
        return storeScene;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
