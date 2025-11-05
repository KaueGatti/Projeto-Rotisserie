package my.company.projetorotisseriejavafx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalCadastrarProdutoController;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalEditProdutoController;
import my.company.projetorotisseriejavafx.DAO.ProdutoDAO;
import my.company.projetorotisseriejavafx.Objects.Produto;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ProdutosController {

    @FXML
    private Button btnCadastrar;

    @FXML
    private TableView<Produto> tableProdutos;
    @FXML
    private TableColumn<Produto, String> colNome;
    @FXML
    private TableColumn<Produto, String> colValor;
    @FXML
    private TableColumn<Produto, String> colStatus;
    @FXML
    private TableColumn<Produto, Void> colEditar;

    @FXML
    private void initialize() {
        initTableProduto();
    }

    @FXML
    void cadastrar(ActionEvent event) {
        abrirModalCadastrar();
    }

    private void initTableProduto() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("formattedValor"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colEditar.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");

            {
                btnEditar.setOnAction(event -> {
                    Produto produto = getTableView().getItems().get(getIndex());
                    abrirModalEditar(produto);
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

        updateTableProduto();
    }

    public void abrirModalEditar(Produto produto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalEditProduto.fxml"));
            Stage modal = new Stage();

            modal.setScene(loader.load());

            ModalEditProdutoController controller = loader.getController();

            controller.initialize(produto);

            modal.initStyle(StageStyle.UTILITY);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setResizable(false);
            modal.showAndWait();
            updateTableProduto();

        } catch (IOException e) {
            System.out.println("Erro ao abrir Editar Produto");
            e.printStackTrace();
        }
    }

    public void abrirModalCadastrar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalCadastrarProduto.fxml"));
            Stage modal = new Stage();

            modal.setScene(loader.load());

            ModalCadastrarProdutoController controller = loader.getController();

            controller.initialize();

            modal.initStyle(StageStyle.UTILITY);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setResizable(false);
            modal.showAndWait();
            updateTableProduto();

        } catch (IOException e) {
            System.out.println("Erro ao abrir Cadastro de Produto");
            e.printStackTrace();
        }
    }

    public void updateTableProduto() {
        tableProdutos.getItems().clear();

        try {
            List<Produto> produtos = ProdutoDAO.read();

            if (!produtos.isEmpty()) {
                tableProdutos.getItems().addAll(produtos);
            }
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "produto");
        }
    }
}
