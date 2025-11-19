package my.company.projetorotisseriejavafx.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
import my.company.projetorotisseriejavafx.Controller.Modal.*;
import my.company.projetorotisseriejavafx.DAO.*;
import my.company.projetorotisseriejavafx.Objects.*;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;
import my.company.projetorotisseriejavafx.Util.Printer;

public class PedidosController implements Initializable {

    ObservableList<Pedido> pedidos = FXCollections.observableArrayList();

    Pedido selectedPedido = new Pedido();

    List<MarmitaVendida> marmitas = new ArrayList<>();
    List<ProdutoVendido> produtos = new ArrayList<>();
    ObservableList<Pagamento> pagamentos = FXCollections.observableArrayList();


    @FXML
    private AnchorPane APPedidos;

    @FXML
    private TableColumn<Pedido, String> colCliente;

    @FXML
    private TableColumn<Pedido, LocalDateTime> colData;

    @FXML
    private TableColumn<Pedido, String> colStatus;

    @FXML
    private TableColumn<Pedido, String> colTipo;
    @FXML
    private TableColumn<Pedido, String> colPagamento;

    @FXML
    private TableColumn<Pedido, Double> colTotal;

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

    @FXML
    void descontosEAdicionais(ActionEvent event) {
        abrirModalDescontosEAdicionais();
    }

    @FXML
    void endereco(ActionEvent event) {
        abrirModalEndereco(selectedPedido.getEndereco());
    }

    @FXML
    void imprimir(ActionEvent event) {
        Printer.printOrder(selectedPedido, marmitas, produtos);
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
        abrirModalPagamentos();
    }

    private void initTablePedidos() {
        colCliente.setCellValueFactory(p -> p.getValue().nomeClienteProperty());
        colTipo.setCellValueFactory(p -> p.getValue().tipoPedidoProperty());
        colPagamento.setCellValueFactory(p -> p.getValue().tipoPagamentoProperty());
        colData.setCellValueFactory(p -> p.getValue().dateTimeProperty());
        colData.setCellFactory(column -> new TableCell<Pedido, LocalDateTime>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy | HH:mm");

            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(formatter.format(item));
                }
            }
        });
        colTotal.setCellValueFactory(p -> p.getValue().valorTotalProperty().asObject());
        colTotal.setCellFactory(column -> new TableCell<Pedido, Double>() {
            private final NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);

                if (empty || value == null) {
                    setText(null);
                } else {
                    setText(formatter.format(value));
                }
            }
        });
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tablePedidos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && !tablePedidos.getSelectionModel().isEmpty()) {
                selectedPedido = tablePedidos.getSelectionModel().getSelectedItem();
                loadDetalhes(selectedPedido);
                initObservableListPagamentos();
            }
        });

        try {
            pedidos = FXCollections.observableArrayList(PedidoDAO.read());

            tablePedidos.setItems(pedidos);

        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "Pedido");
        }
    }

    public void refreshTablePedido() {
        tablePedidos.getItems().clear();

        try {
            pedidos = FXCollections.observableArrayList(PedidoDAO.read());
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "pedido");
        }

    }

    public void loadDetalhes(Pedido pedido) {

        if (pedido == null) {
            LCliente.setText("-");
            LTipo.setText("-");
            LPagamento.setText("-");
            LBairro.setText("-");
            LEntrega.setText("-");
            LValorTotal.setText("-");
            LValorPago.setText("-");
            LValorAPagar.setText("-");
            LDataHora.setText("-");
            LVencimento.setText("-");
            LStatus.setText("-");

            btnImprimir.setDisable(true);
            btnObservacoes.setDisable(true);
            btnEndereco.setDisable(true);
            btnMarmitasEProdutos.setDisable(true);
            btnDescontosEAdicionais.setDisable(true);
            btnPagamentos.setDisable(true);
            return;
        }

        LCliente.setText(pedido.getNomeCliente());
        LTipo.setText(pedido.getTipoPedido());
        LPagamento.setText(pedido.getTipoPagamento());
        if (pedido.getTipoPedido().equals("Entrega")) {
            LBairro.setText(pedido.getBairro().getNome());
            LEntrega.setText(pedido.getFormattedValorEntrega());
            btnEndereco.setDisable(false);
        } else {
            LBairro.setText("-");
            LEntrega.setText("-");
            btnEndereco.setDisable(true);
        }

        LValorTotal.setText(pedido.getFormattedValorTotal());
        LValorPago.setText(pedido.getFormattedValorPago());
        LValorAPagar.setText(pedido.getFormattedValorAPagar());

        LDataHora.setText(pedido.getFormattedDateTime());
        if (pedido.getVencimento() != null) {
            LVencimento.setText(pedido.getFormattedVencimento());
        } else {
            LVencimento.setText("-");
        }

        LStatus.setText(pedido.getStatus());

        btnImprimir.setDisable(false);
        btnObservacoes.setDisable(false);
        btnMarmitasEProdutos.setDisable(false);
        btnDescontosEAdicionais.setDisable(false);
        btnPagamentos.setDisable(false);

        try {
            marmitas = MarmitaVendidaDAO.read(pedido.getId());
            produtos = ProdutoVendidoDAO.read(pedido.getId());
            pagamentos = FXCollections.observableArrayList(PagamentoDAO.read(selectedPedido.getId()));
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "marmitas e produtos vendidos");
        }

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

    public void abrirModalDescontosEAdicionais() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalDescontosEAdicionais.fxml"));
            Stage modal = new Stage();

            modal.setScene(loader.load());

            ModalDescontosEAdicionaisController controller = loader.getController();

            try {
                List<DescontoAdicional> descontosEAdicionais = DescontoAdicionalDAO.read(selectedPedido.getId());

                controller.initialize(descontosEAdicionais);
            } catch (SQLException e) {
                DatabaseExceptionHandler.handleException(e, "Desconto e Adicional");
            }

            modal.initStyle(StageStyle.UTILITY);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setResizable(false);
            modal.showAndWait();

        } catch (IOException e) {
            System.out.println("Erro ao abrir Descontos E Adicionais");
            e.printStackTrace();
        }
    }

    public void abrirModalPagamentos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalPagamentos.fxml"));
            Stage modal = new Stage();

            modal.setScene(loader.load());

            ModalPagamentosController controller = loader.getController();

            controller.initialize(selectedPedido, pagamentos);

            modal.initStyle(StageStyle.UTILITY);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setResizable(false);
            modal.showAndWait();


            loadDetalhes(selectedPedido);

        } catch (IOException e) {
            System.out.println("Erro ao abrir Descontos E Adicionais");
            e.printStackTrace();
        }
    }

    public void initObservableListPagamentos() {
        pagamentos.addListener((ListChangeListener<Pagamento>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    try {
                        for (Pagamento pagamento : change.getAddedSubList()) {
                            PagamentoDAO.create(pagamento);
                        }
                    } catch (SQLException e) {
                        DatabaseExceptionHandler.handleException(e, "Pagamento");
                    }
                }

                if (change.wasRemoved()) {
                    try {
                        for (Pagamento pagamento : change.getRemoved()) {
                            PagamentoDAO.delete(pagamento);
                        }
                    } catch (SQLException e) {
                        DatabaseExceptionHandler.handleException(e, "Pagamento");
                    }
                }

                loadDetalhes(selectedPedido);
            }
        });
    }

}
