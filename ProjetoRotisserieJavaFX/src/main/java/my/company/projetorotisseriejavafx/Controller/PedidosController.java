package my.company.projetorotisseriejavafx.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import my.company.projetorotisseriejavafx.DAO.PedidoDAO;
import my.company.projetorotisseriejavafx.Objects.Pedido;

public class PedidosController implements Initializable {

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
    @FXML
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTablePedidos();
        loadCBTipo();
        loadCBStatus();
        loadCBOrdenar();
    }

    @FXML
    private void pesquisar(ActionEvent event) {
        tablePedidos.getItems().clear();

        String nomeCliente = TFCliente.getText();

        String tipoPedido;

        if (comboBoxTipo.getSelectionModel().getSelectedItem().equals("Todos")) {
            tipoPedido = null;
        } else {
            tipoPedido = comboBoxTipo.getSelectionModel().getSelectedItem();
        }

        String data = TFData.getText();

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
            orderBy = comboBoxOrdenar.getSelectionModel().getSelectedItem();
        }
        
        boolean isDesc = checkBoxDecrescente.isSelected();

        List<Pedido> pedidos = PedidoDAO.readDinamico(nomeCliente, tipoPedido, null, status, orderBy, isDesc);
        tablePedidos.getItems().addAll(pedidos);
    }

    private void initTablePedidos() {
        colCliente.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoPedido"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tablePedidos.getItems().clear();

        List<Pedido> pedidos = PedidoDAO.readDinamico(null, null, null, null, null, false);

        if (pedidos != null) {
            tablePedidos.getItems().addAll(pedidos);
        }
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
    }
}
