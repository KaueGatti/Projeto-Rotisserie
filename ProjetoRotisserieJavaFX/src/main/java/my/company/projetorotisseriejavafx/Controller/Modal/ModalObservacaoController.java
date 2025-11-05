package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ModalObservacaoController {
    
    String observacao;

    @FXML
    private TextArea TAObservacao;
    @FXML
    private AnchorPane root;
    @FXML
    private Scene scene;

    public void initialize(String observacao) {
        TAObservacao.setText(observacao);
    }

    public String getObservacao() {
        return observacao;
    }

    public void onClose() {
        Stage modal = (Stage) scene.getWindow();
        modal.setOnCloseRequest(Event -> {
            System.out.println(2);
        });
    }


}
