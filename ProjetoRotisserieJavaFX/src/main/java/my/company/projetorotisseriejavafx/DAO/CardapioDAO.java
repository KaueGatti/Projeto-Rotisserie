package my.company.projetorotisseriejavafx.DAO;

import my.company.projetorotisseriejavafx.DB.DatabaseConnection;
import my.company.projetorotisseriejavafx.Objects.Cardapio;

import java.sql.*;

public class CardapioDAO {

    public static void create(Cardapio cardapio) throws SQLException {

        String sql = "INSERT INTO Cardapio (principal_1, principal_2, mistura_1, mistura_2, mistura_3, mistura_4," +
                "guarnicao_1, guarnicao_2, guarnicao_3, guarnicao_4, salada_1, salada_2, data_hora)" +

                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'now')";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cardapio.getPrincipal1());
            stmt.setString(2, cardapio.getPrincipal2());
            stmt.setString(3, cardapio.getMistura1());
            stmt.setString(4, cardapio.getMistura2());
            stmt.setString(5, cardapio.getMistura3());
            stmt.setString(6, cardapio.getMistura4());
            stmt.setString(7, cardapio.getGuarnicao1());
            stmt.setString(8, cardapio.getGuarnicao2());
            stmt.setString(9, cardapio.getGuarnicao3());
            stmt.setString(10, cardapio.getGuarnicao4());
            stmt.setString(11, cardapio.getSalada1());
            stmt.setString(12, cardapio.getSalada2());

            stmt.executeUpdate();
        }
    }

    public static Cardapio read() throws SQLException {

        String sql = "SELECT * FROM Cardapio ORDER BY data_hora DESC LIMIT 1;";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);) {

            try (ResultSet rs = stmt.executeQuery()) {

                if (!rs.next()) return null;

                Cardapio cardapio = new Cardapio();

                cardapio.setPrincipal1(rs.getString("principal_1"));
                cardapio.setPrincipal2(rs.getString("principal_2"));
                cardapio.setMistura1(rs.getString("mistura_1"));
                cardapio.setMistura2(rs.getString("mistura_2"));
                cardapio.setMistura3(rs.getString("mistura_3"));
                cardapio.setMistura4(rs.getString("mistura_4"));
                cardapio.setGuarnicao1(rs.getString("guarnicao_1"));
                cardapio.setGuarnicao2(rs.getString("guarnicao_2"));
                cardapio.setGuarnicao3(rs.getString("guarnicao_3"));
                cardapio.setGuarnicao4(rs.getString("guarnicao_4"));
                cardapio.setSalada1(rs.getString("salada_1"));
                cardapio.setSalada2(rs.getString("salada_2"));

                return cardapio;
            }
        }
    }
}
