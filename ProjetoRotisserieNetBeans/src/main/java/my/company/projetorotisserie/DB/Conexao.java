package my.company.projetorotisserie.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexao {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3316/Rotisserie?autoReconnect=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            return conn;
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco: " + e);
        }
        return null;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão\nErro: " + e);
            }
        }
    }

    public static void closeConnection(Connection conn, PreparedStatement stmt) {
        closeConnection(conn);
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar statement\nErro: " + e);
            }
        }
    }

    public static void closeConnection(Connection conn, PreparedStatement stmt, ResultSet rs) {
        closeConnection(conn, stmt);
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão\nErro: " + e);
            }
        }
    }
}
