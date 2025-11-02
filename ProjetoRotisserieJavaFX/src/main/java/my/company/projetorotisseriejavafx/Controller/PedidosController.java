package my.company.projetorotisseriejavafx.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import my.company.projetorotisseriejavafx.Controller.Pane.PaneDetalhesBalcaoController;
import my.company.projetorotisseriejavafx.Controller.Pane.PaneDetalhesEntregaController;
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
    private Pane paneDetalhes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTablePedidos();
    }

    private void initTablePedidos() {
        colCliente.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoPedido"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dateTimeFormat"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tablePedidos.getItems().clear();

        List<Pedido> pedidos = PedidoDAO.read();

        if (!pedidos.isEmpty()) {
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
                        e.printStackTrace();
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
}
