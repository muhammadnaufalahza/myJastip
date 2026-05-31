package myjastip;

import myjastip.storage.Item;
import org.postgresql.util.PSQLException;

import java.sql.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DatabaseUtil {
    public static Connection getConnection() throws SQLException {
    	Scanner sc = new Scanner(System.in);
    	System.out.print("Masukkan Password Supabase: ");

        String PASSWORD = sc.nextLine();
        String USER = "postgres";
        String URL = "jdbc:postgresql://aws-1-ap-south-1.pooler.supabase.com:6543/postgres?user=postgres.stmeucoddhqzfblbtrne&password=" + PASSWORD;

        return DriverManager.getConnection(URL);
    }

    public static void insertItems(ArrayList<Item> items) {
        try (Connection connection = getConnection()) {
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
}

