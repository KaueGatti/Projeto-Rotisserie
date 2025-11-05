package my.company.projetorotisseriejavafx.Controller.Modal;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.Objects.ProdutoVendido;

public class ModalDetalhesProdutoController implements Initializable {


    @FXML
    private TextField TFProduto;
    @FXML
    private TextField TFQuantidade;
    @FXML
    private TextField TFSubtotal;
    @FXML
    private Button btnFechar;
    @FXML
    private Scene scene;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void fechar(ActionEvent event) {
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }
    
    public void load(ProdutoVendido produto) {
        TFProduto.setText(produto.getNome());
        TFQuantidade.setText(String.valueOf(produto.getQuantidade()));
        TFSubtotal.setText(String.valueOf(produto.getSubtotal()));
    }

}
