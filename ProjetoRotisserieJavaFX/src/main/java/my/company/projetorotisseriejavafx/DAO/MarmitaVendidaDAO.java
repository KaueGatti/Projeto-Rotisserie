package my.company.projetorotisseriejavafx.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import my.company.projetorotisseriejavafx.DB.Conexao;
import my.company.projetorotisseriejavafx.Objects.MarmitaVendida;

public class MarmitaVendidaDAO {

    public static void create(List<MarmitaVendida> marmitas) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("CALL create_marmita_vendida(?, ?, ?, ?, ?)");

            for (MarmitaVendida marmita : marmitas) {
                stmt.setInt(1, marmita.getMarmita().getId());
                stmt.setInt(2, marmita.getPedido().getId());
                stmt.setDouble(3, marmita.getSubtotal());
                stmt.setString(4, marmita.getDetalhes());
                stmt.setString(5, marmita.getObservacao());
                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            System.out.println("Falha ao cadastrar marmita vendida: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    /*public static List<Pedido> read() {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Pedido> pedidos = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT * FROM Pedido");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Pedido pedido = new Pedido();

                pedido.setId(rs.getInt("id"));
                pedido.setNome(rs.getString("nome"));
                pedido.setValorDiaria(rs.getDouble("valor_diaria"));
                pedido.setStatus(rs.getString("_status"));
                pedido.setStatus(rs.getString("_status"));
                pedido.setStatus(rs.getString("_status"));
                pedido.setStatus(rs.getString("_status"));
                pedido.setStatus(rs.getString("_status"));
                pedido.setStatus(rs.getString("_status"));
                pedido.setStatus(rs.getString("_status"));
                pedido.setStatus(rs.getString("_status"));
                pedido.setStatus(rs.getString("_status"));
                pedido.setStatus(rs.getString("_status"));

                pedido.add(pedido);
            }

            return pedidos;
        } catch (SQLException e) {
            System.out.println("Falha ao buscar pedido: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
        return null;
    }*/

 /*public static List<Remedio> readDinamico(String descricao, Laboratorio l,
            double valorCustoMin, double valorCustoMax,
            double valorVendaMin, double valorVendaMax,
            String status, String orderBy, boolean desc) {

        Connection con = Conexao.getConnection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Remedio> remedios = new ArrayList();

        try {
            cs = con.prepareCall("CALL filterRemedioDinamico(?, ?, ?, ?, ?, ?, ?, ?, ?)");

            cs.setString(1, "%" + descricao + "%");
            
            if (l != null) {
                cs.setInt(2, l.getId());
            } else {
                cs.setNull(2, Types.INTEGER);
            }
            
            cs.setDouble(3, valorCustoMin);
            cs.setDouble(4, valorCustoMax);
            cs.setDouble(5, valorVendaMin);
            cs.setDouble(6, valorVendaMax);
            
            if (status != null) {
                cs.setString(7, status);
            } else {
                cs.setNull(7, Types.VARCHAR);
            }
            
            if (orderBy != null) {
                cs.setString(8, orderBy);
            } else {
                cs.setNull(8, Types.VARCHAR);
            }
            
            cs.setBoolean(9, desc);

            rs = cs.executeQuery();

            while (rs.next()) {
                Remedio remedio = new Remedio();

                remedio.setId(rs.getInt("id_remedio"));
                for (Laboratorio lab : LaboratorioDAO.read()) {
                    if (lab.getId() == rs.getInt("id_lab")) {
                        remedio.setLaboratorio(lab);
                        break;
                    }
                }
                remedio.setDescricao(rs.getString("descricao"));
                if (rs.getDate("data_ultima_compra") != null) {
                    remedio.setDataUltimaCompra(rs.getDate("data_ultima_compra").toLocalDate());
                }
                remedio.setValorCusto(rs.getDouble("valor_custo"));
                remedio.setValorVenda(rs.getDouble("valor_venda"));
                remedio.setQuantidade(rs.getInt("qntd_armazenada"));
                remedio.setStatus(rs.getString("_status"));

                remedios.add(remedio);
            }

            return remedios;
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Falha ao buscar remédios dinamicamente: " + e);
        } finally {
            Conexao.closeConnection(con, cs);
        }
        return null;
    }*/
}
