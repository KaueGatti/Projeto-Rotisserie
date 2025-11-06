package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class ModalTrocoController {

    @FXML
    private Label LTroco;
    @FXML
    private Label LInfo;

    @FXML
    private Scene scene;

    public void initialize(double valorTroco) {
        String valorTrocoFormatado = String.format("R$ %.2f", valorTroco).replace(".", ",");
        if (valorTroco == 0) {
            LTroco.setText("R$ 0,00");
            LInfo.setText("Pagamento exato! Sem troco");
            return;
        }

        LTroco.setText(valorTrocoFormatado);
    }

}
