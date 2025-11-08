package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.Util.CurrencyFieldUtil;

import java.io.IOException;
import java.time.LocalDate;

public class ModalPagamentoController {

    private String pagamento = null;

    private LocalDate vencimento = null;

    private double valorTotal;

    @FXML
    private Scene scene;
    @FXML
    private ComboBox<String> CBPagamento;
    @FXML
    private DatePicker DPVencimento;
    @FXML
    private Label LInfo;
    @FXML
    private TextField TFValorPago;
    @FXML
    private Button btnFinalizar;

    public void initialize(double valorTotal) {
        this.valorTotal = valorTotal;
        initCampos();
        loadCBPagamento();
    }

    @FXML
    void finalizar(ActionEvent event) {
        if (CBPagamento.getValue().equals("Dinheiro")) {
            if (!validaValorPago()) return;

            double valorTroco = CurrencyFieldUtil.getValue(TFValorPago) - valorTotal;
            abrirModalTroco(valorTroco);

        }

        if (CBPagamento.getValue().equals("Pagar depois")) {
            if (!validaVencimento()) return;

            vencimento = DPVencimento.getValue();
        }

        pagamento = CBPagamento.getValue();

        fecharModal();
    }

    private void fecharModal() {
        Stage modal = (Stage) scene.getWindow();
        modal.close();
    }

    public String getPagamento() {
        return pagamento;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void loadCBPagamento() {
        CBPagamento.getItems().clear();
        CBPagamento.getItems().setAll("Dinheiro", "Débito", "Crédito", "Pix", "Pagar depois");
        CBPagamento.getSelectionModel().selectFirst();

        CBPagamento.setOnAction(event -> {
            if (CBPagamento.getValue().equals("Dinheiro")) {
                DPVencimento.setDisable(true);
                TFValorPago.setDisable(false);
                return;
            }

            if (CBPagamento.getValue().equals("Pagar depois")) {
                TFValorPago.setDisable(true);
                TFValorPago.setText("");
                LInfo.setText("");
                DPVencimento.setDisable(false);
                return;
            }

            TFValorPago.setText("");
            LInfo.setText("");
            TFValorPago.setDisable(true);
            DPVencimento.setDisable(true);
        });
    }

    public void initCampos() {
        CurrencyFieldUtil.configureField(TFValorPago, false, false, false);

        TFValorPago.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validaValorPago();
            }
        });
        DPVencimento.setEditable(false);
    }

    public boolean validaValorPago() {
        if (TFValorPago.getText().trim().isEmpty()) {
            LInfo.setText("Valor pago não pode ser vázio");
            return false;
        }

        double valorPago = CurrencyFieldUtil.getValue(TFValorPago);

        if (valorPago < valorTotal) {
            String valorRestante = String.format("R$ %.2f", valorTotal - valorPago);
            LInfo.setText("Valor insuficiente! Faltam " + valorRestante);
            return false;
        }

        LInfo.setText("");
        return true;
    }

    public boolean validaVencimento() {
        if (DPVencimento.getValue() == null) {
            LInfo.setText("Vencimento não pode ser vázio");
            return false;
        }

        if (DPVencimento.getValue().isBefore(LocalDate.now())) {
            LInfo.setText("A data de vencimento não pode ser anterior a data atual");
            return false;
        }

        return true;
    }

    public void abrirModalTroco(double valorTroco) {
        try {
            Stage modal = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalTroco.fxml"));
            modal.setScene(fxmlLoader.load());

            ModalTrocoController controller = fxmlLoader.getController();

            controller.initialize(valorTroco);

            modal.setResizable(false);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.initStyle(StageStyle.UTILITY);
            modal.show();

        } catch (IOException e) {
            System.out.println("Erro ao abrir modal troco" + e);
            e.printStackTrace();
        }
    }
}
