/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.company.projetorotisseriejavafx.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import my.company.projetorotisseriejavafx.DB.Conexao;
import my.company.projetorotisseriejavafx.Objects.Bairro;

/**
 *
 * @author kaueg
 */
public class BairroDAO {

    public static void create(Bairro bairro) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL create_bairro(?, ?)");

            stmt.setString(1, bairro.getNome());
            stmt.setDouble(2, bairro.getValorEntrega());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Falha ao cadastrar bairro: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static List<Bairro> read() {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Bairro> bairros = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM Bairro");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Bairro bairro = new Bairro();

                bairro.setId(rs.getInt("id"));
                bairro.setNome(rs.getString("nome"));
                bairro.setValorEntrega(rs.getDouble("valor_entrega"));
                bairro.setStatus(rs.getString("_status"));

                bairros.add(bairro);
            }

            return bairros;
        } catch (SQLException e) {
            System.out.println("Falha ao buscar bairros: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
        return new ArrayList<>();
    }
    
    public static List<Bairro> read(int id) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Bairro> bairros = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT * FROM Bairro WHERE id = ?");
            
            stmt.setInt(1, id);
            
            rs = stmt.executeQuery();

            while (rs.next()) {
                Bairro bairro = new Bairro();

                bairro.setId(rs.getInt("id"));
                bairro.setNome(rs.getString("nome"));
                bairro.setValorEntrega(rs.getDouble("valor_entrega"));
                bairro.setStatus(rs.getString("_status"));

                bairros.add(bairro);
            }

            return bairros;
        } catch (SQLException e) {
            System.out.println("Falha ao buscar bairros: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
        return null;
    }

    public static List<Bairro> readDinamico(String nome, double valorEntregaMax, double valorEntregaMin, String status, String orderBy, boolean desc) {

        Connection con = Conexao.getConnection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Bairro> bairros = new ArrayList();

        try {
            cs = con.prepareCall("CALL filterBairroDinamico(?, ?, ?, ?, ?)");

            if (nome != null) {
                cs.setString(1, "%" + nome + "%");
            } else {
                cs.setNull(1, Types.VARCHAR);
            }

            cs.setDouble(2, valorEntregaMax);
            cs.setDouble(3, valorEntregaMin);

            if (status != null) {
                cs.setString(4, status);
            } else {
                cs.setNull(4, Types.VARCHAR);
            }

            if (orderBy != null) {
                cs.setString(5, orderBy);
            } else {
                cs.setNull(5, Types.VARCHAR);
            }

            cs.setBoolean(6, desc);

            rs = cs.executeQuery();

            while (rs.next()) {
                Bairro bairro = new Bairro();

                bairro.setId(rs.getInt("id"));
                bairro.setNome(rs.getString("nome"));
                bairro.setValorEntrega(rs.getDouble("valor_entrega"));
                bairro.setStatus(rs.getString("_status"));

                bairros.add(bairro);
            }

            return bairros;
        } catch (SQLException e) {
            System.out.println("Falha ao buscar bairros dinamicamente: " + e);
        } finally {
            Conexao.closeConnection(con, cs);
        }
        return null;
    }

    public static void update(Bairro bairro) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL update_bairro(?, ?, ?)");

            stmt.setInt(1, bairro.getId());
            stmt.setDouble(2, bairro.getValorEntrega());
            stmt.setString(3, bairro.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Falha ao atualizar bairro: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static void delete(Bairro bairro) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL delete_bairro(?)");

            stmt.setInt(1, bairro.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir bairro: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }
}
