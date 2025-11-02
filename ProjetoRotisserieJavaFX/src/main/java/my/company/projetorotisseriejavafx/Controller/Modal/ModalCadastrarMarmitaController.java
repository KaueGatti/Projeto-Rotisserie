package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ModalCadastrarMarmitaController {

    @FXML
    private TextField TFNome;
    @FXML
    private ComboBox<Integer> CBMisturas;
    @FXML
    private ComboBox<Integer> CBGuarnicoes;
    @FXML
    private TextField TFValor;
    @FXML
    private Button btnSalvar;

    @FXML
    void salvar(ActionEvent event) {

    }
}
