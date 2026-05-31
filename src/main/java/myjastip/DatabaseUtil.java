package myjastip;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Scanner;

public class DatabaseUtil {
	
//    private static final String URL = "jdbc:h2:./myjastip;AUTO_SERVER=TRUE";
    private static final String URL = "jdbc:postgresql://localhost:5432/MYJASTIP";
    private static final String USER = "postgres";
//    private static final String PASS = "";

    public static Connection getConnection() throws SQLException {
    	Scanner sc = new Scanner(System.in);
    	System.out.print("Masukkan Password PostgreSQL: ");
        return DriverManager.getConnection(URL, USER, sc.nextLine());
    }
    
    public static void testDB() {
        
    	try (Connection conn = getConnection()) {
    		
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT version()");

            if (rs.next()) {
                System.out.println("DB Version: " + rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    }
}
