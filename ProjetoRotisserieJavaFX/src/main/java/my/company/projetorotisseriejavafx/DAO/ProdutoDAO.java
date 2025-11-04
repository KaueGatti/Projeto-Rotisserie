package my.company.projetorotisseriejavafx.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import my.company.projetorotisseriejavafx.DB.Conexao;
import my.company.projetorotisseriejavafx.Objects.Produto;

public class ProdutoDAO {

    public static void create(Produto produto) throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL CREATE_PRODUTO(?, ?)");

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getValor());

            stmt.executeUpdate();
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static List<Produto> read() throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Produto> produtos = new ArrayList();

        try {
            stmt = con.prepareStatement("CALL READ_ALL_PRODUTOS()");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto();

                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getDouble("valor"));
                produto.setStatus(rs.getString("_status"));

                produtos.add(produto);
            }

            return produtos;
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static List<Produto> read(int id) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Produto> produtos = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT * FROM Produto WHERE id = ?");

            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto();


                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("descricao"));
                produto.setValor(rs.getDouble("valor"));
                produto.setStatus(rs.getString("_status"));

                produtos.add(produto);
            }

            return produtos;
        } catch (SQLException e) {
            System.out.println("Falha ao buscar Produtos: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
        return null;
    }

    public static void update(Produto produto) throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL UPDATE_PRODUTO(?, ?, ?)");

            stmt.setInt(1, produto.getId());
            stmt.setDouble(2, produto.getValor());
            stmt.setString(3, produto.getStatus());

            stmt.executeUpdate();
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static void delete(Produto produto) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL delete_produto(?)");

            stmt.setInt(1, produto.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir produto: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }
}