package my.company.projetorotisseriejavafx.Controller.Pane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import javafx.scene.layout.Pane;
import my.company.projetorotisseriejavafx.Controller.NovoPedidoController;
import my.company.projetorotisseriejavafx.DAO.ProdutoDAO;
import my.company.projetorotisseriejavafx.Objects.Produto;
import my.company.projetorotisseriejavafx.Objects.ProdutoVendido;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

public class PaneProdutoController implements Initializable {

    private NovoPedidoController controller;

    @FXML
    private Pane paneMarmita1;

    @FXML
    private ComboBox comboBox;
    @FXML
    private Spinner<Integer> spinner;
    @FXML
    private Button bttAdicionar;
    @FXML
    private Button bttLimpar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadProdutos();
        initSpinner();
    }

    private void initSpinner() {
        SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        spinner.setValueFactory(svf);
    }

    public void loadProdutos() {
        comboBox.getItems().clear();
        List<Produto> produtos = new ArrayList<>();

        try {
            produtos = ProdutoDAO.read();
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "produto");
        }

        if (!produtos.isEmpty()) {
            comboBox.getItems().addAll(produtos);
            comboBox.getSelectionModel().selectFirst();
        }
    }

    public void setController(NovoPedidoController controller) {
        this.controller = controller;
    }

    @FXML
    public void Adicionar() {
        ProdutoVendido produto = new ProdutoVendido();
        produto.setProduto((Produto) comboBox.getSelectionModel().getSelectedItem());
        produto.setQuantidade(spinner.getValue());
        controller.adicionarProduto(produto);
        spinner.getValueFactory().setValue(1);
    }

    @FXML
    public void Limpar() {
        spinner.getValueFactory().setValue(1);
    }
}
