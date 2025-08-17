/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package my.company.projetorotisseriejavafx.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import my.company.projetorotisseriejavafx.DAO.MarmitaVendidaDAO;
import my.company.projetorotisseriejavafx.DAO.PedidoDAO;
import my.company.projetorotisseriejavafx.DAO.ProdutoVendidoDAO;
import my.company.projetorotisseriejavafx.Objects.Pedido;

/**
 * FXML Controller class
 *
 * @author kaueg
 */
public class PaneDetalhesEntregaController implements Initializable {
    
    private Pedido pedido;
    
    @FXML
    private TextField TFCliente;
    @FXML
    private TextField TFTIpo;
    @FXML
    private TextField TFPagamento;
    @FXML
    private TextArea TAEndereco;
    @FXML
    private TextField TFMotoboy;
    @FXML
    private TextField TFBairro;
    @FXML
    private TextArea TAObservacoes;
    @FXML
    private TextField TFTotal;
    @FXML
    private TextField TFDataHora;
    @FXML
    private ComboBox<String> comboBoxStatus;
    @FXML
    private CheckBox checkBoxMensalista;
    @FXML
    private TextField TFEntrega;
    @FXML
    private Button btnSalvar;
    @FXML
    private Pane pane;
    @FXML
    private Button btnMarmitasEProdutos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initStatus();
        btnSalvar.setDisable(true);
    }
    
    public void load(Pedido pedido) {
        this.pedido = pedido;
        TFCliente.setText(pedido.getNomeCliente());
        TFTIpo.setText(pedido.getTipoPedido());
        TFPagamento.setText(pedido.getTipoPagamento());
        TAEndereco.setText(pedido.getEndereco());
        TFMotoboy.setText(pedido.getMotoboy().getNome());
        TFBairro.setText(pedido.getBairro().getNome());
        TAObservacoes.setText(pedido.getObservacoes());
        TFTotal.setText(String.valueOf(pedido.getValorTotal()));
        TFDataHora.setText(pedido.getDateTime().toString());
        comboBoxStatus.getSelectionModel().select(pedido.getStatus());
    }
    
    @FXML
    private void marmitasEProdutos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/paneMarmitasEProdutos.fxml"));
            Pane paneMarmitasEProdutos = loader.load();
            PaneMarmitasEProdutosController controller = loader.getController();
            
            controller.loadMarmitasEProdutos(MarmitaVendidaDAO.read(pedido.getId()), ProdutoVendidoDAO.read(pedido.getId()));
            
            pane.setOpacity(0);
            ((Pane) pane.getParent()).getChildren().add(paneMarmitasEProdutos);
        } catch (IOException e) {
            System.out.println("Erro ao carregar paneMarmitasEProdutos:");
            e.printStackTrace();
        }
    }
    
    @FXML
    private void salvar(ActionEvent event) {
        pedido.setStatus(comboBoxStatus.getSelectionModel().getSelectedItem());
        PedidoDAO.update(pedido);
        btnSalvar.setDisable(true);
    }
    
    public void initStatus() {
        comboBoxStatus.getItems().addAll("FINALIZADO", "PENDENTE");
        comboBoxStatus.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.equals(pedido.getStatus())) {
                btnSalvar.setDisable(false);
            } else {
                btnSalvar.setDisable(true);
            }
        });
    }
    
}
