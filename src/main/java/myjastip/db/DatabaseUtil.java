package myjastip.db;

import com.google.gson.Gson;
import myjastip.app.InvalidAuthException;
import myjastip.location.Location;
import myjastip.payment.Order;
import myjastip.payment.OrderStatus;
import myjastip.payment.EscrowPayment;
import myjastip.payment.PaymentStatus;
import myjastip.storage.Cart;
import myjastip.storage.Item;
import myjastip.users.*;
import org.postgresql.util.PSQLException;

import java.sql.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseUtil {
    public static Connection getConnection() throws SQLException {
        String URL = "jdbc:postgresql://aws-1-ap-south-1.pooler.supabase.com:6543/postgres?user=postgres.stmeucoddhqzfblbtrne&password=" + System.getenv("SUPABASE_DB_PASSWORD");
        return DriverManager.getConnection(URL);
    }

    public static void insertUser(User user) throws InvalidAuthException {
        try {
            Connection connection = getConnection();

            String query = String.format("INSERT INTO users (id, name, email, password, phone_number, balance, account_type) VALUES ('%s', '%s', '%s', '%s', '%s', %f, '%s');", user.getUserId(), user.getName(), user.getEmail(), user.getPassword(), user.getPhoneNumber(), user.getBalance(), user.getUserType().toString());
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.executeUpdate();

        } catch (PSQLException e) {
            System.out.println("Error pada PSQLException pada insertUser(): " + e.getMessage());
            throw new InvalidAuthException("Gagal autentikasi");
        } catch (Exception e) {
            System.out.println("Terjadi Error pada insertUser(): " + e.getMessage());
        }
    }

    public static String getUserId(String name, String pass) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("SELECT id FROM users WHERE name = '%s' AND password = '%s'", name, pass);
            var resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getString("id");
            } else {
                return null;
            }
        } catch (PSQLException e) {
            System.out.println("Error PSQLException pada getUserId(): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi Error pada getUserId(): " + e.getMessage());
        }
        return null;
    }

    public static User getUser(String userId) {
        if (userId == null) return null;
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("SELECT * FROM users WHERE id = '%s'", userId);
            var resultSet = statement.executeQuery(query);
            if (resultSet.next()) {

                String userName = resultSet.getString("name");
                String userEmail = resultSet.getString("email");
                String userPassword = resultSet.getString("password");
                String userPhoneNumber = resultSet.getString("phone_number");
                String accountType = resultSet.getString("account_type");
                double balance = resultSet.getDouble("balance");

                if (accountType.equals(UserTypes.JASTIPER.toString())) {
                    return new Jastiper(userId, userName, userEmail, userPassword, userPhoneNumber, balance);
                } else if (accountType.equals(UserTypes.CUSTOMER.toString())) {
                    return new Customer(userId, userName, userEmail, userPassword, userPhoneNumber, balance);
                } else {
                    return new Admin(userId, userName, userEmail, userPassword, userPhoneNumber, balance);
                }
            }


        } catch (PSQLException e) {
            System.out.println("Error PSQLException pada getUser(): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi Error pada getUser()" + e.getMessage());
        }
        return null;
    }

    public static void changeUserBalance(String userId, double newBalance) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("UPDATE users SET balance = %f WHERE id = '%s'", newBalance, userId);
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.executeUpdate();


        } catch (PSQLException e) {
            System.out.println("Error PSQLException pada changeUserBalance(): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi Error pada changeUserBalance(): " + e.getMessage());
        }
    }




    public static void insertItems(List<Item> items) {
        items.clear();
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM \"items\";";
            var resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String itemId = resultSet.getString("id");
                String itemName = resultSet.getString("name");
                String itemDescription = resultSet.getString("description");
                double basePrice = resultSet.getDouble("base_price");
                String storeLocationName = resultSet.getString("store_location_name");
                Array categories = resultSet.getArray("categories");
                String imageUrl = resultSet.getString("image_url");


                if (categories != null) {
                    String[] javaArray = (String[]) categories.getArray();
                    ArrayList<String> itemCategories = new ArrayList<String>(Arrays.asList(javaArray));
                    items.add(new Item(itemId, itemName, itemDescription, basePrice, storeLocationName, itemCategories, imageUrl));
                } else {
                    items.add(new Item(itemId, itemName, itemDescription, basePrice, storeLocationName, new ArrayList<String>(), imageUrl));
                }

            }
        } catch (PSQLException e) {
            System.out.println("Error pada PSQLException pada insertItems(): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi Error pada insertItems(): " + e.getMessage());

        }
    }


    public static void insertOrder(Order order) {
//        OrderStatus status, String locationName, double locationLatitude, double locationLongitude, double totalItemPrice, double transportationFee, double serviceFee, String recieverId, Cart cart
        try {
            Connection connection = getConnection();

            Gson cartGson = new Gson();
            String cartJson = cartGson.toJson(order.getOrderedCart());

            String query = String.format("INSERT INTO orders (id, status, location_name, location_latitude, location_longitude, total_item_price, transportation_fee, service_fee, receiver_id, order_items) VALUES ('%s', '%s', '%s', %f, %f, %f, %f, %f, '%s', '%s');", order.getOrderId(), order.getOrderStatus(), order.getLocation().getLocationName(), order.getLocation().getLatitude(), order.getLocation().getLongitude(), order.getTotalItemPrice(), order.getTransportationFee(), order.getServiceFee(), order.getReceiverId(), cartJson);
            PreparedStatement pstmt = connection.prepareStatement(query);
            int rowsInserted = pstmt.executeUpdate();

        } catch (PSQLException e) {
            System.out.println("Error pada PSQLException pada insertOrder(): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi Error pada insertOrder(): " + e.getMessage());
        }
    }


    public static void insertOrders(List<Order> orders) {
        orders.clear();
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM \"orders\";";
            var resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String orderId = resultSet.getString("id");
                String orderStatus = resultSet.getString("status");
                String locationName = resultSet.getString("location_name");
                double locationLatitude = resultSet.getDouble("location_latitude");
                double locationLongitude = resultSet.getDouble("location_longitude");
                double totalItemPrice = resultSet.getDouble("total_item_price");
                double transportationFee = resultSet.getDouble("total_item_price");
                double serviceFee = resultSet.getDouble("service_fee");
                String rawOrderItems = resultSet.getString("order_items");
                String receiverId = resultSet.getString("receiver_id");

                Gson orderGson = new Gson();
                Cart cart = orderGson.fromJson(rawOrderItems, Cart.class);

                orders.add(new Order(orderId, OrderStatus.valueOf(orderStatus), new Location(locationName, locationLatitude, locationLongitude), totalItemPrice, transportationFee, serviceFee, receiverId, cart));

            }
        } catch (PSQLException e) {
            System.out.println("Error pada PSQLException pada insertItems(): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi Error pada insertItems(): " + e.getMessage());
        }
    }

    public static void insertOrdersByReceiverId(List<Order> orders, String userId) {
        orders.clear();
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("SELECT * FROM orders WHERE receiver_id = '%s'", userId);

            var resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String orderId = resultSet.getString("id");
                String orderStatus = resultSet.getString("status");
                String locationName = resultSet.getString("location_name");
                double locationLatitude = resultSet.getDouble("location_latitude");
                double locationLongitude = resultSet.getDouble("location_longitude");
                double totalItemPrice = resultSet.getDouble("total_item_price");
                double transportationFee = resultSet.getDouble("total_item_price");
                double serviceFee = resultSet.getDouble("service_fee");
                String rawOrderItems = resultSet.getString("order_items");
                String receiverId = resultSet.getString("receiver_id");

                Gson orderGson = new Gson();
                Cart cart = orderGson.fromJson(rawOrderItems, Cart.class);

                orders.add(new Order(orderId, OrderStatus.valueOf(orderStatus), new Location(locationName, locationLatitude, locationLongitude), totalItemPrice, transportationFee, serviceFee, receiverId, cart));

            }
        } catch (PSQLException e) {
            System.out.println("Error pada PSQLException pada insertOrdersByReceiverId()\n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi Error pada insertOrdersByReceiverId()\n" + e.getMessage());

        }
    }
    public static void insertOrdersByJastiperId(List<Order> orders, String userId) {
        orders.clear();
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("SELECT * FROM orders WHERE jastiper_id = '%s'", userId);

            var resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String orderId = resultSet.getString("id");
                String orderStatus = resultSet.getString("status");
                String locationName = resultSet.getString("location_name");
                double locationLatitude = resultSet.getDouble("location_latitude");
                double locationLongitude = resultSet.getDouble("location_longitude");
                double totalItemPrice = resultSet.getDouble("total_item_price");
                double transportationFee = resultSet.getDouble("total_item_price");
                double serviceFee = resultSet.getDouble("service_fee");
                String rawOrderItems = resultSet.getString("order_items");
                String receiverId = resultSet.getString("receiver_id");
                String jastiperId = resultSet.getString("jastiper_id");

                Gson orderGson = new Gson();
                Cart cart = orderGson.fromJson(rawOrderItems, Cart.class);

                orders.add(new Order(orderId, OrderStatus.valueOf(orderStatus), new Location(locationName, locationLatitude, locationLongitude), totalItemPrice, transportationFee, serviceFee, receiverId, cart, jastiperId));

            }
        } catch (PSQLException e) {
            System.out.println("Error pada PSQLException pada insertOrdersByJastiperId()\n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi Error pada insertOrdersByJastiperId()\n" + e.getMessage());

        }
    }


    public static Order getOrderByReceiverId(String userId) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("SELECT * FROM orders WHERE receiver_id = '%s'", userId);
            var resultSet = statement.executeQuery(query);
            resultSet.next();
            String orderId = resultSet.getString("id");
            String orderStatus = resultSet.getString("status");
            String locationName = resultSet.getString("location_name");
            double locationLatitude = resultSet.getDouble("location_latitude");
            double locationLongitude = resultSet.getDouble("location_longitude");
            double totalItemPrice = resultSet.getDouble("total_item_price");
            double transportationFee = resultSet.getDouble("total_item_price");
            double serviceFee = resultSet.getDouble("service_fee");
            String rawOrderItems = resultSet.getString("order_items");
            String receiverId = resultSet.getString("receiver_id");

            Gson orderGson = new Gson();
            Cart cart = orderGson.fromJson(rawOrderItems, Cart.class);

            return new Order(orderId, OrderStatus.valueOf(orderStatus), new Location(locationName, locationLatitude, locationLongitude), totalItemPrice, transportationFee, serviceFee, receiverId, cart);

        } catch (PSQLException e) {
            System.out.println("Error PSQLException pada getOrderByReceiverId(); " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi Error pada getOrderByReceiverId(); " + e.getMessage());
        }
        return null;
    }

    public static Order getOrderByJastiperId(String userId) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("SELECT * FROM orders WHERE jastiper_id = '%s'", userId);
            var resultSet = statement.executeQuery(query);
            resultSet.next();
            String orderId = resultSet.getString("id");
            String orderStatus = resultSet.getString("status");
            String locationName = resultSet.getString("location_name");
            double locationLatitude = resultSet.getDouble("location_latitude");
            double locationLongitude = resultSet.getDouble("location_longitude");
            double totalItemPrice = resultSet.getDouble("total_item_price");
            double transportationFee = resultSet.getDouble("total_item_price");
            double serviceFee = resultSet.getDouble("service_fee");
            String rawOrderItems = resultSet.getString("order_items");
            String receiverId = resultSet.getString("receiver_id");
            String jastiperId = resultSet.getString("jastiper_id");

            Gson orderGson = new Gson();
            Cart cart = orderGson.fromJson(rawOrderItems, Cart.class);

            return new Order(orderId, OrderStatus.valueOf(orderStatus), new Location(locationName, locationLatitude, locationLongitude), totalItemPrice, transportationFee, serviceFee, receiverId, cart, jastiperId);

        } catch (PSQLException e) {
            System.out.println("Error PSQLException pada getOrderByJastiperId(); " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi Error pada getOrderByJastiperId(); " + e.getMessage());
        }
        return null;
    }
    public static Order getOrder(String orderId) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("SELECT * FROM orders WHERE id = '%s'", orderId);
            var resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String orderStatus = resultSet.getString("status");
                String locationName = resultSet.getString("location_name");
                double locationLatitude = resultSet.getDouble("location_latitude");
                double locationLongitude = resultSet.getDouble("location_longitude");
                double totalItemPrice = resultSet.getDouble("total_item_price");
                double transportationFee = resultSet.getDouble("total_item_price");
                double serviceFee = resultSet.getDouble("service_fee");
                String rawOrderItems = resultSet.getString("order_items");
                String receiverId = resultSet.getString("receiver_id");
                String jastiperId = resultSet.getString("jastiper_id");

                Gson orderGson = new Gson();
                Cart cart = orderGson.fromJson(rawOrderItems, Cart.class);

                return new Order(orderId, OrderStatus.valueOf(orderStatus), new Location(locationName, locationLatitude, locationLongitude), totalItemPrice, transportationFee, serviceFee, receiverId, cart, jastiperId);
            }
            return null;
        } catch (PSQLException e) {
            System.out.println("Error PSQLException pada getOrder(): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi Error pada getOrder(): " + e.getMessage());
        }
        return null;
    }

    public static void changeOrderStatus(String orderId, OrderStatus status) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("UPDATE orders SET status = '%s' WHERE id = '%s'", status, orderId);
            PreparedStatement pstmt = connection.prepareStatement(query);
            int rowsInserted = pstmt.executeUpdate();


        } catch (PSQLException e) {
            System.out.println("Error PSQLException pada changeOrderStatus(): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi Error pada changeOrderStatus(): " + e.getMessage());
        }
    }

    public static void addJastiperId(String orderId, String userId) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("UPDATE orders SET jastiper_id = '%s' WHERE id = '%s'", userId, orderId);
            PreparedStatement pstmt = connection.prepareStatement(query);
            int rowsInserted = pstmt.executeUpdate();


        } catch (PSQLException e) {
            System.out.println("Error PSQLException pada changeOrderStatus(): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi Error pada changeOrderStatus(): " + e.getMessage());
        }
    }

    public static void removeOrder(String orderId) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("DELETE FROM orders WHERE id = '%s'", orderId);
            PreparedStatement pstmt = connection.prepareStatement(query);
            int rowsDeleted = pstmt.executeUpdate();

        } catch (PSQLException e) {
            System.out.println("Error PSQLException pada removeOrder(): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi Error pada removeOrder(): " + e.getMessage());
        }
    }

    public static void insertPayment(EscrowPayment payment) {
//        String orderId, PaymentStatus status, double amount
        try {
            Connection connection = getConnection();

            String query = String.format("INSERT INTO payments (id, order_id, status, amount) VALUES ('%s', '%s', '%s', %f);", payment.getPaymentId(), payment.getOrderId(), payment.getStatus(), payment.getAmount());
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.executeUpdate();

        } catch (PSQLException e) {
            System.out.println("Error pada PSQLException pada insertPayment(): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi Error pada insertPayment(): " + e.getMessage());
        }
    }

    public static void changePaymentStatus(String paymentId, PaymentStatus status) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("UPDATE payments SET status = '%s' WHERE id = '%s'", status, paymentId);
            PreparedStatement pstmt = connection.prepareStatement(query);
            int rowsInserted = pstmt.executeUpdate();


        } catch (PSQLException e) {
            System.out.println("Error PSQLException pada changePaymentStatus(): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi Error pada changePaymentStatus(): " + e.getMessage());
        }
    }

    public static EscrowPayment getPaymentByOrderId(String orderId) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("SELECT * FROM payments WHERE order_id = '%s'", orderId);
            var resultSet = statement.executeQuery(query);
            resultSet.next();
            String paymentId = resultSet.getString("id");
            String ordId = resultSet.getString("order_id");
            String paymentStatus = resultSet.getString("status");
            double amount = resultSet.getDouble("amount");

            return new EscrowPayment(paymentId, ordId, amount, PaymentStatus.valueOf(paymentStatus));

        } catch (PSQLException e) {
            System.out.println("Error PSQLException pada getPaymentByOrderId(): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi Error pada getPaymentByOrderId(): " + e.getMessage());
        }
        return null;
    }

    public static void insertPaymentArray(List<EscrowPayment> payments) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM payments";
            payments.clear();

            var resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String paymentId = resultSet.getString("id");
                String ordId = resultSet.getString("order_id");
                String paymentStatus = resultSet.getString("status");
                double amount = resultSet.getDouble("amount");
                payments.add(new EscrowPayment(paymentId, ordId, amount, PaymentStatus.valueOf(paymentStatus)));
            }
        } catch (PSQLException e) {
            System.out.println("Error pada PSQLException pada insertPaymentArray(): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi Error pada insertPaymentArray(): " + e.getMessage());

        }
    }

}

