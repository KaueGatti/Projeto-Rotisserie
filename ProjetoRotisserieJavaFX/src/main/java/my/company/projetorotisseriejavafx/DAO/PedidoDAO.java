package my.company.projetorotisseriejavafx.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import my.company.projetorotisseriejavafx.DB.Conexao;
import my.company.projetorotisseriejavafx.Objects.Bairro;
import my.company.projetorotisseriejavafx.Objects.Mensalista;
import my.company.projetorotisseriejavafx.Objects.Motoboy;
import my.company.projetorotisseriejavafx.Objects.Pedido;

public class PedidoDAO {

    public static int create(Pedido pedido) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("CALL create_pedido(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            if (pedido.getMensalista() != null) {
                stmt.setInt(1, pedido.getMensalista().getId());
            } else {
                stmt.setNull(1, Types.INTEGER);
            }

            if (pedido.getBairro() != null) {
                stmt.setInt(2, pedido.getBairro().getId());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }

            if (pedido.getMotoboy() != null) {
                stmt.setInt(3, pedido.getMotoboy().getId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }

            if (pedido.getNomeCliente() != null) {
                stmt.setString(4, pedido.getNomeCliente());
            } else {
                stmt.setNull(4, Types.VARCHAR);
            }

            stmt.setString(5, pedido.getTipoPagamento());

            stmt.setString(6, pedido.getTipoPedido());

            stmt.setString(7, pedido.getObservacoes());

            stmt.setDouble(8, pedido.getValorEntrega());
            stmt.setDouble(9, pedido.getValorTotal());

            if (pedido.getEndereco() != null) {
                stmt.setString(10, pedido.getEndereco());
            } else {
                stmt.setNull(10, Types.VARCHAR);
            }

            rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Falha ao cadastrar pedido: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
        return -1;
    }

    public static List<Pedido> read() throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Pedido> pedidos = new ArrayList<>();

        try {
            stmt = con.prepareStatement("CALL READ_ALL_PEDIDOS()");

            rs = stmt.executeQuery();

            while (rs.next()) {
                Pedido pedido = new Pedido();

                pedido.setId(rs.getInt("id"));

                rs.getInt("id_mensalista");

                if (rs.getInt("id_mensalista") != 0) {
                    for (Mensalista mensalista : MensalistaDAO.read(rs.getInt("id_mensalista"))) {
                        pedido.setMensalista(mensalista);
                    }
                }

                pedido.setTipoPagamento(rs.getString("tipo_pagamento"));

                pedido.setTipoPedido(rs.getString("tipo_pedido"));

                if (pedido.getTipoPedido().equals("Entrega")) {

                    for (Bairro bairro : BairroDAO.read(rs.getInt("id_bairro"))) {
                        pedido.setBairro(bairro);
                    }

                    for (Motoboy motoboy : MotoboyDAO.read(rs.getInt("id_motoboy"))) {
                        pedido.setMotoboy(motoboy);
                    }

                    pedido.setValorEntrega(rs.getInt("valor_entrega"));
                }

                pedido.setNomeCliente(rs.getString("nome_cliente"));
                pedido.setObservacoes(rs.getString("observacoes"));
                pedido.setValorTotal(rs.getDouble("valor_total"));
                pedido.setEndereco(rs.getString("endereco"));
                pedido.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
                pedido.setStatus(rs.getString("_status"));

                pedidos.add(pedido);
            }

            return pedidos;
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static List<Pedido> read(int id) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Pedido> pedidos = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM Pedido WHERE id = ?");

            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Pedido pedido = new Pedido();

                pedido.setId(rs.getInt("id"));

                rs.getInt("id_mensalista");

                if (rs.getInt("id_mensalista") != 0) {
                    for (Mensalista mensalista : MensalistaDAO.read(rs.getInt("id_mensalista"))) {
                        pedido.setMensalista(mensalista);
                    }
                }

                pedido.setTipoPagamento(rs.getString("tipo_pagamento"));

                pedido.setTipoPedido(rs.getString("tipo_pedido"));

                if (pedido.getTipoPedido().equals("Entrega")) {

                    for (Bairro bairro : BairroDAO.read(rs.getInt("id_bairro"))) {
                        pedido.setBairro(bairro);
                    }

                    for (Motoboy motoboy : MotoboyDAO.read(rs.getInt("id_motoboy"))) {
                        pedido.setMotoboy(motoboy);
                    }

                    pedido.setValorEntrega(rs.getInt("valor_entrega"));
                }

                pedido.setNomeCliente(rs.getString("nome_cliente"));
                pedido.setObservacoes(rs.getString("observacoes"));
                pedido.setValorTotal(rs.getDouble("valor_total"));
                pedido.setEndereco(rs.getString("endereco"));
                pedido.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
                pedido.setStatus(rs.getString("_status"));

                pedidos.add(pedido);
            }
            return pedidos;
        } catch (SQLException e) {
            System.out.println("Falha ao buscar pedidos pelo id: " + e);
            e.printStackTrace();
        } finally {
            Conexao.closeConnection(con, stmt);
        }
        return pedidos;
    }

    public static void update(Pedido pedido) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL update_pedido(?, ?)");

            stmt.setInt(1, pedido.getId());
            stmt.setString(2, pedido.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Falha ao atualizar pedido: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static void delete(Pedido pedido) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL delete_pedido(?)");

            stmt.setInt(1, pedido.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir pedido: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }
}
