package my.company.projetorotisseriejavafx.DAO;

import my.company.projetorotisseriejavafx.DB.Conexao;
import my.company.projetorotisseriejavafx.Objects.DescontoAdicional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DescontoAdicionalDAO {

    public static void create(List<DescontoAdicional> descontosEAdicionais, int idPedido) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("CALL CREATE_DESCONTO_ADICIONAL(?, ?, ?, ?)");

            for (DescontoAdicional descontoAdicional : descontosEAdicionais) {
                stmt.setInt(1, idPedido);
                stmt.setString(2, descontoAdicional.getTipo());
                stmt.setDouble(3, descontoAdicional.getValor());
                stmt.setString(4, descontoAdicional.getObservacao());
                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            System.out.println("Falha ao cadastrar descontos e adicionais: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static List<DescontoAdicional> read(int idPedido) throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<DescontoAdicional> descontosEAdicionais = new ArrayList<>();

        try {
            stmt = con.prepareStatement("CALL READ_ALL_DESCONTOS_ADICIONAIS_PEDIDO(?)");
            
            stmt.setInt(1, idPedido);
            
            rs = stmt.executeQuery();

            while (rs.next()) {
                DescontoAdicional descontoAdicional = new DescontoAdicional();
                descontoAdicional.setId(rs.getInt("id"));
                descontoAdicional.setIdPedido(rs.getInt("id_pedido"));
                descontoAdicional.setTipo(rs.getString("tipo"));
                descontoAdicional.setValor(rs.getDouble("valor"));
                descontoAdicional.setObservacao(rs.getString("observacao"));

                descontosEAdicionais.add(descontoAdicional);
            }
            return descontosEAdicionais;
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }
}
