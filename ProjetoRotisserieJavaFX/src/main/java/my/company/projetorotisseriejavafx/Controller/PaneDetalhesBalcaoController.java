/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package my.company.projetorotisseriejavafx.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import my.company.projetorotisseriejavafx.Objects.Pedido;

/**
 * FXML Controller class
 *
 * @author kaueg
 */
public class PaneDetalhesBalcaoController implements Initializable {

    @FXML
    private TextField TFCliente;
    @FXML
    private CheckBox checkBoxMensalista;
    @FXML
    private TextField TFTipo;
    @FXML
    private TextField TFPagamento;
    @FXML
    private TextArea TAObservacoes;
    @FXML
    private Button btnMarmitas;
    @FXML
    private Button btnProdutos;
    @FXML
    private TextField TFEntrega;
    @FXML
    private TextField TFTotal;
    @FXML
    private TextField TFDataHora;
    @FXML
    private ComboBox<String> comboBoxStatus;
    @FXML
    private Button btnSalvar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    public void load(Pedido pedido) {
        TFCliente.setText(pedido.getNomeCliente());
        TFTipo.setText(pedido.getTipoPedido());
        TFPagamento.setText(pedido.getTipoPagamento());
        TAObservacoes.setText(pedido.getObservacoes());
        TFTotal.setText(String.valueOf(pedido.getValorTotal()));
        TFDataHora.setText(pedido.getDateTime().toString());
        comboBoxStatus.getSelectionModel().select(pedido.getStatus());
    }

}
