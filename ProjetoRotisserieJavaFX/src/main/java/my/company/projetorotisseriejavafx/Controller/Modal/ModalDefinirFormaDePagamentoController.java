package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.DAO.PedidoDAO;
import my.company.projetorotisseriejavafx.DB.DatabaseConnection;
import my.company.projetorotisseriejavafx.Objects.Pedido;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

import java.sql.SQLException;

public class ModalDefinirFormaDePagamentoController {

    private Pedido pedido;

    @FXML
    private ComboBox<String> CBPagamento;

    @FXML
    private Button btnDefinir;

    @FXML
    private AnchorPane root;

    public void initialize(Pedido pedido) {
        initCBPagamento();
        this.pedido = pedido;
    }

    @FXML
    void definir(ActionEvent event) {

        try {
            PedidoDAO.definirPagamento(pedido.getId(), CBPagamento.getValue());

            pedido.setTipoPagamento(CBPagamento.getValue());
            if (!CBPagamento.getValue().equalsIgnoreCase("Pagar depois")) {
                pedido.setStatus("PAGO");
            }

            fecharModal();
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "Erro ao definir pagamento");
        }
    }

    private void initCBPagamento() {
        CBPagamento.getItems().clear();

        CBPagamento.getItems().addAll("Dinheiro", "Cartão", "Pix", "Pagar depois");
        CBPagamento.getSelectionModel().selectFirst();
    }

    private void fecharModal() {
        Stage modal = (Stage) root.getScene().getWindow();
        modal.close();
    }

}
