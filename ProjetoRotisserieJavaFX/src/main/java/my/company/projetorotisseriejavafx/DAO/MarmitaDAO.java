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

/**
 *
 * @author kaueg
 */
public class MarmitaDAO {

    public static void create(Marmita marmita) throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL CREATE_MARMITA(?, ?, ?, ?)");

            stmt.setString(1, marmita.getNome());
            stmt.setInt(2, marmita.getMaxMistura());
            stmt.setInt(3, marmita.getMaxGuarnicao());
            stmt.setDouble(4, marmita.getValor());

            stmt.executeUpdate();
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
            stmt = con.prepareStatement("CALL READ_ALL_MARMITAS()");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Marmita marmita = new Marmita();

                marmita.setId(rs.getInt("id"));
                marmita.setNome(rs.getString("nome"));
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
        return marmitas;
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
                marmita.setNome(rs.getString("nome"));
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

    public static void update(Marmita marmita) throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL UPDATE_MARMITA(?, ?, ?)");

            stmt.setInt(1, marmita.getId());
            stmt.setDouble(2, marmita.getValor());
            stmt.setString(3, marmita.getStatus());

            stmt.executeUpdate();
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static void delete(Marmita marmita) throws  SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL delete_marmita(?)");

            stmt.setInt(1, marmita.getId());

            stmt.executeUpdate();
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }
}
