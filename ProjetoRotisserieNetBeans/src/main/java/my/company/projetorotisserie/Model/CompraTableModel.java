/*package my.company.projetorotisserie.Model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class CompraTableModel extends AbstractTableModel {

    List<Compra> compras = new ArrayList();
    String[] colunas = {"Fornecedor", "Data da compra", "Data de entrega", "Nº Nota Fiscal", "Total da Nota", "Forma de Pagamento"};

    @Override
    public int getRowCount() {
        return compras.size();
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
        fireTableDataChanged();
    }

    public String[] getColunas() {
        return colunas;
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return compras.get(rowIndex).getLaboratorio().getNome();
            case 1:
                return compras.get(rowIndex).getDataCompra();
            case 2:
                if (compras.get(rowIndex).getDataEntrega() != null) {
                    return compras.get(rowIndex).getDataEntrega();
                }
                return "Não entregue";
            case 3:
                return compras.get(rowIndex).getNmr_nota_fiscal();
            case 4:
                return FormatadorValor.formatarValor(compras.get(rowIndex).getTotalNota());
            case 5:
                return compras.get(rowIndex).getPagamento();
        }
        return null;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                compras.get(rowIndex).setLaboratorio((Laboratorio) value);
                break;
            case 1:
                compras.get(rowIndex).setDataCompra(LocalDate.parse((String) value));
                break;
            case 2:
                compras.get(rowIndex).setDataEntrega((LocalDate) value);
                break;
            case 3:
                compras.get(rowIndex).setNmr_nota_fiscal((String) value);
                break;
            case 4:
                compras.get(rowIndex).setTotalNota(Double.parseDouble((String) value));
                break;
            case 5:
                compras.get(rowIndex).setPagamento((String) value);
                break;
        }
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void addLinha(Compra compra) {
        compras.add(compra);
        fireTableDataChanged();
    }

    public void deleteLinha(int indexLinha) {
        compras.remove(indexLinha);
        fireTableRowsDeleted(indexLinha, indexLinha);
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void loadTable() {
        compras.clear();
        for (Compra compra : CompraDAO.read()) {
            compras.add(compra);
        }
        fireTableDataChanged();
    }
}*/
