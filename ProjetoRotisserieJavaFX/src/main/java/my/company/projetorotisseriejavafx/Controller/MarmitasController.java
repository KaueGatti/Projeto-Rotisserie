package my.company.projetorotisseriejavafx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalCadastrarMarmitaController;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalEditMarmitaController;
import my.company.projetorotisseriejavafx.DAO.MarmitaDAO;
import my.company.projetorotisseriejavafx.Objects.Marmita;
import my.company.projetorotisseriejavafx.Util.CssHelper;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;
import my.company.projetorotisseriejavafx.Util.IconHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MarmitasController {

    @FXML
    private Button btnCadastrar;

    @FXML
    private TableView<Marmita> tableMarmitas;
    @FXML
    private TableColumn<Marmita, String> colNome;
    @FXML
    private TableColumn<Marmita, String> colValor;
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
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("formattedValor"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colEditar.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("");

            {
                btnEditar.setMaxWidth(Double.MAX_VALUE);
                btnEditar.getStyleClass().add("BEditar");
                btnEditar.getStyleClass().add("icon-edit");

                btnEditar.setOnAction(event -> {
                    Marmita marmita = getTableView().getItems().get(getIndex());
                    abrirModalEditar(marmita);
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

        refreshTableMarmita();
    }

    public void abrirModalCadastrar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalCadastrarMarmita.fxml"));

            Parent root = loader.load();

            Stage modal = new Stage();
            Scene scene = new Scene(root);

            CssHelper.loadCss(scene);

            modal.setScene(scene);

            ModalCadastrarMarmitaController controller = loader.getController();

            controller.initialize();

            modal.initStyle(StageStyle.UTILITY);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setResizable(false);
            modal.showAndWait();
            refreshTableMarmita();

        } catch (IOException e) {
            System.out.println("Erro ao abrir modal Editar Marmita");
            e.printStackTrace();
        }
    }

    public void abrirModalEditar(Marmita marmita) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalEditMarmita.fxml"));

            Parent root = loader.load();

            Stage modal = new Stage();
            Scene scene = new Scene(root);

            CssHelper.loadCss(scene);

            modal.setScene(scene);

            ModalEditMarmitaController controller = loader.getController();

            controller.setMarmita(marmita);

            modal.initStyle(StageStyle.UTILITY);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setResizable(false);
            modal.showAndWait();
            refreshTableMarmita();

        } catch (IOException e) {
            System.out.println("Erro ao abrir modal Editar Marmita");
            e.printStackTrace();
        }
    }

    private void refreshTableMarmita() {
        tableMarmitas.getItems().clear();

        try {
            List<Marmita> marmitas = MarmitaDAO.read();

            if (!marmitas.isEmpty()) {
                tableMarmitas.getItems().addAll(marmitas);
            }
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "marmita");
        }

    }

}
