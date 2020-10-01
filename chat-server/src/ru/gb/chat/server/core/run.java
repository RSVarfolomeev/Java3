package ru.gb.chat.server.core;

import ru.gb.chat.common.Common;

import java.sql.*;
import java.util.Arrays;

class SqlClient2 {

    private static Connection connection;
    private static Statement statement;

    synchronized static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:chat-server/chat.db");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized static String getNickname(String login, String password) {
        String query = String.format("select nickname from users where login='%s' and password='%s'", login, password);
        try (ResultSet set = statement.executeQuery(query)) {
            if (set.next())
                return set.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    synchronized static void changeNickname(String newNickname, String login, String password) {
        String query = String.format("update users set nickname='%s' where login='%s' and password='%s'", newNickname, login, password);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

public class run {

    private static String handleNonAuthMessage(String msg) {
        String[] arr = msg.split(Common.DELIMITER);
        String login = arr[1];
        String password = arr[2];
        System.out.println(Arrays.toString(arr));
        SqlClient2.connect();
        String nickname = SqlClient2.getNickname(login, password);
        return nickname;
    }

    private static void change(String msg) {
        String[] arr = msg.split(Common.DELIMITER);
        String login = arr[1];
        String password = arr[2];
        SqlClient2.connect();
        SqlClient2.changeNickname("QA", login, password);
    }

    public static void main(String[] args) {
        String msg = "/auth_request±QA±123";
        System.out.println(handleNonAuthMessage(msg));
        change(msg);
        System.out.println(handleNonAuthMessage(msg));
    }
}
