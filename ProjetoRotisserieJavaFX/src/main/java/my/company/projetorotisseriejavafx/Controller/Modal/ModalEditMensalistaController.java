package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.DAO.MensalistaDAO;
import my.company.projetorotisseriejavafx.Objects.Mensalista;
import my.company.projetorotisseriejavafx.Util.CurrencyFieldUtil;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

import java.sql.SQLException;

public class ModalEditMensalistaController {

    private Mensalista mensalista;

    @FXML
    private Scene scene;
    @FXML
    private ComboBox<String> CBStatus;
    @FXML
    private TextField TFNome;
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
        
        mensalista.setStatus(CBStatus.getValue());

        try {
            MensalistaDAO.update(mensalista);
            LInfo.setText("Mensalista atualizada com sucesso");
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
    }

    public void fecharModal() {
        Stage modal = (Stage) scene.getWindow();
        modal.close();
    }
}
