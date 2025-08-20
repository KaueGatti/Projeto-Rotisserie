package my.company.projetorotisseriejavafx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalEditProdutoController;
import my.company.projetorotisseriejavafx.DAO.ProdutoDAO;
import my.company.projetorotisseriejavafx.Objects.Produto;

import java.io.IOException;
import java.util.List;

public class ProdutosController {

    @FXML
    private ComboBox<String> CBStatus;
    @FXML
    private TextField TFDescricao;
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnPesquisar;

    @FXML
    private TableView<Produto> tableProdutos;
    @FXML
    private TableColumn<Produto, String> colDescricao;
    @FXML
    private TableColumn<Produto, String> colStatus;
    @FXML
    private TableColumn<Produto, Void> colEditar;

    @FXML
    private void initialize() {
        initTableProduto();
        loadStatus();
    }

    @FXML
    void cadastrar(ActionEvent event) {

    }

    @FXML
    void pesquisar(ActionEvent event) {

    }

    private void initTableProduto() {
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colEditar.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");

            {
                btnEditar.setOnAction(event -> {
                    Produto produto = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalEditProduto.fxml"));
                        Stage modal = new Stage();

                        modal.setScene(loader.load());

                        ModalEditProdutoController controller = loader.getController();

                        controller.setProduto(produto);

                        modal.setOnCloseRequest(windowEvent -> {
                            windowEvent.consume();
                        });

                        modal.setResizable(false);
                        modal.initStyle(StageStyle.UTILITY);
                        modal.showAndWait();

                    } catch (IOException e) {
                        System.out.println("Erro ao abrir Editar Produto");
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

        List<Produto> produtos = ProdutoDAO.read();

        if (!produtos.isEmpty()) {
            for (Produto produto : produtos) {
                tableProdutos.getItems().add(produto);
            }
        }
    }

    private void loadStatus() {
        CBStatus.getItems().addAll("Todos", "ATIVO", "INATIVO");
        CBStatus.getSelectionModel().selectFirst();
    }
}
