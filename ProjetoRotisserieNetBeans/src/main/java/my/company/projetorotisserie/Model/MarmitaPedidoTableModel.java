package my.company.projetorotisserie.Model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import my.company.projetorotisserie.DAO.MarmitasCadastradasDAO;
import my.company.projetorotisserie.Objects.Marmita;

public class MarmitaPedidoTableModel extends AbstractTableModel {

    private List<Marmita> marmitas = new ArrayList<>();
    private final String[] colunas = {"Descrição", "Tamanho", "Arroz", "Feijão", "Mistura 1", "Mistura 2", "Mistura 3", "Guarnição 1", "Guarnição 2", "Guarnição 3", "Salada", "Valor"};

    public MarmitaPedidoTableModel() {
        loadTable();
    }

    @Override
    public int getRowCount() {
        return marmitas.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return marmitas.get(rowIndex).getDescricao();
            case 1:
                return marmitas.get(rowIndex).getTamanho();
            case 2:
                return marmitas.get(rowIndex).getPrincipal().get(0);
            case 3:
                return marmitas.get(rowIndex).getPrincipal().get(1);
            case 4:
                return marmitas.get(rowIndex).getMisturas().get(0);
            case 5:
                return marmitas.get(rowIndex).getMisturas().get(1);
            case 6:
                return marmitas.get(rowIndex).getMisturas().get(2);
            case 7:
                return marmitas.get(rowIndex).getGuarnicoes().get(0);
            case 8:
                return marmitas.get(rowIndex).getGuarnicoes().get(1);
            case 9:
                return marmitas.get(rowIndex).getGuarnicoes().get(2);
            case 10:
                return marmitas.get(rowIndex).getSalada();
            case 11:
                return marmitas.get(rowIndex).getValor();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public void setValueAt(Object marmita, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                marmitas.get(rowIndex).setDescricao((String) marmita);
                break;
            case 1:
                marmitas.get(rowIndex).setValor(Double.parseDouble((String) marmita));
                break;
            case 2:
                marmitas.get(rowIndex).setTamanho((String) marmita);
                break;
        }
        fireTableRowsUpdated(rowIndex, rowIndex);
    }
    
    public List<Marmita> getMarmitas() {
        return marmitas;
    }

    public void deleteRow(int rowIndex) {
        marmitas.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addRow(Marmita marmita) {
        marmitas.add(marmita);
        fireTableDataChanged();
    }

    public Marmita getRowData(int rowIndex) {
        return marmitas.get(rowIndex);
    }

    public void createMarmita(Marmita marmita) {
        MarmitasCadastradasDAO.create(marmita);
        loadTable();
    }

    public void readData() {
        for (Marmita marmita : MarmitasCadastradasDAO.read()) {
            marmitas.add(marmita);
        }
    }

    public void updateMarmita(Marmita marmita) {
        MarmitasCadastradasDAO.update(marmita);
        fireTableDataChanged();
    }

    public void deleteMarmita(Marmita marmita, int rowIndex) {
        MarmitasCadastradasDAO.delete(marmita);
        deleteRow(rowIndex);
    }

    public void loadTable() {
        fireTableDataChanged();
    }
}
