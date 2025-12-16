package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.DAO.BairroDAO;
import my.company.projetorotisseriejavafx.DAO.ClienteDAO;
import my.company.projetorotisseriejavafx.Objects.Bairro;
import my.company.projetorotisseriejavafx.Objects.Cliente;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

import java.sql.SQLException;
import java.util.List;

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
    private ComboBox<Bairro> CBBairro;
    @FXML
    private TextArea TAEndereco;
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
        cliente.setBairro(CBBairro.getValue());
        cliente.setEndereco(TAEndereco.getText());
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
        initCBBairro();
    }

    private void loadTextFields() {
        TFNome.setText(cliente.getNome());
        TFContato.setText(cliente.getContato());
        TAEndereco.setText(cliente.getEndereco());
    }

    public void fecharModal() {
        Stage modal = (Stage) root.getScene().getWindow();
        modal.close();
    }

    private boolean validaCliente() {
        if (TFContato.getText().trim().isEmpty()) {
            LInfo.setText("Contato não pode estar vázio");
            return false;
        }

        return true;
    }

    private void initCBBairro() {
        CBBairro.getItems().clear();
        CBBairro.getItems().add(new Bairro("Nenhum"));

        try {
            List<Bairro> bairros = BairroDAO.listarAtivos();

            if (!bairros.isEmpty()) {
                CBBairro.getItems().addAll(bairros);
            }

            if (cliente.getBairro().getId() != 0) {
                CBBairro.getSelectionModel().select(cliente.getBairro());
            } else {
                CBBairro.getSelectionModel().selectFirst();
            }

        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "bairro");
        }
    }
}
