package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.DAO.MensalistaDAO;
import my.company.projetorotisseriejavafx.Objects.Mensalista;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

import java.sql.SQLException;

public class ModalEditMensalistaController {

    private Mensalista mensalista;

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

    public void initialize(Mensalista mensalista) {
        this.mensalista = mensalista;
        loadTextFields();
        loadComboBoxs();
    }

    @FXML
    void salvar(ActionEvent event) {

        if (!validaMensalista()) return;

        mensalista.setContato(TFContato.getText());
        mensalista.setStatus(CBStatus.getValue());

        try {
            MensalistaDAO.update(mensalista);
            fecharModal();
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "mensalista");
        }
    }

    private void loadComboBoxs() {
        CBStatus.getItems().addAll("ATIVO", "INATIVO");
        CBStatus.getSelectionModel().select(mensalista.getStatus());
    }

    private void loadTextFields() {
        TFNome.setText(mensalista.getNome());
        TFContato.setText(mensalista.getContato());
    }

    public void fecharModal() {
        Stage modal =  (Stage) root.getScene().getWindow();
        modal.close();
    }

    private boolean validaMensalista() {
        if (TFContato.getText().trim().isEmpty()) {
            LInfo.setText("Contato não pode estar vázio");
            return false;
        }

        return true;
    }
}
