package my.company.projetorotisseriejavafx.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import javafx.scene.layout.Pane;
import my.company.projetorotisseriejavafx.DAO.ProdutoDAO;
import my.company.projetorotisseriejavafx.Objects.Produto;

public class PaneProdutoController implements Initializable {

    @FXML
    private Pane paneMarmita1;
    @FXML
    private ComboBox comboBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadProdutos();
    }

    public void loadProdutos() {
        comboBox.getItems().clear();
        for (Produto produto : ProdutoDAO.read()) {
            comboBox.getItems().add(produto);
        }
        comboBox.getSelectionModel().selectFirst();
    }
}
