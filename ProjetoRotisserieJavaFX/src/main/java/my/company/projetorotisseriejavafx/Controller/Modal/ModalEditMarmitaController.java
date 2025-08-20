package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.Objects.Marmita;

public class ModalEditMarmitaController {

    private Marmita marmita;


    @FXML
    private Scene scene;
    @FXML
    private ComboBox<Integer> CBGuarnicoes;
    @FXML
    private ComboBox<Integer> CBMisturas;
    @FXML
    private ComboBox<String> CBStatus;
    @FXML
    private TextField TFDescricao;
    @FXML
    private TextField TFValor;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnSalvar;

    @FXML
    void cancelar(ActionEvent event) {
        ((Stage) scene.getWindow()).close();
    }

    @FXML
    void salvar(ActionEvent event) {

    }

    private void loadComboBoxs() {
        CBMisturas.getItems().addAll(1, 2, 3, 4);
        CBMisturas.getSelectionModel().select(marmita.getMaxMistura());

        CBGuarnicoes.getItems().addAll(1, 2, 3, 4);
        CBGuarnicoes.getSelectionModel().select(marmita.getMaxGuarnicao());

        CBStatus.getItems().addAll("ATIVO", "INATIVO");
        CBStatus.getSelectionModel().select(marmita.getStatus());
    }

    private void loadTextFields() {
        TFDescricao.setText(marmita.getDescricao());

        TFValor.setText(String.valueOf(marmita.getValor()));
    }

    public void setMarmita(Marmita marmita) {
        this.marmita = marmita;
        loadTextFields();
        loadComboBoxs();
    }

}
