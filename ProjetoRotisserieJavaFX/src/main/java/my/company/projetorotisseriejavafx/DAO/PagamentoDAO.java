package my.company.projetorotisseriejavafx.DAO;

import my.company.projetorotisseriejavafx.DB.DatabaseConnection;
import my.company.projetorotisseriejavafx.Objects.Pagamento;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO {

    static public int criar(Pagamento pagamento) throws SQLException {
        String sql = "INSERT INTO Pagamento (id_pedido, tipo_pagamento, valor, observacao) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, pagamento.getIdPedido());
            stmt.setString(2, pagamento.getTipoPagamento());
            stmt.setDouble(3, pagamento.getValor());
            stmt.setString(4, pagamento.getObservacao());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    static public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Pagamento WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    static public List<Pagamento> listarPorPedido(int idPedido) throws SQLException {
        String sql = "SELECT * FROM Pagamento WHERE id_pedido = ? ORDER BY data DESC";
        List<Pagamento> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Pagamento pag = new Pagamento();
                    pag.setId(rs.getInt("id"));
                    pag.setIdPedido(rs.getInt("id_pedido"));
                    pag.setTipoPagamento(rs.getString("tipo_pagamento"));
                    pag.setValor(rs.getDouble("valor"));
                    pag.setObservacao(rs.getString("observacao"));
                    pag.setData(LocalDate.parse(rs.getString("data")));
                    lista.add(pag);
                }
            }
        }
        return lista;
    }

    static public double calcularTotalPago(int idPedido) throws SQLException {
        String sql = "SELECT SUM(valor) as total FROM Pagamento WHERE id_pedido = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        }
        return 0.0;
    }
}