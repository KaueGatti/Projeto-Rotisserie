package my.company.projetorotisseriejavafx.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import my.company.projetorotisseriejavafx.Util.*;

public class PedidosController implements Initializable {

    ObservableList<Pedido> pedidos = FXCollections.observableArrayList();

    Pedido selectedPedido = new Pedido();

    List<MarmitaVendida> marmitas = new ArrayList<>();
    List<ProdutoVendido> produtos = new ArrayList<>();
    ObservableList<Pagamento> pagamentos = FXCollections.observableArrayList();


    @FXML
    private AnchorPane APPedidos;

    @FXML
    private ComboBox<Mensalista> CBMensalista;

    @FXML
    private ComboBox<String> CBPagamento;

    @FXML
    private DatePicker DPData;

    @FXML
    private CheckBox CBData;

    @FXML
    private ComboBox<String> CBStatus;

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

    @FXML
    private Button btnRelatorio;

    @FXML
    private Button btnPesquisar;

    @FXML
    private Button btnPedidosAtrasados;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCBMensalista();
        initCBPagamento();
        initDPData();
        initCBData();
        initCBStatus();
        initTablePedidos();
    }

    @FXML
    void pesquisar(ActionEvent event) {
        filtrarPedidos();
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

    @FXML
    void relatorio(ActionEvent event) {
        abrirRelatorio();
    }

    @FXML
    void pedidosAtrasados(ActionEvent event) {
        abrirModalPedidosAtrasados();
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
            Parent root = loader.load();

            Stage modal = new Stage();
            Scene scene = new Scene(root);

            CssHelper.loadCss(scene);

            modal.setScene(scene);

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
            Parent root = loader.load();

            Stage modal = new Stage();
            Scene scene = new Scene(root);

            CssHelper.loadCss(scene);

            modal.setScene(scene);

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
            Parent root = loader.load();

            Stage modal = new Stage();
            Scene scene = new Scene(root);

            CssHelper.loadCss(scene);

            modal.setScene(scene);

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
            Parent root = loader.load();

            Stage modal = new Stage();
            Scene scene = new Scene(root);

            CssHelper.loadCss(scene);

            modal.setScene(scene);

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
            Parent root = loader.load();

            Stage modal = new Stage();
            Scene scene = new Scene(root);

            CssHelper.loadCss(scene);

            modal.setScene(scene);

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

    public void abrirRelatorio() {
        AnchorPane APPrincipal = (AnchorPane) APPedidos.getParent();
        APPrincipal.getChildren().clear();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Relatorio.fxml"));
            Parent root = loader.load();

            IconHelper.applyIconsTo(root);

            APPrincipal.getChildren().setAll(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initCBMensalista() {
        CBMensalista.getItems().clear();
        CBMensalista.getItems().add(new Mensalista("Todos"));
        CBMensalista.getSelectionModel().selectFirst();

        try {
            List<Mensalista> mensalistas = MensalistaDAO.read();
            CBMensalista.getItems().addAll(mensalistas);
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "Mensalista");
        }
    }

    private void initCBPagamento() {
        CBPagamento.getItems().clear();
        CBPagamento.getItems().addAll("Todos", "Dinheiro", "Débito", "Crédito", "Pix", "Pagar depois");
        CBPagamento.getSelectionModel().selectFirst();
    }

    private void initDPData() {
        DPData.setValue(LocalDate.now());
        DPData.setEditable(false);
    }

    private void initCBData() {
        CBData.setOnAction(event -> {
            if (CBData.isSelected()) {
                DPData.setDisable(false);
            } else {
                DPData.setDisable(true);
            }
        });
    }

    private void initCBStatus() {
        CBStatus.getItems().clear();
        CBStatus.getItems().addAll("Todos", "Pago", "A pagar");
        CBStatus.getSelectionModel().selectFirst();
    }

    private void filtrarPedidos() {
        this.pedidos.clear();

        List<Pedido> pedidos = new ArrayList<>();

        try {
            if (CBData.isSelected()) {
                pedidos = PedidoDAO.read(DPData.getValue());
            } else {
                pedidos = PedidoDAO.read();
            }
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "Pedido");
        }

        Stream<Pedido> pedidosStream = pedidos.stream();

        if (CBMensalista.getSelectionModel().getSelectedIndex() != 0) {
            pedidosStream = pedidosStream.filter(p -> p.getMensalista() != null)
                    .filter(p -> p.getMensalista().getNome().equals(CBMensalista.getValue().getNome()));
        }

        if (CBPagamento.getSelectionModel().getSelectedIndex() != 0) {
            String pagamento = Normalize.normalize(CBPagamento.getValue());
            pedidosStream = pedidosStream.filter(p -> p.getTipoPagamento().equalsIgnoreCase(pagamento));
        }

        if (CBStatus.getSelectionModel().getSelectedIndex() != 0) {
            pedidosStream = pedidosStream.filter(p -> p.getStatus().equalsIgnoreCase(CBStatus.getValue()));
        }

        pedidos = pedidosStream.toList();

        this.pedidos.setAll(pedidos);
    }

    public void selectPedidoInTable(Pedido pedido) {
        tablePedidos.getSelectionModel().select(pedido);
    }

    private void abrirModalPedidosAtrasados() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalPedidosAtrasados.fxml"));
            Parent root = loader.load();

            Stage modal = new Stage();
            Scene scene = new Scene(root);

            CssHelper.loadCss(scene);

            modal.setScene(scene);

            ModalPedidosAtrasadosController controller = loader.getController();

            List<Pedido> pedidosAtrasados = pedidos.stream()
                    .filter(p -> p.getStatus().equalsIgnoreCase("A Pagar"))
                    .filter(p -> p.getVencimento().isBefore(LocalDate.now())).toList();

            controller.initialize(pedidosAtrasados, CBMensalista.getItems(), this);

            modal.initStyle(StageStyle.UTILITY);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setResizable(false);
            modal.showAndWait();

        } catch (IOException e) {
            System.out.println("Erro ao abrir Pedidos Atrasados");
            e.printStackTrace();
        }
    }

}
