package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class ModalObservacoesPedidoController {

    @FXML
    private TextArea TAobservacao;

    @FXML
    private AnchorPane root;

    public void initialize(String observacoes) {
        TAobservacao.setText(observacoes);
    }

}
