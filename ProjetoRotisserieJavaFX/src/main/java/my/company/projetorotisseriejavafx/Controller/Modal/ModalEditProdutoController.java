package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.Objects.Produto;

public class ModalEditProdutoController {

    private Produto produto;

    @FXML
    private Scene scene;

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

    public void setProduto(Produto produto) {
        this.produto = produto;
        TFDescricao.setText(produto.getNome());
        TFValor.setText(String.valueOf(produto.getValor()));
        CBStatus.getSelectionModel().select(produto.getStatus());
    }

}
