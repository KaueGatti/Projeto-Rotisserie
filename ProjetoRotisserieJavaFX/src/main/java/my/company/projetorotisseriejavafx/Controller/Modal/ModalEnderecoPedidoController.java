package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class ModalEnderecoPedidoController {

    @FXML
    private TextArea TAEndereco;

    @FXML
    private AnchorPane root;

    @FXML
    private Scene scene;

    public void initialize(String endereco) {
        TAEndereco.setText(endereco);
    }

}
