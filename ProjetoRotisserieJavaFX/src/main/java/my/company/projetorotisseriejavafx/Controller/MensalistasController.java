package my.company.projetorotisseriejavafx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalEditProdutoController;
import my.company.projetorotisseriejavafx.DAO.MensalistaDAO;
import my.company.projetorotisseriejavafx.DAO.ProdutoDAO;
import my.company.projetorotisseriejavafx.Objects.Mensalista;
import my.company.projetorotisseriejavafx.Objects.Produto;

import java.io.IOException;
import java.util.List;

public class MensalistasController {

    @FXML
    private Button btnCadastrar;

    @FXML
    private TableView<Mensalista> tableMensalistas;
    @FXML
    private TableColumn<Mensalista, String> colNome;
    @FXML
    private TableColumn<Mensalista, String> colStatus;
    @FXML
    private TableColumn<Mensalista, Void> colPedidos;
    @FXML
    private TableColumn<Mensalista, Void> colEditar;


    @FXML
    private void initialize() {
        initTableMensalistas();
    }

    @FXML
    void cadastrar(ActionEvent event) {

    }

    private void initTableMensalistas() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        List<Mensalista> mensalistas = MensalistaDAO.read();

        if (!mensalistas.isEmpty()) {
            for (Mensalista mensalista : mensalistas) {
                tableMensalistas.getItems().add(mensalista);
            }
        }
    }
}
