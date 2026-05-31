package myjastip;

import myjastip.storage.Item;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Scanner;

public class DatabaseUtil {
	
//    private static final String URL = "jdbc:postgresql://localhost:5432/MYJASTIP";
//    private static final String PASS = "";

    public static Connection getConnection() throws SQLException {
    	Scanner sc = new Scanner(System.in);
    	System.out.print("Masukkan Password Supabase: ");
        String PASSWORD = sc.nextLine();
        String USER = "postgres";
        String URL = "jdbc:postgresql://aws-1-ap-south-1.pooler.supabase.com:6543/postgres?user=postgres.stmeucoddhqzfblbtrne&password=" + PASSWORD;

        return DriverManager.getConnection(URL);
    }
    
    public static void initializeDB() {
        
    	try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM \"ITEMS\";";
            var resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("itemName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    }

    public static void insertItems(ArrayList<Item> items) {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM \"ITEMS\";";
            var resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String itemId = resultSet.getString("itemId");
                String itemName = resultSet.getString("itemName");
                String itemDescription = resultSet.getString("description");
                double basePrice = resultSet.getDouble("basePrice");
                String storeLocationName = resultSet.getString("storeLocationName");

                items.add(new Item(itemId, itemName, itemDescription, basePrice, storeLocationName, new ArrayList<>()));

            }
        } catch (Exception e) {
            System.out.println("Gagal mengisi item");
            e.printStackTrace();
        }
    }
}

