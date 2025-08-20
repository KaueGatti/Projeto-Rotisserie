/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package my.company.projetorotisseriejavafx.Controller.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import my.company.projetorotisseriejavafx.DAO.MarmitaVendidaDAO;
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
    private TextArea TAEndereco;

    @FXML
    private TextArea TAObservacoes;

    @FXML
    private TextField TFBairro;

    @FXML
    private TextField TFCliente;

    @FXML
    private TextField TFDataHora;

    @FXML
    private TextField TFEntrega;

    @FXML
    private TextField TFMotoboy;

    @FXML
    private TextField TFPagamento;

    @FXML
    private TextField TFStatus;

    @FXML
    private TextField TFTipo;

    @FXML
    private TextField TFTotal;

    @FXML
    private TextField TFValorAPagar;

    @FXML
    private TextField TFValorPago;

    @FXML
    private TextField TFVencimento;

    @FXML
    private Button btnMarmitasEProdutos;

    @FXML
    private Button btnReallizarPagamento;

    @FXML
    private Pane pane;

    @Override
    public void initialize(URL url, ResourceBundle rb){
    }

    @FXML
    void realizarPagamento(ActionEvent event) {

    }

    public void load(Pedido pedido) {
        this.pedido = pedido;
        TFCliente.setText(pedido.getNomeCliente());
        TFTipo.setText(pedido.getTipoPedido());
        TFPagamento.setText(pedido.getTipoPagamento());
        TAEndereco.setText(pedido.getEndereco());
        TFMotoboy.setText(pedido.getMotoboy().getNome());
        TFBairro.setText(pedido.getBairro().getNome());
        TAObservacoes.setText(pedido.getObservacoes());
        TFEntrega.setText(String.valueOf(pedido.getValorEntrega()));
        TFTotal.setText(String.valueOf(pedido.getValorTotal()));
        TFValorPago.setText(String.valueOf(pedido.getValorPago()));
        TFValorAPagar.setText(String.valueOf(pedido.getValorAPagar()));
        TFDataHora.setText(pedido.getDateTime().toString());
        TFVencimento.setText(pedido.getVencimento().toString());
        TFStatus.setText(pedido.getStatus());
        if (pedido.getMensalista() != null) {
            btnReallizarPagamento.setVisible(false);
            btnReallizarPagamento.setDisable(true);
        }
    }

    @FXML
    private void marmitasEProdutos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Pane/paneMarmitasEProdutos.fxml"));
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
}
