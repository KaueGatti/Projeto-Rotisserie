package my.company.projetorotisseriejavafx.Util;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import org.kordamp.ikonli.javafx.FontIcon;

public class IconHelper {

    public static void applyIconsTo(Parent root) {
        root.lookupAll(".button").forEach(node -> {
            if (node instanceof Button btn) {
                applyIcon(btn);
            }
        });
    }

    public static void applyIcon(Button btn) {
        for (String style : btn.getStyleClass()) {
            switch (style) {
                case "icon-edit":
                    FontIcon iconEdit = new FontIcon("bi-pencil-square");
                    iconEdit.getStyleClass().add("icon-edit");
                    btn.setGraphic(iconEdit);
                    break;
                case "icon-delete":
                    FontIcon iconDelete = new FontIcon("bi-trash-fill");
                    iconDelete.getStyleClass().add("icon-delete");
                    btn.setGraphic(iconDelete);
                    break;
                case "icon-search":
                    FontIcon iconSearch = new FontIcon("bi-search");
                    iconSearch.getStyleClass().add("icon-search");
                    btn.setGraphic(iconSearch);
                    break;
            }
        }
    }
}

