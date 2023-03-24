package org.example;

import org.jetbrains.annotations.NotNull;
import com.mysql.jdbc.Driver;

import java.sql.*;

public class DBService {

    public static Connection connection;

    public static void getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/servlet";
        String user = "root";
        String password = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void add(@NotNull User user) throws SQLException {

        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT users(login, password, email) VALUES ('")
                .append(user.getLogin())
                .append("', '")
                .append(user.getPassword())
                .append("', '")
                .append(user.getEmail())
                .append("')");
        statement.executeUpdate(sb.toString());
        statement.close();
    }

    public static boolean thisEmailIsUnique(@NotNull String email) throws SQLException {
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(*) FROM users WHERE email = '")
                .append(email)
                .append("'");
        ResultSet resultSet = statement.executeQuery(sb.toString());
        resultSet.first();
        var i = resultSet.getInt(1);
        statement.close();
        resultSet.close();
        return i == 0;
    }

    public static boolean thisLoginIsUnique(@NotNull String login) throws SQLException {
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(*) FROM users WHERE login = '")
                .append(login)
                .append("'");
        ResultSet resultSet = statement.executeQuery(sb.toString());
        resultSet.first();
        var i = resultSet.getInt(1);
        statement.close();
        resultSet.close();
        return i == 0;
    }

    public static User getUserByLogin(@NotNull String login) throws SQLException {
        Statement statement = connection.createStatement();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM users WHERE login = '")
                .append(login)
                .append("'");
        ResultSet resultSet = statement.executeQuery(sb.toString());
        String password = "";
        String email = "";
        int count = 0;
        while (resultSet.next()) {
            password = resultSet.getString("password");
            email = resultSet.getString("email");
            count++;
        }
        statement.close();
        resultSet.close();
        if (count != 1) return null;
        else return new User(email,login,password);
    }
}
