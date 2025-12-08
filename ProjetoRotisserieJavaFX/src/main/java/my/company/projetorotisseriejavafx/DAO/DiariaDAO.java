package my.company.projetorotisseriejavafx.DAO;

import my.company.projetorotisseriejavafx.DB.DatabaseConnection;
import my.company.projetorotisseriejavafx.Objects.Diaria;

import java.sql.*;
import java.time.LocalDate;

public class DiariaDAO {

    public static Diaria read(LocalDate data) throws SQLException {
        String sql = "SELECT COUNT(*) AS entregas, SUM(valor_entrega) as valorEntregas FROM Pedido\n" +
                "\tWHERE DATE(date_time) = ? AND tipo_pedido = 'Entrega';";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, Date.valueOf(data));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Diaria diaria = new Diaria();

                    diaria.setEntregas(rs.getInt("entregas"));
                    diaria.setValorEntregas(rs.getDouble("valorEntregas"));

                    return diaria;
                }
            }
        }
        return null;
    }

}
