package my.company.projetorotisseriejavafx.DAO;

import my.company.projetorotisseriejavafx.DB.DatabaseConnection;
import my.company.projetorotisseriejavafx.Objects.Marmita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarmitaDAO {

    static public int criar(Marmita marmita) throws SQLException {
        String sql = "INSERT INTO Marmita (nome, max_mistura, max_guarnicao, valor) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, marmita.getNome());
            stmt.setInt(2, marmita.getMaxMistura());
            stmt.setInt(3, marmita.getMaxGuarnicao());
            stmt.setDouble(4, marmita.getValor());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao criar marmita, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao criar marmita, ID não obtido.");
                }
            }
        }
    }

    static public void atualizar(Marmita marmita) throws SQLException {
        String sql = "UPDATE Marmita SET max_mistura = ?, max_guarnicao = ?, valor = ?, status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, marmita.getMaxMistura());
            stmt.setInt(2, marmita.getMaxGuarnicao());
            stmt.setDouble(3, marmita.getValor());
            stmt.setString(4, marmita.getStatus());
            stmt.setInt(5, marmita.getId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Marmita com ID " + marmita.getId() + " não encontrada.");
            }
        }
    }

    static public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Marmita WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Marmita com ID " + id + " não encontrada.");
            }
        }
    }

    static public List<Marmita> listarTodas() throws SQLException {
        String sql = "SELECT * FROM Marmita ORDER BY nome";
        List<Marmita> marmitas = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Marmita marmita = new Marmita();
                marmita.setId(rs.getInt("id"));
                marmita.setNome(rs.getString("nome"));
                marmita.setMaxMistura(rs.getInt("max_mistura"));
                marmita.setMaxGuarnicao(rs.getInt("max_guarnicao"));
                marmita.setValor(rs.getDouble("valor"));
                marmita.setStatus(rs.getString("status"));

                marmitas.add(marmita);
            }
        }

        return marmitas;
    }

    static public Marmita buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Marmita WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Marmita marmita = new Marmita();
                    marmita.setId(rs.getInt("id"));
                    marmita.setNome(rs.getString("nome"));
                    marmita.setMaxMistura(rs.getInt("max_mistura"));
                    marmita.setMaxGuarnicao(rs.getInt("max_guarnicao"));
                    marmita.setValor(rs.getDouble("valor"));
                    marmita.setStatus(rs.getString("status"));

                    return marmita;
                }
            }
        }

        return null;
    }

    static public List<Marmita> listarAtivas() throws SQLException {
        String sql = "SELECT * FROM Marmita WHERE status = 'ATIVO' ORDER BY nome";
        List<Marmita> marmitas = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Marmita marmita = new Marmita();
                marmita.setId(rs.getInt("id"));
                marmita.setNome(rs.getString("nome"));
                marmita.setMaxMistura(rs.getInt("max_mistura"));
                marmita.setMaxGuarnicao(rs.getInt("max_guarnicao"));
                marmita.setValor(rs.getDouble("valor"));
                marmita.setStatus(rs.getString("status"));

                marmitas.add(marmita);
            }
        }

        return marmitas;
    }
}