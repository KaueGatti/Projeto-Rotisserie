package my.company.projetorotisseriejavafx.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import javafx.scene.layout.Pane;
import my.company.projetorotisseriejavafx.DAO.ProdutoDAO;
import my.company.projetorotisseriejavafx.Objects.Produto;
import my.company.projetorotisseriejavafx.Objects.ProdutoVendido;

public class PaneProdutoController implements Initializable {
    
    private NovoPedidoController controller;

    @FXML
    private Pane paneMarmita1;
    
    @FXML
    private ComboBox comboBox;
    @FXML
    private Spinner<Integer> spinner;

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
        for (Produto produto : ProdutoDAO.read()) {
            comboBox.getItems().add(produto);
        }
        comboBox.getSelectionModel().selectFirst();
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
    }
    
    @FXML
    public void Limpar() {
        spinner.getValueFactory().setValue(1);
    }
}
