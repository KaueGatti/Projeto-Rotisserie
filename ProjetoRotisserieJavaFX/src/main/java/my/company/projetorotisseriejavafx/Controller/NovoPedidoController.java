package my.company.projetorotisseriejavafx.Controller;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalDescontosController;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalDetalhesMarmitaController;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalDetalhesProdutoController;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalPagamentoController;
import my.company.projetorotisseriejavafx.Controller.Pane.PaneMarmitaController;
import my.company.projetorotisseriejavafx.Controller.Pane.PaneProdutoController;
import my.company.projetorotisseriejavafx.DAO.BairroDAO;
import my.company.projetorotisseriejavafx.DAO.MarmitaVendidaDAO;
import my.company.projetorotisseriejavafx.DAO.MensalistaDAO;
import my.company.projetorotisseriejavafx.DAO.MotoboyDAO;
import my.company.projetorotisseriejavafx.DAO.PedidoDAO;
import my.company.projetorotisseriejavafx.DAO.ProdutoVendidoDAO;
import my.company.projetorotisseriejavafx.Objects.Bairro;
import my.company.projetorotisseriejavafx.Objects.MarmitaVendida;
import my.company.projetorotisseriejavafx.Objects.Mensalista;
import my.company.projetorotisseriejavafx.Objects.Motoboy;
import my.company.projetorotisseriejavafx.Objects.Pedido;
import my.company.projetorotisseriejavafx.Objects.ProdutoVendido;

public class NovoPedidoController implements Initializable {

    private double valorTotal = 0;
    private double valorEntrega = 0;
    private double valorAdicional = 0;
    private double valorDesconto = 0;
    private String pagamento;
    @FXML
    private ToggleGroup tipo;
    @FXML
    private Button btnFinalizar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnDescontos;

    public NovoPedidoController() {
    }

    @FXML
    private Pane panePrincipal;

    @FXML
    private TableView<MarmitaVendida> tableMarmita;
    @FXML
    private TableColumn<MarmitaVendida, String> colDescricaoMarmita;
    @FXML
    private TableColumn<MarmitaVendida, Double> colSubtotalMarmita;
    @FXML
    private TableColumn<MarmitaVendida, Void> colDelMarmita;

    @FXML
    private TableView<ProdutoVendido> tableProduto;
    @FXML
    private TableColumn<ProdutoVendido, String> colDescricaoProduto;
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
    private ComboBox comboBoxMensalista;
    @FXML
    private ComboBox comboBoxMotoboy;
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
        }
    }

    @FXML
    private void Finalizar() {
        Pedido pedido = new Pedido();

        if (checkBoxMensalista.isSelected()) {
            pedido.setMensalista((Mensalista) comboBoxMensalista.getSelectionModel().getSelectedItem());
        } else {
            pedido.setNomeCliente(TFNomeCliente.getText());
        }

        if (RBEntrega.isSelected()) {
            pedido.setTipoPedido("Entrega");
            pedido.setMotoboy((Motoboy) comboBoxMotoboy.getSelectionModel().getSelectedItem());
            pedido.setEndereco(TAEndereco.getText());
            pedido.setBairro((Bairro) comboBoxBairro.getSelectionModel().getSelectedItem());
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

                for (MarmitaVendida marmita : tableMarmita.getItems()) {
                    marmita.setPedido(pedido);
                }

                for (ProdutoVendido produto : tableProduto.getItems()) {
                    produto.setPedido(pedido);
                }

                System.out.println(pedido.getId());

                MarmitaVendidaDAO.create(tableMarmita.getItems());
                ProdutoVendidoDAO.create(tableProduto.getItems());

                close();

            }

        }

    }

    @FXML
    private void Cancelar() {
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
        for (Bairro bairro : BairroDAO.read()) {
            comboBoxBairro.getItems().add(bairro);
        }
        comboBoxBairro.setOnAction(e -> {
            valorEntrega = ((Bairro) comboBoxBairro.getSelectionModel().getSelectedItem()).getValorEntrega();
            atualizaValor();
        });
        comboBoxBairro.getSelectionModel().selectFirst();
        valorEntrega = ((Bairro) comboBoxBairro.getSelectionModel().getSelectedItem()).getValorEntrega();
        atualizaValor();
    }

    private void loadMensalista() {
        comboBoxMensalista.getItems().clear();
        for (Mensalista mensalista : MensalistaDAO.read()) {
            comboBoxMensalista.getItems().add(mensalista);
        }
        comboBoxMensalista.getSelectionModel().selectFirst();
    }

    private void loadMotoboy() {
        comboBoxMotoboy.getItems().clear();
        for (Motoboy motoboy : MotoboyDAO.read()) {
            comboBoxMotoboy.getItems().add(motoboy);
        }
        comboBoxMotoboy.getSelectionModel().selectFirst();
    }

    public void adicionarMarmita(MarmitaVendida marmitaVendida) {
        tableMarmita.getItems().add(marmitaVendida);
        valorTotal += marmitaVendida.getMarmita().getValor();
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
        colDescricaoMarmita.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colSubtotalMarmita.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
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
        colDescricaoProduto.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colQuantidadeProduto.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colSubtotalProduto.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
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
        
        tableProduto.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                if (tableProduto.getSelectionModel().getSelectedItem() != null) {
                    try {
                        Stage modal = new Stage();

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalDetalhesProduto.fxml"));

                        modal.setScene(loader.load());

                        ModalDetalhesProdutoController controller = loader.getController();
                        
                        controller.load(tableProduto.getSelectionModel().getSelectedItem());
                        
                        modal.setOnCloseRequest(eventClose -> {
                            event.consume();
                        });
                        
                        modal.setResizable(false);
                        modal.initStyle(StageStyle.UTILITY);
                        modal.showAndWait();

                    } catch (IOException e) {
                        System.out.println("Erro Modal Detalhes Produto:");
                        e.printStackTrace();
                    }
                }
            }
        }
        );

    }

    private void atualizaValor() {
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String valorEntregaFormatado = formatoMoeda.format(valorEntrega);
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
    private void Descontos() {
        abrirModalDesconto();
    }

    public void abrirModalDesconto() {
        try {
            Stage modal = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalDescontos.fxml"));
            modal.setScene(fxmlLoader.load());

            ModalDescontosController controller = fxmlLoader.getController();

            controller.loadDescontos(valorDesconto, valorAdicional);

            modal.setOnCloseRequest(event -> {
                event.consume();
            });
            modal.setResizable(false);
            modal.initStyle(StageStyle.UTILITY);
            modal.setX(700);
            modal.setY(400);
            modal.showAndWait();

            if (controller.getDesconto() != -1) {
                valorDesconto = controller.getDesconto();
            }

            if (controller.getAdicional() != -1) {
                valorAdicional = controller.getAdicional();
            }

            atualizaValor();

        } catch (IOException e) {
            System.out.println("Erro ao abrir modal desconto" + e);
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

    public void abrirModalProduto() {
        try {
            Stage modal = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalDetalhesProduto.fxml"));
            modal.setScene(fxmlLoader.load());

            ModalDetalhesProdutoController controller = fxmlLoader.getController();

            controller.load(tableProduto.getSelectionModel().getSelectedItem());

            modal.setOnCloseRequest(event -> {
                event.consume();
            });
            modal.setResizable(false);
            modal.initStyle(StageStyle.UTILITY);
            modal.setX(700);
            modal.setY(400);
            modal.showAndWait();

        } catch (IOException e) {
            System.out.println("Erro ao abrir modal detalhes produto" + e);
            e.printStackTrace();
        }

    }
}
