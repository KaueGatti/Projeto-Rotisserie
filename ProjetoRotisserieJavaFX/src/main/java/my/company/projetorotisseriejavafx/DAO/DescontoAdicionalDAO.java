package my.company.projetorotisseriejavafx.DAO;

import javafx.collections.ObservableList;
import my.company.projetorotisseriejavafx.DB.DatabaseConnection;
import my.company.projetorotisseriejavafx.Objects.DescontoAdicional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DescontoAdicionalDAO {

    static public int criar(ObservableList<DescontoAdicional> das, int idPedido) throws SQLException {
        String sql = "INSERT INTO Desconto_Adicional (id_pedido, tipo, valor, observacao) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            for (DescontoAdicional da : das) {

                stmt.setInt(1, idPedido);
                stmt.setString(2, da.getTipo());
                stmt.setDouble(3, da.getValor());
                stmt.setString(4, da.getObservacao());

                stmt.addBatch();
            }

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    static public List<DescontoAdicional> listarPorPedido(int idPedido) throws SQLException {
        String sql = "SELECT * FROM Desconto_Adicional WHERE id_pedido = ?";
        List<DescontoAdicional> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DescontoAdicional da = new DescontoAdicional();
                    da.setId(rs.getInt("id"));
                    da.setIdPedido(rs.getInt("id_pedido"));
                    da.setTipo(rs.getString("tipo"));
                    da.setValor(rs.getDouble("valor"));
                    da.setObservacao(rs.getString("observacao"));
                    lista.add(da);
                }
            }
        }
        return lista;
    }

    static public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Desconto_Adicional WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
