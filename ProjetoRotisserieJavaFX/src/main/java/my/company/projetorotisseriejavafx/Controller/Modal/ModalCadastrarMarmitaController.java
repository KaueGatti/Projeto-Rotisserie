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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModalCadastrarMarmitaController {

    @FXML
    private AnchorPane root;
    @FXML
    private TextField TFNome;
    @FXML
    private ComboBox<Integer> CBMisturas;
    @FXML
    private ComboBox<Integer> CBGuarnicoes;
    @FXML
    private TextField TFValor;
    @FXML
    private Label LInfo;
    @FXML
    private Button btnCadastrar;

    public void initialize() {
        initCampos();
        loadCBMisturas();
        loadCBGuarnicoes();
    }

    @FXML
    void cadastrar(ActionEvent event) {
        Marmita marmita = new Marmita();

        if (!validaCampos()) return;

        marmita.setNome(TFNome.getText());
        marmita.setMaxMistura(CBMisturas.getValue());
        marmita.setMaxGuarnicao(CBGuarnicoes.getValue());
        marmita.setValor(CurrencyFieldUtil.getValue(TFValor));

        if (!validaMarmita(marmita)) return;

        try {
            MarmitaDAO.criar(marmita);
            LInfo.setText("Marmita Cadastrada com sucesso!");
            fecharModal();
        } catch (Exception e) {
            DatabaseExceptionHandler.handleException(e, "marmita");
        }

    }

    public void loadCBMisturas() {
        List<Integer> values = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        CBMisturas.getItems().clear();
        CBMisturas.getItems().addAll(values);
        CBMisturas.getSelectionModel().selectFirst();
    }

    public void loadCBGuarnicoes() {
        List<Integer> values = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        CBGuarnicoes.getItems().clear();
        CBGuarnicoes.getItems().addAll(values);
        CBGuarnicoes.getSelectionModel().selectFirst();
    }

    private boolean validaMarmita(Marmita marmita) {
        if (marmita.getNome() == null || marmita.getNome().trim().isEmpty()) {
            LInfo.setText("Nome inválido");
            return false;
        }

        if (marmita.getValor() <= 0D) {
            LInfo.setText("Defina um valor maior que R$0,00");
            return false;
        }

        return true;
    }

    private boolean validaCampos() {
        if (TFNome.getText().trim().isEmpty()) {
            LInfo.setText("Nome não pode estar vazio");
            return false;
        }

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
}
