package my.company.projetorotisseriejavafx.Controller.Pane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
    private ComboBox<Produto> comboBoxProduto;
    @FXML
    private Spinner<Integer> spinner;
    @FXML
    private Label LInfo;
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
        comboBoxProduto.getItems().clear();
        List<Produto> produtos = new ArrayList<>();

        try {
            produtos = ProdutoDAO.read();
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "produto");
        }

        if (!produtos.isEmpty()) {
            comboBoxProduto.getItems().addAll(produtos);
            comboBoxProduto.getSelectionModel().selectFirst();
        }
    }

    public void setController(NovoPedidoController controller) {
        this.controller = controller;
    }

    @FXML
    public void Adicionar() {
        ProdutoVendido produto = new ProdutoVendido();
        produto.setIdProduto(comboBoxProduto.getValue().getId());
        produto.setNome(comboBoxProduto.getValue().getNome());
        produto.setQuantidade(spinner.getValue());
        produto.setSubtotal(produto.getQuantidade() * comboBoxProduto.getValue().getValor());
        controller.adicionarProduto(produto);
        spinner.getValueFactory().setValue(1);
    }

    @FXML
    public void Limpar() {
        spinner.getValueFactory().setValue(1);
    }
}
