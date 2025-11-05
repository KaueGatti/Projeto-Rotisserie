package my.company.projetorotisseriejavafx.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import my.company.projetorotisseriejavafx.DB.Conexao;
import my.company.projetorotisseriejavafx.Objects.Marmita;
import my.company.projetorotisseriejavafx.Objects.MarmitaVendida;
import my.company.projetorotisseriejavafx.Objects.Pedido;

public class MarmitaVendidaDAO {

    public static void create(List<MarmitaVendida> marmitas, int idPedido) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("CALL create_marmita_vendida(?, ?, ?, ?, ?)");

            for (MarmitaVendida marmita : marmitas) {
                stmt.setInt(1, idPedido);
                stmt.setInt(2, marmita.getIdMarmita());
                stmt.setDouble(3, marmita.getSubtotal());
                stmt.setString(4, marmita.getDetalhes());
                stmt.setString(5, marmita.getObservacao());
                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            System.out.println("Falha ao cadastrar marmita vendida: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static List<MarmitaVendida> read(int idPedido) throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<MarmitaVendida> marmitasVendidas = new ArrayList<>();

        try {
            stmt = con.prepareStatement("READ ALL_MARMITAS_PEDIDO(?)");
            
            stmt.setInt(1, idPedido);
            
            rs = stmt.executeQuery();

            while (rs.next()) {
                MarmitaVendida marmitaVendida = new MarmitaVendida();
                marmitaVendida.setNome(rs.getString("nome"));
                marmitaVendida.setDetalhes(rs.getString("detalhes"));
                marmitaVendida.setSubtotal(rs.getDouble("subtotal"));
                marmitaVendida.setObservacao(rs.getString("observacao"));

                marmitasVendidas.add(marmitaVendida);
            }

            return marmitasVendidas;
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }
}
