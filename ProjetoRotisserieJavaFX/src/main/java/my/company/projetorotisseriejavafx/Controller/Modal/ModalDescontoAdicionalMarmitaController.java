package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.Objects.DescontoAdicional;
import my.company.projetorotisseriejavafx.Util.CurrencyFieldUtil;

public class ModalDescontoAdicionalMarmitaController {

    private DescontoAdicional descontoAdicional;

    @FXML
    private ComboBox<String> CBTipo;

    @FXML
    private Label LInfo;

    @FXML
    private TextArea TAMotivo;

    @FXML
    private TextField TFValor;

    @FXML
    private Button btnDefinir;

    @FXML
    private Button btnRemover;

    @FXML
    private AnchorPane root;

    public void initialize(DescontoAdicional descontoAdicional) {
        this.descontoAdicional = descontoAdicional;
        loadCBTipo();
        initCampos();
    }

    @FXML
    void remover(ActionEvent event) {
        this.descontoAdicional = null;
        fecharModal();
    }

    @FXML
    void definir(ActionEvent event) {
        DescontoAdicional descontoAdicional = new DescontoAdicional();

        if (!validaCampos()) return;

        if (!validaDescontoAdicional()) return;

        descontoAdicional.setTipo(CBTipo.getValue());
        descontoAdicional.setValor(CurrencyFieldUtil.getValue(TFValor));
        descontoAdicional.setObservacao(TAMotivo.getText());

        this.descontoAdicional = descontoAdicional;

        fecharModal();
    }

    public DescontoAdicional getDescontoAdicional() {
        return descontoAdicional;
    }

    public boolean validaCampos() {
        if (TFValor.getText().trim().isEmpty()) {
            LInfo.setText("Valor não pode ser vazio");
            return false;
        }

        return true;
    }

    public boolean validaDescontoAdicional() {
        if (CurrencyFieldUtil.getValue(TFValor) <= 0) {
            LInfo.setText("O valo deve ser maior que 00,00");
            return false;
        }
        return true;
    }

    public void initCampos() {
        CurrencyFieldUtil.configureField(TFValor, false, false, false);

        if (descontoAdicional != null) {
            CBTipo.getSelectionModel().select(descontoAdicional.getTipo());
            TFValor.setText(descontoAdicional.getValor().toString());
            TAMotivo.setText(descontoAdicional.getObservacao());
        }
    }

    public void loadCBTipo() {
        CBTipo.getItems().clear();
        CBTipo.getItems().addAll("Desconto", "Adicional");
        CBTipo.getSelectionModel().selectFirst();
    }

    public void fecharModal() {
        Stage modal =  (Stage) root.getScene().getWindow();
        modal.close();
    }

}
