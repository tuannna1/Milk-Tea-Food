/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anh Tuấn
 */
public class JdbcHelper {
    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String dburl = "jdbc:sqlserver://localhost:1433;databaseName=Milk_Tea&FoodS;";
    private static String username = "java3";
    private static String password = "java";
     public static Connection conn;
    // Câu lệnh dùng để nạp driver
    static {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(dburl, username, password);
            System.out.println("connect successfully!");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Lỗi thiếu thư viện kết nối");
        } catch (SQLException ex) {
             ex.printStackTrace();
            System.out.println("Lỗi kết nối CSDL!");
        }
    }
// xây dựng prepareStatement
    public static PreparedStatement prepareStatement(String sql, Object... args) throws SQLException {
        Connection connection = DriverManager.getConnection(dburl, username, password);
        PreparedStatement pstmt = null;
        if (sql.trim().startsWith("{")) {
            pstmt = connection.prepareCall(sql);
        } else {
            pstmt = connection.prepareStatement(sql);
        }
        for (int i = 0; i < args.length; i++) {
            pstmt.setObject(i + 1, args[i]);
        }
        return pstmt;
    }
// câu lệnh  SQL thao tác (INSERT, UPDATE, DELETE) 
    public static int executeUpdate(String sql, Object... args) {
        try {
            PreparedStatement stmt = prepareStatement(sql, args);
            try {
                stmt.executeUpdate();
            } finally {
                stmt.getConnection().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
//câu lệnh SQL truy vấn (SELECT) hoặc thủ tục lưu truy vấn dữ liệu
    public static ResultSet executeQuery(String sql, Object... args) {
        try {
            PreparedStatement stmt = prepareStatement(sql, args);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    

    
}
