package my.company.projetorotisseriejavafx.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import my.company.projetorotisseriejavafx.Controller.Pane.PaneDetalhesBalcaoController;
import my.company.projetorotisseriejavafx.Controller.Pane.PaneDetalhesEntregaController;
import my.company.projetorotisseriejavafx.DAO.PedidoDAO;
import my.company.projetorotisseriejavafx.Objects.Pedido;

public class PedidosController implements Initializable {

    LocalDate inicio;
    LocalDate fim;

    @FXML
    private AnchorPane APPedidos;
    @FXML
    private TableView<Pedido> tablePedidos;
    @FXML
    private TableColumn<Pedido, String> colCliente;
    @FXML
    private TableColumn<Pedido, String> colTipo;
    @FXML
    private TableColumn<Pedido, LocalDate> colData;
    @FXML
    private TableColumn<Pedido, Double> colTotal;
    @FXML
    private TableColumn<Pedido, String> colStatus;
    @FXML
    private TextField TFCliente;
    @FXML
    private ComboBox<String> comboBoxTipo;
    private TextField TFData;
    @FXML
    private ComboBox<String> comboBoxStatus;
    @FXML
    private ComboBox<String> comboBoxOrdenar;
    @FXML
    private CheckBox checkBoxDecrescente;
    @FXML
    private Button btnPesquisar;
    @FXML
    private Pane paneDetalhes;
    @FXML
    private DatePicker DPFim;
    @FXML
    private DatePicker DPInicio;
    @FXML
    private Label labelInfoData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTablePedidos();
        initDatePicker();
        loadCBTipo();
        loadCBStatus();
        loadCBOrdenar();
    }

    @FXML
    private void pesquisar(ActionEvent event) {
        tablePedidos.getItems().clear();

        String nomeCliente = TFCliente.getText();

        boolean intervaloData = false;

        LocalDateTime dataInicio = null;
        LocalDateTime dataFim = null;

        if (DPInicio.getValue() != null && DPFim.getValue() != null) {
            dataInicio = LocalDateTime.of(DPInicio.getValue(), LocalTime.of(00, 00, 00));
            dataFim = LocalDateTime.of(DPFim.getValue(), LocalTime.of(23, 59, 59));
            if (dataInicio.isAfter(dataFim)) {
                labelInfoData.setText("Defina um intervalo de datas válido");
            } else {
                intervaloData = true;
            }
        } else if (DPInicio.getValue() != null) {
            dataInicio = LocalDateTime.of(DPInicio.getValue(), LocalTime.of(00, 00, 00));
            intervaloData = true;
        } else if (DPFim.getValue() != null) {
            dataFim = LocalDateTime.of(DPFim.getValue(), LocalTime.of(23, 59, 59));
            intervaloData = true;
        } else {
            intervaloData = true;
        }

        String tipoPedido;

        if (comboBoxTipo.getSelectionModel().getSelectedItem().equals("Todos")) {
            tipoPedido = null;
        } else {
            tipoPedido = comboBoxTipo.getSelectionModel().getSelectedItem();
        }

        String status;

        if (comboBoxStatus.getSelectionModel().getSelectedItem().equals("Todos")) {
            status = null;
        } else {
            status = comboBoxStatus.getSelectionModel().getSelectedItem();
        }

        String orderBy;

        if (comboBoxOrdenar.getSelectionModel().getSelectedItem().equals("Nenhum")) {
            orderBy = null;
        } else {
            switch (comboBoxOrdenar.getSelectionModel().getSelectedIndex()) {
                case 1:
                    orderBy = "nome_cliente";
                    break;
                case 2:
                    orderBy = "tipo_pedido";
                    break;
                case 3:
                    orderBy = "date_time";
                    break;
                case 4:
                    orderBy = "valor_total";
                    break;
                case 5:
                    orderBy = "_status";
                    break;
                default:
                    orderBy = null;
            }
        }

        boolean isDesc = checkBoxDecrescente.isSelected();

        if (intervaloData) {
            List<Pedido> pedidos = PedidoDAO.readDinamico(nomeCliente, tipoPedido, dataInicio, dataFim, status, orderBy, isDesc);
            tablePedidos.getItems().addAll(pedidos);
        }

    }

    private void initTablePedidos() {
        colCliente.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoPedido"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dateTimeFormat"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tablePedidos.getItems().clear();

        List<Pedido> pedidos = PedidoDAO.readDinamico("", null, null, null, null, null, false);

        if (pedidos != null) {
            tablePedidos.getItems().addAll(pedidos);
        }

        tablePedidos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && !tablePedidos.getSelectionModel().isEmpty()) {
                paneDetalhes.getChildren().clear();
                if (tablePedidos.getSelectionModel().getSelectedItem().getTipoPedido().equals("Balcão")) {
                    paneDetalhes.getChildren().clear();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Pane/paneDetalhesBalcao.fxml"));
                        Pane balcaoPane = loader.load();
                        PaneDetalhesBalcaoController balcaoController = loader.getController();
                        balcaoController.load(tablePedidos.getSelectionModel().getSelectedItem());
                        paneDetalhes.getChildren().add(balcaoPane);
                    } catch (IOException e) {
                        System.out.println("Erro ao carregar PaneDetalhesBalcao: " + e);
                    }
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Pane/paneDetalhesEntrega.fxml"));
                        Pane entregaPane = loader.load();
                        PaneDetalhesEntregaController entregaController = loader.getController();
                        entregaController.load(tablePedidos.getSelectionModel().getSelectedItem());
                        paneDetalhes.getChildren().add(entregaPane);
                    } catch (IOException e) {
                        System.out.println("Erro ao carregar PaneDetalhesEntrega: " + e);
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initDatePicker() {
        DPInicio.getEditor().setDisable(true);
        DPFim.getEditor().setDisable(true);
    }

    private void loadCBTipo() {
        comboBoxTipo.getItems().clear();
        comboBoxTipo.getItems().addAll("Todos", "Entrega", "Balcão");
        comboBoxTipo.getSelectionModel().selectFirst();
    }

    private void loadCBStatus() {
        comboBoxStatus.getItems().clear();
        comboBoxStatus.getItems().addAll("Todos", "Finalizado");
        comboBoxStatus.getSelectionModel().selectFirst();
    }

    private void loadCBOrdenar() {
        comboBoxOrdenar.getItems().clear();
        comboBoxOrdenar.getItems().addAll("Nenhum", "Cliente", "Tipo", "Data e Horário", "Valor total", "Status");
        comboBoxOrdenar.getSelectionModel().selectFirst();
        checkBoxDecrescente.setDisable(true);
        comboBoxOrdenar.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.equals("Nenhum")) {
                checkBoxDecrescente.setDisable(false);
            } else {
                checkBoxDecrescente.setDisable(true);
            }
        });
    }
}
