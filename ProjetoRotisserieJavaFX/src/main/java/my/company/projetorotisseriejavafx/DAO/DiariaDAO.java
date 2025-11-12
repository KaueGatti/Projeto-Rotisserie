package my.company.projetorotisseriejavafx.DAO;

import my.company.projetorotisseriejavafx.DB.Conexao;
import my.company.projetorotisseriejavafx.Objects.Diaria;

import java.sql.*;
import java.time.LocalDate;

public class DiariaDAO {
    
    public static Diaria read(LocalDate data) throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("CALL READ_DIARIA(?)");

            stmt.setDate(1, Date.valueOf(data));
            
            rs = stmt.executeQuery();

            if (rs.next()) {
                Diaria diaria = new Diaria();

                diaria.setEntregas(rs.getInt("entregas"));
                diaria.setValorEntregas(rs.getDouble("valorEntregas"));

                return diaria;
            }
        } finally {
            Conexao.closeConnection(con, stmt);
        }

        return null;
    }

}
