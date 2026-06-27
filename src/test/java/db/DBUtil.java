package db;

import java.sql.*;

public class DBUtil {

    private static Connection connection;

    public static Connection getConnection() throws Exception {
        if (connection == null) {
            connection = DriverManager.getConnection(
                    "jdbc:h2:file:~/testdb", "sa", "");
        }
        return connection;
    }

    public static void createTable() throws Exception {
        Statement stmt = getConnection().createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS students(id INT, name VARCHAR, score INT)");
        stmt.execute("DELETE FROM students"); //  reset data
    }

    public static void insert(int id, String name, int score) throws Exception {
        PreparedStatement ps = getConnection().prepareStatement(
                "INSERT INTO students VALUES(?, ?, ?)");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setInt(3, score);
        ps.executeUpdate();
    }

    public static ResultSet fetchAll() throws Exception {
        Statement stmt = getConnection().createStatement();
        return stmt.executeQuery("SELECT * FROM students");
    }
}