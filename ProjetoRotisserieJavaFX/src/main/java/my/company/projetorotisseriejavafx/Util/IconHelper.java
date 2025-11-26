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

    private static void applyIcon(Button btn) {
        for (String style : btn.getStyleClass()) {
            switch (style) {
                case "icon-edit":
                    btn.setGraphic(new FontIcon("bi-pencil-square"));
                    break;
                case "icon-delete":
                    btn.setGraphic(new FontIcon("bi-trash-fill"));
                    break;
                case "icon-search":
                    btn.setGraphic(new FontIcon("bi-search"));
                    break;
            }
        }
    }
}

