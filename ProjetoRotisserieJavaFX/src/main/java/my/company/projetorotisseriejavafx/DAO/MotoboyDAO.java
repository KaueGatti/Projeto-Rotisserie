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
import my.company.projetorotisseriejavafx.Objects.Motoboy;

/**
 *
 * @author kaueg
 */
public class MotoboyDAO {

    public static void create(Motoboy motoboy) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL create_motoboy(?, ?)");

            stmt.setString(1, motoboy.getNome());
            stmt.setDouble(2, motoboy.getValorDiaria());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Falha ao cadastrar motoboy: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static List<Motoboy> read() {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Motoboy> motoboys = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT * FROM Motoboy");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Motoboy motoboy = new Motoboy();

                motoboy.setId(rs.getInt("id"));
                motoboy.setNome(rs.getString("nome"));
                motoboy.setValorDiaria(rs.getDouble("valor_diaria"));
                motoboy.setStatus(rs.getString("_status"));

                motoboys.add(motoboy);
            }

            return motoboys;
        } catch (SQLException e) {
            System.out.println("Falha ao buscar motoboys: " + e);
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
    public static void update(Motoboy motoboy) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL update_motoboy(?, ?)");

            stmt.setInt(1, motoboy.getId());
            stmt.setDouble(2, motoboy.getValorDiaria());
            stmt.setString(3, motoboy.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Falha ao atualizar motoboy: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static void delete(Motoboy motoboy) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL delete_motoboy(?)");

            stmt.setInt(1, motoboy.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir motoboy: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }
}
