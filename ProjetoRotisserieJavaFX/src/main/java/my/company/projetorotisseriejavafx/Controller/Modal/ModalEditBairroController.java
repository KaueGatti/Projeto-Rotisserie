package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.DAO.BairroDAO;
import my.company.projetorotisseriejavafx.Objects.Bairro;
import my.company.projetorotisseriejavafx.Util.CurrencyFieldUtil;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

import java.sql.SQLException;

public class ModalEditBairroController {

    private Bairro bairro;

    @FXML
    private TextField TFNome;

    @FXML
    private TextField TFValorEntrega;

    @FXML
    private ComboBox<String> CBStatus;

    @FXML
    private Label LInfo;

    @FXML
    private Button btnSalvar;

    @FXML
    private AnchorPane root;

    @FXML
    public void initialize(Bairro bairro) {
        loadCBStatus();

        this.bairro = bairro;
        TFNome.setText(bairro.getNome());
        TFValorEntrega.setText(String.valueOf(bairro.getValorEntrega()));
        CBStatus.getSelectionModel().select(bairro.getStatus());
    }

    @FXML
    void salvar(ActionEvent event) {

        if (!validaCampos()) return;

        bairro.setValorEntrega(CurrencyFieldUtil.getValue(TFValorEntrega));
        bairro.setStatus(CBStatus.getValue());

        if (!validaBairro(bairro)) return;

        try {
            BairroDAO.atualizar(bairro);
            LInfo.setText("Bairro atualizado com sucesso!");
            fecharModal();
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "bairro");
        }
    }

    private boolean validaBairro(Bairro bairro) {

        if (bairro.getValorEntrega() <= 0D) {
            LInfo.setText("Defina um valor maior que R$0,00");
            return false;
        }

        return true;
    }

    private boolean validaCampos() {

        if (TFValorEntrega.getText().trim().isEmpty()) {
            LInfo.setText("Valor não pode estar vazio");
            return false;
        }

        return true;
    }

    public void fecharModal() {
        Stage modal =  (Stage) root.getScene().getWindow();
        modal.close();
    }

    private void initCampos() {
        CurrencyFieldUtil.configureField(TFValorEntrega, false, false, false);
    }

    public void loadCBStatus() {
        CBStatus.getItems().clear();
        CBStatus.getItems().addAll("ATIVO", "INATIVO");
    }

}
