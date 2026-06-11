package myjastip.db;

import com.google.gson.Gson;
import myjastip.payment.Order;
import myjastip.storage.Cart;
import myjastip.storage.Item;
import myjastip.users.Customer;
import myjastip.users.Jastiper;
import myjastip.users.User;
import org.postgresql.util.PSQLException;

import java.sql.*;

import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseUtil {
    public static Connection getConnection() throws SQLException {
        String URL = "jdbc:postgresql://aws-1-ap-south-1.pooler.supabase.com:6543/postgres?user=postgres.stmeucoddhqzfblbtrne&password=" + System.getenv("SUPABASE_DB_PASSWORD");
        return DriverManager.getConnection(URL);
    }

    public static String getUserId(String name, String pass)
    {
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
            System.out.println("Error PSQLException pada getUserId()");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Terjadi Error");
            System.exit(0);
        }
        return null;
    }

    public static User getUser(String userId) {
        if (userId == null) return new Customer();
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("SELECT * FROM users WHERE id = '%s'", userId);
            var resultSet = statement.executeQuery(query);
            resultSet.next();
            String userName = resultSet.getString("name");
            String userEmail = resultSet.getString("email");
            String userPassword = resultSet.getString("password");
            String userPhoneNumber = resultSet.getString("phone_number");
            String userAddress = resultSet.getString("address");
            boolean isJastiper = resultSet.getBoolean("is_jastiper");

            if (isJastiper) {
                return new Jastiper(userId, userName, userEmail, userPassword, userPhoneNumber, 0.0, false, false);
            } else {
                return new Customer(userId, userName, userEmail, userPassword, userPhoneNumber, userAddress);
            }

        } catch (PSQLException e) {
            System.out.println("Error PSQLException pada getUser()");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Terjadi Error");
            System.exit(0);
        }
        return new Customer();
    }

    public static void insertItems(ArrayList<Item> items) {
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
            System.out.println("Error pada PSQLException pada insertItems()");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Terjadi Error pada insertItems()");
            System.exit(0);

        }
    }


    public static void insertOrder(String status, String locationName, double locationLatitude, double locationLongitude, double totalItemPrice, double transportationFee, double serviceFee, String recieverId, Cart cart) {
        try {
            Connection connection = getConnection();
//            String cartJson = "{\n" +
//                    "\t\"items\": [\n" +
//                    "\t\t{\n" +
//                    "\t\t\t\"item_id\": \"uuid\",\n" +
//                    "\t\t\t\"quantity\": 6\n" +
//                    "\t\t},\n" +
//                    "\t\t{\n" +
//                    "\t\t\t\"item_id\": \"uuid2\",\n" +
//                    "\t\t\t\"quantity\": 7\n" +
//                    "\t\t}\n" +
//                    "\t]\n" +
//                    "}";

            Gson cartGson = new Gson();
            String cartJson = cartGson.toJson(cart);

            String query = String.format("INSERT INTO orders (status, location_name, location_latitude, location_longitude, total_item_price, transportation_fee, service_fee, receiver_id, order_items) VALUES ('%s', '%s', %f, %f, %f, %f, %f, '%s', '%s');", status, locationName, locationLatitude, locationLongitude, totalItemPrice, transportationFee, serviceFee, recieverId, cartJson);
            PreparedStatement pstmt = connection.prepareStatement(query);
            int rowsInserted = pstmt.executeUpdate();

        } catch (PSQLException e) {
            System.out.println("Error pada PSQLException pada insertOrder()");
            e.printStackTrace();
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Terjadi Error pada insertOrder()");
            System.exit(0);

        }
    }
//    public static void insertUsers(ArrayList<User> users, Connection connection) {
//        try {
//            Statement statement = connection.createStatement();
//            String query = "SELECT * FROM \"users\";";
//            var resultSet = statement.executeQuery(query);
//            while (resultSet.next()) {
//                String userId = resultSet.getString("id");
//                String userName = resultSet.getString("name");
//                String userEmail = resultSet.getString("email");
//                String userPassword = resultSet.getString("password");
//                String userPhoneNumber = resultSet.getString("phone_number");
//                String userAddress = resultSet.getString("address");
//                boolean isJastiper = resultSet.getBoolean("is_jastiper");
//
//                if (isJastiper) {
//                    users.add(new Jastiper(userId, userName, userEmail, userPassword, userPhoneNumber, 0.0, false, false));
//                } else {
//                    users.add(new Customer(userId, userName, userEmail, userPassword, userPhoneNumber, userAddress, new Cart()));
//                }
//
//            }
//        } catch (PSQLException e) {
//            System.out.println("Error pada PSQLException");
//            System.exit(0);
//        } catch (Exception e) {
//            System.out.println("Terjadi Error");
//            System.exit(0);
//        }
//    }



}

