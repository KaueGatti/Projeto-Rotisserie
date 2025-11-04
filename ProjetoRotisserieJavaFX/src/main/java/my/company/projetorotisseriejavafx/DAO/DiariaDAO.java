package my.company.projetorotisseriejavafx.DAO;

import my.company.projetorotisseriejavafx.DB.Conexao;
import my.company.projetorotisseriejavafx.Objects.Diaria;
import my.company.projetorotisseriejavafx.Objects.Motoboy;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DiariaDAO {
    
    public static Diaria read(Motoboy motoboy, LocalDate data) throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("CALL READ_DIARIA(?, ?)");
            
            stmt.setInt(1, motoboy.getId());
            stmt.setDate(2, Date.valueOf(data));
            
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
