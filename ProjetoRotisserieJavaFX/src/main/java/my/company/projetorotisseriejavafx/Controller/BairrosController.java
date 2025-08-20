package my.company.projetorotisseriejavafx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalEditBairroController;
import my.company.projetorotisseriejavafx.DAO.BairroDAO;
import my.company.projetorotisseriejavafx.Objects.Bairro;

import java.io.IOException;
import java.util.List;

public class BairrosController {

    @FXML
    private ComboBox<String> CBStatus;
    @FXML
    private TextField TFNome;
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnPesquisar;

    @FXML
    private TableView<Bairro> tableBairros;
    @FXML
    private TableColumn<Bairro, String> colNome;
    @FXML
    private TableColumn<Bairro, String> colStatus;
    @FXML
    private TableColumn<Bairro, Void> colEditar;

    @FXML
    private void initialize() {
        initTableBairro();
        loadStatus();
    }

    @FXML
    void cadastrar(ActionEvent event) {

    }

    @FXML
    void pesquisar(ActionEvent event) {

    }

    private void initTableBairro() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colEditar.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");

            {
                btnEditar.setOnAction(event -> {
                    Bairro bairro = getTableView().getItems().get(getIndex());

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalEditBairro.fxml"));
                        Stage modal = new Stage();

                        modal.setScene(loader.load());

                        ModalEditBairroController controller = loader.getController();

                        controller.setBairro(bairro);

                        modal.setOnCloseRequest(windowEvent -> {
                            windowEvent.consume();
                        });

                        modal.setResizable(false);
                        modal.initStyle(StageStyle.UTILITY);
                        modal.showAndWait();

                    } catch (IOException e) {
                        System.out.println("Erro em Editar Bairro:");
                        e.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnEditar);
                }
            }
        });

        List<Bairro> bairros = BairroDAO.read();

        if (!bairros.isEmpty()) {
            for (Bairro bairro : bairros) {
                tableBairros.getItems().add(bairro);
            }
        }
    }

    private void loadStatus() {
        CBStatus.getItems().addAll("Todos", "ATIVO", "INATIVO");
        CBStatus.getSelectionModel().selectFirst();
    }
}
