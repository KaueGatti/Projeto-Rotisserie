package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.Controller.PedidosController;
import my.company.projetorotisseriejavafx.Objects.Cliente;
import my.company.projetorotisseriejavafx.Objects.Pedido;
import my.company.projetorotisseriejavafx.Util.IconHelper;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ModalPedidosAtrasadosController {

    @FXML
    private TableColumn<Pedido, String> colCliente;

    @FXML
    private TableColumn<Pedido, LocalDateTime> colData;

    @FXML
    private TableColumn<Pedido, Double> colValor;

    @FXML
    private TableColumn<Pedido, LocalDate> colVencimento;

    @FXML
    private TableColumn<Pedido, String> colContato;

    @FXML
    private TableColumn<Pedido, Void> colVer;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<Pedido> tablePedidos;

    private List<Pedido> pedidosAtrasados;
    private PedidosController controller;

    public void initialize(List<Pedido> pedidosAtrasados, List<Cliente> clientes, PedidosController controller) {
        this.pedidosAtrasados = pedidosAtrasados;
        this.controller = controller;
        initTablePedidos();
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
        colContato.setCellValueFactory(p -> {
            if (p.getValue().mensalistaProperty().get() != null) {
                return p.getValue().getCliente().contatoProperty();
            }

            return new SimpleStringProperty("Sem contato");
        });
        colVer.setCellFactory(param -> new TableCell<>() {
            private final Button btnVer = new Button("");

            {
                btnVer.setMaxWidth(Double.MAX_VALUE);
                btnVer.getStyleClass().add("BVer");
                btnVer.getStyleClass().add("icon-view");

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
                    HBox wrapper = new HBox(btnVer);
                    wrapper.setSpacing(0);
                    wrapper.setPadding(new Insets(0));
                    wrapper.setFillHeight(true);

                    wrapper.setMaxWidth(Double.MAX_VALUE);

                    IconHelper.applyIcon(btnVer);

                    setGraphic(wrapper);
                }
            }
        });

        tablePedidos.getItems().setAll(pedidosAtrasados);
    }

    private void fecharModal() {
        Stage modal = (Stage) root.getScene().getWindow();
        modal.close();
    }

}
