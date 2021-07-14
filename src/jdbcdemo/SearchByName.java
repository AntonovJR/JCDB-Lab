package jdbcdemo;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class SearchByName {
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
                DriverManager.getConnection("jdbc:mysql://localhost:3306/diablo", props);

        PreparedStatement stmt = connection.prepareStatement

                ("SELECT `user_name`,`first_name`, `last_name`, COUNT(ug.`game_id`) AS 'countGames' FROM `users`\n" +
                        "JOIN users_games ug on users.id = ug.user_id\n" +
                        "WHERE `user_name` = ?;");

        System.out.println("user name: ");
        String userName = scanner.nextLine();

        stmt.setString(1, userName);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            if (rs.getString("user_name") == null) {
                System.out.println("No such user exists");

            } else {

                System.out.printf("User: %s%n%s %s has played %d games",
                        rs.getString("user_name"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("countGames"));
            }
        }
    }
}