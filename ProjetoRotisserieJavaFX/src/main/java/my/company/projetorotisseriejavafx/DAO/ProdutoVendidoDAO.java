package my.company.projetorotisseriejavafx.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import my.company.projetorotisseriejavafx.DB.Conexao;
import my.company.projetorotisseriejavafx.Objects.ProdutoVendido;

public class ProdutoVendidoDAO {

    public static void create(List<ProdutoVendido> produtos) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("CALL create_produto_vendido(?, ?, ?, ?)");

            for (ProdutoVendido produto : produtos) {
                stmt.setInt(1, produto.getProduto().getId());
                stmt.setInt(2, produto.getPedido().getId());
                stmt.setInt(3, produto.getQuantidade());
                stmt.setDouble(4, produto.getSubtotal());
                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            System.out.println("Falha ao cadastrar produto vendido: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }
}
