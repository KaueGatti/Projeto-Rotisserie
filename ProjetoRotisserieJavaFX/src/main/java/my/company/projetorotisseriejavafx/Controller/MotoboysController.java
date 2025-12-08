package my.company.projetorotisseriejavafx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalEditMotoboyController;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalImprimirDiariaController;
import my.company.projetorotisseriejavafx.DAO.MotoboyDAO;
import my.company.projetorotisseriejavafx.Objects.Motoboy;
import my.company.projetorotisseriejavafx.Util.CssHelper;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;
import my.company.projetorotisseriejavafx.Util.IconHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MotoboysController {

    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnImprimirDiaria;

    @FXML
    private TableColumn<Motoboy, Void> colEditar;
    @FXML
    private TableColumn<Motoboy, String> colNome;
    @FXML
    private TableColumn<Motoboy, String> colDiaria;
    @FXML
    private TableColumn<Motoboy, String> colStatus;

    @FXML
    private TableView<Motoboy> tableMotoboys;

    public void initialize() {
        initTableMotoboy();
    }

    @FXML
    void cadastrar(ActionEvent event) {
        abrirModalCadastrar();
    }

    @FXML
    void imprimirDiaria(ActionEvent event) {
        abrirModalImprimirDiaria();
    }

    public void abrirModalCadastrar() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalCadastrarMotoboy.fxml"));

            Parent root = loader.load();

            Stage modal = new Stage();
            Scene scene = new Scene(root);

            CssHelper.loadCss(scene);

            modal.setScene(scene);

            modal.initModality(Modality.APPLICATION_MODAL);
            modal.initStyle(StageStyle.UTILITY);
            modal.showAndWait();
            updateTableMotoboy();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abrirModalEditar(Motoboy motoboy) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalEditMotoboy.fxml"));

            Parent root = loader.load();

            Stage modal = new Stage();
            Scene scene = new Scene(root);

            CssHelper.loadCss(scene);

            modal.setScene(scene);

            ModalEditMotoboyController controller = loader.getController();

            controller.initialize(motoboy);

            modal.initModality(Modality.APPLICATION_MODAL);
            modal.initStyle(StageStyle.UTILITY);
            modal.showAndWait();
            updateTableMotoboy();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abrirModalImprimirDiaria() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalImprimirDiaria.fxml"));

            Parent root = loader.load();

            Stage modal = new Stage();
            Scene scene = new Scene(root);

            CssHelper.loadCss(scene);

            modal.setScene(scene);

            ModalImprimirDiariaController controller = loader.getController();

            if (tableMotoboys.getSelectionModel().getSelectedIndex() != -1) {
                controller.initialize(tableMotoboys.getSelectionModel().getSelectedItem());
            } else {
                controller.initialize(null);
            }

            modal.initModality(Modality.APPLICATION_MODAL);
            modal.initStyle(StageStyle.UTILITY);
            modal.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTableMotoboy() {
        tableMotoboys.getItems().clear();

        try {
            List<Motoboy> motoboys = MotoboyDAO.listarTodos();

            if (!motoboys.isEmpty()) {
                tableMotoboys.getItems().addAll(motoboys);
                btnImprimirDiaria.setDisable(false);
                return;
            }

            btnImprimirDiaria.setDisable(true);
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "motoboy");
        }
    }

    public void initTableMotoboy() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDiaria.setCellValueFactory(new PropertyValueFactory<>("formattedValorDiaria"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colEditar.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("");

            {
                btnEditar.setMaxWidth(Double.MAX_VALUE);
                btnEditar.getStyleClass().add("BEditar");
                btnEditar.getStyleClass().add("icon-edit");

                btnEditar.setOnAction(event -> {
                    Motoboy motoboy = getTableView().getItems().get(getIndex());
                    abrirModalEditar(motoboy);
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

        updateTableMotoboy();
    }

}
