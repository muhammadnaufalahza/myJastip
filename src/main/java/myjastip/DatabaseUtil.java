package myjastip;

import myjastip.storage.Cart;
import myjastip.storage.Item;
import myjastip.users.Customer;
import myjastip.users.Jastiper;
import myjastip.users.User;
import org.postgresql.util.PSQLException;

import java.sql.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DatabaseUtil {
    public static Connection getConnection(String password) throws SQLException {
        String URL = "jdbc:postgresql://aws-1-ap-south-1.pooler.supabase.com:6543/postgres?user=postgres.stmeucoddhqzfblbtrne&password=" + password;
        return DriverManager.getConnection(URL);
    }

    public static void insertItems(ArrayList<Item> items, Connection connection) {
        try {
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


                if (categories != null) {
                    String[] javaArray = (String[]) categories.getArray();
                    ArrayList<String> itemCategories = new ArrayList<String>(Arrays.asList(javaArray));
                    items.add(new Item(itemId, itemName, itemDescription, basePrice, storeLocationName, itemCategories));
                } else {
                    items.add(new Item(itemId, itemName, itemDescription, basePrice, storeLocationName, new ArrayList<String>()));
                }

            }
        } catch (PSQLException e) {
            System.out.println("Error pada PSQLException");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Terjadi Error");
            System.exit(0);

        }
    }

    public static void insertUsers(ArrayList<User> users, Connection connection) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM \"users\";";
            var resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String userId = resultSet.getString("id");
                String userName = resultSet.getString("name");
                String userEmail = resultSet.getString("email");
                String userPassword = resultSet.getString("password");
                String userPhoneNumber = resultSet.getString("phone_number");
                String userAddress = resultSet.getString("address");
                boolean isJastiper = resultSet.getBoolean("is_jastiper");

                if (isJastiper) {
                    users.add(new Jastiper(userId, userName, userEmail, userPassword, userPhoneNumber, 0.0, false, false));
                } else {
                    users.add(new Customer(userId, userName, userEmail, userPassword, userPhoneNumber, userAddress, new Cart()));
                }

            }
        } catch (PSQLException e) {
            System.out.println("Error pada PSQLException");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Terjadi Error");
            System.exit(0);

        }
    }

}

