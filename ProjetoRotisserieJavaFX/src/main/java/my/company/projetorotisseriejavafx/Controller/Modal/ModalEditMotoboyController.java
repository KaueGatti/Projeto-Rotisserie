package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.DAO.MotoboyDAO;
import my.company.projetorotisseriejavafx.Objects.Motoboy;
import my.company.projetorotisseriejavafx.Util.CurrencyFieldUtil;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

public class ModalEditMotoboyController {

    private Motoboy motoboy;

    @FXML
    private AnchorPane root;

    @FXML
    private ComboBox<String> CBStatus;

    @FXML
    private TextField TFNome;

    @FXML
    private TextField TFValor;

    @FXML
    private Label LInfo;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnSalvar;

    public void initialize(Motoboy motoboy) {
        initCampos();
        loadCBStatus();

        this.motoboy = motoboy;
        TFNome.setText(motoboy.getNome());
        TFValor.setText(String.valueOf(motoboy.getValorDiaria()));
        CBStatus.getSelectionModel().select(motoboy.getStatus());
    }

    @FXML
    void salvar(ActionEvent event) {
        if (!validaCampos()) return;

        motoboy.setValorDiaria(CurrencyFieldUtil.getValue(TFValor));
        motoboy.setStatus(CBStatus.getValue());

        if (!validaMotoboy(motoboy)) return;

        try {
            MotoboyDAO.update(motoboy);
            LInfo.setText("Motoboy atualizado com sucesso!");
            fecharModal();
        } catch (Exception e) {
            DatabaseExceptionHandler.handleException(e, "motoboy");
        }

    }

    private boolean validaMotoboy(Motoboy motoboy) {

        if (motoboy.getValorDiaria() <= 0D) {
            LInfo.setText("Defina um valor maior que R$0,00");
            return false;
        }

        return true;
    }

    private boolean validaCampos() {

        if (TFValor.getText().trim().isEmpty()) {
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
        CurrencyFieldUtil.configureField(TFValor, false, false, false);
    }

    public void loadCBStatus() {
        CBStatus.getItems().clear();
        CBStatus.getItems().addAll("ATIVO", "INATIVO");
    }

}
