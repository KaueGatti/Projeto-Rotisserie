package my.company.projetorotisseriejavafx.Util;

import javafx.collections.ListChangeListener;
import my.company.projetorotisseriejavafx.DAO.ItemCardapioDAO;
import my.company.projetorotisseriejavafx.Objects.ItemCardapio;

import java.sql.SQLException;

public class ItemCardapioListListener implements ListChangeListener<ItemCardapio> {
    @Override
    public void onChanged(Change<? extends ItemCardapio> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                change.getAddedSubList().forEach(itemCardapio -> {
                    try {
                        int id = ItemCardapioDAO.create(itemCardapio);
                        itemCardapio.setId(id);
                    } catch (SQLException e) {
                        DatabaseExceptionHandler.handleException(e, "Item do cardápio");
                    }
                });
            }
            if (change.wasRemoved()) {
                change.getRemoved().forEach(itemCardapio -> {
                    try {
                        ItemCardapioDAO.delete(itemCardapio.getId());
                    } catch (SQLException e) {
                        DatabaseExceptionHandler.handleException(e, "Item do cardápio");
                    }
                });
            }
        }
    }
}
