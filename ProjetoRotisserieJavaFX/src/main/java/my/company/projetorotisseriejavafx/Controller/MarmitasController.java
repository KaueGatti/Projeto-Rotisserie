package my.company.projetorotisseriejavafx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalCadastrarMarmitaController;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalEditMarmitaController;
import my.company.projetorotisseriejavafx.DAO.MarmitaDAO;
import my.company.projetorotisseriejavafx.Objects.Marmita;

import java.io.IOException;
import java.util.List;

public class MarmitasController {

    @FXML
    private Button btnCadastrar;

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
    }

    @FXML
    void cadastrar(ActionEvent event) {
        abrirModalCadastrar();
    }

    private void initTableMarmita() {
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colEditar.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");

            {
                btnEditar.setOnAction(event -> {
                    Marmita marmita = getTableView().getItems().get(getIndex());
                    abrirModalEditar(marmita);
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

    public void abrirModalCadastrar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalCadastrarMarmita.fxml"));

            Stage modal = new Stage();
            modal.setScene(loader.load());

            ModalCadastrarMarmitaController controller = loader.getController();

            controller.initialize();

            modal.initStyle(StageStyle.UTILITY);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setResizable(false);
            modal.showAndWait();
            updateTableMarmitas();

        } catch (IOException e) {
            System.out.println("Erro ao abrir modal Editar Marmita");
            e.printStackTrace();
        }
    }

    public void abrirModalEditar(Marmita marmita) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalEditMarmita.fxml"));

            Stage modal = new Stage();
            modal.setScene(loader.load());

            ModalEditMarmitaController controller = loader.getController();

            controller.setMarmita(marmita);

            modal.initStyle(StageStyle.UTILITY);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setResizable(false);
            modal.showAndWait();
            updateTableMarmitas();

        } catch (IOException e) {
            System.out.println("Erro ao abrir modal Editar Marmita");
            e.printStackTrace();
        }
    }

    private void updateTableMarmitas() {
        tableMarmitas.getItems().clear();

        List<Marmita> marmitas = MarmitaDAO.read();

        if (!marmitas.isEmpty()) {
            for (Marmita marmita : marmitas) {
                tableMarmitas.getItems().add(marmita);
            }
        }
    }

}
