package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.DAO.PedidoDAO;
import my.company.projetorotisseriejavafx.Objects.Pedido;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

import java.sql.SQLException;

public class ModalAvisoPagamentosController {

    Pedido pedido;
    int response;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;

    @FXML
    private AnchorPane root;

    public void initialize(Pedido pedido) {
        this.pedido = pedido;
    }

    @FXML
    void cancelar(ActionEvent event) {
        response = 0;
        fecharModal();
    }

    @FXML
    void confirmar(ActionEvent event) {
        response = 1;

        try {
            PedidoDAO.finalizar(pedido.getId());
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "Falha ao finalizar o pedido");
        }

        fecharModal();
    }

    public void fecharModal() {
        Stage modal = (Stage) root.getScene().getWindow();
        modal.close();
    }

    public int getResponse() {
        return response;
    }

}
