package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.DAO.ProdutoDAO;
import my.company.projetorotisseriejavafx.Objects.Produto;
import my.company.projetorotisseriejavafx.Util.CurrencyFieldUtil;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

public class ModalEditProdutoController {

    private Produto produto;

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

    public void initialize(Produto produto) {
        initCampos();
        loadCBStatus();

        this.produto = produto;
        TFNome.setText(produto.getNome());
        TFValor.setText(String.valueOf(produto.getValor()));
        CBStatus.getSelectionModel().select(produto.getStatus());
    }

    @FXML
    void salvar(ActionEvent event) {
        if (!validaCampos()) return;

        produto.setValor(CurrencyFieldUtil.getValue(TFValor));
        produto.setStatus(CBStatus.getValue());

        if (!validaProduto(produto)) return;

        try {
            ProdutoDAO.atualizar(produto);
            LInfo.setText("Produto atualizado com sucesso!");
            fecharModal();
        } catch (Exception e) {
            DatabaseExceptionHandler.handleException(e, "produto");
        }

    }

    private boolean validaProduto(Produto produto) {

        if (produto.getValor() <= 0D) {
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
