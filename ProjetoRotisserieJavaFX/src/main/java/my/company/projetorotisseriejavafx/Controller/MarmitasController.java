package my.company.projetorotisseriejavafx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalEditMarmitaController;
import my.company.projetorotisseriejavafx.DAO.MarmitaDAO;
import my.company.projetorotisseriejavafx.Objects.Marmita;

import java.io.IOException;
import java.util.List;

public class MarmitasController {

    @FXML
    private ComboBox<String> CBStatus;
    @FXML
    private TextField TFDescricao;
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnPesquisar;

    @FXML
    private TableView<Marmita> tableMarmitas;
    @FXML
    private TableColumn<Marmita, String> colDescricao;
    @FXML
    private TableColumn<Marmita, String> colStatus;
    @FXML
    private TableColumn<Marmita, Void> colEditar;

    @FXML
    private void initialize() {
        initTableMarmita();
        loadStatus();
    }

    @FXML
    void cadastrar(ActionEvent event) {

    }

    @FXML
    void pesquisar(ActionEvent event) {

    }

    private void initTableMarmita() {
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colEditar.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");

            {
                btnEditar.setOnAction(event -> {
                    Marmita marmita = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalEditMarmita.fxml"));

                        Stage modal = new Stage();
                        modal.setScene(loader.load());

                        ModalEditMarmitaController controller = loader.getController();

                        controller.setMarmita(marmita);

                        modal.setOnCloseRequest(windowEvent -> {
                            windowEvent.consume();
                        });

                        modal.initStyle(StageStyle.TRANSPARENT);
                        modal.setResizable(false);
                        modal.showAndWait();

                    } catch (IOException e) {
                        System.out.println("Erro ao abrir modal Editar Marmita");
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

        List<Marmita> marmitas = MarmitaDAO.read();

        if (!marmitas.isEmpty()) {
            for (Marmita marmita : marmitas) {
                tableMarmitas.getItems().add(marmita);
            }
        }
    }

    private void loadStatus() {
        CBStatus.getItems().addAll("Todos", "ATIVO", "INATIVO");
        CBStatus.getSelectionModel().selectFirst();
    }
}
