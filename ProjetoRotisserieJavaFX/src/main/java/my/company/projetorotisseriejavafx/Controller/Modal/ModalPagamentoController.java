package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.Objects.Pedido;
import my.company.projetorotisseriejavafx.Util.CssHelper;
import my.company.projetorotisseriejavafx.Util.CurrencyFieldUtil;

import java.io.IOException;
import java.time.LocalDate;

public class ModalPagamentoController {

    private double valorPedido;

    private String pagamento = null;

    private LocalDate vencimento = null;

    @FXML
    private AnchorPane root;

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

    public void initialize(Double valorPedido) {
        this.valorPedido = valorPedido;
        initCampos();
        loadCBPagamento();
    }

    @FXML
    void finalizar(ActionEvent event) {

        if (CBPagamento.getValue().equals("Dinheiro")) {
            if (!validaValorPago()) return;

            double valorTroco = CurrencyFieldUtil.getValue(TFValorPago) - valorPedido;
            abrirModalTroco(valorTroco);

        }

        if (CBPagamento.getValue().equals("Pagar depois")) {
            if (!validaVencimento()) return;

            vencimento = DPVencimento.getValue();
        }

        if (CBPagamento.getValue().equals("Débito")) {
            pagamento = "Debito";
            fecharModal();
            return;
        }

        if (CBPagamento.getValue().equals("Crédito")) {
            pagamento = "Credito";
            fecharModal();
            return;
        }

        pagamento = CBPagamento.getValue();

        fecharModal();
    }

    private void fecharModal() {
        Stage modal = (Stage) root.getScene().getWindow();
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

        if (valorPago < valorPedido) {
            String valorRestante = String.format("R$ %.2f", valorPedido - valorPago);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalTroco.fxml"));

            Parent root = loader.load();

            Stage modal = new Stage();
            Scene scene = new Scene(root);

            CssHelper.loadCss(scene);

            modal.setScene(scene);

            ModalTrocoController controller = loader.getController();

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
