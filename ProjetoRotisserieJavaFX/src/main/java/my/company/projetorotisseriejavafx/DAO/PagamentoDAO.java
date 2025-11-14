package my.company.projetorotisseriejavafx.DAO;

import my.company.projetorotisseriejavafx.DB.Conexao;
import my.company.projetorotisseriejavafx.Objects.Pagamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO {

    public static void create(Pagamento pagamento) throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL CREATE_PAGAMENTO(?, ?, ?, ?)");

            stmt.setInt(1, pagamento.getIdPedido());
            stmt.setString(2, pagamento.getTipoPagamento());
            stmt.setDouble(3, pagamento.getValor());
            stmt.setString(4, pagamento.getObservacao());

            stmt.executeUpdate();
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static List<Pagamento> read() throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Pagamento> pagamentos = new ArrayList();

        try {
            stmt = con.prepareStatement("CALL READ_ALL_PAGAMENTOS()");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Pagamento pagamento = new Pagamento();

                pagamento.setId(rs.getInt("id"));
                pagamento.setIdPedido(rs.getInt("id_pedido"));
                pagamento.setTipoPagamento(rs.getString("tipo_pagamento"));
                pagamento.setValor(rs.getDouble("valor"));
                pagamento.setObservacao(rs.getString("observacao"));
                pagamento.setData(rs.getDate("data").toLocalDate());

                pagamentos.add(pagamento);
            }

            return pagamentos;
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static List<Pagamento> read(int idPedido) throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Pagamento> pagamentos = new ArrayList();

        try {
            stmt = con.prepareStatement("CALL READ_PAGAMENTOS_PEDIDO(?)");

            stmt.setInt(1, idPedido);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Pagamento pagamento = new Pagamento();

                pagamento.setId(rs.getInt("id"));
                pagamento.setIdPedido(rs.getInt("id_pedido"));
                pagamento.setTipoPagamento(rs.getString("tipo_pagamento"));
                pagamento.setValor(rs.getDouble("valor"));
                pagamento.setObservacao(rs.getString("observacao"));
                pagamento.setData(rs.getDate("data").toLocalDate());

                pagamentos.add(pagamento);
            }

            return pagamentos;
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }
}