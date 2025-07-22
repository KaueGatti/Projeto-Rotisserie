/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.company.projetorotisserie.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import my.company.projetorotisserie.DB.Conexao;
import my.company.projetorotisserie.Objects.Marmita;

/**
 *
 * @author kaueg
 */
public class MarmitasCadastradasDAO {

    public static void create(Marmita marmita) {
        Connection conn = Conexao.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("INSERT INTO marmitas_cadastradas (descricao, tamanho, valor) VALUES (?, ?, ?)");

            stmt.setString(1, marmita.getDescricao());
            stmt.setString(2, marmita.getTamanho());
            stmt.setDouble(3, marmita.getValor());

            stmt.executeUpdate();
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
            stmt = conn.prepareStatement("SELECT * FROM marmitas_cadastradas");
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

    public static void update(Marmita marmita) {
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
            System.out.println("Erro ao atualizar dados\nErro: " + e);
        } finally {
            Conexao.closeConnection(conn, stmt);
        }
    }

    public static void delete(Marmita marmita) {
        Connection conn = Conexao.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM marmitas_cadastradas WHERE cod = ?");

            stmt.setInt(1, marmita.getCod());

            stmt.executeUpdate();
            System.out.println("Dados deletados");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar dados\nErro: " + e);
        } finally {
            Conexao.closeConnection(conn, stmt);
        }
    }

}
