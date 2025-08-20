/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package my.company.projetorotisseriejavafx.Controller.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalDetalhesMarmitaController;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalDetalhesProdutoController;
import my.company.projetorotisseriejavafx.Objects.MarmitaVendida;
import my.company.projetorotisseriejavafx.Objects.ProdutoVendido;

/**
 * FXML Controller class
 *
 * @author kaueg
 */
public class PaneMarmitasEProdutosController implements Initializable {

    @FXML
    private Button btnVoltar;
    @FXML
    private TableView<MarmitaVendida> tableMarmita;
    @FXML
    private TableColumn<MarmitaVendida, String> colDescricaoMarmita;
    @FXML
    private TableColumn<MarmitaVendida, Double> colSubtotalMarmita;
    @FXML
    private TableView<ProdutoVendido> tableProduto;
    @FXML
    private TableColumn<ProdutoVendido, String> colDescricaoProduto;
    @FXML
    private TableColumn<ProdutoVendido, Integer> colQuantidadeProduto;
    @FXML
    private TableColumn<ProdutoVendido, Double> colSubtotalProduto;
    @FXML
    private Pane pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTableMarmita();
        initTableProduto();
    }

    @FXML
    private void voltar(ActionEvent event) {
        Pane paneDetalhes = (Pane) pane.getParent();
        paneDetalhes.getChildren().remove(pane);
        for (Node node : paneDetalhes.getChildren()) {
            node.setOpacity(1);
        }
    }

    public void loadMarmitasEProdutos(List<MarmitaVendida> marmitas, List<ProdutoVendido> produtos) {
        if (marmitas != null) {
            for (MarmitaVendida marmita : marmitas) {
                tableMarmita.getItems().add(marmita);
            }
        }

        if (produtos != null) {
            for (ProdutoVendido produto : produtos) {
                tableProduto.getItems().add(produto);
            }
        }
    }

    private void initTableMarmita() {
        colDescricaoMarmita.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colSubtotalMarmita.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        tableMarmita.setOnMouseClicked(event -> {
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

    private void initTableProduto() {
        colDescricaoProduto.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colQuantidadeProduto.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colSubtotalProduto.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        tableProduto.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                if (tableProduto.getSelectionModel().getSelectedItem() != null) {
                    try {
                        Stage modal = new Stage();

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalDetalhesProduto.fxml"));

                        modal.setScene(loader.load());

                        ModalDetalhesProdutoController controller = loader.getController();

                        controller.load(tableProduto.getSelectionModel().getSelectedItem());

                        modal.setOnCloseRequest(eventClose -> {
                            event.consume();
                        });

                        modal.setResizable(false);
                        modal.initStyle(StageStyle.UTILITY);
                        modal.showAndWait();

                    } catch (IOException e) {
                        System.out.println("Erro Modal Detalhes Produto:");
                        e.printStackTrace();
                    }
                }
            }
        }
        );
    }
}
