package my.company.projetorotisserie.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CompraDAO {

    /*public static int create(Compra c) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("CALL add_compra(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, c.getLaboratorio().getId());
            stmt.setDate(2, Date.valueOf(c.getDataCompra()));
            stmt.setString(3, c.getNmr_nota_fiscal());
            stmt.setDouble(4, c.getTotalNota());
            stmt.setString(5, c.getPagamento());

            rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar compra: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
        return 0;
    }

    public static List<Compra> read() {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Compra> compras = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT * FROM compra");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Compra compra = new Compra();

                compra.setId(rs.getInt("id_compra"));
                for (Laboratorio lab : LaboratorioDAO.read()) {
                    if (lab.getId() == rs.getInt("id_lab")) {
                        compra.setLaboratorio(lab);
                        break;
                    }
                }
                compra.setDataCompra(LocalDate.parse(rs.getString("data_compra")));
                if (rs.getDate("data_entrega") != null) {
                    compra.setDataEntrega(rs.getDate("data_entrega").toLocalDate());
                }
                compra.setNmr_nota_fiscal(rs.getString("nmr_nota_fiscal"));
                compra.setTotalNota(rs.getDouble("total_nota"));
                compra.setPagamento(rs.getString("forma_pagamento"));

                compras.add(compra);
            }
            return compras;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar compras: " + e);
        } finally {
            Conexao.closeConnection(con, stmt, rs);
        }
        return null;
    }

    public static List<Compra> read(String nNF) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Compra> compras = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT * FROM compra WHERE nmr_nota_fiscal LIKE ?");

            stmt.setString(1, "%" + nNF + "%");

            rs = stmt.executeQuery();

            while (rs.next()) {
                Compra compra = new Compra();

                compra.setId(rs.getInt("id_compra"));
                for (Laboratorio lab : LaboratorioDAO.read()) {
                    if (lab.getId() == rs.getInt("id_lab")) {
                        compra.setLaboratorio(lab);
                        break;
                    }
                }
                compra.setDataCompra(LocalDate.parse(rs.getString("data_compra")));
                if (rs.getDate("data_entrega") != null) {
                    compra.setDataEntrega(rs.getDate("data_entrega").toLocalDate());
                }
                compra.setNmr_nota_fiscal(rs.getString("nmr_nota_fiscal"));
                compra.setTotalNota(rs.getDouble("total_nota"));
                compra.setPagamento(rs.getString("forma_pagamento"));

                compras.add(compra);
            }
            return compras;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar compras: " + e);
        } finally {
            Conexao.closeConnection(con, stmt, rs);
        }
        return null;
    }

    public static List<Compra> read(Laboratorio l) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Compra> compras = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT * FROM compra WHERE id_lab = ?");

            stmt.setInt(1, l.getId());

            rs = stmt.executeQuery();

            while (rs.next()) {
                Compra compra = new Compra();

                compra.setId(rs.getInt("id_compra"));
                for (Laboratorio lab : LaboratorioDAO.read()) {
                    if (lab.getId() == rs.getInt("id_lab")) {
                        compra.setLaboratorio(lab);
                        break;
                    }
                }
                compra.setDataCompra(LocalDate.parse(rs.getString("data_compra")));
                if (rs.getDate("data_entrega") != null) {
                    compra.setDataEntrega(rs.getDate("data_entrega").toLocalDate());
                }
                compra.setNmr_nota_fiscal(rs.getString("nmr_nota_fiscal"));
                compra.setTotalNota(rs.getDouble("total_nota"));
                compra.setPagamento(rs.getString("forma_pagamento"));

                compras.add(compra);
            }
            return compras;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar compras: " + e);
        } finally {
            Conexao.closeConnection(con, stmt, rs);
        }
        return null;
    }

    public static List<Compra> read(Laboratorio l, String pagamento) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Compra> compras = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT * FROM compra WHERE id_lab = ? AND forma_pagamento = ?");

            stmt.setInt(1, l.getId());
            stmt.setString(2, pagamento);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Compra compra = new Compra();

                compra.setId(rs.getInt("id_compra"));
                for (Laboratorio lab : LaboratorioDAO.read()) {
                    if (lab.getId() == rs.getInt("id_lab")) {
                        compra.setLaboratorio(lab);
                        break;
                    }
                }
                compra.setDataCompra(LocalDate.parse(rs.getString("data_compra")));
                if (rs.getDate("data_entrega") != null) {
                    compra.setDataEntrega(rs.getDate("data_entrega").toLocalDate());
                }
                compra.setNmr_nota_fiscal(rs.getString("nmr_nota_fiscal"));
                compra.setTotalNota(rs.getDouble("total_nota"));
                compra.setPagamento(rs.getString("forma_pagamento"));

                compras.add(compra);
            }
            return compras;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar compras: " + e);
        } finally {
            Conexao.closeConnection(con, stmt, rs);
        }
        return null;
    }

    public static List<Compra> readDinamico(String nNF, Laboratorio l, String pagamento, 
            double valorTotalMin, double valorTotalMax, 
            String orderBy, boolean desc) {
        
        Connection con = Conexao.getConnection();
        CallableStatement cr = null;
        ResultSet rs = null;
        List<Compra> compras = new ArrayList();

        try {
            cr = con.prepareCall("CALL filterCompraDinamico(?, ?, ?, ?, ?, ?, ?)");

            if (nNF != null) {
                cr.setString(1, "%" + nNF + "%");
            } else {
                cr.setNull(1, Types.VARCHAR);
            }
            
            if (l != null) {
                cr.setInt(2, l.getId());
            } else {
                cr.setNull(2, Types.INTEGER);
            }
            
            if (pagamento != null) {
                cr.setString(3, pagamento);
            } else {
                cr.setNull(3, Types.VARCHAR);
            }
            
            cr.setDouble(4, valorTotalMin);
            cr.setDouble(5, valorTotalMax);
            
            if (orderBy != null) {
                cr.setString(6, orderBy);
            } else {
                cr.setNull(6, Types.VARCHAR);
            }
            
            cr.setBoolean(7, desc);

            rs = cr.executeQuery();

            while (rs.next()) {
                Compra compra = new Compra();

                compra.setId(rs.getInt("id_compra"));
                for (Laboratorio lab : LaboratorioDAO.read()) {
                    if (lab.getId() == rs.getInt("id_lab")) {
                        compra.setLaboratorio(lab);
                        break;
                    }
                }
                compra.setDataCompra(LocalDate.parse(rs.getString("data_compra")));
                if (rs.getDate("data_entrega") != null) {
                    compra.setDataEntrega(rs.getDate("data_entrega").toLocalDate());
                }
                compra.setNmr_nota_fiscal(rs.getString("nmr_nota_fiscal"));
                compra.setTotalNota(rs.getDouble("total_nota"));
                compra.setPagamento(rs.getString("forma_pagamento"));

                compras.add(compra);
            }
            return compras;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar compras dinamicamente: " + e);
        } finally {
            Conexao.closeConnection(con, cr, rs);
        }
        return null;
    }

    public static void update(Compra c) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL update_compra(?, ?)");

            stmt.setInt(1, c.getId());
            stmt.setDate(2, Date.valueOf(c.getDataEntrega()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar compra: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public static void delete(Compra c) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("CALL delete_compra(?)");

            stmt.setInt(1, c.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar compra: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }*/

}
