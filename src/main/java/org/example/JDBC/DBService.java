package org.example.JDBC;

import org.jetbrains.annotations.NotNull;

import java.sql.*;

public class DBService {
    private static final String url = "jdbc:mysql://localhost:3306/servlet";
    private static final String user = "root";
    private static final String password = "root";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private final Connection connection;

    public DBService() {
        this.connection = getConnection();
    }

    public Connection getConnection(){
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addUser(@NotNull String login, @NotNull String password, @NotNull String email) {
        Statement statement = null;
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sb = "INSERT users(login, password, email) VALUES ('" +
                    login +
                    "', '" +
                    password +
                    "', '" +
                    email +
                    "')";
            statement.executeUpdate(sb);
            statement.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser(@NotNull String login) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sb = "SELECT * FROM users WHERE login = '" +
                    login +
                    "'";
        ResultSet resultSet = statement.executeQuery(sb);
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean emailIsUnique(@NotNull String email) {
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sb = "SELECT COUNT(*) FROM users WHERE email = '" +
                    email +
                    "'";
            ResultSet resultSet = statement.executeQuery(sb);
            resultSet.first();
            var i = resultSet.getInt(1);
            statement.close();
            resultSet.close();
            return i == 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean loginIsUnique(@NotNull String login) {
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sb = "SELECT COUNT(*) FROM users WHERE login = '" +
                    login +
                    "'";
            ResultSet resultSet = statement.executeQuery(sb);
            resultSet.first();
            var i = resultSet.getInt(1);
            statement.close();
            resultSet.close();
            return i == 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
