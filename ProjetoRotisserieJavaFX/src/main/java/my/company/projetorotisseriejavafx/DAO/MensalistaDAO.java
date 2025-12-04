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

public class MensalistaDAO {

    public static void create(Mensalista mensalista) throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL CREATE_MENSALISTA(?, ?)");

            stmt.setString(1, mensalista.getNome());
            stmt.setString(2, mensalista.getContato());

            stmt.executeUpdate();
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static List<Mensalista> read() throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Mensalista> mensalistas = new ArrayList();

        try {
            stmt = con.prepareStatement("CALL READ_ALL_MENSALISTAS()");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Mensalista mensalista = new Mensalista();

                mensalista.setId(rs.getInt("id"));
                mensalista.setNome(rs.getString("nome"));
                mensalista.setContato(rs.getString("contato"));
                mensalista.setConta(rs.getDouble("conta"));
                mensalista.setStatus(rs.getString("status"));

                mensalistas.add(mensalista);
            }

            return mensalistas;
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }
    
    public static List<Mensalista> read(int id) throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Mensalista> mensalistas = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM Mensalista WHERE id = ?");
            
            stmt.setInt(1, id);
            
            rs = stmt.executeQuery();

            while (rs.next()) {
                Mensalista mensalista = new Mensalista();

                mensalista.setId(rs.getInt("id"));
                mensalista.setNome(rs.getString("nome"));
                mensalista.setContato(rs.getString("contato"));
                mensalista.setConta(rs.getDouble("conta"));
                mensalista.setStatus(rs.getString("status"));

                mensalistas.add(mensalista);
            }

            return mensalistas;
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static void update(Mensalista mensalista) throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL UPDATE_MENSALISTA(?, ?, ?)");

            stmt.setInt(1, mensalista.getId());
            stmt.setString(2, mensalista.getStatus());
            stmt.setString(3, mensalista.getContato());

            stmt.executeUpdate();
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
