package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import my.company.projetorotisseriejavafx.Objects.Pagamento;
import my.company.projetorotisseriejavafx.Objects.Pedido;
import my.company.projetorotisseriejavafx.Util.CssHelper;
import my.company.projetorotisseriejavafx.Util.IconHelper;

import java.io.IOException;
import java.time.LocalDate;

public class ModalPagamentosController {

    private Pedido pedido;
    private ObservableList<Pagamento> pagamentos;

    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnDCP;

    @FXML
    private TableColumn<Pagamento, LocalDate> colData;

    @FXML
    private TableColumn<Pagamento, String> colPagamento;

    @FXML
    private TableColumn<Pagamento, String> colValor;

    @FXML
    private TableColumn<Pagamento, Void> colExcluir;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<Pagamento> tablePagamento;

    public void initialize(Pedido pedido, ObservableList<Pagamento> pagamentos) {
        this.pedido = pedido;
        this.pagamentos = pagamentos;
        initTablePagamento();
        initBtns();
    }

    @FXML
    void adicionar(ActionEvent event) {
        abrirModalAdicionarPagamento();
    }

    @FXML
    void definirComoPago(ActionEvent event) {
        abrirModalAviso();
    }

    public void adidionarPagamento(Pagamento pagamento) {
        pagamento.setIdPedido(pedido.getId());
        pagamentos.add(pagamento);
    }

    public void abrirModalAdicionarPagamento() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalAdicionarPagamento.fxml"));
            Parent root = loader.load();

            Stage modal = new Stage();
            Scene scene = new Scene(root);

            CssHelper.loadCss(scene);

            modal.setScene(scene);

            ModalAdicionarPagamentoController controller = loader.getController();

            controller.initialize(this, pedido);

            modal.initStyle(StageStyle.UTILITY);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setResizable(false);
            modal.showAndWait();

        } catch (IOException e) {
            System.out.println("Erro ao abrir Adicionar Pagamento");
            e.printStackTrace();
        }
    }

    public void abrirModalAviso() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalAvisoPagamentos.fxml"));
            Parent root = loader.load();

            Stage modal = new Stage();
            Scene scene = new Scene(root);

            CssHelper.loadCss(scene);
            IconHelper.applyIconsTo(root);

            modal.setScene(scene);

            ModalAvisoPagamentosController controller = loader.getController();

            controller.initialize(pedido);

            modal.initStyle(StageStyle.UTILITY);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setResizable(false);
            modal.showAndWait();

            if (controller.getResponse() == 1) {
                btnAdicionar.setDisable(true);
                btnDCP.setDisable(true);
                pedido.setStatus("PAGO");
                fecharModal();
            }

        } catch (IOException e) {
            System.out.println("Erro ao abrir Adicionar Pagamento");
            e.printStackTrace();
        }
    }

    private void initTablePagamento() {
        colData.setCellValueFactory(new PropertyValueFactory<>("formattedData"));
        colPagamento.setCellValueFactory(new PropertyValueFactory<>("tipoPagamento"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("formattedValor"));
        if (pedido.getStatus().equals("A PAGAR")) {
            colExcluir.setCellFactory(param -> new TableCell<>() {

                private final Button btnExcluir = new Button("Excluir");

                {
                    btnExcluir.setOnAction(event -> {
                        Pagamento pagamento = getTableView().getItems().get(getIndex());

                        pedido.setValorPago(pedido.getValorPago() - pagamento.getValor());

                        pagamentos.remove(pagamento);
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

        tablePagamento.setRowFactory(tv -> {
            TableRow<Pagamento> row = new TableRow<>();
            Tooltip tooltip = new Tooltip();
            tooltip.setShowDelay(Duration.millis(400));
            row.itemProperty().addListener((obs, oldItem, newItem) -> {
                if (newItem != null) {
                    tooltip.setText("Observação: " + newItem.getObservacao());
                    row.setTooltip(tooltip);
                } else {
                    row.setTooltip(null);
                }
            });
            return row;
        });

        tablePagamento.setItems(pagamentos);
    }

    public void initBtns() {
        if (pedido.getStatus().equals("PAGO")) {
            btnAdicionar.setDisable(true);
            btnDCP.setDisable(true);
        }
    }

    public void fecharModal() {
        Stage modal =  (Stage) root.getScene().getWindow();
        modal.close();
    }

    public void disableBtnAdicionar(boolean bool) {
        btnAdicionar.setDisable(bool);
    }

}
