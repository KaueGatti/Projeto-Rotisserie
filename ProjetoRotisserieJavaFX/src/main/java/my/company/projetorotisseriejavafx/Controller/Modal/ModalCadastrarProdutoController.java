package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.DAO.ProdutoDAO;
import my.company.projetorotisseriejavafx.Objects.Produto;
import my.company.projetorotisseriejavafx.Util.CurrencyFieldUtil;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

public class ModalCadastrarProdutoController {

    @FXML
    private AnchorPane root;
    @FXML
    private TextField TFNome;
    @FXML
    private TextField TFValor;
    @FXML
    private Label LInfo;
    @FXML
    private Button btnCadastrar;

    public void initialize() {
        initCampos();
    }

    @FXML
    void cadastrar(ActionEvent event) {
        Produto produto = new Produto();

        if (!validaCampos()) return;

        produto.setNome(TFNome.getText());
        produto.setValor(CurrencyFieldUtil.getValue(TFValor));

        if (!validaProduto(produto)) return;

        try {
            ProdutoDAO.create(produto);
            LInfo.setText("Produto Cadastrado com sucesso!");
            fecharModal();
        } catch (Exception e) {
            DatabaseExceptionHandler.handleException(e, "produto");
        }

    }

    private boolean validaProduto(Produto produto) {
        if (produto.getNome() == null || produto.getNome().isEmpty()) {
            LInfo.setText("Nome inválido");
            return false;
        }

        if (produto.getValor() <= 0D) {
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
