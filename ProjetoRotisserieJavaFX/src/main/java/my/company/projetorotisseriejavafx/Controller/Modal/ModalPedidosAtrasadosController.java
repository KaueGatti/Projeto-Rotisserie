package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.Controller.PedidosController;
import my.company.projetorotisseriejavafx.DAO.PedidoDAO;
import my.company.projetorotisseriejavafx.Objects.Mensalista;
import my.company.projetorotisseriejavafx.Objects.Motoboy;
import my.company.projetorotisseriejavafx.Objects.Pedido;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class ModalPedidosAtrasadosController {

    @FXML
    private ComboBox<Mensalista> CBMensalista;

    @FXML
    private TableColumn<Pedido, String> colCliente;

    @FXML
    private TableColumn<Pedido, LocalDateTime> colData;

    @FXML
    private TableColumn<Pedido, Double> colValor;

    @FXML
    private TableColumn<Pedido, LocalDate> colVencimento;

    @FXML
    private TableColumn<Pedido, Void> colVer;

    @FXML
    private Scene scene;

    @FXML
    private TableView<Pedido> tablePedidos;

    private List<Pedido> pedidosAtrasados;
    private List<Mensalista> mensalistas;
    private PedidosController controller;

    public void initialize(List<Pedido> pedidosAtrasados, List<Mensalista> mensalistas, PedidosController controller) {
        this.pedidosAtrasados = pedidosAtrasados;
        this.mensalistas = mensalistas;
        this.controller = controller;
        initTablePedidos();
        initCBMensalista();
    }

    private void initTablePedidos() {
        colCliente.setCellValueFactory(p -> p.getValue().nomeClienteProperty());
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
        colVencimento.setCellValueFactory(p -> p.getValue().vencimentoProperty());
        colVencimento.setCellFactory(column -> new TableCell<Pedido, LocalDate>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(formatter.format(item));
                }
            }
        });
        colValor.setCellValueFactory(p -> p.getValue().valorTotalProperty().asObject());
        colValor.setCellFactory(column -> new TableCell<Pedido, Double>() {
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
        colVer.setCellFactory(param -> new TableCell<>() {
            private final Button btnVer = new Button("Ver");

            {
                btnVer.setOnAction(event -> {
                    Pedido pedido = getTableView().getItems().get(getIndex());
                    controller.loadDetalhes(pedido);
                    controller.selectPedidoInTable(pedido);
                    fecharModal();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnVer);
                }
            }
        });

        tablePedidos.getItems().setAll(pedidosAtrasados);
    }

    private void initCBMensalista() {
        CBMensalista.getItems().clear();
        CBMensalista.getItems().addAll(mensalistas);
        CBMensalista.getSelectionModel().selectFirst();

        CBMensalista.valueProperty().addListener((observable, oldValue, newValue) -> {
            List<Pedido> filtrados;

            if (newValue.getNome().equals("Todos")) {
                filtrados = pedidosAtrasados.stream().toList();
                tablePedidos.getItems().setAll(filtrados);
                return;
            }

            filtrados = pedidosAtrasados.stream()
                    .filter(p -> p.getMensalista() != null)
                    .filter(p -> p.getMensalista().getNome().equals(CBMensalista.getValue().getNome()))
                    .toList();

            tablePedidos.getItems().setAll(filtrados);
        });
    }

    private void fecharModal() {
        Stage modal = (Stage)  scene.getWindow();
        modal.close();
    }

}
