package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.DAO.MarmitaDAO;
import my.company.projetorotisseriejavafx.Objects.Marmita;
import my.company.projetorotisseriejavafx.Util.CurrencyFieldUtil;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

import java.sql.SQLException;
import java.sql.SQLOutput;

public class ModalEditMarmitaController {

    private Marmita marmita;

    @FXML
    private AnchorPane root;
    @FXML
    private ComboBox<Integer> CBGuarnicoes;
    @FXML
    private ComboBox<Integer> CBMisturas;
    @FXML
    private ComboBox<String> CBStatus;
    @FXML
    private TextField TFNome;
    @FXML
    private TextField TFValor;
    @FXML
    private Label LInfo;
    @FXML
    private Button btnSalvar;

    @FXML
    void salvar(ActionEvent event) {
        if (!validaCampos()) return;

        marmita.setMaxMistura(CBMisturas.getValue());
        marmita.setMaxGuarnicao(CBGuarnicoes.getValue());
        marmita.setValor(CurrencyFieldUtil.getValue(TFValor));
        marmita.setStatus(CBStatus.getValue());

        if (!validaMarmita(marmita)) return;

        try {
            MarmitaDAO.atualizar(marmita);
            LInfo.setText("Marmita atualizada com sucesso");
            fecharModal();
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "marmita");
        }
    }

    private void loadComboBoxs() {
        CBMisturas.getItems().addAll(1, 2, 3, 4);
        CBMisturas.getSelectionModel().select((Integer) marmita.getMaxMistura());

        CBGuarnicoes.getItems().addAll(1, 2, 3, 4);
        CBGuarnicoes.getSelectionModel().select((Integer) marmita.getMaxGuarnicao());

        CBStatus.getItems().addAll("ATIVO", "INATIVO");
        CBStatus.getSelectionModel().select(marmita.getStatus());
    }

    private void loadTextFields() {
        TFNome.setText(marmita.getNome());
        TFValor.setText(String.valueOf(marmita.getValor()));
    }

    public void setMarmita(Marmita marmita) {
        this.marmita = marmita;
        loadTextFields();
        loadComboBoxs();
        initCampos();
    }

    private boolean validaMarmita(Marmita marmita) {

        if (marmita.getValor() <= 0D) {
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

    private void initCampos() {
        CurrencyFieldUtil.configureField(TFValor, false, false, false);
    }

    public void fecharModal() {
        Stage modal =  (Stage) root.getScene().getWindow();
        modal.close();
    }
}
