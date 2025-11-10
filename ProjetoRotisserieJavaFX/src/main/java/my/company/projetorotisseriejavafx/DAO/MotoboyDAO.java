package my.company.projetorotisseriejavafx.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import my.company.projetorotisseriejavafx.DB.Conexao;
import my.company.projetorotisseriejavafx.Objects.Motoboy;

public class MotoboyDAO {

    public static void create(Motoboy motoboy) throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL CREATE_MOTOBOY(?, ?)");

            stmt.setString(1, motoboy.getNome());
            stmt.setDouble(2, motoboy.getValorDiaria());

            stmt.executeUpdate();
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static List<Motoboy> read() throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Motoboy> motoboys = new ArrayList();

        try {
            stmt = con.prepareStatement("CALL READ_ALL_MOTOBOYS()");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Motoboy motoboy = new Motoboy();

                motoboy.setId(rs.getInt("id"));
                motoboy.setNome(rs.getString("nome"));
                motoboy.setValorDiaria(rs.getDouble("valor_diaria"));
                motoboy.setStatus(rs.getString("status"));

                motoboys.add(motoboy);
            }

            return motoboys;
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static List<Motoboy> readDiaria(Integer id_motoboy, LocalDate data) throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Motoboy> motoboys = new ArrayList();

        try {
            stmt = con.prepareStatement("CALL READ_DIARIA(?, ?)");

            stmt.setInt(1, id_motoboy);
            stmt.setDate(2, java.sql.Date.valueOf(data));

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
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }
    
    public static List<Motoboy> read(int id) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Motoboy> motoboys = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT * FROM Motoboy WHERE id = ?");
            
            stmt.setInt(1, id);
            
            rs = stmt.executeQuery();

            while (rs.next()) {
                Motoboy motoboy = new Motoboy();

                motoboy.setId(rs.getInt("id"));
                motoboy.setNome(rs.getString("nome"));
                motoboy.setValorDiaria(rs.getDouble("valor_diaria"));
                motoboy.setStatus(rs.getString("status"));

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

    public static void update(Motoboy motoboy) throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL UPDATE_MOTOBOY(?, ?, ?)");

            stmt.setInt(1, motoboy.getId());
            stmt.setDouble(2, motoboy.getValorDiaria());
            stmt.setString(3, motoboy.getStatus());

            stmt.executeUpdate();
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
