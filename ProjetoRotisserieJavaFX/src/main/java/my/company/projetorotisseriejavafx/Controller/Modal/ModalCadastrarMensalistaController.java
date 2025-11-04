package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.DAO.MensalistaDAO;

import my.company.projetorotisseriejavafx.Objects.Mensalista;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

import java.sql.SQLException;

public class ModalCadastrarMensalistaController {

    @FXML
    private Label LInfo;

    @FXML
    private TextField TFNome;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Scene scene;

    public void initialize() {
    }

    @FXML
    void cadastrar(ActionEvent event) {
        Mensalista mensalista = new Mensalista();

        if (!validaCampos()) return;

        mensalista.setNome(TFNome.getText());

        if (!validaMensalista(mensalista)) return;

        try {
            MensalistaDAO.create(mensalista);
            LInfo.setText("Mensalista Cadastrado com sucesso!");
            fecharModal();
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "mensalista");
        }

    }

    private boolean validaMensalista(Mensalista mensalista) {
        if (mensalista.getNome() == null || mensalista.getNome().trim().isEmpty()) {
            LInfo.setText("Nome inválido");
            return false;
        }

        return true;
    }

    private boolean validaCampos() {
        if (TFNome.getText().trim().isEmpty()) {
            LInfo.setText("Nome não pode estar vazio");
            return false;
        }

        return true;
    }

    public void fecharModal() {
        Stage modal = (Stage) scene.getWindow();
        modal.close();
    }

}
