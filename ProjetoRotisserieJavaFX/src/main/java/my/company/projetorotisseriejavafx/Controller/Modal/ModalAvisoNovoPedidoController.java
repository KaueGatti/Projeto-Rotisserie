package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.Objects.Cardapio;
import my.company.projetorotisseriejavafx.Objects.Marmita;

public class ModalAvisoNovoPedidoController {

    private boolean cadastrar = false;

    @FXML
    private Label LIcon;

    @FXML
    private Label LMensagem;

    @FXML
    private Button btnCadastrar;

    @FXML
    private AnchorPane root;

    public void initialize(String msg, Object tipo) {
        loadIcon();
        LMensagem.setText(msg);
        if (!(tipo instanceof Marmita) && !(tipo instanceof Cardapio)) {
            btnCadastrar.setVisible(false);
        }
    }

    @FXML
    void cadastrar(ActionEvent event) {
        cadastrar = true;
        fecharModal();
    }

    public void loadIcon() {
        Image pngWarning = new Image(getClass().getResourceAsStream("/Images/Warning.png"));
        ImageView ivWarning = new ImageView(pngWarning);
        LIcon.setGraphic(ivWarning);
    }

    public void fecharModal() {
        Stage modal =  (Stage) root.getScene().getWindow();
        modal.close();
    }

    public boolean isCadastrar() {
        return cadastrar;
    }

}
