package my.company.projetorotisseriejavafx.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import my.company.projetorotisseriejavafx.DB.DatabaseConnection;
import my.company.projetorotisseriejavafx.Objects.Mensalista;

public class MensalistaDAO {

    public int criar(String nome, String contato) throws SQLException {
        String sql = "INSERT INTO Mensalista (nome, contato) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, nome);
            stmt.setString(2, contato);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao criar mensalista, nenhuma linha afetada.");
            }

            // Retorna o ID gerado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao criar mensalista, ID não obtido.");
                }
            }
        }
    }

    public void atualizar(int id, String status, String contato) throws SQLException {
        String sql = "UPDATE Mensalista SET status = ?, contato = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setString(2, contato);
            stmt.setInt(3, id);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Mensalista com ID " + id + " não encontrado.");
            }
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Mensalista WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Mensalista com ID " + id + " não encontrado.");
            }
        }
    }

    public List<Mensalista> listarTodos() throws SQLException {
        String sql = "SELECT * FROM Mensalista ORDER BY nome";
        List<Mensalista> mensalistas = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Mensalista mensalista = new Mensalista();
                mensalista.setId(rs.getInt("id"));
                mensalista.setNome(rs.getString("nome"));
                mensalista.setContato(rs.getString("contato"));
                mensalista.setConta(rs.getDouble("conta"));
                mensalista.setStatus(rs.getString("status"));

                mensalistas.add(mensalista);
            }
        }

        return mensalistas;
    }

    public Mensalista buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Mensalista WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Mensalista mensalista = new Mensalista();
                    mensalista.setId(rs.getInt("id"));
                    mensalista.setNome(rs.getString("nome"));
                    mensalista.setContato(rs.getString("contato"));
                    mensalista.setConta(rs.getDouble("conta"));
                    mensalista.setStatus(rs.getString("status"));

                    return mensalista;
                }
            }
        }

        return null;
    }

    public List<Mensalista> listarAtivos() throws SQLException {
        String sql = "SELECT * FROM Mensalista WHERE status = 'ATIVO' ORDER BY nome";
        List<Mensalista> mensalistas = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Mensalista mensalista = new Mensalista();
                mensalista.setId(rs.getInt("id"));
                mensalista.setNome(rs.getString("nome"));
                mensalista.setContato(rs.getString("contato"));
                mensalista.setConta(rs.getDouble("conta"));
                mensalista.setStatus(rs.getString("status"));

                mensalistas.add(mensalista);
            }
        }

        return mensalistas;
    }

    public Mensalista buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT * FROM Mensalista WHERE nome = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Mensalista mensalista = new Mensalista();
                    mensalista.setId(rs.getInt("id"));
                    mensalista.setNome(rs.getString("nome"));
                    mensalista.setContato(rs.getString("contato"));
                    mensalista.setConta(rs.getDouble("conta"));
                    mensalista.setStatus(rs.getString("status"));

                    return mensalista;
                }
            }
        }

        return null;
    }

    public List<Mensalista> listarComContaAberta() throws SQLException {
        String sql = "SELECT * FROM Mensalista WHERE conta > 0 AND status = 'ATIVO' ORDER BY conta DESC";
        List<Mensalista> mensalistas = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Mensalista mensalista = new Mensalista();
                mensalista.setId(rs.getInt("id"));
                mensalista.setNome(rs.getString("nome"));
                mensalista.setContato(rs.getString("contato"));
                mensalista.setConta(rs.getDouble("conta"));
                mensalista.setStatus(rs.getString("status"));

                mensalistas.add(mensalista);
            }
        }

        return mensalistas;
    }

    public double obterTotalContasAbertas() throws SQLException {
        String sql = "SELECT SUM(conta) as total FROM Mensalista WHERE status = 'ATIVO'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("total");
            }
        }

        return 0.0;
    }

    public void zerarConta(int id) throws SQLException {
        String sql = "UPDATE Mensalista SET conta = 0 WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void ajustarConta(int id, double novoValor) throws SQLException {
        String sql = "UPDATE Mensalista SET conta = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, novoValor);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }
}
