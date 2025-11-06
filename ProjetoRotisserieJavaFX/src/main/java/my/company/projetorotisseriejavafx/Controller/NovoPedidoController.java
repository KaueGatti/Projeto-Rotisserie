package my.company.projetorotisseriejavafx.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalDescontosEAdicionaisController;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalDetalhesMarmitaController;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalPagamentoController;
import my.company.projetorotisseriejavafx.Controller.Pane.PaneMarmitaController;
import my.company.projetorotisseriejavafx.Controller.Pane.PaneProdutoController;
import my.company.projetorotisseriejavafx.DAO.BairroDAO;
import my.company.projetorotisseriejavafx.DAO.MarmitaVendidaDAO;
import my.company.projetorotisseriejavafx.DAO.MensalistaDAO;
import my.company.projetorotisseriejavafx.DAO.MotoboyDAO;
import my.company.projetorotisseriejavafx.DAO.PedidoDAO;
import my.company.projetorotisseriejavafx.DAO.ProdutoVendidoDAO;
import my.company.projetorotisseriejavafx.Objects.*;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

public class NovoPedidoController implements Initializable {

    private double valorTotal = 0;
    private double valorEntrega = 0;
    private ObservableList<DescontoAdicional> descontosEAdicionais = FXCollections.observableArrayList();
    private String pagamento;
    @FXML
    private ToggleGroup tipo;
    @FXML
    private Button btnFinalizar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnDescontosEAdicionais;

    public NovoPedidoController() {
    }

    @FXML
    private Pane panePrincipal;

    @FXML
    private TableView<MarmitaVendida> tableMarmita;
    @FXML
    private TableColumn<MarmitaVendida, String> colNomeMarmita;
    @FXML
    private TableColumn<MarmitaVendida, Double> colSubtotalMarmita;
    @FXML
    private TableColumn<MarmitaVendida, Void> colDelMarmita;

    @FXML
    private TableView<ProdutoVendido> tableProduto;
    @FXML
    private TableColumn<ProdutoVendido, String> colNomeProduto;
    @FXML
    private TableColumn<ProdutoVendido, Integer> colQuantidadeProduto;
    @FXML
    private TableColumn<ProdutoVendido, Double> colSubtotalProduto;
    @FXML
    private TableColumn<ProdutoVendido, Void> colDelProduto;

    @FXML
    private ToggleButton tabButtonLeft;
    @FXML
    private ToggleButton tabButtonRight;
    @FXML
    private ComboBox<Bairro> comboBoxBairro;
    @FXML
    private ComboBox<Mensalista> comboBoxMensalista;
    @FXML
    private ComboBox<Motoboy> comboBoxMotoboy;
    @FXML
    private CheckBox checkBoxMensalista;
    @FXML
    private RadioButton RBEntrega;
    @FXML
    private Label labelCliente;
    @FXML
    private TextField TFNomeCliente;
    @FXML
    private TextArea TAEndereco;
    @FXML
    private TextArea TAObservacoes;
    @FXML
    private Label labelValorEntrega;
    @FXML
    private Label labelValorTotal;
    @FXML
    private Label labelClienteInfo;
    @FXML
    private Label labelEnderecoInfo;
    @FXML
    private Label labelItensInfo;

    @FXML
    private AnchorPane APMarmitaProduto;
    @FXML
    private Pane paneEndereco;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxMensalista.setDisable(true);
        RBEntrega.setSelected(true);
        initDescontosEAdicionais();
        initTableMarmita();
        initTableProduto();
        loadBairro();
        loadMensalista();
        loadMotoboy();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Pane/paneMarmita.fxml"));
            Pane marmitaPane = loader.load();
            PaneMarmitaController marmitaController = loader.getController();
            marmitaController.setController(this);
            APMarmitaProduto.getChildren().add(marmitaPane);
        } catch (IOException e) {
            System.out.println("Erro ao carregar PaneMarmita: " + e);
            e.printStackTrace();
        }
    }

    @FXML
    private void finalizar() {
        Pedido pedido = new Pedido();

        if (checkBoxMensalista.isSelected()) {
            pedido.setMensalista(comboBoxMensalista.getValue());
        } else {
            pedido.setNomeCliente(TFNomeCliente.getText());
        }

        if (RBEntrega.isSelected()) {
            pedido.setTipoPedido("Entrega");
            pedido.setMotoboy(comboBoxMotoboy.getValue());
            pedido.setEndereco(TAEndereco.getText());
            pedido.setBairro(comboBoxBairro.getValue());
        } else {
            pedido.setTipoPedido("Balcão");
        }

        pedido.setObservacoes(TAObservacoes.getText());

        pedido.setValorEntrega(valorEntrega);

        pedido.setValorTotal(valorTotal);

        if (verificaPedido()) {

            abrirModalPagamento();

            if (pagamento != null || !pagamento.equals("")) {

                pedido.setTipoPagamento(pagamento);

                int idPedido = PedidoDAO.create(pedido);

                pedido.setId(idPedido);

                System.out.println(pedido.getId());

                MarmitaVendidaDAO.create(tableMarmita.getItems(), pedido.getId());
                ProdutoVendidoDAO.create(tableProduto.getItems(), pedido.getId());

                close();

            }

        }

    }

    @FXML
    private void cancelar() {
        close();
    }

    @FXML
    private void marmitaClicked(ActionEvent event) {
        if (tabButtonLeft.isSelected()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Pane/paneMarmita.fxml"));
                Pane marmitaPane = loader.load();
                PaneMarmitaController marmitaController = loader.getController();
                marmitaController.setController(this);
                APMarmitaProduto.getChildren().clear();
                APMarmitaProduto.getChildren().add(marmitaPane);
                tabButtonRight.setSelected(false);
            } catch (Exception e) {
                System.out.println("Erro marmitaClicked: " + e);
                e.printStackTrace();
            }
        } else {
            tabButtonLeft.setSelected(true);
        }
    }

    @FXML
    private void produtoClicked(ActionEvent event) {
        if (tabButtonRight.isSelected()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Pane/paneProduto.fxml"));
                Pane produtoPane = loader.load();
                PaneProdutoController produtoController = loader.getController();
                produtoController.setController(this);
                APMarmitaProduto.getChildren().clear();
                APMarmitaProduto.getChildren().add(produtoPane);
                tabButtonLeft.setSelected(false);
            } catch (Exception e) {
                System.out.println("Erro produtoClicked: " + e);
                e.printStackTrace();
            }
        } else {
            tabButtonRight.setSelected(true);
        }
    }

    @FXML
    private void RBGTipo(ActionEvent event) {
        if (RBEntrega.isSelected()) {
            paneEndereco.setDisable(false);
            valorEntrega = comboBoxBairro.getSelectionModel().getSelectedItem().getValorEntrega();
            atualizaValor();
        } else {
            valorEntrega = 0;
            atualizaValor();
            paneEndereco.setDisable(true);
            labelEnderecoInfo.setText("");
        }
    }

    private void loadBairro() {
        comboBoxBairro.getItems().clear();

        try {
            List<Bairro> bairros = BairroDAO.read();

            if (!bairros.isEmpty()) {
                comboBoxBairro.getItems().addAll(bairros);

                comboBoxBairro.getSelectionModel().selectFirst();
                valorEntrega = comboBoxBairro.getSelectionModel().getSelectedItem().getValorEntrega();
                atualizaValor();

                comboBoxBairro.setOnAction(e -> {
                    valorEntrega = comboBoxBairro.getSelectionModel().getSelectedItem().getValorEntrega();
                    atualizaValor();
                });
            }

        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "bairro");
        }


    }

    private void loadMensalista() {
        comboBoxMensalista.getItems().clear();

        try {
            List<Mensalista> mensalistas = MensalistaDAO.read();
            if (!mensalistas.isEmpty()) {
                comboBoxMensalista.getItems().addAll(mensalistas);
                comboBoxMensalista.getSelectionModel().selectFirst();
            }
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "mensalista");
        }
    }

    private void loadMotoboy() {
        try {
            comboBoxMotoboy.getItems().clear();

            List<Motoboy> motoboys = MotoboyDAO.read();
            if (!motoboys.isEmpty()) {
                comboBoxMotoboy.getItems().addAll(motoboys);
                comboBoxMotoboy.getSelectionModel().selectFirst();
            }
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "motoboy");
        }
    }

    public void adicionarMarmita(MarmitaVendida marmitaVendida) {
        tableMarmita.getItems().add(marmitaVendida);
        valorTotal += marmitaVendida.getSubtotal();
        atualizaValor();
        labelItensInfo.setText("");
    }

    public void adicionarProduto(ProdutoVendido produtoVendido) {
        tableProduto.getItems().add(produtoVendido);
        valorTotal += produtoVendido.getSubtotal();
        atualizaValor();
        labelItensInfo.setText("");
    }

    private void initTableMarmita() {
        colNomeMarmita.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colSubtotalMarmita.setCellValueFactory(new PropertyValueFactory<>("formattedSubtotal"));
        colDelMarmita.setCellFactory(param -> new TableCell<>() {
            private final Button btnExcluir = new Button("Excluir");

            {
                btnExcluir.setOnAction(event -> {
                    MarmitaVendida marmita = getTableView().getItems().get(getIndex());
                    valorTotal -= marmita.getSubtotal();
                    getTableView().getItems().remove(marmita);
                    atualizaValor();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnExcluir);
                }
            }
        });

        tableMarmita.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2) {
                        if (tableMarmita.getSelectionModel().getSelectedItem() != null) {
                            try {
                                Stage modal = new Stage();

                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalDetalhesMarmita.fxml"));

                                modal.setScene(loader.load());

                                ModalDetalhesMarmitaController controller = loader.getController();

                                controller.load(tableMarmita.getSelectionModel().getSelectedItem());

                                modal.setOnCloseRequest(eventClose -> {
                                    event.consume();
                                });
                                modal.setResizable(false);
                                modal.initStyle(StageStyle.UTILITY);
                                modal.showAndWait();

                            } catch (IOException e) {
                                System.out.println("Erro Modal Detalhes Marmita:");
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );

    }

    private void initTableProduto() {
        colNomeProduto.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colQuantidadeProduto.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colSubtotalProduto.setCellValueFactory(new PropertyValueFactory<>("formattedSubtotal"));
        colDelProduto.setCellFactory(param -> new TableCell<>() {
            private final Button btnExcluir = new Button("Excluir");

            {
                btnExcluir.setOnAction(event -> {
                    ProdutoVendido produto = getTableView().getItems().get(getIndex());
                    valorTotal -= produto.getSubtotal();
                    getTableView().getItems().remove(produto);
                    atualizaValor();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnExcluir);
                }
            }
        });
    }

    private void atualizaValor() {
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String valorEntregaFormatado = formatoMoeda.format(valorEntrega);
        double valorDesconto = descontosEAdicionais.stream()
                .filter(desconto -> desconto.getTipo().equals("Desconto"))
                .mapToDouble(DescontoAdicional::getValor).sum();

        double valorAdicional = descontosEAdicionais.stream()
                .filter(adicional -> adicional.getTipo().equals("Adicional"))
                .mapToDouble(DescontoAdicional::getValor).sum();

        String valorTotalFormatado = formatoMoeda.format((valorTotal + valorEntrega + valorAdicional) - valorDesconto);

        labelValorEntrega.setText("Entrega " + valorEntregaFormatado);
        labelValorTotal.setText("Total " + valorTotalFormatado);
    }

    private boolean verificaPedido() {
        boolean isValid = true;

        if (!checkBoxMensalista.isSelected() && (TFNomeCliente.getText().equals("") || TFNomeCliente.getText() == null)) {
            labelClienteInfo.setText("Este campo não pode estar vazio");
            isValid = false;
        } else {
            labelClienteInfo.setText("");
        }

        if (RBEntrega.isSelected() && (TAEndereco.getText().equals("") || TAEndereco.getText() == null)) {
            labelEnderecoInfo.setText("Este campo não pode estar vazio");
            isValid = false;
        } else {
            labelEnderecoInfo.setText("");
        }

        if (tableMarmita.getItems().size() <= 0 && tableProduto.getItems().size() <= 0) {
            labelItensInfo.setText("Adicione ao menos 1 marmita/produto ao pedido");
            isValid = false;
        } else {
            labelItensInfo.setText("");
        }

        return isValid;
    }

    @FXML
    private void checkBoxMensalista(ActionEvent event) {
        if (checkBoxMensalista.isSelected()) {
            comboBoxMensalista.setDisable(false);
            labelCliente.setDisable(true);
            TFNomeCliente.setDisable(true);
            labelClienteInfo.setText("");
        } else {
            comboBoxMensalista.setDisable(true);
            labelCliente.setDisable(false);
            TFNomeCliente.setDisable(false);
        }
    }

    @FXML
    private void descontosEAdicionais() {
        abrirModalDescontosEAdicionais();
    }

    public void abrirModalDescontosEAdicionais() {
        try {
            Stage modal = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalDescontosEAdicionais.fxml"));
            modal.setScene(fxmlLoader.load());

            ModalDescontosEAdicionaisController controller = fxmlLoader.getController();

            controller.initialize(this, descontosEAdicionais);

            modal.setResizable(false);
            modal.initStyle(StageStyle.UTILITY);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.showAndWait();

            atualizaValor();

        } catch (IOException e) {
            System.out.println("Erro ao abrir modal descontos e adicionais" + e);
            e.printStackTrace();
        }

    }

    public void abrirModalPagamento() {
        try {
            Stage modal = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalPagamento.fxml"));
            modal.setScene(fxmlLoader.load());

            ModalPagamentoController controller = fxmlLoader.getController();

            modal.setOnCloseRequest(event -> {
                event.consume();
            });
            modal.setResizable(false);
            modal.initStyle(StageStyle.UTILITY);
            modal.showAndWait();

            pagamento = controller.getPagamento();

            atualizaValor();

        } catch (IOException e) {
            System.out.println("Erro ao abrir modal desconto" + e);
            e.printStackTrace();
        }
    }

    private void close() {
        ((AnchorPane) panePrincipal.getParent()).getChildren().clear();
    }

    public ObservableList<DescontoAdicional> getDescontosEAdicionais() {
        return descontosEAdicionais;
    }

    public void initDescontosEAdicionais() {
        descontosEAdicionais.addListener((ListChangeListener<DescontoAdicional>)change -> {
            while (change.next()) {
                atualizaValor();
            }
        });
    }

    public void setDescontosEAdicionais(ObservableList<DescontoAdicional> descontosEAdicionais) {
        this.descontosEAdicionais = descontosEAdicionais;
    }
}
