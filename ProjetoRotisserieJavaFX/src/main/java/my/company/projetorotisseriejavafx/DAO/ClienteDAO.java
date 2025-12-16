package my.company.projetorotisseriejavafx.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import my.company.projetorotisseriejavafx.DB.DatabaseConnection;
import my.company.projetorotisseriejavafx.Objects.Bairro;
import my.company.projetorotisseriejavafx.Objects.Cliente;

public class ClienteDAO {

    static public int criar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO Cliente (nome, contato, bairro_id, endereco) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getContato());

            if (cliente.getBairro() != null) {
                stmt.setInt(3, cliente.getBairro().getId());
            }

            stmt.setString(4, cliente.getEndereco());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao criar cliente, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao criar cliente, ID não obtido.");
                }
            }
        }
    }

    static public void atualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE Cliente" +
                " SET contato = ?, bairro_id = ?, endereco = ?, status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getContato());
            stmt.setInt(2, cliente.getBairro().getId());
            stmt.setString(3, cliente.getEndereco());
            stmt.setString(4, cliente.getStatus());
            stmt.setInt(5, cliente.getId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Cliente com ID " + cliente.getId() + " não encontrado.");
            }
        }
    }

    static public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Cliente WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Cliente com ID " + id + " não encontrado.");
            }
        }
    }

    static public List<Cliente> listarTodos() throws SQLException {
        String sql = """
                SELECT
                c.*,
                b.id AS bairro_id,
                b.nome AS bairro_nome,
                b.valor_entrega AS bairro_valor_entrega
                FROM Cliente c
                LEFT JOIN Bairro b ON c.bairro_id = b.id
                ORDER BY nome""";
        List<Cliente> clientes = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setContato(rs.getString("contato"));

                Bairro bairro = new Bairro();

                bairro.setId(rs.getInt("bairro_id"));
                bairro.setNome(rs.getString("bairro_nome"));
                bairro.setValorEntrega(rs.getDouble("bairro_valor_entrega"));

                cliente.setBairro(bairro);

                cliente.setEndereco(rs.getString("endereco"));

                cliente.setConta(rs.getDouble("conta"));
                cliente.setStatus(rs.getString("status"));

                clientes.add(cliente);
            }
        }

        return clientes;
    }

    static public Cliente buscarPorId(int id) throws SQLException {
        String sql = """
                SELECT\
                c.*,\
                b.id AS bairro_id,
                b.nome AS bairro_nome,
                b.valor_entrega AS bairro_valor_entrega
                FROM Cliente c
                LEFT JOIN Bairro b ON c.bairro_id = b.id
                WHERE c.id = ?
                ORDER BY nome""";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setContato(rs.getString("contato"));

                    Bairro bairro = new Bairro();

                    bairro.setId(rs.getInt("bairro_id"));
                    bairro.setNome(rs.getString("bairro_nome"));
                    bairro.setValorEntrega(rs.getDouble("bairro_valor_entrega"));

                    cliente.setBairro(bairro);

                    cliente.setEndereco(rs.getString("endereco"));

                    cliente.setConta(rs.getDouble("conta"));
                    cliente.setStatus(rs.getString("status"));

                    return cliente;
                }
            }
        }

        return null;
    }

    static public List<Cliente> listarAtivos() throws SQLException {
        String sql = """
                SELECT
                c.*,
                b.id AS bairro_id,
                b.nome AS bairro_nome,
                b.valor_entrega AS bairro_valor_entrega
                FROM Cliente c
                LEFT JOIN Bairro b ON c.bairro_id = b.id
                WHERE c.status = 'ATIVO'
                ORDER BY nome""";
        List<Cliente> clientes = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setContato(rs.getString("contato"));

                Bairro bairro = new Bairro();

                bairro.setId(rs.getInt("bairro_id"));
                bairro.setNome(rs.getString("bairro_nome"));
                bairro.setValorEntrega(rs.getDouble("bairro_valor_entrega"));

                cliente.setBairro(bairro);

                cliente.setEndereco(rs.getString("endereco"));

                cliente.setConta(rs.getDouble("conta"));
                cliente.setStatus(rs.getString("status"));

                clientes.add(cliente);
            }
        }

        return clientes;
    }

    static public Cliente buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT * FROM Cliente WHERE nome = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setContato(rs.getString("contato"));
                    cliente.setConta(rs.getDouble("conta"));
                    cliente.setStatus(rs.getString("status"));

                    return cliente;
                }
            }
        }

        return null;
    }

    static public List<Cliente> listarComContaAberta() throws SQLException {
        String sql = "SELECT * FROM Cliente WHERE conta > 0 AND status = 'ATIVO' ORDER BY conta DESC";
        List<Cliente> clientes = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setContato(rs.getString("contato"));
                cliente.setConta(rs.getDouble("conta"));
                cliente.setStatus(rs.getString("status"));

                clientes.add(cliente);
            }
        }

        return clientes;
    }

    static public double obterTotalContasAbertas() throws SQLException {
        String sql = "SELECT SUM(conta) as total FROM Cliente WHERE status = 'ATIVO'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("total");
            }
        }

        return 0.0;
    }

    static public void zerarConta(int id) throws SQLException {
        String sql = "UPDATE Cliente SET conta = 0 WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    static public void ajustarConta(int id, double novoValor) throws SQLException {
        String sql = "UPDATE Cliente SET conta = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, novoValor);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }
}
