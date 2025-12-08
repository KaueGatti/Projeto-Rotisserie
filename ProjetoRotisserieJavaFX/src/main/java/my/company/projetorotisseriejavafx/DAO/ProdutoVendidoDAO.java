package my.company.projetorotisseriejavafx.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import my.company.projetorotisseriejavafx.DB.DatabaseConnection;
import my.company.projetorotisseriejavafx.Objects.ProdutoVendido;

public class ProdutoVendidoDAO {

    static public int criar(ObservableList<ProdutoVendido> pvs, int idPedido) throws SQLException {
        String sql = "INSERT INTO Produto_Vendido (id_pedido, id_produto, quantidade, subtotal) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            for (ProdutoVendido pv : pvs) {
                stmt.setInt(1, idPedido);
                stmt.setInt(2, pv.getIdProduto());
                stmt.setInt(3, pv.getQuantidade());
                stmt.setDouble(4, pv.getSubtotal());

                stmt.addBatch();
            }

            stmt.executeBatch();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    static public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Produto_Vendido WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    static public List<ProdutoVendido> listarPorPedido(int idPedido) throws SQLException {
        String sql = "SELECT PV.*, P.nome as nome_produto FROM Produto_Vendido AS PV " +
                "JOIN Produto AS P ON P.id = PV.id_produto WHERE PV.id_pedido = ?";
        List<ProdutoVendido> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ProdutoVendido pv = new ProdutoVendido();
                    pv.setId(rs.getInt("id"));
                    pv.setIdProduto(rs.getInt("id_produto"));
                    pv.setQuantidade(rs.getInt("quantidade"));
                    pv.setSubtotal(rs.getDouble("subtotal"));
                    lista.add(pv);
                }
            }
        }
        return lista;
    }
}
