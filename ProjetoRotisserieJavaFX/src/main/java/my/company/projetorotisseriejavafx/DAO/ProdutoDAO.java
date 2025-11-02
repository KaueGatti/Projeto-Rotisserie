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

    public static void create(Produto produto) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL create_produto(?, ?)");

            stmt.setString(1, produto.getDescricao());
            stmt.setDouble(2, produto.getValor());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Falha ao cadastrar remédio: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static List<Produto> read() {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Produto> produtos = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT * FROM Produto");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto();

            
                produto.setId(rs.getInt("id"));
                produto.setDescricao(rs.getString("descricao"));
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
                produto.setDescricao(rs.getString("descricao"));
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

    public static void update(Produto produto) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL update_produto(?, ?)");

            stmt.setInt(1, produto.getId());
            stmt.setString(3, produto.getDescricao());
            stmt.setDouble(4, produto.getValor());
            stmt.setString(6, produto.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Falha ao atualizar produto: " + e);
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