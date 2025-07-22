package my.company.projetorotisserie.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import my.company.projetorotisserie.DB.Conexao;
import my.company.projetorotisserie.Objects.Pedido;

/**
 *
 * @author kaueg
 */
public class PedidoDAO {

    public static void create(Pedido pedido) {
        Connection conn = Conexao.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("INSERT INTO pedidos (nome_cliente, valor_total, valor_entrega, tipo, data_pedido, horario_pedido) VALUES (?, ?, ?, ?, ?, ?)");

            stmt.setString(1, pedido.getCliente());
            stmt.setDouble(2, pedido.getValorTotal());
            stmt.setDouble(3, pedido.getValorEntrega());
            stmt.setString(4, pedido.getTipo());
            stmt.setDate(5, Date.valueOf(pedido.getData()));
            stmt.setTime(6, Time.valueOf(pedido.getHorário()));
            
            stmt.executeUpdate();

            System.out.println("Pedido realizado");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar pedido: " + e);
        } finally {
            Conexao.closeConnection(conn, stmt);
        }
    }

    public static List<Pedido> read() {
        List<Pedido> pedidos = new ArrayList<>();
        Connection conn = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement("SELECT * FROM pedidos");

            rs = stmt.executeQuery();

            while (rs.next()) {
                Pedido pedido = new Pedido();
                
                pedido.setCliente(rs.getString("nome_cliente"));
                pedido.setValorTotal(rs.getDouble("valor_total"));
                pedido.setValorEntrega(rs.getDouble("valor_entrega"));
                pedido.setTipo(rs.getString("tipo"));
                pedido.setId(rs.getInt("id"));
                
                pedidos.add(pedido);
            }
            return pedidos;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar pedidos: " + e);
        } finally {
            Conexao.closeConnection(conn, stmt, rs);
        }
        return null;
    }

    public static void update(Pedido pedido) {
        Connection conn = Conexao.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("UPDATE pedidos SET nome_cliente = ?, valor_total = ?, valor_entrega = ?, tipo = ? WHERE id = ?");

            stmt.setString(1, pedido.getCliente());
            stmt.setDouble(2, pedido.getValorTotal());
            stmt.setDouble(3, pedido.getValorEntrega());
            stmt.setString(4, pedido.getTipo());
            stmt.setInt(5, pedido.getId());
            
            stmt.executeUpdate();

            System.out.println("Pedido atualizado");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar pedido: " + e);
        } finally {
            Conexao.closeConnection(conn, stmt);
        }
    }

    public static void delete(Pedido pedido) {
        Connection conn = Conexao.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM pedidos WHERE id = ?");

            stmt.setInt(1, pedido.getId());

            System.out.println("Pedido excluido");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir pedido: " + e);
        } finally {
            Conexao.closeConnection(conn, stmt);
        }
    }

}
