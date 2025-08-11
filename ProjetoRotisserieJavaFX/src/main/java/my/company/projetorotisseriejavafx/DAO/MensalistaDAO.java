/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.company.projetorotisseriejavafx.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import my.company.projetorotisseriejavafx.DB.Conexao;
import my.company.projetorotisseriejavafx.Objects.Bairro;
import my.company.projetorotisseriejavafx.Objects.Mensalista;

/**
 *
 * @author kaueg
 */
public class MensalistaDAO {

    public static void create(Mensalista mensalista) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL create_mensalista(?, ?, ?, ?)");

            stmt.setInt(1, mensalista.getBairro().getId());
            stmt.setString(2, mensalista.getNome());
            stmt.setString(3, mensalista.getCPF());
            stmt.setString(3, mensalista.getEndereco());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Falha ao cadastrar mensalista: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static List<Mensalista> read() {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Mensalista> mensalistas = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT * FROM Mensalista");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Mensalista mensalista = new Mensalista();

                mensalista.setId(rs.getInt("id"));
                //mensalista.setBairro(rs.getInt("id_bairro"));
                mensalista.setNome(rs.getString("nome"));
                mensalista.setCPF(rs.getString("cpf"));
                mensalista.setConta(rs.getDouble("conta"));
                mensalista.setEndereco(rs.getString("endereco"));
                mensalista.setStatus(rs.getString("_status"));

                mensalistas.add(mensalista);
            }

            return mensalistas;
        } catch (SQLException e) {
            System.out.println("Falha ao buscar mensalistas: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
        return null;
    }
    
    public static List<Mensalista> read(int id) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Mensalista> mensalistas = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT * FROM Mensalista WHERE id = ?");
            
            stmt.setInt(1, id);
            
            rs = stmt.executeQuery();

            while (rs.next()) {
                Mensalista mensalista = new Mensalista();

                mensalista.setId(rs.getInt("id"));
                
                if (rs.getInt("id_bairro") != Types.NULL) {
                    for (Bairro bairro : BairroDAO.read(rs.getInt("id_bairro"))) {
                        mensalista.setBairro(bairro);
                    }
                }
                
                mensalista.setNome(rs.getString("nome"));
                mensalista.setCPF(rs.getString("cpf"));
                mensalista.setConta(rs.getDouble("conta"));
                mensalista.setEndereco(rs.getString("endereco"));
                mensalista.setStatus(rs.getString("_status"));

                mensalistas.add(mensalista);
            }

            return mensalistas;
        } catch (SQLException e) {
            System.out.println("Falha ao buscar mensalistas: " + e);
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
    public static void update(Mensalista mensalista) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL update_mensalista(?, ?, ?)");

            stmt.setInt(1, mensalista.getId());
            stmt.setString(2, mensalista.getEndereco());
            stmt.setString(3, mensalista.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Falha ao atualizar mensalista: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static void delete(Mensalista mensalista) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL delete_mensalista(?)");

            stmt.setInt(1, mensalista.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir mensalista: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }
}
