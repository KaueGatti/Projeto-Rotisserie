package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.Objects.Pagamento;
import my.company.projetorotisseriejavafx.Util.CurrencyFieldUtil;

import java.time.LocalDate;

public class ModalAdicionarPagamentoController {

    ModalPagamentosController controller;

    @FXML
    private ComboBox<String> CBPagamento;

    @FXML
    private DatePicker DPData;

    @FXML
    private Label LInfo;

    @FXML
    private TextArea TAObservacao;

    @FXML
    private TextField TFValor;

    @FXML
    private Button btnAdicionar;

    @FXML
    private Scene scene;

    public void initialize(ModalPagamentosController controller) {
        this.controller = controller;
        initCampos();
    }

    @FXML
    void adicionar(ActionEvent event) {
        if (!validaValor()) return;

        if (!validaData()) return;

        Pagamento pagamento = new Pagamento();

        pagamento.setTipoPagamento(CBPagamento.getValue());
        pagamento.setValor(CurrencyFieldUtil.getValue(TFValor));
        pagamento.setData(LocalDate.now());
        pagamento.setObservacao(TAObservacao.getText());

        controller.adidionarPagamento(pagamento);
        fecharModal();
    }

    public void initCampos() {
        CurrencyFieldUtil.configureField(TFValor, false, false, false);

        DPData.setEditable(false);

        String[] tiposPagamentos = {"Dinheiro", "Débito", "Crédito"};
        CBPagamento.getItems().addAll(tiposPagamentos);
        CBPagamento.getSelectionModel().selectFirst();
    }

    public boolean validaValor() {
        if (TFValor.getText().trim().isEmpty()) {
            LInfo.setText("Valor não pode ser vazio");
            return false;
        }

        if (CurrencyFieldUtil.getValue(TFValor) < 0) {
            LInfo.setText("O valor deve ser maior que 0,00");
            return false;
        }

        LInfo.setText("");
        return true;
    }

    public boolean validaData() {
        if (DPData.getValue() == null) {
            LInfo.setText("Data inválida");
            return false;
        }

        LInfo.setText("");
        return true;
    }

    public void fecharModal() {
        Stage modal = (Stage) scene.getWindow();
        modal.close();
    }

}
