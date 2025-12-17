package my.company.projetorotisseriejavafx.Controller.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalDescontoAdicionalMarmitaController;
import my.company.projetorotisseriejavafx.Controller.NovoPedidoController;
import my.company.projetorotisseriejavafx.DAO.ProdutoDAO;
import my.company.projetorotisseriejavafx.Objects.DescontoAdicional;
import my.company.projetorotisseriejavafx.Objects.Produto;
import my.company.projetorotisseriejavafx.Objects.ProdutoVendido;
import my.company.projetorotisseriejavafx.Util.CssHelper;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;
import my.company.projetorotisseriejavafx.Util.IconHelper;

public class PaneProdutoController implements Initializable {

    private NovoPedidoController controller;

    private DescontoAdicional descontoAdicional = null;

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
    @FXML
    private Button btnDescontoAdicional;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadProdutos();
        initSpinner();
    }

    @FXML
    public void Adicionar() {
        ProdutoVendido produto = new ProdutoVendido();
        produto.setIdProduto(comboBoxProduto.getValue().getId());
        produto.setNome(comboBoxProduto.getValue().getNome());
        produto.setQuantidade(spinner.getValue());
        produto.setSubtotal(produto.getQuantidade() * comboBoxProduto.getValue().getValor());

        if (descontoAdicional != null) {
            if (descontoAdicional.getTipo().equals("Desconto")) {
                produto.setSubtotal(produto.getSubtotal() - descontoAdicional.getValor());
            } else {
                produto.setSubtotal(produto.getSubtotal() + descontoAdicional.getValor());
            }
        }

        controller.adicionarProduto(produto);

        Limpar();
    }

    @FXML
    void descontoAdicional(ActionEvent event) {
        abrirModalDescontoAdicional();
    }

    @FXML
    public void Limpar() {
        spinner.getValueFactory().setValue(1);
        descontoAdicional = null;
    }

    private void initSpinner() {
        SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        spinner.setValueFactory(svf);
    }

    public void loadProdutos() {
        comboBoxProduto.getItems().clear();
        List<Produto> produtos = new ArrayList<>();

        try {
            produtos = ProdutoDAO.listarAtivos();
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

    private void abrirModalDescontoAdicional() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalDescontoAdicionalMarmita.fxml"));
            Parent root = loader.load();

            Stage modal = new Stage();
            Scene scene = new Scene(root);

            CssHelper.loadCss(scene);
            IconHelper.applyIconsTo(root);

            modal.setScene(scene);

            ModalDescontoAdicionalMarmitaController controller = loader.getController();

            controller.initialize(descontoAdicional);

            modal.setResizable(false);
            modal.initStyle(StageStyle.UTILITY);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setX(55);
            modal.setY(300);
            modal.showAndWait();

            this.descontoAdicional = controller.getDescontoAdicional();

        } catch (IOException e) {
            System.out.println("Erro ao abrir modal desconto/adicional marmita" + e);
        }
    }
}
