package my.company.projetorotisseriejavafx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import my.company.projetorotisseriejavafx.DAO.MarmitaDAO;
import my.company.projetorotisseriejavafx.DAO.ProdutoDAO;
import my.company.projetorotisseriejavafx.Objects.Marmita;
import my.company.projetorotisseriejavafx.Objects.Produto;

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
