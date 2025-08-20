package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.Objects.Bairro;

public class ModalEditBairroController {

    private Bairro bairro;

    @FXML
    private TextField TFNome;

    @FXML
    private TextField TFValorEntrega;

    @FXML
    private ComboBox<String> CBStatus;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnSalvar;

    @FXML
    private Scene scene;

    @FXML
    private void initialize() {
        CBStatus.getItems().addAll("ATIVO", "INATIVO");
    }

    @FXML
    void cancelar(ActionEvent event) {
        ((Stage) scene.getWindow()).close();
    }

    @FXML
    void salvar(ActionEvent event) {

    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
        TFNome.setText(bairro.getNome());
        TFValorEntrega.setText(String.valueOf(bairro.getValorEntrega()));
        CBStatus.getSelectionModel().select(bairro.getStatus());
    }

}
