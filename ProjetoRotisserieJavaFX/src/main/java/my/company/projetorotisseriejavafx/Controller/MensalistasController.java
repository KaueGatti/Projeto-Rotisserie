package my.company.projetorotisseriejavafx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalEditProdutoController;
import my.company.projetorotisseriejavafx.DAO.MensalistaDAO;
import my.company.projetorotisseriejavafx.DAO.ProdutoDAO;
import my.company.projetorotisseriejavafx.Objects.Mensalista;
import my.company.projetorotisseriejavafx.Objects.Produto;

import java.io.IOException;
import java.util.List;

public class MensalistasController {

    @FXML
    private ComboBox<String> CBStatus;

    @FXML
    private TextField TFNome;

    @FXML
    private Button btnCadastrar;


    @FXML
    private Button btnPesquisar;

    @FXML
    private TableView<Mensalista> tableMensalistas;
    @FXML
    private TableColumn<Mensalista, String> colNome;
    @FXML
    private TableColumn<Mensalista, String> colStatus;
    @FXML
    private TableColumn<Mensalista, Void> colPedidos;
    @FXML
    private TableColumn<Mensalista, Void> colEditar;


    @FXML
    private void initialize() {
        initTableMensalistas();
        loadStatus();
    }

    @FXML
    void cadastrar(ActionEvent event) {

    }

    @FXML
    void pesquisar(ActionEvent event) {

    }

    private void initTableMensalistas() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        /*colEditar.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");

            {
                btnEditar.setOnAction(event -> {
                    Mensalista mensalista = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalEditMensalista.fxml"));
                        Stage modal = new Stage();

                        modal.setScene(loader.load());

                        //ModalEditProdutoController controller = loader.getController();

                        //controller.setProduto(mensalista);

                        modal.setOnCloseRequest(windowEvent -> {
                            windowEvent.consume();
                        });

                        modal.setResizable(false);
                        modal.initStyle(StageStyle.UTILITY);
                        modal.showAndWait();

                    } catch (IOException e) {
                        System.out.println("Erro ao abrir Editar Mensalista:");
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
        });*/
        /*colPedidos.setCellFactory(param -> new TableCell<>() {
            private final Button btnPedidos = new Button("Pedidos");

            {
                btnPedidos.setOnAction(event -> {
                    Mensalista mensalista = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MensalistaPedidos.fxml"));
                        Stage modal = new Stage();

                        modal.setScene(loader.load());

                        //ModalEditProdutoController controller = loader.getController();

                        //controller.setProduto(mensalista);

                        modal.setOnCloseRequest(windowEvent -> {
                            windowEvent.consume();
                        });

                        modal.setResizable(false);
                        modal.initStyle(StageStyle.UTILITY);
                        modal.showAndWait();

                    } catch (IOException e) {
                        System.out.println("Erro ao abrir Mensalista Pedidos:");
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
                    setGraphic(btnPedidos);
                }
            }
        });*/

        List<Mensalista> mensalistas = MensalistaDAO.read();

        if (!mensalistas.isEmpty()) {
            for (Mensalista mensalista : mensalistas) {
                tableMensalistas.getItems().add(mensalista);
            }
        }
    }

    private void loadStatus() {
        CBStatus.getItems().addAll("Todos", "ATIVO", "INATIVO");
        CBStatus.getSelectionModel().selectFirst();
    }
}
