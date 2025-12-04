package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ModalAvisoCardapioController {

    @FXML
    private Label LInfo;

    @FXML
    private AnchorPane root;

    public void initialize(String message) {
        LInfo.setText(message);
    }

}
