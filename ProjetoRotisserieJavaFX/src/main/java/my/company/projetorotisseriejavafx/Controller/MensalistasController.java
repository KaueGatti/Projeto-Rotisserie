package my.company.projetorotisseriejavafx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalEditMensalistaController;
import my.company.projetorotisseriejavafx.DAO.MensalistaDAO;
import my.company.projetorotisseriejavafx.Objects.Mensalista;
import my.company.projetorotisseriejavafx.Objects.Produto;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;
import my.company.projetorotisseriejavafx.Util.IconHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MensalistasController {

    @FXML
    private Button btnCadastrar;
    @FXML
    private TableView<Mensalista> tableMensalistas;
    @FXML
    private TableColumn<Mensalista, String> colNome;
    @FXML
    private TableColumn<Mensalista, Double> colConta;
    @FXML
    private TableColumn<Mensalista, String> colStatus;
    @FXML
    private TableColumn<Mensalista, Void> colEditar;

    @FXML
    public void initialize() {
        initTableMensalistas();
    }

    @FXML
    void cadastrar(ActionEvent event) {
        abrirModalCadastrar();
    }

    private void initTableMensalistas() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colConta.setCellValueFactory(new PropertyValueFactory<>("formattedConta"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colEditar.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("");

            {
                btnEditar.setMaxWidth(Double.MAX_VALUE);
                btnEditar.getStyleClass().add("BEditar");
                btnEditar.getStyleClass().add("icon-edit");

                btnEditar.setOnAction(event -> {
                    Mensalista mensalista = getTableView().getItems().get(getIndex());
                    abrirModalEditar(mensalista);
                });

                HBox.setHgrow(btnEditar, Priority.ALWAYS);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox wrapper = new HBox(btnEditar);
                    wrapper.setSpacing(0);
                    wrapper.setPadding(new Insets(0));
                    wrapper.setFillHeight(true);

                    wrapper.setMaxWidth(Double.MAX_VALUE);

                    IconHelper.applyIcon(btnEditar);

                    setGraphic(wrapper);
                }
            }
        });

        updateTableMensalista();
    }

    public void abrirModalCadastrar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalCadastrarMensalista.fxml"));

            Stage modal = new Stage();
            modal.setScene(loader.load());

            modal.initModality(Modality.APPLICATION_MODAL);
            modal.initStyle(StageStyle.UTILITY);
            modal.showAndWait();
            updateTableMensalista();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abrirModalEditar(Mensalista mensalista) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalEditMensalista.fxml"));

            Stage modal = new Stage();
            modal.setScene(loader.load());

            ModalEditMensalistaController controller = loader.getController();

            controller.initialize(mensalista);

            modal.initModality(Modality.APPLICATION_MODAL);
            modal.initStyle(StageStyle.UTILITY);
            modal.showAndWait();
            updateTableMensalista();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTableMensalista() {
        tableMensalistas.getItems().clear();

        try {
            List<Mensalista> mensalistas = MensalistaDAO.read();
            if (!mensalistas.isEmpty()) {
                tableMensalistas.getItems().addAll(mensalistas);
            }
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "mensalista");
        }
    }
}
