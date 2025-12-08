package my.company.projetorotisseriejavafx.DAO;

import my.company.projetorotisseriejavafx.DB.DatabaseConnection;
import my.company.projetorotisseriejavafx.Objects.ItemCardapio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemCardapioDAO {

    public static int create(ItemCardapio itemCardapio) throws SQLException {

        String sql = "INSERT INTO Item_Cardapio (nome, categoria)\n" +
                "    VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, itemCardapio.getNome());
            stmt.setString(2, itemCardapio.getCategoria());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }

        }
    }

    public static List<ItemCardapio> read() throws SQLException {
        String sql = "SELECT * FROM Item_Cardapio;";
        List<ItemCardapio> itensCardapio = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    ItemCardapio itemCardapio = new ItemCardapio();
                    itemCardapio.setId(rs.getInt("id"));
                    itemCardapio.setNome(rs.getString("nome"));
                    itemCardapio.setCategoria(rs.getString("categoria"));

                    itensCardapio.add(itemCardapio);
                }
                return itensCardapio;
            }
        }
    }

    public static void delete(int id) throws SQLException {

        String sql = "DELETE FROM Item_Cardapio WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }
}
