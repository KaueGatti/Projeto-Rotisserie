package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.DAO.BairroDAO;
import my.company.projetorotisseriejavafx.DAO.ClienteDAO;

import my.company.projetorotisseriejavafx.Objects.Bairro;
import my.company.projetorotisseriejavafx.Objects.Cliente;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ModalCadastrarClienteController implements Initializable {

    @FXML
    private Label LInfo;

    @FXML
    private TextField TFNome;
    @FXML
    private TextField TFContato;
    @FXML
    private TextArea TAEndereco;
    @FXML
    private ComboBox<Bairro> CBBairro;

    @FXML
    private Button btnCadastrar;

    @FXML
    private AnchorPane root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCBBairro();
    }

    @FXML
    void cadastrar(ActionEvent event) {
        Cliente cliente = new Cliente();

        if (!validaCliente()) return;

        cliente.setNome(TFNome.getText());
        cliente.setContato(TFContato.getText());
        cliente.setBairro(CBBairro.getValue());
        cliente.setEndereco(TAEndereco.getText());

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

    private void initCBBairro() {
        CBBairro.getItems().clear();
        CBBairro.getItems().add(new Bairro("Nenhum"));

        try {
            List<Bairro> bairros = BairroDAO.listarAtivos();

            if (!bairros.isEmpty()) {
                CBBairro.getItems().addAll(bairros);
            }

            CBBairro.getSelectionModel().selectFirst();

        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "bairro");
        }
    }

}
