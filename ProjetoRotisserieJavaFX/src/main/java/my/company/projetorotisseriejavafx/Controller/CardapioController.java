package my.company.projetorotisseriejavafx.Controller;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalDetalhesMarmitaController;
import my.company.projetorotisseriejavafx.Objects.ItemCardapio;
import my.company.projetorotisseriejavafx.Objects.MarmitaVendida;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CardapioController implements Initializable {

    ObservableList<ItemCardapio> principais = FXCollections.observableArrayList();
    ObservableList<ItemCardapio> misturas = FXCollections.observableArrayList();
    ObservableList<ItemCardapio> guarnicoes = FXCollections.observableArrayList();
    ObservableList<ItemCardapio> saladas = FXCollections.observableArrayList();

    @FXML
    private ComboBox<ItemCardapio> CBGuarnicao1;

    @FXML
    private ComboBox<ItemCardapio> CBGuarnicao2;

    @FXML
    private ComboBox<ItemCardapio> CBGuarnicao3;

    @FXML
    private ComboBox<ItemCardapio> CBGuarnicao4;

    @FXML
    private ComboBox<ItemCardapio> CBMistura1;

    @FXML
    private ComboBox<ItemCardapio> CBMistura2;

    @FXML
    private ComboBox<ItemCardapio> CBMistura3;

    @FXML
    private ComboBox<ItemCardapio> CBMistura4;

    @FXML
    private ComboBox<ItemCardapio> CBPrincipal1;

    @FXML
    private ComboBox<ItemCardapio> CBPrincipal2;

    @FXML
    private ComboBox<ItemCardapio> CBSalada1;

    @FXML
    private ComboBox<ItemCardapio> CBSalada2;

    @FXML
    private Button btnAdicionarGuarnicao;

    @FXML
    private Button btnAdicionarMistura;

    @FXML
    private Button btnAdicionarPrincipal;

    @FXML
    private Button btnAdicionarSalada;

    @FXML
    private Button btnAtualizar;

    @FXML
    private TableColumn<ItemCardapio, String> colNomeGuarnicao;

    @FXML
    private TableColumn<ItemCardapio, String> colNomeMistura;

    @FXML
    private TableColumn<ItemCardapio, String> colNomePrincipal;

    @FXML
    private TableColumn<ItemCardapio, String> colNomeSalada;

    @FXML
    private TableView<ItemCardapio> tableGuarnicoes;

    @FXML
    private TableView<ItemCardapio> tableMisturas;

    @FXML
    private TableView<ItemCardapio> tablePrincipais;

    @FXML
    private TableView<ItemCardapio> tableSaladas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTables();
    }

    @FXML
    void atualizar(ActionEvent event) {

    }

    public void initTables() {

    };

    public void initTablePrincipais() {
        colNomePrincipal.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDelPrincipal.setCellFactory(param -> new TableCell<>() {
            private final Button btnExcluir = new Button("Excluir");

            {
                btnExcluir.setOnAction(event -> {
                    MarmitaVendida marmita = getTableView().getItems().get(getIndex());
                    valorTotal -= marmita.getSubtotal();
                    getTableView().getItems().remove(marmita);
                    atualizaValor();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnExcluir);
                }
            }
        });

        tablePrincipais.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2) {
                        if (tableMarmita.getSelectionModel().getSelectedItem() != null) {
                            try {
                                Stage modal = new Stage();

                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalDetalhesMarmita.fxml"));

                                modal.setScene(loader.load());

                                ModalDetalhesMarmitaController controller = loader.getController();

                                controller.load(tableMarmita.getSelectionModel().getSelectedItem());

                                modal.setOnCloseRequest(eventClose -> {
                                    event.consume();
                                });
                                modal.setResizable(false);
                                modal.initStyle(StageStyle.UTILITY);
                                modal.showAndWait();

                            } catch (IOException e) {
                                System.out.println("Erro Modal Detalhes Marmita:");
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
    }


}
