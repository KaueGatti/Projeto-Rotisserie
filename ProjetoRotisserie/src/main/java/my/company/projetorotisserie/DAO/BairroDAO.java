package my.company.projetorotisserie.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import my.company.projetorotisserie.DB.Conexao;
import my.company.projetorotisserie.Objects.Bairro;

public class BairroDAO {

    public static void create(Bairro bairro) {
        Connection conn = Conexao.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("INSERT INTO bairro (nome, valor_entrega) VALUES (?, ?)");

            stmt.setString(1, bairro.getNome());
            stmt.setDouble(2, bairro.getValorEntrega());
            
            stmt.executeUpdate();

            System.out.println("Bairro adicionado");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar bairro: " + e);
        } finally {
            Conexao.closeConnection(conn, stmt);
        }
    }

    public static List<Bairro> read() {
        List<Bairro> bairros = new ArrayList<>();
        Connection conn = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement("SELECT * FROM bairro");

            rs = stmt.executeQuery();

            while (rs.next()) {
                Bairro bairro = new Bairro();

                bairro.setId(rs.getInt("id"));
                bairro.setNome(rs.getString("nome"));
                bairro.setValorEntrega(rs.getDouble("valor_entrega"));

                bairros.add(bairro);
            }
            return bairros;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar bairros: " + e);
        } finally {
            Conexao.closeConnection(conn, stmt, rs);
        }
        return null;
    }
    
    public static Bairro read(String nomeBairro) {
        Connection conn = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement("SELECT * FROM bairro WHERE nome = ?");

            stmt.setString(1, nomeBairro);
            
            rs = stmt.executeQuery();

            while (rs.next()) {
                Bairro bairro = new Bairro();

                bairro.setId(rs.getInt("id"));
                bairro.setNome(rs.getString("nome"));
                bairro.setValorEntrega(rs.getDouble("valor_entrega"));

                return bairro;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar bairro pelo nome: " + e);
        } finally {
            Conexao.closeConnection(conn, stmt, rs);
        }
        return null;
    }

    public static void update(Bairro bairro) {
        Connection conn = Conexao.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("UPDATE bairro SET nome = ?, valor_entrega = ? WHERE id = ?");

            stmt.setString(1, bairro.getNome());
            stmt.setDouble(2, bairro.getValorEntrega());
            stmt.setInt(3, bairro.getId());

            stmt.executeUpdate();

            System.out.println("Bairro atualizado");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar bairro: " + e);
        } finally {
            Conexao.closeConnection(conn, stmt);
        }
    }

    public static void delete(Bairro bairro) {
        Connection conn = Conexao.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM bairro WHERE id = ?");

            stmt.setInt(1, bairro.getId());
            
            stmt.executeUpdate();

            System.out.println("Bairro excluido");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir bairro: " + e);
        } finally {
            Conexao.closeConnection(conn, stmt);
        }
    }
}
