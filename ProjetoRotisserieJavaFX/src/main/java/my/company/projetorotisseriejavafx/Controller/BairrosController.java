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
import my.company.projetorotisseriejavafx.Controller.Modal.ModalCadastrarBairroController;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalEditBairroController;
import my.company.projetorotisseriejavafx.DAO.BairroDAO;
import my.company.projetorotisseriejavafx.Objects.Bairro;
import my.company.projetorotisseriejavafx.Objects.Produto;
import my.company.projetorotisseriejavafx.Util.CssHelper;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;
import my.company.projetorotisseriejavafx.Util.IconHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BairrosController {

    @FXML
    private Button btnCadastrar;

    @FXML
    private TableView<Bairro> tableBairros;
    @FXML
    private TableColumn<Bairro, String> colNome;
    @FXML
    private TableColumn<Bairro, String> colEntrega;
    @FXML
    private TableColumn<Bairro, String> colStatus;
    @FXML
    private TableColumn<Bairro, Void> colEditar;

    @FXML
    private void initialize() {
        initTableBairro();
    }

    @FXML
    void cadastrar(ActionEvent event) {
        abrirModalCadastrar();
    }

    private void initTableBairro() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEntrega.setCellValueFactory(new PropertyValueFactory<>("formattedValorEntrega"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colEditar.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("");

            {
                btnEditar.setMaxWidth(Double.MAX_VALUE);
                btnEditar.getStyleClass().add("BEditar");
                btnEditar.getStyleClass().add("icon-edit");

                btnEditar.setOnAction(event -> {
                    Bairro bairro = getTableView().getItems().get(getIndex());
                    abrirModalEditar(bairro);
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

        updateTableBairro();

    }

    public void abrirModalCadastrar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalCadastrarBairro.fxml"));
            Parent root = loader.load();

            Stage modal = new Stage();
            Scene scene = new Scene(root);

            CssHelper.loadCss(scene);

            modal.setScene(scene);

            ModalCadastrarBairroController controller = loader.getController();

            controller.initialize();

            modal.setResizable(false);
            modal.initStyle(StageStyle.UTILITY);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.showAndWait();
            updateTableBairro();

        } catch (IOException e) {
            System.out.println("Erro em Cadastrar Bairro:");
            e.printStackTrace();
        }
    }

    public void abrirModalEditar(Bairro bairro) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalEditBairro.fxml"));
            Parent root = loader.load();

            Stage modal = new Stage();
            Scene scene = new Scene(root);

            CssHelper.loadCss(scene);

            modal.setScene(scene);

            ModalEditBairroController controller = loader.getController();

            controller.initialize(bairro);

            modal.setResizable(false);
            modal.initStyle(StageStyle.UTILITY);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.showAndWait();
            updateTableBairro();

        } catch (IOException e) {
            System.out.println("Erro em Editar Bairro:");
            e.printStackTrace();
        }
    }

    public void updateTableBairro() {
        tableBairros.getItems().clear();

        try {
            List<Bairro> bairros = BairroDAO.listarTodos();

            if (!bairros.isEmpty()) {
                tableBairros.getItems().addAll(bairros);
            }
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "bairro");
        }
    }
}
