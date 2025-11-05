package my.company.projetorotisseriejavafx.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
import my.company.projetorotisseriejavafx.Controller.Pane.PaneDetalhesBalcaoController;
import my.company.projetorotisseriejavafx.Controller.Pane.PaneDetalhesEntregaController;
import my.company.projetorotisseriejavafx.DAO.PedidoDAO;
import my.company.projetorotisseriejavafx.Objects.Pedido;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

public class PedidosController implements Initializable {

    @FXML
    private AnchorPane APPedidos;

    @FXML
    private TextArea TAEndereco;

    @FXML
    private TextArea TAObservacoes;

    @FXML
    private TextField TFBairro;

    @FXML
    private TextField TFCliente;

    @FXML
    private TextField TFDataHora;

    @FXML
    private TextField TFEntrega;

    @FXML
    private TextField TFMotoboy;

    @FXML
    private TextField TFPagamento;

    @FXML
    private TextField TFStatus;

    @FXML
    private TextField TFTipo;

    @FXML
    private TextField TFValorAPagar;

    @FXML
    private TextField TFValorPago;

    @FXML
    private TextField TFValorTotal;

    @FXML
    private TextField TFVencimento;

    @FXML
    private Button btnMarmitasEProdutos;

    @FXML
    private Button btnPagamentos;

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
                loadDetalhes(tablePedidos.getSelectionModel().getSelectedItem());
            }
        });

        refreshTablePedido();
    }

    @FXML
    void marmitasEProdutos(ActionEvent event) {

    }

    @FXML
    void pagamentos(ActionEvent event) {

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
        TFCliente.setText(pedido.getNomeCliente());
        TFTipo.setText(pedido.getTipoPedido());
        TFPagamento.setText(pedido.getTipoPagamento());
        TAObservacoes.setText(pedido.getObservacoes());
        if (pedido.getTipoPedido().equals("Entrega")) {
            TFMotoboy.setText(pedido.getMotoboy().getNome());
            TFBairro.setText(pedido.getBairro().getNome());
            TFEntrega.setText(pedido.getFormattedValorEntrega());
        }

        TFValorTotal.setText(pedido.getFormattedValorTotal());
        TFValorPago.setText(pedido.getFormattedValorPago());
        TFValorAPagar.setText(pedido.getFormattedValorAPagar());

        TFDataHora.setText(pedido.getDateTimeFormat());
        TFVencimento.setText("0");

        TFStatus.setText(pedido.getStatus());
    }
}
