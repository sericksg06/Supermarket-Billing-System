import java.sql.*;
import java.util.Scanner;

public class BillingApp {

    static final String DB_URL = "jdbc:mysql://localhost:3306/SupermarketDB";
    static final String USER = "root";
    static final String PASS = "password"; // replace with your MySQL password

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Scanner sc = new Scanner(System.in);
            int choice;
            do {
                System.out.println("\nSupermarket Billing Menu:");
                System.out.println("1. Show Products");
                System.out.println("2. Buy Product");
                System.out.println("3. Exit");
                System.out.print("Choice: ");
                choice = sc.nextInt();
                switch (choice) {
                    case 1 -> showProducts(conn);
                    case 2 -> buyProduct(conn, sc);
                    case 3 -> System.out.println("Exiting...");
                    default -> System.out.println("Invalid choice!");
                }
            } while (choice != 3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void showProducts(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Products");
        System.out.println("ProductID | Name | Price");
        while (rs.next()) {
            System.out.println(rs.getInt("ProductID") + " | " + rs.getString("Name") + " | " + rs.getDouble("Price"));
        }
    }

    static void buyProduct(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter ProductID: ");
        int id = sc.nextInt();
        System.out.print("Enter Quantity: ");
        int qty = sc.nextInt();

        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Products WHERE ProductID=?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            double price = rs.getDouble("Price");
            double total = price * qty;
            PreparedStatement sale = conn.prepareStatement("INSERT INTO Sales (ProductID, Quantity, TotalPrice) VALUES (?, ?, ?)");
            sale.setInt(1, id);
            sale.setInt(2, qty);
            sale.setDouble(3, total);
            sale.executeUpdate();
            System.out.println("Purchase successful! Total: ₹" + total);
        } else {
            System.out.println("Product not found!");
        }
    }
}
