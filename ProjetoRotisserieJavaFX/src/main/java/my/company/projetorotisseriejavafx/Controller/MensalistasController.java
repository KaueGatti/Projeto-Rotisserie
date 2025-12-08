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
import my.company.projetorotisseriejavafx.Controller.Modal.ModalEditMensalistaController;
import my.company.projetorotisseriejavafx.DAO.MensalistaDAO;
import my.company.projetorotisseriejavafx.Objects.Mensalista;
import my.company.projetorotisseriejavafx.Objects.Pedido;
import my.company.projetorotisseriejavafx.Objects.Produto;
import my.company.projetorotisseriejavafx.Util.CssHelper;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;
import my.company.projetorotisseriejavafx.Util.IconHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MensalistasController {

    @FXML
    private Button btnCadastrar;
    @FXML
    private TableView<Mensalista> tableMensalistas;
    @FXML
    private TableColumn<Mensalista, String> colNome;
    @FXML
    private TableColumn<Mensalista, String> colContato;
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
        colNome.setCellValueFactory(m -> m.getValue().nomeProperty());
        colContato.setCellValueFactory(m -> m.getValue().contatoProperty());
        colConta.setCellValueFactory(m -> m.getValue().contaProperty().asObject());
        colConta.setCellFactory(column -> new TableCell<Mensalista, Double>() {
            private final NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);

                if (empty || value == null) {
                    setText(null);
                } else {
                    setText(formatter.format(value));
                }
            }
        });
        colStatus.setCellValueFactory(m -> m.getValue().statusProperty());
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

            Parent root = loader.load();

            Stage modal = new Stage();
            Scene scene = new Scene(root);

            CssHelper.loadCss(scene);

            modal.setScene(scene);

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

            Parent root = loader.load();

            Stage modal = new Stage();
            Scene scene = new Scene(root);

            CssHelper.loadCss(scene);

            modal.setScene(scene);

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
            List<Mensalista> mensalistas = MensalistaDAO.listarTodos();
            if (!mensalistas.isEmpty()) {
                tableMensalistas.getItems().addAll(mensalistas);
            }
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "mensalista");
        }
    }
}
