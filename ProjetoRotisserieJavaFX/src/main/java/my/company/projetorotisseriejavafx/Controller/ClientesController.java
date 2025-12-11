package my.company.projetorotisseriejavafx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalEditClienteController;
import my.company.projetorotisseriejavafx.DAO.ClienteDAO;
import my.company.projetorotisseriejavafx.Objects.Cliente;
import my.company.projetorotisseriejavafx.Util.CssHelper;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;
import my.company.projetorotisseriejavafx.Util.IconHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ClientesController {

    @FXML
    private Button btnCadastrar;
    @FXML
    private TableView<Cliente> tableCliente;
    @FXML
    private TableColumn<Cliente, String> colNome;
    @FXML
    private TableColumn<Cliente, String> colContato;
    @FXML
    private TableColumn<Cliente, Double> colConta;
    @FXML
    private TableColumn<Cliente, String> colStatus;
    @FXML
    private TableColumn<Cliente, Void> colEditar;

    @FXML
    public void initialize() {
        initTableCliente();
    }

    @FXML
    void cadastrar(ActionEvent event) {
        abrirModalCadastrar();
    }

    private void initTableCliente() {
        colNome.setCellValueFactory(c -> c.getValue().nomeProperty());
        colContato.setCellValueFactory(c -> c.getValue().contatoProperty());
        colConta.setCellValueFactory(c -> c.getValue().contaProperty().asObject());
        colConta.setCellFactory(column -> new TableCell<Cliente, Double>() {
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
        colStatus.setCellValueFactory(c -> c.getValue().statusProperty());
        colEditar.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("");

            {
                btnEditar.setMaxWidth(Double.MAX_VALUE);
                btnEditar.getStyleClass().add("BEditar");
                btnEditar.getStyleClass().add("icon-edit");

                btnEditar.setOnAction(event -> {
                    Cliente cliente = getTableView().getItems().get(getIndex());
                    abrirModalEditar(cliente);
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

        updateTableCliente();
    }

    public void abrirModalCadastrar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalCadastrarCliente.fxml"));

            Parent root = loader.load();

            Stage modal = new Stage();
            Scene scene = new Scene(root);

            CssHelper.loadCss(scene);

            modal.setScene(scene);

            modal.initModality(Modality.APPLICATION_MODAL);
            modal.initStyle(StageStyle.UTILITY);
            modal.showAndWait();
            updateTableCliente();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abrirModalEditar(Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalEditCliente.fxml"));

            Parent root = loader.load();

            Stage modal = new Stage();
            Scene scene = new Scene(root);

            CssHelper.loadCss(scene);

            modal.setScene(scene);

            ModalEditClienteController controller = loader.getController();

            controller.initialize(cliente);

            modal.initModality(Modality.APPLICATION_MODAL);
            modal.initStyle(StageStyle.UTILITY);
            modal.showAndWait();
            updateTableCliente();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTableCliente() {
        tableCliente.getItems().clear();

        try {
            List<Cliente> clientes = ClienteDAO.listarTodos();
            if (!clientes.isEmpty()) {
                tableCliente.getItems().addAll(clientes);
            }
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "cliente");
        }
    }
}
