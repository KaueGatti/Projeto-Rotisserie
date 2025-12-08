package my.company.projetorotisseriejavafx.DAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import my.company.projetorotisseriejavafx.DB.DatabaseConnection;
import my.company.projetorotisseriejavafx.Objects.Motoboy;

public class MotoboyDAO {

    static public int criar(Motoboy motoboy) throws SQLException {
        String sql = "INSERT INTO Motoboy (nome, valor_diaria) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, motoboy.getNome());
            stmt.setDouble(2, motoboy.getValorDiaria());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao criar motoboy, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao criar motoboy, ID não obtido.");
                }
            }
        }
    }

    static public void atualizar(Motoboy motoboy) throws SQLException {
        String sql = "UPDATE Motoboy SET valor_diaria = ?, status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, motoboy.getValorDiaria());
            stmt.setString(2, motoboy.getStatus());
            stmt.setInt(3, motoboy.getId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Motoboy com ID " + motoboy.getId() + " não encontrado.");
            }
        }
    }

    static public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Motoboy WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Motoboy com ID " + id + " não encontrado.");
            }
        }
    }

    static public List<Motoboy> listarTodos() throws SQLException {
        String sql = "SELECT * FROM Motoboy ORDER BY nome";
        List<Motoboy> motoboys = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Motoboy motoboy = new Motoboy();
                motoboy.setId(rs.getInt("id"));
                motoboy.setNome(rs.getString("nome"));
                motoboy.setValorDiaria(rs.getDouble("valor_diaria"));
                motoboy.setStatus(rs.getString("status"));

                motoboys.add(motoboy);
            }
        }

        return motoboys;
    }

    static public Motoboy buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Motoboy WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Motoboy motoboy = new Motoboy();
                    motoboy.setId(rs.getInt("id"));
                    motoboy.setNome(rs.getString("nome"));
                    motoboy.setValorDiaria(rs.getDouble("valor_diaria"));
                    motoboy.setStatus(rs.getString("status"));

                    return motoboy;
                }
            }
        }

        return null;
    }

    static public List<Motoboy> listarAtivos() throws SQLException {
        String sql = "SELECT * FROM Motoboy WHERE status = 'ATIVO' ORDER BY nome";
        List<Motoboy> motoboys = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Motoboy motoboy = new Motoboy();
                motoboy.setId(rs.getInt("id"));
                motoboy.setNome(rs.getString("nome"));
                motoboy.setValorDiaria(rs.getDouble("valor_diaria"));
                motoboy.setStatus(rs.getString("status"));

                motoboys.add(motoboy);
            }
        }

        return motoboys;
    }

    static public Motoboy buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT * FROM Motoboy WHERE nome = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Motoboy motoboy = new Motoboy();
                    motoboy.setId(rs.getInt("id"));
                    motoboy.setNome(rs.getString("nome"));
                    motoboy.setValorDiaria(rs.getDouble("valor_diaria"));
                    motoboy.setStatus(rs.getString("status"));

                    return motoboy;
                }
            }
        }

        return null;
    }

    static public double calcularPagamento(int idMotoboy, int numeroEntregas) throws SQLException {
        Motoboy motoboy = buscarPorId(idMotoboy);
        if (motoboy == null) {
            throw new SQLException("Motoboy não encontrado");
        }

        return motoboy.getValorDiaria() * numeroEntregas;
    }
}
