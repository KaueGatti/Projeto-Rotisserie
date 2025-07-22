/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.company.projetorotisserie.Model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import my.company.projetorotisserie.DAO.MarmitasCadastradasDAO;
import my.company.projetorotisserie.Objects.Marmita;

public class MarmitaTableModel extends AbstractTableModel {

    private List<Marmita> marmitas = new ArrayList<>();
    private final String[] colunas = {"Descrição", "Valor", "Tamanho"};

    public MarmitaTableModel() {
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
                return marmitas.get(rowIndex).getValor();
            case 2:
                return marmitas.get(rowIndex).getTamanho();
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
        for (Marmita marmita: MarmitasCadastradasDAO.read()) {
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
        marmitas.clear();
        readData();
        fireTableDataChanged();
    }
}
