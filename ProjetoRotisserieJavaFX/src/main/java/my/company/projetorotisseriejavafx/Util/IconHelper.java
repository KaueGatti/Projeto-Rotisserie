package my.company.projetorotisseriejavafx.Util;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.kordamp.ikonli.javafx.FontIcon;

public class IconHelper {

    public static void applyIconsTo(Parent root) {
        root.lookupAll(".button").forEach(node -> {
            if (node instanceof Button btn) {
                applyIcon(btn);
            }
        });

        root.lookupAll(".label").forEach(node -> {
            if (node instanceof Label label) {
                applyIcon(label);
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
                case "icon-warning":
                    FontIcon iconWarning = new FontIcon("bi-exclamation-triangle-fill");
                    iconWarning.getStyleClass().add("icon-search");
                    btn.setGraphic(iconWarning);
                    break;
            }
        }
    }

    public static void applyIcon(Label label) {
        for (String style : label.getStyleClass()) {
            switch (style) {
                case "icon-edit":
                    FontIcon iconEdit = new FontIcon("bi-pencil-square");
                    iconEdit.getStyleClass().add("icon-edit");
                    label.setGraphic(iconEdit);
                    break;
                case "icon-delete":
                    FontIcon iconDelete = new FontIcon("bi-trash-fill");
                    iconDelete.getStyleClass().add("icon-delete");
                    label.setGraphic(iconDelete);
                    break;
                case "icon-search":
                    FontIcon iconSearch = new FontIcon("bi-search");
                    iconSearch.getStyleClass().add("icon-search");
                    label.setGraphic(iconSearch);
                    break;
                case "icon-warning":
                    FontIcon iconWarning = new FontIcon("bi-exclamation-triangle-fill");
                    iconWarning.getStyleClass().add("icon-search");
                    label.setGraphic(iconWarning);
                    break;
            }
        }
    }
}

