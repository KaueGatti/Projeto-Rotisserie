package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class ModalObservacaoController {

    @FXML
    private TextArea TAObservacao;
    @FXML
    private AnchorPane root;

    public void initialize(String observacao) {
        TAObservacao.setText(observacao);
    }

    public String getObservacao() {
        return TAObservacao.getText();
    }


}
