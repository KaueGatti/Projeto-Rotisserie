package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import my.company.projetorotisseriejavafx.DAO.PagamentoDAO;
import my.company.projetorotisseriejavafx.Objects.DescontoAdicional;
import my.company.projetorotisseriejavafx.Objects.Pagamento;
import my.company.projetorotisseriejavafx.Objects.Pedido;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class ModalPagamentosController {

    private Pedido pedido;
    private ObservableList<Pagamento> pagamentos;

    @FXML
    private Button btnAdicionar;

    @FXML
    private TableColumn<Pagamento, LocalDate> colData;

    @FXML
    private TableColumn<Pagamento, String> colPagamento;

    @FXML
    private TableColumn<Pagamento, String> colValor;

    @FXML
    private Scene scene;

    @FXML
    private TableView<Pagamento> tablePagamento;

    public void initialize(Pedido pedido, ObservableList<Pagamento> pagamentos) {
        this.pedido = pedido;
        this.pagamentos = pagamentos;
        initTablePagamento();
        initBtnAdicionar();
    }

    @FXML
    void adicionar(ActionEvent event) {
        abrirModalAdicionarPagamento();
    }


    public void adidionarPagamento(Pagamento pagamento) {
        pagamento.setIdPedido(pedido.getId());
        pagamentos.add(pagamento);
    }

    public void abrirModalAdicionarPagamento() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalAdicionarPagamento.fxml"));
            Stage modal = new Stage();

            modal.setScene(loader.load());

            ModalAdicionarPagamentoController controller = loader.getController();

            controller.initialize(this);

            modal.initStyle(StageStyle.UTILITY);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setResizable(false);
            modal.show();
            fecharModal();

        } catch (IOException e) {
            System.out.println("Erro ao abrir Adicionar Pagamento");
            e.printStackTrace();
        }
    }

    private void initTablePagamento() {
        colData.setCellValueFactory(new PropertyValueFactory<>("formattedData"));
        colPagamento.setCellValueFactory(new PropertyValueFactory<>("tipoPagamento"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("formattedValor"));

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

    public void initBtnAdicionar() {
        if (pedido.getStatus().equals("FINALIZADO")) {
            btnAdicionar.setDisable(true);
        }
    }

    public void fecharModal() {
        Stage modal = (Stage) scene.getWindow();
        modal.close();
    }

}
