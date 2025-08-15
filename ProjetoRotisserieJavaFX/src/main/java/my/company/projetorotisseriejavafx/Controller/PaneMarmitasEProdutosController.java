/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package my.company.projetorotisseriejavafx.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
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
    }

    private void initTableProduto() {
        colDescricaoProduto.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colQuantidadeProduto.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colSubtotalProduto.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
    }
}
