/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.company.projetorotisseriejavafx.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import my.company.projetorotisseriejavafx.DB.Conexao;
import my.company.projetorotisseriejavafx.Objects.Marmita;
import my.company.projetorotisseriejavafx.Objects.Produto;

/**
 *
 * @author kaueg
 */
public class MarmitaDAO {

    public static void create(Marmita marmita) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL create_marmita(?, ?, ?, ?)");

            stmt.setString(1, marmita.getDescricao());
            stmt.setInt(2, marmita.getMaxMistura());
            stmt.setInt(3, marmita.getMaxGuarnicao());
            stmt.setDouble(4, marmita.getValor());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Falha ao cadastrar marmita: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static List<Marmita> read() {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Marmita> marmitas = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM Marmita");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Marmita marmita = new Marmita();

                marmita.setId(rs.getInt("id"));
                marmita.setDescricao(rs.getString("descricao"));
                marmita.setMaxMistura(rs.getInt("max_mistura"));
                marmita.setMaxGuarnicao(rs.getInt("max_guarnicao"));
                marmita.setValor(rs.getDouble("valor"));
                marmita.setStatus(rs.getString("_status"));

                marmitas.add(marmita);
            }

            return marmitas;
        } catch (SQLException e) {
            System.out.println("Falha ao buscar Marmitas: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
        return new ArrayList<>();
    }
    
    public static List<Marmita> read(int id) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Marmita> marmitas = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT * FROM Marmita WHERE id = ?");
            
            stmt.setInt(1, id);
            
            rs = stmt.executeQuery();

            while (rs.next()) {
                Marmita marmita = new Marmita();

                marmita.setId(rs.getInt("id"));
                marmita.setDescricao(rs.getString("descricao"));
                marmita.setMaxMistura(rs.getInt("max_mistura"));
                marmita.setMaxGuarnicao(rs.getInt("max_guarnicao"));
                marmita.setValor(rs.getDouble("valor"));
                marmita.setStatus(rs.getString("_status"));

                marmitas.add(marmita);
            }

            return marmitas;
        } catch (SQLException e) {
            System.out.println("Falha ao buscar Marmitas pelo id: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
        return null;
    }

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
    public static void update(Marmita marmita) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL update_marmita(?, ?, ?)");

            stmt.setInt(1, marmita.getId());
            stmt.setDouble(2, marmita.getValor());
            stmt.setString(3, marmita.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Falha ao atualizar marmita: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static void delete(Marmita marmita) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL delete_marmita(?)");

            stmt.setInt(1, marmita.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir marmita: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }
}
