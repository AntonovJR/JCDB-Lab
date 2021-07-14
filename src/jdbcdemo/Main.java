package jdbcdemo;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter DB username (<Enter> for 'root'): ");
        String username = scanner.nextLine().trim();
        username = username.length() > 0 ? username : "root";

        System.out.println("Enter DB password (<Enter> for 'Antonov93'): ");
        String password = scanner.nextLine().trim();
        password = password.length() > 0 ? password : "Antonov93";

        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.printf("Database driver: %s", DB_DRIVER);
            System.exit(0);
        }
        System.out.println("DB Driver loaded successfully");

        Properties props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);
        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/soft_uni", props);

        PreparedStatement stmt = connection.prepareStatement

                ("SELECT * FROM employees WHERE salary > ?");

        System.out.println("Salary: ");
        String salary = scanner.nextLine();

        stmt.setDouble(1, Double.parseDouble(salary));

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {

            System.out.printf("%s %s%n",
                    rs.getString("first_name"), rs.getString("last_name"));

        }
    }
}

