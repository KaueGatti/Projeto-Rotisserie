package my.company.projetorotisseriejavafx.DAO;

import my.company.projetorotisseriejavafx.DB.Conexao;
import my.company.projetorotisseriejavafx.Objects.ItemCardapio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemCardapioDAO {

    public static int create(ItemCardapio itemCardapio) throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("CALL CREATE_ITEM_CARDAPIO(?, ?)", Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, itemCardapio.getNome());
            stmt.setString(2, itemCardapio.getCategoria());

            rs = stmt.executeQuery();

            rs.next();

            return rs.getInt("id");

        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static List<ItemCardapio> read() throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ItemCardapio> itensCardapio = new ArrayList<>();

        try {
            stmt = con.prepareStatement("CALL READ_ALL_ITENS_CARDAPIO()");

            rs = stmt.executeQuery();

            while (rs.next()) {
                ItemCardapio itemCardapio = new ItemCardapio();
                itemCardapio.setId(rs.getInt("id"));
                itemCardapio.setNome(rs.getString("nome"));
                itemCardapio.setCategoria(rs.getString("categoria"));

                itensCardapio.add(itemCardapio);
            }
            return itensCardapio;
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static List<ItemCardapio> read(String categoria) throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ItemCardapio> itensCardapio = new ArrayList<>();

        try {
            stmt = con.prepareStatement("CALL READ_ITENS_CARDAPIO_BY_CATEGORIA(?)");

            stmt.setString(1, categoria);

            rs = stmt.executeQuery();

            while (rs.next()) {
                ItemCardapio itemCardapio = new ItemCardapio();
                itemCardapio.setId(rs.getInt("id"));
                itemCardapio.setNome(rs.getString("nome"));
                itemCardapio.setCategoria(rs.getString("categoria"));

                itensCardapio.add(itemCardapio);
            }
            return itensCardapio;
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static void delete(int id) throws SQLException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL DELETE_ITEM_CARDAPIO(?)");

            stmt.setInt(1, id);

            stmt.executeUpdate();
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }
}
