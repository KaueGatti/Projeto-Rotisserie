package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.DAO.ClienteDAO;

import my.company.projetorotisseriejavafx.Objects.Cliente;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

import java.sql.SQLException;

public class ModalCadastrarClienteController {

    @FXML
    private Label LInfo;

    @FXML
    private TextField TFNome;
    @FXML
    private TextField TFContato;

    @FXML
    private Button btnCadastrar;

    @FXML
    private AnchorPane root;

    @FXML
    void cadastrar(ActionEvent event) {
        Cliente cliente = new Cliente();

        if (!validaCliente()) return;

        cliente.setNome(TFNome.getText());
        cliente.setContato(TFContato.getText());

        try {
            ClienteDAO.criar(cliente);
            fecharModal();
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "cliente");
        }

    }

    private boolean validaCliente() {
        if (TFNome.getText().trim().isEmpty()) {
            LInfo.setText("Nome não pode estar vázio");
            return false;
        }

        if (TFContato.getText().trim().isEmpty()) {
            LInfo.setText("Contato não pode estar vázio");
            return false;
        }

        return true;
    }

    public void fecharModal() {
        Stage modal = (Stage) root.getScene().getWindow();
        modal.close();
    }

}
