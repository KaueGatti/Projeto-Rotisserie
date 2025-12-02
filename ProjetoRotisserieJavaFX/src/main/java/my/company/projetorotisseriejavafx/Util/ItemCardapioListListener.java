package my.company.projetorotisseriejavafx.Util;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import my.company.projetorotisseriejavafx.DAO.ItemCardapioDAO;
import my.company.projetorotisseriejavafx.Objects.ItemCardapio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemCardapioListListener implements ListChangeListener<ItemCardapio> {

    @Override
    public void onChanged(Change<? extends ItemCardapio> change) {
        while (change.next()) {
            if (change.wasAdded()) {

                List<ItemCardapio> remover = new ArrayList<>();
                change.getAddedSubList().forEach(itemCardapio -> {
                    try {
                        int id = ItemCardapioDAO.create(itemCardapio);
                        itemCardapio.setId(id);
                    } catch (SQLException e) {
                        DatabaseExceptionHandler.handleException(e, "Item do cardápio");
                        remover.add(itemCardapio);
                    }
                });

                if (!remover.isEmpty()) {
                    Platform.runLater(() ->
                            change.getList().removeAll(remover)
                    );
                }
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
