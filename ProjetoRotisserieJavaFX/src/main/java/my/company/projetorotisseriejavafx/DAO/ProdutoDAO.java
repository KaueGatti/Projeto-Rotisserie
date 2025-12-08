package my.company.projetorotisseriejavafx.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import my.company.projetorotisseriejavafx.DB.DatabaseConnection;
import my.company.projetorotisseriejavafx.Objects.Produto;

public class ProdutoDAO {

    static public int criar(Produto produto) throws SQLException {
        String sql = "INSERT INTO Produto (nome, valor) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getValor());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao criar produto, nenhuma linha afetada.");
            }

            // Retorna o ID gerado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao criar produto, ID não obtido.");
                }
            }
        }
    }

    static public void atualizar(Produto produto) throws SQLException {
        String sql = "UPDATE Produto SET valor = ?, status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, produto.getValor());
            stmt.setString(2, produto.getStatus());
            stmt.setInt(3, produto.getId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Produto com ID " + produto.getId() + " não encontrado.");
            }
        }
    }

    static public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Produto WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Produto com ID " + id + " não encontrado.");
            }
        }
    }

    static public List<Produto> listarTodos() throws SQLException {
        String sql = "SELECT * FROM Produto ORDER BY nome";
        List<Produto> produtos = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getDouble("valor"));
                produto.setStatus(rs.getString("status"));

                produtos.add(produto);
            }
        }

        return produtos;
    }

    static public Produto buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Produto WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Produto produto = new Produto();
                    produto.setId(rs.getInt("id"));
                    produto.setNome(rs.getString("nome"));
                    produto.setValor(rs.getDouble("valor"));
                    produto.setStatus(rs.getString("status"));

                    return produto;
                }
            }
        }

        return null;
    }

    static public List<Produto> listarAtivos() throws SQLException {
        String sql = "SELECT * FROM Produto WHERE status = 'ATIVO' ORDER BY nome";
        List<Produto> produtos = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getDouble("valor"));
                produto.setStatus(rs.getString("status"));

                produtos.add(produto);
            }
        }

        return produtos;
    }

    static public List<Produto> buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT * FROM Produto WHERE nome LIKE ? AND status = 'ATIVO' ORDER BY nome";
        List<Produto> produtos = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Produto produto = new Produto();
                    produto.setId(rs.getInt("id"));
                    produto.setNome(rs.getString("nome"));
                    produto.setValor(rs.getDouble("valor"));
                    produto.setStatus(rs.getString("status"));

                    produtos.add(produto);
                }
            }
        }

        return produtos;
    }
}