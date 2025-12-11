package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.DAO.ClienteDAO;
import my.company.projetorotisseriejavafx.Objects.Cliente;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

import java.sql.SQLException;

public class ModalEditClienteController {

    private Cliente cliente;

    @FXML
    private AnchorPane root;
    @FXML
    private ComboBox<String> CBStatus;
    @FXML
    private TextField TFNome;
    @FXML
    private TextField TFContato;
    @FXML
    private Label LInfo;
    @FXML
    private Button btnSalvar;

    public void initialize(Cliente cliente) {
        this.cliente = cliente;
        loadTextFields();
        loadComboBoxs();
    }

    @FXML
    void salvar(ActionEvent event) {

        if (!validaCliente()) return;

        cliente.setContato(TFContato.getText());
        cliente.setStatus(CBStatus.getValue());

        try {
            ClienteDAO.atualizar(cliente);
            fecharModal();
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "cliente");
        }
    }

    private void loadComboBoxs() {
        CBStatus.getItems().addAll("ATIVO", "INATIVO");
        CBStatus.getSelectionModel().select(cliente.getStatus());
    }

    private void loadTextFields() {
        TFNome.setText(cliente.getNome());
        TFContato.setText(cliente.getContato());
    }

    public void fecharModal() {
        Stage modal =  (Stage) root.getScene().getWindow();
        modal.close();
    }

    private boolean validaCliente() {
        if (TFContato.getText().trim().isEmpty()) {
            LInfo.setText("Contato não pode estar vázio");
            return false;
        }

        return true;
    }
}
