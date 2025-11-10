package my.company.projetorotisseriejavafx.Controller;

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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalEnderecoPedidoController;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalMarmitasEProdutosController;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalObservacoesPedidoController;
import my.company.projetorotisseriejavafx.DAO.MarmitaVendidaDAO;
import my.company.projetorotisseriejavafx.DAO.PedidoDAO;
import my.company.projetorotisseriejavafx.DAO.ProdutoVendidoDAO;
import my.company.projetorotisseriejavafx.Objects.MarmitaVendida;
import my.company.projetorotisseriejavafx.Objects.Pedido;
import my.company.projetorotisseriejavafx.Objects.ProdutoVendido;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

public class PedidosController implements Initializable {

    Pedido selectedPedido = new Pedido();

    List<MarmitaVendida> marmitas = new ArrayList<>();
    List<ProdutoVendido> produtos = new ArrayList<>();

    @FXML
    private AnchorPane APPedidos;

    @FXML
    private TableColumn<Pedido, String> colCliente;

    @FXML
    private TableColumn<Pedido, String> colData;

    @FXML
    private TableColumn<Pedido, String> colStatus;

    @FXML
    private TableColumn<Pedido, String> colTipo;

    @FXML
    private TableColumn<Pedido, String> colTotal;

    @FXML
    private Pane paneDetalhes;

    @FXML
    private TableView<Pedido> tablePedidos;

    @FXML
    private Label LBairro;

    @FXML
    private Label LCliente;

    @FXML
    private Label LDataHora;

    @FXML
    private Label LEntrega;

    @FXML
    private Label LMotoboy;

    @FXML
    private Label LPagamento;

    @FXML
    private Label LStatus;

    @FXML
    private Label LTipo;

    @FXML
    private Label LValorAPagar;

    @FXML
    private Label LValorPago;

    @FXML
    private Label LValorTotal;

    @FXML
    private Label LVencimento;

    @FXML
    private Button btnDescontosEAdicionais;

    @FXML
    private Button btnEndereco;

    @FXML
    private Button btnImprimir;

    @FXML
    private Button btnObservacoes;

    @FXML
    private Button btnMarmitasEProdutos;

    @FXML
    private Button btnPagamentos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTablePedidos();
    }

    private void initTablePedidos() {
        colCliente.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoPedido"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dateTimeFormat"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("formattedValorTotal"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tablePedidos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && !tablePedidos.getSelectionModel().isEmpty()) {
                selectedPedido = tablePedidos.getSelectionModel().getSelectedItem();
                loadDetalhes(selectedPedido);
            }
        });

        refreshTablePedido();
    }

    public void refreshTablePedido() {
        tablePedidos.getItems().clear();

        try {
            List<Pedido> pedidos = PedidoDAO.read();

            if (!pedidos.isEmpty()) {
                tablePedidos.getItems().addAll(pedidos);
            }
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "pedido");
        }

    }

    public void loadDetalhes(Pedido pedido) {

        LCliente.setText(pedido.getNomeCliente());
        LTipo.setText(pedido.getTipoPedido());
        LPagamento.setText(pedido.getTipoPagamento());
        if (pedido.getTipoPedido().equals("Entrega")) {
            LMotoboy.setText(pedido.getMotoboy().getNome());
            LBairro.setText(pedido.getBairro().getNome());
            LEntrega.setText(pedido.getFormattedValorEntrega());
            btnEndereco.setDisable(false);
        } else {
            LMotoboy.setText("-");
            LBairro.setText("-");
            LEntrega.setText("-");
            btnEndereco.setDisable(true);
        }

        LValorTotal.setText(pedido.getFormattedValorTotal());
        LValorPago.setText(pedido.getFormattedValorPago());
        LValorAPagar.setText(pedido.getFormattedValorAPagar());

        LDataHora.setText(pedido.getDateTimeFormat());

        if (pedido.getVencimento() != null) {
            LVencimento.setText(pedido.getVencimento().toString());
        } else {
            LVencimento.setText("-");
        }

        LStatus.setText(pedido.getStatus());

        btnImprimir.setDisable(false);
        btnObservacoes.setDisable(false);
        btnMarmitasEProdutos.setDisable(false);
        btnDescontosEAdicionais.setDisable(false);

        if (pedido.getStatus().equals("FINALIZADO")) {
            btnPagamentos.setDisable(true);
        } else {
            btnPagamentos.setDisable(false);
        }

        try {
            marmitas = MarmitaVendidaDAO.read(pedido.getId());
            produtos = ProdutoVendidoDAO.read(pedido.getId());
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "marmitas e produtos vendidos");
        }

    }

    @FXML
    void descontosEAdicionais(ActionEvent event) {
    }

    @FXML
    void endereco(ActionEvent event) {
        abrirModalEndereco(selectedPedido.getEndereco());
    }

    @FXML
    void imprimir(ActionEvent event) {

    }

    @FXML
    void marmitasEProdutos(ActionEvent event) {
        abrirModalMarmitasEProdutos(marmitas, produtos);
    }

    @FXML
    void observacoes(ActionEvent event) {
        abrirModalObservacoes(selectedPedido.getObservacoes());
    }

    @FXML
    void pagamentos(ActionEvent event) {

    }

    public void abrirModalObservacoes(String observacoes) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalObservacoesPedido.fxml"));
            Stage modal = new Stage();

            modal.setScene(loader.load());

            ModalObservacoesPedidoController controller = loader.getController();

            controller.initialize(observacoes);

            modal.initStyle(StageStyle.UTILITY);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setResizable(false);
            modal.showAndWait();

        } catch (IOException e) {
            System.out.println("Erro ao abrir Observaçoes");
            e.printStackTrace();
        }
    }

    public void abrirModalEndereco(String endereco) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalEnderecoPedido.fxml"));
            Stage modal = new Stage();

            modal.setScene(loader.load());

            ModalEnderecoPedidoController controller = loader.getController();

            controller.initialize(endereco);

            modal.initStyle(StageStyle.UTILITY);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setResizable(false);
            modal.showAndWait();

        } catch (IOException e) {
            System.out.println("Erro ao abrir Endereco");
            e.printStackTrace();
        }
    }

    public void abrirModalMarmitasEProdutos(List<MarmitaVendida> marmitas, List<ProdutoVendido> produtos) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalMarmitasEProdutos.fxml"));
            Stage modal = new Stage();

            modal.setScene(loader.load());

            ModalMarmitasEProdutosController controller = loader.getController();

            controller.initialize(marmitas, produtos);

            modal.initStyle(StageStyle.UTILITY);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setResizable(false);
            modal.showAndWait();

        } catch (IOException e) {
            System.out.println("Erro ao abrir Marmitas e Produtos");
            e.printStackTrace();
        }
    }
}
