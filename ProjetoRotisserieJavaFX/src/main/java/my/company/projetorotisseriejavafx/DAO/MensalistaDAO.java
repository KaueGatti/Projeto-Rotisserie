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
            stmt = con.prepareStatement("CALL CREATE_MENSALISTA(?)");

            stmt.setString(1, mensalista.getNome());

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
                mensalista.setConta(rs.getDouble("conta"));
                mensalista.setStatus(rs.getString("_status"));

                mensalistas.add(mensalista);
            }

            return mensalistas;
        } finally {
            Conexao.closeConnection(con, stmt);
        }
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
