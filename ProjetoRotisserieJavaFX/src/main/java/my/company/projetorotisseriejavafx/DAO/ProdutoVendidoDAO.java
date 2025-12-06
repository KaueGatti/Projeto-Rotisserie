package my.company.projetorotisseriejavafx.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import my.company.projetorotisseriejavafx.DB.DatabaseConnection;
import my.company.projetorotisseriejavafx.Objects.ProdutoVendido;

public class ProdutoVendidoDAO {

    public static void create(List<ProdutoVendido> produtos, int idPedido) {
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("CALL CREATE_PRODUTO_VENDIDO(?, ?, ?, ?)");

            for (ProdutoVendido produto : produtos) {
                stmt.setInt(1, idPedido);
                stmt.setInt(2, produto.getIdProduto());
                stmt.setInt(3, produto.getQuantidade());
                stmt.setDouble(4, produto.getSubtotal());
                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            System.out.println("Falha ao cadastrar produto vendido: " + e);
        } finally {
            DatabaseConnection.closeConnection(con, stmt);
        }
    }

    public static List<ProdutoVendido> read(int idPedido) {
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ProdutoVendido> produtos = new ArrayList<>();

        try {
            stmt = con.prepareStatement("CALL READ_ALL_PRODUTOS_PEDIDO(?)");

            stmt.setInt(1, idPedido);

            rs = stmt.executeQuery();

            while (rs.next()) {
                ProdutoVendido produtoVendido = new ProdutoVendido();

                produtoVendido.setNome(rs.getString("nome"));
                produtoVendido.setQuantidade(rs.getInt("quantidade"));
                produtoVendido.setSubtotal(rs.getDouble("subtotal"));

                produtos.add(produtoVendido);
            }

            return produtos;
        } catch (SQLException e) {
            System.out.println("Falha ao buscar Produtos Vendidos pelo pedido: " + e);
        } finally {
            DatabaseConnection.closeConnection(con, stmt);
        }
        return null;
    }
}
