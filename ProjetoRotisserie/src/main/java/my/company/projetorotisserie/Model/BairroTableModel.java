/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.company.projetorotisserie.Model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import my.company.projetorotisserie.DAO.BairroDAO;
import my.company.projetorotisserie.Objects.Bairro;

public class BairroTableModel extends AbstractTableModel {
    
    private List<Bairro> bairros = new ArrayList<>();
    private final String[] colunas = {"Nome", "Valor de Entrega"};

    public BairroTableModel() {
        loadTable();
    }

    @Override
    public int getRowCount() {
        return bairros.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return bairros.get(rowIndex).getNome();
            case 1:
                return bairros.get(rowIndex).getValorEntrega();
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
                bairros.get(rowIndex).setNome((String) marmita);
                break;
            case 1:
                bairros.get(rowIndex).setValorEntrega(Double.parseDouble((String) marmita));
                break;
        }
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void deleteRow(int rowIndex) {
        bairros.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addRow(Bairro bairro) {
        bairros.add(bairro);
        fireTableDataChanged();
    }

    public Bairro getRowData(int rowIndex) {
        return bairros.get(rowIndex);
    }

    public void createBairro(Bairro bairro) {
        BairroDAO.create(bairro);
        loadTable();
    }

    public void readData() {
        for (Bairro bairro: BairroDAO.read()) {
            bairros.add(bairro);
        }
    }
    
    public void updateBairro(Bairro bairro) {
        BairroDAO.update(bairro);
        fireTableDataChanged();
    }
    
    public void deleteBairro(Bairro bairro, int rowIndex) {
        BairroDAO.delete(bairro);
        deleteRow(rowIndex);
    }

    public void loadTable() {
        bairros.clear();
        readData();
        fireTableDataChanged();
    }
}
