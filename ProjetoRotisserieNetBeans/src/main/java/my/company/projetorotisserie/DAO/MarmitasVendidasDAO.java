package my.company.projetorotisserie.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import my.company.projetorotisserie.DB.Conexao;
import my.company.projetorotisserie.Objects.Marmita;

public class MarmitasVendidasDAO {

    public static void create(List<Marmita> marmitas, int idPedido) {
        Connection conn = Conexao.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("INSERT INTO marmitas_vendidas (descricao, tamanho, mistura_1, mistura_2, mistura_3, guarnicao_1, guarnicao_2, guarnicao_3, salada, cod_marmita, id_pedido) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            
            for (Marmita marmita: marmitas) {
            stmt.setString(1, marmita.getDescricao());
            stmt.setString(2, marmita.getTamanho());
            stmt.setString(3, marmita.getMisturas().get(0));
            stmt.setString(4, marmita.getMisturas().get(1));
            stmt.setString(5, marmita.getMisturas().get(2));
            stmt.setString(6, marmita.getGuarnicoes().get(0));
            stmt.setString(7, marmita.getGuarnicoes().get(1));
            stmt.setString(8, marmita.getGuarnicoes().get(2));
            stmt.setString(9, marmita.getSalada());
            stmt.setInt(10, marmita.getCod());
            stmt.setInt(11, idPedido);

            stmt.executeUpdate();
            }
            System.out.println("Dados cadastrados");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar dados\nErro: " + e);
        }
    }

    public static List<Marmita> read() {
        List<Marmita> marmitas = new ArrayList<>();
        Connection conn = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("SELECT * FROM marmitas_vendidas");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Marmita marmita = new Marmita();
                marmita.setCod(rs.getInt("cod"));
                marmita.setDescricao(rs.getString("descricao"));
                marmita.setTamanho(rs.getString("tamanho"));
                marmita.setValor(rs.getDouble("valor"));
                

                marmitas.add(marmita);
            }
            return marmitas;
        } catch (SQLException e) {
            System.out.println("Erro ao buscar dados\nErro: " + e);
        } finally {
            Conexao.closeConnection(conn, stmt, rs);
        }
        return null;
    }

    public static Marmita read(String descricao) {
        Connection conn = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("SELECT * FROM marmitas_cadastradas WHERE descricao = ?");

            stmt.setString(1, descricao);

            rs = stmt.executeQuery();

            if (rs.next()) {
                Marmita marmita = new Marmita();
                marmita.setCod(rs.getInt("cod"));
                marmita.setDescricao(rs.getString("descricao"));
                marmita.setTamanho(rs.getString("tamanho"));
                marmita.setValor(rs.getDouble("valor"));
                return marmita;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar dados\nErro: " + e);
        } finally {
            Conexao.closeConnection(conn, stmt, rs);
        }
        return null;
    }

    /*public static void update(Marmita marmita) {
        Connection conn = Conexao.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("UPDATE marmitas_cadastradas SET descricao = ?, tamanho = ?, valor = ? WHERE cod = ?");

            stmt.setString(1, marmita.getDescricao());
            stmt.setString(2, marmita.getTamanho());
            stmt.setDouble(3, marmita.getValor());
            stmt.setInt(4, marmita.getCod());

            stmt.executeUpdate();
            System.out.println("Dados atualizados");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar marmita vendida\nErro: " + e);
        } finally {
            Conexao.closeConnection(conn, stmt);
        }
    }

    public static void delete(Marmita marmita) {
        Connection conn = Conexao.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM marmitas_vendidas WHERE cod = ?");

            stmt.setInt(1, marmita.getCod());

            stmt.executeUpdate();
            System.out.println("Marmita vendida deletada");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar marmita vendida\nErro: " + e);
        } finally {
            Conexao.closeConnection(conn, stmt);
        }
    }*/
}
