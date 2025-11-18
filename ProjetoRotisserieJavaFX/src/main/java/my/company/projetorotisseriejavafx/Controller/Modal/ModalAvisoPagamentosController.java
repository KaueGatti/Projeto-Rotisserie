package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.DAO.PedidoDAO;
import my.company.projetorotisseriejavafx.Objects.Pedido;

public class ModalAvisoPagamentosController {

    Pedido pedido;
    int response;

    @FXML
    private ImageView IVWarning;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;

    @FXML
    private Scene scene;

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
        PedidoDAO.finalizar(pedido.getId());
        fecharModal();
    }

    public void fecharModal() {
        Stage modal = (Stage) scene.getWindow();
        modal.close();
    }

    public int getResponse() {
        return response;
    }

}
