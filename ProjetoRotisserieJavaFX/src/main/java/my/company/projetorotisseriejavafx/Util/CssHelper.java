package my.company.projetorotisseriejavafx.Util;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javax.swing.*;

public class CssHelper {

    public static void loadCss(Scene scene) {
        scene.getStylesheets().add(
                CssHelper.class.getResource("/styles/main.css").toExternalForm()
        );

        IconHelper.applyIconsTo(scene.getRoot());
    }
}

