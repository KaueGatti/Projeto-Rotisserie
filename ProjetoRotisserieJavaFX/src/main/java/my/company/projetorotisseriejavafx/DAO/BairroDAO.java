package my.company.projetorotisseriejavafx.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import my.company.projetorotisseriejavafx.DB.DatabaseConnection;
import my.company.projetorotisseriejavafx.Objects.Bairro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BairroDAO {

    static public int criar(Bairro bairro) throws SQLException {
        String sql = "INSERT INTO Bairro (nome, valor_entrega) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, bairro.getNome());
            stmt.setDouble(2, bairro.getValorEntrega());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao criar bairro, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao criar bairro, ID não obtido.");
                }
            }
        }
    }

    static public void atualizar(Bairro bairro) throws SQLException {
        String sql = "UPDATE Bairro SET valor_entrega = ?, status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, bairro.getValorEntrega());
            stmt.setString(2, bairro.getStatus());
            stmt.setInt(3, bairro.getId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Bairro com ID " + bairro.getId() + " não encontrado.");
            }
        }
    }

    static public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Bairro WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Bairro com ID " + id + " não encontrado.");
            }
        }
    }

    static public List<Bairro> listarTodos() throws SQLException {
        String sql = "SELECT * FROM Bairro ORDER BY nome";
        List<Bairro> bairros = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Bairro bairro = new Bairro();
                bairro.setId(rs.getInt("id"));
                bairro.setNome(rs.getString("nome"));
                bairro.setValorEntrega(rs.getDouble("valor_entrega"));
                bairro.setStatus(rs.getString("status"));

                bairros.add(bairro);
            }
        }

        return bairros;
    }

    static public Bairro buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Bairro WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Bairro bairro = new Bairro();
                    bairro.setId(rs.getInt("id"));
                    bairro.setNome(rs.getString("nome"));
                    bairro.setValorEntrega(rs.getDouble("valor_entrega"));
                    bairro.setStatus(rs.getString("status"));

                    return bairro;
                }
            }
        }

        return null;
    }

    static public List<Bairro> listarAtivos() throws SQLException {
        String sql = "SELECT * FROM Bairro WHERE status = 'ATIVO' ORDER BY nome";
        List<Bairro> bairros = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Bairro bairro = new Bairro();
                bairro.setId(rs.getInt("id"));
                bairro.setNome(rs.getString("nome"));
                bairro.setValorEntrega(rs.getDouble("valor_entrega"));
                bairro.setStatus(rs.getString("status"));

                bairros.add(bairro);
            }
        }

        return bairros;
    }

    static public Bairro buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT * FROM Bairro WHERE nome = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Bairro bairro = new Bairro();
                    bairro.setId(rs.getInt("id"));
                    bairro.setNome(rs.getString("nome"));
                    bairro.setValorEntrega(rs.getDouble("valor_entrega"));
                    bairro.setStatus(rs.getString("status"));

                    return bairro;
                }
            }
        }

        return null;
    }

    static public List<Bairro> buscarPorNomeParcial(String nome) throws SQLException {
        String sql = "SELECT * FROM Bairro WHERE nome LIKE ? AND status = 'ATIVO' ORDER BY nome";
        List<Bairro> bairros = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Bairro bairro = new Bairro();
                    bairro.setId(rs.getInt("id"));
                    bairro.setNome(rs.getString("nome"));
                    bairro.setValorEntrega(rs.getDouble("valor_entrega"));
                    bairro.setStatus(rs.getString("status"));

                    bairros.add(bairro);
                }
            }
        }

        return bairros;
    }
}